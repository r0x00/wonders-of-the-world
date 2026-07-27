package com.ecommerce.wonders.services;

import com.ecommerce.wonders.exception.BadRequestException;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import com.ecommerce.wonders.dto.PaymentDto.CreatePayment;
import com.ecommerce.wonders.dto.PaymentDto.ResponsePayment;
import com.ecommerce.wonders.dto.PaymentDto.ResponsePaymentGetAll;
import com.ecommerce.wonders.dto.PaymentDto.UpdatePayment;
import com.ecommerce.wonders.dto.UserDto.ResponseUser;
import com.ecommerce.wonders.mappers.PaymentMapper;
import com.ecommerce.wonders.mappers.UserMapper;
import com.ecommerce.wonders.model.Payment;
import com.ecommerce.wonders.model.Payment_;
import com.ecommerce.wonders.model.User;
import com.ecommerce.wonders.repository.PaymentRepository;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public PaymentService(
        PaymentRepository paymentRepository,
        PaymentMapper paymentMapper,
        UserService userService,
        UserMapper userMapper
    ) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public ResponsePayment getPayment(Long id, Long userId) {
        Payment payment = this.paymentRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new BadRequestException("Payment not found with ID: " + id));

        ResponsePayment result = this.paymentMapper.toDto(payment);
        
        return result;
    }

    public ResponsePaymentGetAll getAllPayment(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, JpaSort.of(Payment_.updatedAt).descending());
        Page<Payment> payments = this.paymentRepository.getAllByUserId(userId, pageable);

        List<ResponsePayment> values = payments.stream()
            .map(payment -> this.paymentMapper.toDto(payment))
            .toList();
            

        Long count = payments.getTotalElements();

        ResponsePaymentGetAll result = new ResponsePaymentGetAll(values, count);
        
        return result;
    }

    public void createPayment(Long userId, CreatePayment rawJson) {
        ResponseUser checkUser = this.userService.getUserById(userId);

        if(checkUser == null) {
            throw new BadRequestException("User not found with ID: " + userId);
        }

        Payment payment = this.paymentMapper.toEntityFromCreateDto(rawJson);

        User user = this.userMapper.toEntity(checkUser);
        payment.setUser(user);

        this.paymentRepository.save(payment);
    }

    public void updatePayment(Long id, Long userId, UpdatePayment rawJson) {
        Payment payment = this.paymentRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new BadRequestException("Payment not found with ID: " + id));

        this.paymentMapper.updateEntityFromDto(rawJson, payment);

        this.paymentRepository.save(payment);
    }

    public void deletePayment(Long id, Long userId) {
        this.paymentRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new BadRequestException("Payment not found with ID: " + id));
        
        this.paymentRepository.deleteById(id);
    }
}
