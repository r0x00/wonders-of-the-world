package com.ecommerce.wonders.dto;

import java.time.YearMonth;
import java.util.List;

import com.ecommerce.wonders.enums.EnumPaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public final class PaymentDto {
    private PaymentDto() {}

    // Payment Create DTO
    public record CreatePayment(
        @Pattern(regexp = "^\\d{12,19}$", message = "Card Number must be between 12 and 19 digits")
        String cardNumber,

        @NotBlank(message = "Card Holder Name cannot be empty")
        @Size(min = 2, max = 26, message = "Card Holder Name must be between 2 and 26 characters")
        String cardHolderName,

        @Pattern(regexp = "^\\d{3,4}$", message = "CVV must be 3 or 4 digits")
        String cvv,

        @NotNull(message = "Card Number cannot be empty")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
        YearMonth expirationDate,

        @NotBlank(message = "Payment Method cannot be empty")
        EnumPaymentMethod paymentMethod,

        @NotNull(message = "UserId cannot be empty")
        Long userId
    ){}

    // Payment Update DTO
    public record UpdatePayment(
        @Pattern(regexp = "^\\d{12,19}$", message = "Card Number must be between 12 and 19 digits")
        String cardNumber,

        @NotBlank(message = "Card Holder Name cannot be empty")
        @Size(min = 2, max = 26, message = "Card Holder Name must be between 2 and 26 characters")
        String cardHolderName,

        @Pattern(regexp = "^\\d{3,4}$", message = "CVV must be 3 or 4 digits")
        String cvv,

        @NotNull(message = "Card Number cannot be empty")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
        YearMonth expirationDate,

        @NotBlank(message = "Payment Method cannot be empty")
        EnumPaymentMethod paymentMethod,

        @NotNull(message = "UserId cannot be empty")
        Long userId
    ){}

    // Payment Response DTO
    public record ResponsePayment(
        Long id,
        String cardNumber,
        String cardHolderName,
        String cvv,
        YearMonth expirationDate,
        EnumPaymentMethod paymentMethod,
        Long userId
    ){}

    // Payment Response Get All
    public record ResponsePaymentGetAll( 
        List<ResponsePayment> values,
        Long count
    ){}
}
