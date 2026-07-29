package com.ecommerce.wonders.dto;

import java.util.List;

import com.ecommerce.wonders.enums.EnumRating;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public final class RatingDto {
    private RatingDto() {}

    // Rating Create DTO
    public record CreateRatingDto(
        @NotNull(message="Rating cannot be empty")
        EnumRating rating,

        @Size(min=0, max=500, message="Comment must be between 0 and 500 characters")
        String comment,

        @Size(min=0, max=10, message="Image must be between 0 and 10 images")
        List<String> images
    ){}

    // Rating Update DTO
    public record UpdateRatingDto(
        @NotNull(message="Rating cannot be empty")
        EnumRating rating,

        @Size(min=0, max=500, message="Comment must be between 0 and 500 characters")
        String comment,

        @Size(min=0, max=10, message="Image must be between 0 and 10 images")
        List<String> images
    ){}

    // Rating Response DTO
    public record ResponseRating(
        Long id,
        EnumRating rating, 
        String comment,
        List<String> images,
        Long productId,
        Long userId
    ){}

    // Rating Response Get All DTO
    public record ResponseRatingGetAll(
        List<ResponseRating> values,
        Long count
    ){}
}
