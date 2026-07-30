package com.ecommerce.wonders.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.wonders.dto.OrderDto.ResponseOrder;
import com.ecommerce.wonders.model.Address;
import com.ecommerce.wonders.model.Order;
import com.ecommerce.wonders.model.Payment;
import com.ecommerce.wonders.model.Product;
import com.ecommerce.wonders.model.User;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    ResponseOrder toDto(Order order);

    Order toEntity(ResponseOrder dto);

    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "userEmail", source = "user.email")
    @Mapping(target = "userAddressName", source = "address.name")
    @Mapping(target = "userAddressStreetAddress", source = "address.streetAddress")
    @Mapping(target = "userAddressCity", source = "address.city")
    @Mapping(target = "userAddressState", source = "address.state")
    @Mapping(target = "userAddressCountry", source = "address.country")
    @Mapping(target = "userAddressZipCode", source = "address.zipCode")
    @Mapping(target = "productPrice", source = "product.price")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productImage", source = "product.image")
    @Mapping(target = "productStoreName", source = "product.store.name")
    @Mapping(target = "userPaymentMethod", source = "payment.paymentMethod")
    @Mapping(target = "userPaymentLast4Digits", source = "payment.last4Digits")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Order toEntityFromCreateData(User user, Address address, Product product, Payment payment);


    @Mapping(target = "userAddressName", source = "name")
    @Mapping(target = "userAddressStreetAddress", source = "streetAddress")
    @Mapping(target = "userAddressCity", source = "city")
    @Mapping(target = "userAddressState", source = "state")
    @Mapping(target = "userAddressCountry", source = "country")
    @Mapping(target = "userAddressZipCode", source = "zipCode")
    void updateEntityFromUserAddressEntity(Address address, @MappingTarget Order existingOrder);


    @Mapping(target = "userPaymentMethod", source = "paymentMethod")
    @Mapping(target = "userPaymentLast4Digits", source = "last4Digits")
    void updateEntityFromPaymentEntity(Payment payment, @MappingTarget Order existingOrder);
}
