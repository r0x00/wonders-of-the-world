package com.ecommerce.wonders.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.wonders.dto.AuthDto.AuthJWTResponse;
import com.ecommerce.wonders.dto.AuthDto.LoginRequest;
import com.ecommerce.wonders.services.AuthService;

@RestController
@RequestMapping("auth") 
public class AuthController {
    private final AuthService authService;

    public AuthController(
        AuthService authService
    ) {
        this.authService = authService;
    }
    
    @PostMapping("/login/jwt")
    public ResponseEntity<AuthJWTResponse> login(
        @RequestBody LoginRequest rawJson
    ) {
        String result = this.authService.login(rawJson);

        return ResponseEntity.ok(new AuthJWTResponse(result));
    }
}
