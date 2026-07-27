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
import com.ecommerce.wonders.exception.BadRequestException;
import com.ecommerce.wonders.mappers.StoreMapper;
import com.ecommerce.wonders.model.Store;
import com.ecommerce.wonders.model.Store_;
import com.ecommerce.wonders.repository.StoreRepository;

@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreService(
        StoreRepository storeRepository,
        StoreMapper storeMapper
    ) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
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

    public ResponseStore getStore(Long id) {
        Store store = this.storeRepository.findById(id).orElseThrow(() -> new BadRequestException("Store not found with ID: " + id));

        ResponseStore result = this.storeMapper.toDto(store);

        return result;
    }

    public void createStore(CreateStore createStore) {
        Store store = this.storeMapper.toEntityFromCreateDto(createStore);

        this.storeRepository.save(store);
    }

    public void updateStore(Long id, UpdateStore updateStore) {
        Store store = this.storeRepository.findById(id).orElseThrow(() -> new BadRequestException("Store not found with ID: " + id));

        this.storeMapper.updateEntityFromDto(updateStore, store);

        this.storeRepository.save(store);
    }

    public void deleteStore(Long id) {
        this.storeRepository.findById(id).orElseThrow(() -> new BadRequestException("Store not found with ID: " + id));

        this.storeRepository.deleteById(id);
    }
}
