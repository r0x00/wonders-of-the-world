package com.ecommerce.wonders.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.wonders.dto.PaymentDto.CreatePayment;
import com.ecommerce.wonders.dto.PaymentDto.ResponsePayment;
import com.ecommerce.wonders.dto.PaymentDto.ResponsePaymentGetAll;
import com.ecommerce.wonders.dto.PaymentDto.UpdatePayment;
import com.ecommerce.wonders.services.PaymentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<ResponsePaymentGetAll> getAllPayments(
        @PathVariable Long userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") @Max(value = 200, message = "Size must be less than 200") int size
    ) {
        ResponsePaymentGetAll result = this.paymentService.getAllPayments(userId, page, size);

        return ResponseEntity.ok(result);
    }

    @GetMapping("{userId}/{id}")
    public ResponseEntity<ResponsePayment> getPayment(
        @PathVariable Long userId,
        @PathVariable Long id
    ) {
        ResponsePayment result = this.paymentService.getPayment(id, userId);
        return ResponseEntity.ok(result);
    }


    @PostMapping("{userId}")
    public void createPayment(
        @PathVariable Long userId,
        @Valid @RequestBody CreatePayment rawJson
    ) {
        this.paymentService.createPayment(userId, rawJson);
    }

    @PatchMapping("{userId}/{id}")
    public void updatePayment(
        @PathVariable Long id,
        @PathVariable Long userId,
        @Valid @RequestBody UpdatePayment rawJson
    ) {
        this.paymentService.updatePayment(id, userId, rawJson);
    }

    @DeleteMapping("{userId}/{id}")
    public void deletePayment(
        @PathVariable Long id,
        @PathVariable Long userId
    ){
        this.paymentService.deletePayment(id, userId);
    }
}
