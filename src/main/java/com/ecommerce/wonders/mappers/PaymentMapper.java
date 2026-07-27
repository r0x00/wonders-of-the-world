package com.ecommerce.wonders.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.ecommerce.wonders.dto.PaymentDto.CreatePayment;
import com.ecommerce.wonders.dto.PaymentDto.ResponsePayment;
import com.ecommerce.wonders.dto.PaymentDto.UpdatePayment;
import com.ecommerce.wonders.model.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    ResponsePayment toDto(Payment payment);

    Payment toEntity(ResponsePayment responsePayment);

    Payment toEntityFromCreateDto(CreatePayment createPayment);

    void updateEntityFromDto(UpdatePayment updatePayment, @MappingTarget Payment existingPayment);
}
