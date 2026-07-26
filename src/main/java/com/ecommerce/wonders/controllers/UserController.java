package com.ecommerce.wonders.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.wonders.dto.UserDto.CreateUser;
import com.ecommerce.wonders.dto.UserDto.ResponseUser;
import com.ecommerce.wonders.dto.UserDto.ResponseUserGetAll;
import com.ecommerce.wonders.dto.UserDto.UpdateUser;
import com.ecommerce.wonders.services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseUserGetAll> getAllUsers(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") @Max(value = 200, message = "Size must be less than 200") int size
    ) {
        ResponseUserGetAll result = this.userService.getAllUsers(page, size);

        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUser> getUserById(
        @PathVariable Long id
    ) {
        ResponseUser result = this.userService.getUserById(id);

        return ResponseEntity.ok(result);
    }


    @PatchMapping("/{id}")
    public void updateUserById(
        @PathVariable Long id, 
        @Valid @RequestBody UpdateUser rawJson
    ) {
        this.userService.updateUserById(id, rawJson);
    }

    @PostMapping
    public void createUser(
        @Valid @RequestBody CreateUser rawJson 
    ) {
        this.userService.createUser(rawJson);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(
        @PathVariable Long id
    ) {
        this.userService.deleteUserById(id);
    }
}