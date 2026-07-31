package com.ecommerce.wonders.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import com.ecommerce.wonders.model.User;
import com.ecommerce.wonders.services.AddressService;

import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<ResponseAddressGetAll> getAllAddresses(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") @Max(value = 200, message = "Size must be less than 200") int size,
        Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        ResponseAddressGetAll result = this.addressService.getAllAddresses(userId, page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReponseAddress> getAddress(
        @PathVariable Long id,
        Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        ReponseAddress result = this.addressService.getAddress(id, userId);

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public void createAddress(
        @Valid @RequestBody CreateAddress rawJson,
        Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        this.addressService.createAddress(userId, rawJson);
    }

    @PatchMapping("{id}")
    public void updateAddress(
        @PathVariable Long id,
        @Valid @RequestBody UpdateAddress rawJson,
        Authentication authentication
    ){
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        this.addressService.updateAddress(id, userId, rawJson);
    }

    @DeleteMapping("{id}")
    public void deleteAddress(
        @PathVariable Long id,
        Authentication authentication
    ){
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();


        this.addressService.deleteAddress(id, userId);
    }
}
