package com.ecommerce.wonders.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.wonders.dto.StockDto.ResponseStock;
import com.ecommerce.wonders.dto.StockDto.ResponseStockGetAll;
import com.ecommerce.wonders.dto.StockDto.UpdateStock;
import com.ecommerce.wonders.services.StockService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;

@RestController
@RequestMapping("/stock")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }


    @GetMapping("store/{storeId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER') and @storeService.isUserStoreOwner(#storeId, authentication.principal.id)")
    public ResponseEntity<ResponseStockGetAll> getAllStocksFromStore(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") @Max(value = 200, message = "Size must be less than 200") int size,
        @PathVariable Long storeId
    ) {
        ResponseStockGetAll result = this.stockService.getAllStocksFromStore(storeId, page, size);

        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER') and @stockService.isUserStockOwner(#id, authentication.principal.id)")
    public ResponseEntity<ResponseStock> getStockById(
        @PathVariable Long id
    ) {
        ResponseStock result = this.stockService.getStockById(id);

        return ResponseEntity.ok(result);
    }


    @GetMapping("product/{productId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER') and @productService.isUserProductOwner(#productId, authentication.principal.id)")
    public ResponseEntity<ResponseStock> getStockByProductId(
        @PathVariable Long productId
    ) {
        ResponseStock result = this.stockService.getStockByProductId(productId);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER') and @stockService.isUserStockOwner(#id, authentication.principal.id)")
    public void updateStock(
        @PathVariable Long id,
        @Valid @RequestBody UpdateStock rawJson
    ) {
        this.stockService.updateStock(id, rawJson);
    }
    
}
