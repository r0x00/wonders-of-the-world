package com.ecommerce.wonders.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.ecommerce.wonders.dto.AddressDto.CreateAddress;
import com.ecommerce.wonders.dto.AddressDto.ReponseAddress;
import com.ecommerce.wonders.dto.AddressDto.UpdateAddress;
import com.ecommerce.wonders.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    ReponseAddress toDto(Address address);

    Address toEntity(ReponseAddress responseAddress);

    Address toEntityFromCreateADto(CreateAddress createAddress);

    void updateEntityFromDto(UpdateAddress updateAddress, @MappingTarget Address existingAddress);
}
