package com.ecommerce.wonders.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.ecommerce.wonders.dto.AddressDto.ReponseAddress;
import com.ecommerce.wonders.dto.OrderDto.CreateOrder;
import com.ecommerce.wonders.dto.OrderDto.ResponseOrder;
import com.ecommerce.wonders.dto.OrderDto.ResponseOrderGetAll;
import com.ecommerce.wonders.dto.PaymentDto.ResponsePayment;
import com.ecommerce.wonders.dto.ProductDto.ResponseProduct;
import com.ecommerce.wonders.dto.StoreDto.ResponseStore;
import com.ecommerce.wonders.dto.UserDto.ResponseUser;
import com.ecommerce.wonders.enums.EnumOrderStatus;
import com.ecommerce.wonders.exception.BadRequestException;
import com.ecommerce.wonders.mappers.AddressMapper;
import com.ecommerce.wonders.mappers.OrderMapper;
import com.ecommerce.wonders.mappers.PaymentMapper;
import com.ecommerce.wonders.mappers.ProductMapper;
import com.ecommerce.wonders.mappers.UserMapper;
import com.ecommerce.wonders.model.Address;
import com.ecommerce.wonders.model.Order;
import com.ecommerce.wonders.model.Order_;
import com.ecommerce.wonders.model.Payment;
import com.ecommerce.wonders.model.Product;
import com.ecommerce.wonders.model.User;
import com.ecommerce.wonders.repository.OrderRepository;


@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final AddressService addressService;
    private final PaymentService paymentService;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final PaymentMapper paymentMapper;
    private final StoreService storeService;
    private final SqsStockProducerService sqsStockProducerService;

    public OrderService(
        OrderRepository orderRepository,
        UserService userService,
        ProductService productService,
        OrderMapper orderMapper,
        UserMapper userMapper,
        ProductMapper productMapper,
        AddressService addressService,
        AddressMapper addressMapper,
        PaymentMapper paymentMapper,
        PaymentService paymentService,
        StoreService storeService,
        SqsStockProducerService sqsStockProducerService
    ) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.productService = productService;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.productMapper = productMapper;
        this.addressService = addressService;
        this.addressMapper = addressMapper;
        this.paymentMapper = paymentMapper;
        this.paymentService = paymentService;
        this.storeService = storeService;
        this.sqsStockProducerService = sqsStockProducerService;
    }

    public ResponseOrderGetAll getAllOrdersFromUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, JpaSort.of(Order_.updatedAt).descending());

        Page<Order> orders = this.orderRepository.getAllByUserId(userId, pageable);

        List<ResponseOrder> values = orders.stream()
            .map(order -> this.orderMapper.toDto(order))
            .toList();

        Long count = orders.getTotalElements();

        ResponseOrderGetAll result = new ResponseOrderGetAll(values, count);

        return result;
    }

    public ResponseOrder getOrderByIdAndUserId(Long id, Long userId) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new BadRequestException("Order not found with ID: " + id));

        if(!order.getUser().getId().equals(userId)) {
            throw new BadRequestException("Order not found with ID: " + id);
        }

        ResponseOrder result = this.orderMapper.toDto(order);

        return result;
    }

    public ResponseOrder getOrderById(Long id) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new BadRequestException("Order not found with ID: " + id));

        ResponseOrder result = this.orderMapper.toDto(order);

        return result;
    }

    public void createOrder(Long userId, Long productId, CreateOrder rawJson) {
        ResponseProduct responseProduct = this.productService.getProductById(productId);
        Product product = this.productMapper.toEntity(responseProduct);

        ResponseStore responseStore = this.storeService.getStoreById(responseProduct.storeId());

        if(responseStore.userId() == userId) {
            throw new BadRequestException("You can't buy your own product");
        }

        ResponseUser userResponse = this.userService.getUserById(userId);
        User user = this.userMapper.toEntity(userResponse);

        ReponseAddress responseAddress = this.addressService.getAddress(rawJson.addressId(), userId);
        Address address = this.addressMapper.toEntity(responseAddress);

        ResponsePayment ResponsePayment = this.paymentService.getPayment(rawJson.paymentId(), userId);
        Payment payment = this.paymentMapper.toEntity(ResponsePayment);

        Order order = this.orderMapper.toEntityFromCreateData(user, address, product, payment);

        order.setUser(user);
        order.setProduct(product);
        order.setProductStoreName(responseStore.name());
        order.setTotal(rawJson.quantity() * product.getPrice());
        order.setQuantity(rawJson.quantity());
        order.setDeliveryDate(LocalDate.now().plusDays(3));

        Order savedOrder = this.orderRepository.save(order);

        this.sqsStockProducerService.sendMessageToCheckStockQueue(savedOrder.getId(), payment.getId());
    }

    public void cancelOrder(Long id) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new BadRequestException("Order not found with ID: " + id));

        if(order.getStatus() == EnumOrderStatus.CANCELLED) {
            throw new BadRequestException("Order is already cancelled");
        }

        if(order.getStatus() == EnumOrderStatus.COMPLETED) {
            throw new BadRequestException("Order is already completed");
        }

        order.setStatus(EnumOrderStatus.CANCELLED);
        this.orderRepository.save(order);
    }

    public void completeOrder(Long id) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new BadRequestException("Order not found with ID: " + id));

        if(order.getStatus() == EnumOrderStatus.COMPLETED) {
            throw new BadRequestException("Order is already completed");
        }

        order.setStatus(EnumOrderStatus.COMPLETED);
        this.orderRepository.save(order);
    }

    public void orderPaymentFailed(Long id) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new BadRequestException("Order not found with ID: " + id));

        if(order.getStatus() == EnumOrderStatus.COMPLETED) {
            throw new BadRequestException("Order is already completed");
        }

        if(order.getStatus() == EnumOrderStatus.CANCELLED) {
            throw new BadRequestException("Order is already cancelled");
        }

        if(order.getStatus() == EnumOrderStatus.PAYMENT_CONFIRMED) {
            throw new BadRequestException("Payment is already confirmed");
        }

        order.setStatus(EnumOrderStatus.PAYMENT_FAILED);
        this.orderRepository.save(order);
    }

    public void orderPaymentConfirmed(Long id) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new BadRequestException("Order not found with ID: " + id));

        if(order.getStatus() == EnumOrderStatus.COMPLETED) {
            throw new BadRequestException("Order is already completed");
        }

        if(order.getStatus() == EnumOrderStatus.CANCELLED) {
            throw new BadRequestException("Order is already cancelled");
        }

        order.setStatus(EnumOrderStatus.PAYMENT_CONFIRMED);
        this.orderRepository.save(order);
    }

    // Simulate that the order is delivered when the delivery date is reached
    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void simulateOrderCompletion() {
        LocalDate currentDate = LocalDate.now();

        this.orderRepository.updateStatusForDeliveryDate(currentDate, EnumOrderStatus.COMPLETED);

    }
}
