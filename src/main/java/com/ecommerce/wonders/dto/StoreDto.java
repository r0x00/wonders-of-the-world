package com.ecommerce.wonders.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public final class StoreDto {
    private StoreDto() {}

    // Store Create DTO
    public record CreateStore(
        @NotEmpty(message = "Name cannot be empty")
        @Size(min = 10, max = 200, message = "Description must be between 10 and 500 characters")
        String name,

        @NotEmpty(message = "Description cannot be empty")
        @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
        String description,

        @Pattern(regexp = "^https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)$", message = "Invalid image URL format")
        String image
    ){}

    // Store Update DTO
    public record UpdateStore(
        @NotEmpty(message = "Name cannot be empty")
        @Size(min = 10, max = 200, message = "Description must be between 10 and 500 characters")
        String name,

        @NotEmpty(message = "Description cannot be empty")
        @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
        String description,

        @Pattern(regexp = "^https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)$", message = "Invalid image URL format")
        String image
    ){}

    // Store Response DTO
    public record ResponseStore(
        Long id,
        String name,
        String description,
        String image
    ){}

    // Store Response Get All DTO
    public record ResponseStoreGetAll(
        List<ResponseStore> values,
        Long count
    ){}
}
