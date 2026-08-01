package com.ecommerce.wonders.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.ecommerce.wonders.dto.UserDto.CreateUser;
import com.ecommerce.wonders.dto.UserDto.ResponseUser;
import com.ecommerce.wonders.dto.UserDto.UpdateUser;
import com.ecommerce.wonders.dto.UserDto.UpdateUserPermission;
import com.ecommerce.wonders.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ResponseUser toDto(User user);
    
    User toEntity(ResponseUser responseUser);

    User toEntityFromCreateDto(CreateUser createUser);

    void updateEntityFromDto(UpdateUser updateUser, @MappingTarget User existingUser);

    void updateEntityFromUpdatePermissionDto(UpdateUserPermission updateUserPermission, @MappingTarget User existingUser);
}
