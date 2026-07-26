package com.ecommerce.wonders.services;

import org.springframework.stereotype.Service;

import com.ecommerce.wonders.repository.StockRepository;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
    
}
