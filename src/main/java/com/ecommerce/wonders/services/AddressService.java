package com.ecommerce.wonders.services;

import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import com.ecommerce.wonders.dto.AddressDto.CreateAddress;
import com.ecommerce.wonders.dto.AddressDto.ReponseAddress;
import com.ecommerce.wonders.dto.AddressDto.ResponseAddressGetAll;
import com.ecommerce.wonders.dto.AddressDto.UpdateAddress;
import com.ecommerce.wonders.dto.UserDto.ResponseUser;
import com.ecommerce.wonders.exception.BadRequestException;
import com.ecommerce.wonders.mappers.AddressMapper;
import com.ecommerce.wonders.mappers.UserMapper;
import com.ecommerce.wonders.model.Address;
import com.ecommerce.wonders.model.Address_;
import com.ecommerce.wonders.model.User;
import com.ecommerce.wonders.repository.AddressRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

    public AddressService(
        AddressRepository addressRepository, 
        UserService userService, 
        UserMapper userMapper,
        AddressMapper addressMapper
    ) {
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
    }

    public ReponseAddress getAddress(Long id, Long userId) {
        Address address = this.addressRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new BadRequestException("Address not found with ID: " + id));

        ReponseAddress result = this.addressMapper.toDto(address);

        return result;
    }

    public ResponseAddressGetAll getAllAddresses(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, JpaSort.of(Address_.updatedAt).descending());

        Page<Address> addresses = this.addressRepository.getAllByUserId(userId, pageable); 

        List<ReponseAddress> values = addresses.stream()
            .map(address -> this.addressMapper.toDto(address))
            .toList();

        Long count = this.addressRepository.count();

        ResponseAddressGetAll result = new ResponseAddressGetAll(values, count);
        
        return result;
    }

    public void createAddress(Long userId, CreateAddress rawJson) {
        ResponseUser checkUser = this.userService.getUserById(userId);

        if(checkUser == null) {
            throw new BadRequestException("User not found with ID: " + userId);
        }

        Address address = this.addressMapper.toEntityFromCreateADto(rawJson);
        
        User user = this.userMapper.toEntity(checkUser);
        address.setUser(user);

        this.addressRepository.save(address);
    }

    public void updateAddress(Long id, Long userId, UpdateAddress rawJson) {
        Address address = this.addressRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new BadRequestException("Address not found with ID: " + id));

        this.addressMapper.updateEntityFromDto(rawJson, address);

        this.addressRepository.save(address);
    }

    public void deleteAddress(Long id, Long userId) {
        this.addressRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new BadRequestException("Address not found with ID: " + id));

        this.addressRepository.deleteById(id);
    }
}
