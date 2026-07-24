package com.ecommerce.wonders.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class UserDto {
    private UserDto() {}

    // User Create DTO 
    public record CreateUser(
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "address cannot be empty")
        String address
    ) {}


    // User Update DTO
    public record UpdateUser(
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name, 

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        String email
    ) {}

    public record ResponseUser(        
        Long id,
        String name,
        String email
    ) {}


    public record ResponseUserGetAll(        
        List<ResponseUser> values,
        Long count
    ) {}

}
