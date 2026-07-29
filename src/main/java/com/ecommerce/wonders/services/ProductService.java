package com.ecommerce.wonders.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import com.ecommerce.wonders.dto.ProductDto.CreateProduct;
import com.ecommerce.wonders.dto.ProductDto.ResponseProduct;
import com.ecommerce.wonders.dto.ProductDto.ResponseProductGetAll;
import com.ecommerce.wonders.dto.ProductDto.UpdateProduct;
import com.ecommerce.wonders.dto.StockDto.ResponseStock;
import com.ecommerce.wonders.dto.StoreDto.ResponseStore;
import com.ecommerce.wonders.enums.EnumCategory;
import com.ecommerce.wonders.exception.BadRequestException;
import com.ecommerce.wonders.mappers.ProductMapper;
import com.ecommerce.wonders.mappers.StockMapper;
import com.ecommerce.wonders.mappers.StoreMapper;
import com.ecommerce.wonders.model.Product;
import com.ecommerce.wonders.model.Product_;
import com.ecommerce.wonders.model.Stock;
import com.ecommerce.wonders.model.Store;
import com.ecommerce.wonders.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final StoreMapper storeMapper;
    private final StockMapper stockMapper;
    private final StockService stockService;
    private final StoreService storeService;

    public ProductService(
        ProductRepository productRepository,
        ProductMapper productMapper,
        StockService stockService,
        StoreService storeService,
        StoreMapper storeMapper,
        StockMapper stockMapper
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.stockService = stockService;
        this.storeService = storeService;
        this.storeMapper = storeMapper;
        this.stockMapper = stockMapper;
    }

    public ResponseProductGetAll getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, JpaSort.of(Product_.updatedAt).descending());

        Page<Product> products = this.productRepository.findAll(pageable);

        List<ResponseProduct> values = products.stream()
            .map(product -> this.productMapper.toDto(product))
            .toList();

        Long count = products.getTotalElements();

        ResponseProductGetAll result = new ResponseProductGetAll(values, count);
    
        return result;
    }

    public ResponseProductGetAll getAllProductsFromStoreId(Long storeId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, JpaSort.of(Product_.updatedAt).descending()); 

        Page<Product> products = this.productRepository.getAllByStoreId(storeId, pageable);

        List<ResponseProduct> values = products.stream()
            .map(product -> this.productMapper.toDto(product))
            .toList();

        Long count = products.getTotalElements();


        ResponseProductGetAll result = new ResponseProductGetAll(values, count);

        return result;
    }


    public ResponseProductGetAll getAllProductsFromCategory(EnumCategory category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, JpaSort.of(Product_.updatedAt).descending()); 

        Page<Product> products = this.productRepository.getAllByCategory(category, pageable);

        List<ResponseProduct> values = products.stream()
            .map(product -> this.productMapper.toDto(product))
            .toList();

        Long count = products.getTotalElements();

        ResponseProductGetAll result = new ResponseProductGetAll(values, count);

        return result;
    }


    public ResponseProduct getProduct(Long id) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new BadRequestException("Product not found with ID: " + id));

        ResponseProduct result = this.productMapper.toDto(product);

        return result;
    }

    public void createProduct(Long storeId, CreateProduct rawJson) {
        ResponseStore storeResponse = this.storeService.getStore(storeId);

        Product product = this.productMapper.toEntityFromCreateDto(rawJson);

        Store store = this.storeMapper.toEntity(storeResponse);

        product.setStore(store);

        product.setStock(null);

        this.productRepository.save(product);


        try {
            this.stockService.createStock(product, store);

            ResponseStock stockResponse = this.stockService.getStockByProductId(product.getId());

            Stock stock = this.stockMapper.toEntity(stockResponse);

            product.setStock(stock);

        } catch(Exception ex) {
            this.productRepository.delete(product);

            throw new BadRequestException("An error occured when creating Product Stock.");
        }

        this.productRepository.save(product);
    }

    public void updateProduct(Long id, UpdateProduct rawJson) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new BadRequestException("Product not found with ID: " + id));

        this.productMapper.updateEntityFromDto(rawJson, product);

        this.productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        this.productRepository.findById(id).orElseThrow(() -> new BadRequestException("Product not found with ID: " + id));

        this.productRepository.deleteById(id);
    }
}
