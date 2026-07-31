package com.ecommerce.wonders.dto;

import jakarta.validation.constraints.NotBlank;

public final class AuthDto {
    public record LoginRequest(
        @NotBlank(message = "email cannot be empty")
        String email,

        @NotBlank(message = "password cannot be empty")
        String password
    ) {}

    public record AuthJWTResponse(
        String token
    ) {}
}
