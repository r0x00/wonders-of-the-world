package com.ecommerce.wonders.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.wonders.dto.AddressDto.CreateAddress;
import com.ecommerce.wonders.dto.AddressDto.ReponseAddress;
import com.ecommerce.wonders.dto.AddressDto.ResponseAddressGetAll;
import com.ecommerce.wonders.dto.AddressDto.UpdateAddress;
import com.ecommerce.wonders.services.AddressService;

import jakarta.validation.constraints.Max;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<ResponseAddressGetAll> getAllAddresses(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") @Max(value = 200, message = "Size must be less than 200") int size,
        @PathVariable Long userId
        
    ) {
        ResponseAddressGetAll result = this.addressService.getAllAddresses(userId, page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("{userId}/{id}")
    public ResponseEntity<ReponseAddress> getAddress(
        @PathVariable Long id,
        @PathVariable Long userId
    ) {
        ReponseAddress result = this.addressService.getAddress(id, userId);

        return ResponseEntity.ok(result);
    }

    @PostMapping("{userId}")
    public void createAddress(
        @PathVariable Long userId,
        @RequestBody CreateAddress rawJson
    ) {
        this.addressService.createAddress(userId, rawJson);
    }

    @PatchMapping("{userId}/{id}")
    public void updateAddress(
        @PathVariable Long id,
        @PathVariable Long userId,
        @RequestBody UpdateAddress rawJson
    ){
        this.addressService.updateAddress(id, userId, rawJson);
    }

    @DeleteMapping("{userId}/{id}")
    public void deleteAddress(
        @PathVariable Long id,
        @PathVariable Long userId
    ){
        this.addressService.deleteAddress(id, userId);
    }
}
