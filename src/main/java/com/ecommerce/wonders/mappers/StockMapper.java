package com.ecommerce.wonders.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.wonders.dto.StockDto.ResponseStock;
import com.ecommerce.wonders.dto.StockDto.UpdateStock;
import com.ecommerce.wonders.model.Stock;

@Mapper(componentModel = "spring")
public interface StockMapper {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "storeId", source = "store.id")
    ResponseStock toDto(Stock stock);

    Stock toEntity(ResponseStock responseStock);

    void updateEntityFromDto(UpdateStock updateStock, @MappingTarget Stock existingStock);
}
