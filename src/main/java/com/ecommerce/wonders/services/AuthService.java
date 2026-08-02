package com.ecommerce.wonders.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.wonders.dto.AuthDto.LoginRequest;
import com.ecommerce.wonders.exception.BadRequestException;
import com.ecommerce.wonders.model.User;
import com.ecommerce.wonders.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; 
    private final TokenService tokenService;

    public AuthService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public String login(LoginRequest rawJson) {
        String genericError = "User or Password is wrong.";

        try {
            String email = rawJson.email();


            User user = this.userRepository.findUserByEmail(email).orElseThrow(() -> new BadRequestException(genericError));


            Boolean isPasswordValid = this.passwordEncoder.matches(rawJson.password(), user.getPassword());

            if(!isPasswordValid) {
                throw new BadRequestException(genericError);
            }

            String userLoginJWToken = this.tokenService.generateJWT(user);

            return userLoginJWToken;
        } catch (Exception e) {
            throw new BadRequestException(genericError);
        }
    } 
}