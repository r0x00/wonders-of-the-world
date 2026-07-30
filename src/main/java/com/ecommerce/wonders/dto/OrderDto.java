package com.ecommerce.wonders.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.wonders.enums.EnumOrderStatus;
import com.ecommerce.wonders.enums.EnumPaymentMethod;
import jakarta.validation.constraints.NotNull;

public final class OrderDto {
    private OrderDto() {}

    // Order Create DTO
    public record CreateOrder(
        @NotNull(message = "AddressId cannot be empty")
        Long addressId,

        @NotNull(message = "PaymentId cannot be empty")
        Long paymentId,

        @NotNull(message = "Quantity cannot be empty")
        int quantity
    ){}

    // Order Update DTO
    public record UpdateOrder(
        @NotNull(message = "AddressId cannot be empty")
        Long addressId,

        @NotNull(message = "PaymentId cannot be empty")
        Long paymentId
    ){}

    // Order Response DTO
    public record ResponseOrder(
        Long id,
        Long userId,
        Long productId,
        int quantity,
        EnumOrderStatus status,
        LocalDateTime deliveryDate,
        double total,
        double productPrice,
        String productName,
        String productImage,
        String productStoreName,
        EnumPaymentMethod userPaymentMethod,
        String userPaymentLast4Digits,
        String userAddressName,
        String userAddressStreetAddress,
        String userAddressCity,
        String userAddressState,
        String userAddressCountry,
        String userAddressZipCode,
        String userName,
        String userEmail
    ){}

    // Order Get All Response DTO
    public record ResponseOrderGetAll(
        List<ResponseOrder> values,
        Long count
    ){}
}
