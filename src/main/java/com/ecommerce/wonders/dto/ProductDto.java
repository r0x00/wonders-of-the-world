package com.ecommerce.wonders.dto;

import java.util.List;

import com.ecommerce.wonders.enums.EnumCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public final class ProductDto {
    private ProductDto() {}
    // Product Create DTO
    public record CreateProduct(
        @NotEmpty(message = "Name cannot be empty")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,

        @NotNull(message = "Price cannot be empty")
        double price,

        @NotNull(message = "Category cannot be empty")
        EnumCategory category,

        @NotEmpty(message = "Description cannot be empty")
        @Size(min = 10, max = 400, message = "Description must be between 10 and 400 characters")
        String description,

        @NotEmpty(message = "Details cannot be empty")
        @Size(min = 10, max = 400, message = "Details must be between 10 and 400 characters")
        String details,

        @Pattern(regexp = "^https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)$", message = "Invalid image URL format")
        String image
    ){}

    // Product Update DTO
    public record UpdateProduct(
        @NotEmpty(message = "Name cannot be empty")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,

        @NotNull(message = "Price cannot be empty")
        double price,

        @NotNull(message = "Category cannot be empty")
        EnumCategory category,

        @NotEmpty(message = "Description cannot be empty")
        @Size(min = 10, max = 400, message = "Description must be between 10 and 400 characters")
        String description,

        @NotEmpty(message = "Details cannot be empty")
        @Size(min = 10, max = 400, message = "Details must be between 10 and 400 characters")
        String details,

        @Pattern(regexp = "^https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)$", message = "Invalid image URL format")
        String image
    ){}

    // Product Response DTO
    public record ResponseProduct(
        Long id,
        String name,
        double price,
        EnumCategory category,
        String description,
        String details,
        String image,
        Long storeId,
        Long stockId
    ){}

    // Product Response Get All DTO
    public record ResponseProductGetAll(
        List<ResponseProduct> values,
        Long count
    ){}
    
}
