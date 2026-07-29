package com.ecommerce.wonders.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.wonders.dto.ProductDto.CreateProduct;
import com.ecommerce.wonders.dto.ProductDto.ResponseProduct;
import com.ecommerce.wonders.dto.ProductDto.UpdateProduct;
import com.ecommerce.wonders.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "storeId", source = "store.id")
    @Mapping(target = "stockId", source = "stock.id")
    ResponseProduct toDto(Product product);

    Product toEntity(ResponseProduct responseProduct);

    Product toEntityFromCreateDto(CreateProduct createProduct);

    void updateEntityFromDto(UpdateProduct updateProduct, @MappingTarget Product existingProduct);
}
