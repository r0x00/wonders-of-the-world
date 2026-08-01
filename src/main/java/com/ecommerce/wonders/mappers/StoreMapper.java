package com.ecommerce.wonders.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.wonders.dto.StoreDto.CreateStore;
import com.ecommerce.wonders.dto.StoreDto.ResponseStore;
import com.ecommerce.wonders.dto.StoreDto.UpdateStore;
import com.ecommerce.wonders.model.Store;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    @Mapping(target = "userId", source = "user.id")
    ResponseStore toDto(Store store);

    Store toEntity(ResponseStore responseStore);

    Store toEntityFromCreateDto(CreateStore createStore);

    void updateEntityFromDto(UpdateStore updateStore, @MappingTarget Store existingStore);
}
