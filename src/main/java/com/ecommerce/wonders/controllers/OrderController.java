package com.ecommerce.wonders.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.wonders.dto.OrderDto.CreateOrder;
import com.ecommerce.wonders.dto.OrderDto.ResponseOrderGetAll;
import com.ecommerce.wonders.services.OrderService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseOrderGetAll> getAllOrdersFromUser(
        @PathVariable Long userId, 
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") @Max(value = 200, message = "Size must be less than 200") int size
    ) {
        ResponseOrderGetAll result = this.orderService.getAllOrdersFromUser(userId, page, size);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/user/{userId}/product/{productId}")
    public void createOrder(
        @PathVariable Long userId,
        @PathVariable Long productId,
        @Valid @RequestBody CreateOrder rawJson
    ) {
        this.orderService.createOrder(userId, productId, rawJson);
    }


    @PatchMapping("status/cancel/{id}")
    public void cancelOrder(
        @PathVariable Long id
    ) {
        this.orderService.cancelOrder(id);
    }
}
