package com.ecommerce.wonders.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.wonders.dto.StoreDto.CreateStore;
import com.ecommerce.wonders.dto.StoreDto.ResponseStore;
import com.ecommerce.wonders.dto.StoreDto.ResponseStoreGetAll;
import com.ecommerce.wonders.dto.StoreDto.UpdateStore;
import com.ecommerce.wonders.services.StoreService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;

@RestController
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public ResponseEntity<ResponseStoreGetAll> getAllStores(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") @Max(value = 200, message = "Size must be less than 200") int size
    ) {
        ResponseStoreGetAll result = this.storeService.getAllStores(page, size);

        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseStore> getStoreById(
        @PathVariable Long id
    ) {
        ResponseStore result = this.storeService.getStoreById(id);

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public void createStore(
        @Valid @RequestBody CreateStore rawJson
    ) {
        this.storeService.createStore(rawJson);
    }

    @PatchMapping("{id}")
    public void updateStore(
        @PathVariable Long id,
        @Valid @RequestBody UpdateStore rawJson
    ) {
        this.storeService.updateStore(id, rawJson);
    }

    @DeleteMapping("{id}")
    public void deleteStore(
        @PathVariable Long id
    ) {
        this.storeService.deleteStore(id);
    }
}
