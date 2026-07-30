package com.ecommerce.wonders.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class AddressDto {
    private AddressDto() {}

    // Address Create DTO
    public record CreateAddress(
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
        String name,

        @NotBlank(message = "Street Address cannot be empty")
        @Size(min = 3, max = 300, message = "Street Address must be between 3 and 300 characters")
        String streetAddress,

        @NotBlank(message = "City cannot be empty")
        @Size(min= 2, max = 100, message = "City must be between 2 and 100 characters")
        String city,

        @NotBlank(message = "State cannot be empty")
        @Size(min= 2, max = 60, message = "State must be between 2 and 60 characters")
        String state,

        @NotBlank(message = "Country cannot be empty")
        @Size(min= 2, max = 60, message = "Country must be between 2 and 60 characters")
        String country,

        @NotBlank(message = "Zip Code cannot be empty")
        @Size(min= 3, max = 15, message = "Zip Code must be between 3 and 15 characters")
        String zipCode
    ){}

    // Address Update DTO
    public record UpdateAddress(
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
        String name,

        @NotBlank(message = "Street Address cannot be empty")
        @Size(min = 3, max = 300, message = "Street Address must be between 3 and 100 characters")
        String streetAddress,

        @NotBlank(message = "City cannot be empty")
        @Size(min= 2, max = 100, message = "City must be between 2 and 100 characters")
        String city,

        @NotBlank(message = "State cannot be empty")
        @Size(min= 2, max = 60, message = "State must be between 2 and 60 characters")
        String state,

        @NotBlank(message = "Country cannot be empty")
        @Size(min= 2, max = 60, message = "Country must be between 2 and 60 characters")
        String country,

        @NotBlank(message = "Zip Code cannot be empty")
        @Size(min= 3, max = 15, message = "Zip Code must be between 3 and 15 characters")
        String zipCode
    ){}

    // Address Response DTO
    public record ReponseAddress(
        Long id,
        String name,
        String streetAddress,
        String city,
        String state,
        String country,
        String zipCode
    ) {}

    // Address Response Get All DTO
    public record ResponseAddressGetAll(
        List<ReponseAddress> values,
        Long count
    ) {}
    
}
