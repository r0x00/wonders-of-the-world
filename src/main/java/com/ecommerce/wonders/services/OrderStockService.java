package com.ecommerce.wonders.services;


import org.springframework.stereotype.Service;

import com.ecommerce.wonders.exception.BadRequestException;
import com.ecommerce.wonders.model.Stock;
import com.ecommerce.wonders.repository.StockRepository;

@Service
public class OrderStockService {
    private final StockRepository stockRepository;
    private final OrderPaymentService checkPaymentService;
    private final OrderService orderService;

    public OrderStockService(
        StockRepository stockRepository,
        OrderPaymentService checkPaymentService,
        OrderService orderService
    ) {
        this.stockRepository = stockRepository;
        this.checkPaymentService = checkPaymentService;
        this.orderService = orderService;
    }

    public void checkStockByOrderId(Long orderId, Long paymentId) {
        Long productId = this.orderService.getOrderById(orderId).productId();
        Stock stock = this.stockRepository.findByProductId(productId).orElseThrow(() -> new BadRequestException("Stock not found with Product ID: " +  productId));
        
        System.out.println(stock.getQuantity());
        if(stock.getQuantity() == 0) {
            this.orderService.cancelOrder(orderId);
            
            return;
        }

        stock.setQuantity(stock.getQuantity() - 1);

        this.stockRepository.save(stock);

        this.checkPaymentService.checkPayment(paymentId, orderId);
    }
}