package com.ecommerce.wonders.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.wonders.dto.ProductDto.CreateProduct;
import com.ecommerce.wonders.dto.ProductDto.ResponseProduct;
import com.ecommerce.wonders.dto.ProductDto.ResponseProductGetAll;
import com.ecommerce.wonders.dto.ProductDto.UpdateProduct;
import com.ecommerce.wonders.enums.EnumCategory;
import com.ecommerce.wonders.services.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ResponseProductGetAll> getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") @Max(value = 200, message = "Size must be less than 200") int size
    ) {
        ResponseProductGetAll result = this.productService.getAllProducts(page, size);

        return ResponseEntity.ok(result);   
    }

    @GetMapping("store/{storeId}")
    public ResponseEntity<ResponseProductGetAll> getAllProductsFromStore(
        @PathVariable Long storeId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") @Max(value = 200, message = "Size must be less than 200") int size
    ) {
        ResponseProductGetAll result = this.productService.getAllProductsFromStoreId(storeId, page, size);

        return ResponseEntity.ok(result);   
    }

    @GetMapping("category/{category}")
    public ResponseEntity<ResponseProductGetAll> getAllProductsFromCategory(
        @PathVariable EnumCategory category,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") @Max(value = 200, message = "Size must be less than 200") int size
    ) {
        ResponseProductGetAll result = this.productService.getAllProductsFromCategory(category, page, size);

        return ResponseEntity.ok(result);   
    }


    @GetMapping("{id}")
    public ResponseEntity<ResponseProduct> getProductById(
        @PathVariable Long id
    ) {
        ResponseProduct result = this.productService.getProductById(id);

        return ResponseEntity.ok(result);   
    }

    @PostMapping("store/{storeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public void createProduct(
        @PathVariable Long storeId, 
        @Valid @RequestBody CreateProduct rawJson
    ) {
        this.productService.createProduct(storeId, rawJson);
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER') and @productService.isUserProductOwner(#id, authentication.principal.id)")
    public void updateProduct(
        @PathVariable Long id, 
        @Valid @RequestBody UpdateProduct rawJson
    ) {
        this.productService.updateProduct(id, rawJson);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER') and @productService.isUserProductOwner(#id, authentication.principal.id)")  
    public void deleteProduct(
        @PathVariable Long id
    ) {
        this.productService.deleteProduct(id);
    }
}
