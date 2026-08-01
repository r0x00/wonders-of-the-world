package com.ecommerce.wonders.dto;

import java.util.List;

import com.ecommerce.wonders.enums.EnumUserPermission;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public final class UserDto {
    private UserDto() {}

    // User Create DTO 
    public record CreateUser(
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
        String name,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!#@&]{8,100}$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
        String password
    ) {}


    // User Update DTO
    public record UpdateUser(
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
        String name, 

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Invalid email format")
        String email
    ) {}


    // User Update Password DTO
    public record UpdateUserPassword(
        @NotBlank(message = "Password cannot be empty")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!#@&]{8,100}$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
        String password, 

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!#@&]{8,100}$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
        String currentPassword
    ) {}

    // User Update Password DTO
    public record UpdateUserPermission(
        @NotNull(message = "Permission cannot be empty")
        EnumUserPermission permission
    ) {}

    // User Response DTO
    public record ResponseUser(        
        Long id,
        String name,
        String email,
        EnumUserPermission permission
    ) {}

    // User Response Get All DTO
    public record ResponseUserGetAll(        
        List<ResponseUser> values,
        Long count
    ) {}

}
