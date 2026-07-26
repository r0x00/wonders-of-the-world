package com.ecommerce.wonders.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public final class StockDto {
    private StockDto() {}

    // Stock Update DTO
    public record UpdateStock(
        @NotNull(message = "Quantity cannot be empty")
        int quantity,

        @NotNull(message = "ProductId cannot be empty")
        Long productId
    ){}

    // Stock Response DTO
    public record ResponseStock(
        Long id,
        int quantity,
        Long productId
    ){}

    // Stock Response Get All DTO
    public record ResponseStockGetAll(
        List<ResponseStock> values,
        Long count
    ){}
}