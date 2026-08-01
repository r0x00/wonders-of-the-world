package com.ecommerce.wonders.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import com.ecommerce.wonders.dto.StockDto.ResponseStock;
import com.ecommerce.wonders.dto.StockDto.ResponseStockGetAll;
import com.ecommerce.wonders.dto.StockDto.UpdateStock;
import com.ecommerce.wonders.exception.BadRequestException;
import com.ecommerce.wonders.mappers.StockMapper;
import com.ecommerce.wonders.model.Product;
import com.ecommerce.wonders.model.Stock;
import com.ecommerce.wonders.model.Stock_;
import com.ecommerce.wonders.model.Store;
import com.ecommerce.wonders.repository.StockRepository;

@Service
public class StockService {
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    public StockService(
        StockRepository stockRepository,
        StockMapper stockMapper
    ) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    public void createStock(Product product, Store store) {
        Stock stock = new Stock();

        stock.setQuantity(0);
        stock.setProduct(product);
        stock.setStore(store);

        this.stockRepository.save(stock);
    }

    public ResponseStockGetAll getAllStocksFromStore(Long storeId,int page, int size) {
        Pageable pageable = PageRequest.of(page, size, JpaSort.of(Stock_.updatedAt).descending());

        Page<Stock> stocks = this.stockRepository.getAllByStoreId(storeId, pageable);

        List<ResponseStock> values = stocks.stream()
            .map(stock -> this.stockMapper.toDto(stock))
            .toList();

        Long count = stocks.getTotalElements();

        ResponseStockGetAll result = new ResponseStockGetAll(values, count);

        return result;
    }

    public ResponseStock getStockById(Long id) {
        Stock stock = this.stockRepository.findById(id).orElseThrow(() -> new BadRequestException("Stock not found with ID: " + id));
        
        ResponseStock result = this.stockMapper.toDto(stock);

        return result;
    }

    public ResponseStock getStockByProductId(Long productId) {
        Stock stock = this.stockRepository.findByProductId(productId).orElseThrow(() -> new BadRequestException("Stock not found with Product ID: " + productId));

        ResponseStock result = this.stockMapper.toDto(stock);

        return result;
    }

    public void updateStock(Long id,  UpdateStock rawJson) {
        Stock stock = this.stockRepository.findById(id).orElseThrow(() -> new BadRequestException("Stock not found with ID: " + id));
        
        this.stockMapper.updateEntityFromDto(rawJson, stock);

        this.stockRepository.save(stock);
    }


    public boolean isUserStockOwner(Long id, Long userId) {
        Stock stock = this.stockRepository.findById(id).orElseThrow(() -> new BadRequestException("Stock not found with ID: " + id));

        boolean result = stock.getStore().getUser().getId().equals(userId);

        return result;
    }
}
