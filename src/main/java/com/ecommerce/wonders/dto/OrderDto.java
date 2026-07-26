package com.ecommerce.wonders.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public final class OrderDto {
    private OrderDto() {}

    // Order Create DTO
    public record CreateOrder(
        @NotNull(message = "UserId cannot be empty")
        Long userId,

        @NotNull(message = "ProductId cannot be empty")
        Long productId
    ){}

    // Order Update DTO
    public record UpdateOrder(
        @NotNull(message = "UserId cannot be empty")
        Long userId,

        @NotNull(message = "ProductId cannot be empty")
        Long productId
    ){}

    // Order Response DTO
    public record ResponseOrder(
        Long id,
        Long userId,
        Long productId
    ){}

    // Order Get All Response DTO
    public record ResponseOrderGetAll(
        List<ResponseOrder> values,
        Long count
    ){}
}
