package com.ecommerce.wonders.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import com.ecommerce.wonders.dto.StoreDto.CreateStore;
import com.ecommerce.wonders.dto.StoreDto.ResponseStore;
import com.ecommerce.wonders.dto.StoreDto.ResponseStoreGetAll;
import com.ecommerce.wonders.dto.StoreDto.UpdateStore;
import com.ecommerce.wonders.dto.UserDto.ResponseUser;
import com.ecommerce.wonders.dto.UserDto.UpdateUserPermission;
import com.ecommerce.wonders.enums.EnumUserPermission;
import com.ecommerce.wonders.exception.BadRequestException;
import com.ecommerce.wonders.mappers.StoreMapper;
import com.ecommerce.wonders.mappers.UserMapper;
import com.ecommerce.wonders.model.Store;
import com.ecommerce.wonders.model.Store_;
import com.ecommerce.wonders.model.User;
import com.ecommerce.wonders.repository.StoreRepository;

@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public StoreService(
        StoreRepository storeRepository,
        StoreMapper storeMapper,
        UserService userService,
        UserMapper userMapper
    ) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public ResponseStoreGetAll getAllStores(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, JpaSort.of(Store_.updatedAt).descending());

        Page<Store> stores = this.storeRepository.findAll(pageable);

        List<ResponseStore> values = stores.stream()
            .map(store -> this.storeMapper.toDto(store))
            .toList();

        Long count = stores.getTotalElements();

        ResponseStoreGetAll result = new ResponseStoreGetAll(values, count);

        return result;
    }

    public ResponseStore getStoreById(Long id) {
        Store store = this.storeRepository.findById(id).orElseThrow(() -> new BadRequestException("Store not found with ID: " + id));

        ResponseStore result = this.storeMapper.toDto(store);

        return result;
    }

    public boolean isUserStoreOwner(Long id, Long userId) {
        Store store = this.storeRepository.findById(id).orElseThrow(() -> new BadRequestException("Store not found with ID: " + id));

        boolean result = store.getUser().getId().equals(userId);

        return result;
    }

    public void createStore(CreateStore createStore) {
        ResponseUser user = this.userService.getUserById(createStore.userId());

        if(user == null) {
            throw new BadRequestException("User not found with ID: " + createStore.userId());
        }

        Store existingStore = this.storeRepository.findByUserId(createStore.userId()).orElse(null);

        if(existingStore != null) {
            throw new BadRequestException("User already have a store owner");
        }

        this.userService.updateUserPermission(createStore.userId(), new UpdateUserPermission(EnumUserPermission.ROLE_SELLER));

        Store store = this.storeMapper.toEntityFromCreateDto(createStore);

        User seller = userMapper.toEntity(user);

        store.setUser(seller);

        this.storeRepository.save(store);
    }

    public void updateStore(Long id, UpdateStore updateStore) {
        Store store = this.storeRepository.findById(id).orElseThrow(() -> new BadRequestException("Store not found with ID: " + id));

        this.storeMapper.updateEntityFromDto(updateStore, store);

        this.storeRepository.save(store);
    }

    public void deleteStore(Long id) {
        Store store = this.storeRepository.findById(id).orElseThrow(() -> new BadRequestException("Store not found with ID: " + id));

        Long userId = store.getUser().getId();

        this.storeRepository.deleteById(id);

        this.userService.updateUserPermission(userId, new UpdateUserPermission(EnumUserPermission.ROLE_CUSTOMER));
    }
}
