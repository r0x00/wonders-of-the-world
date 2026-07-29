package com.ecommerce.wonders.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import com.ecommerce.wonders.dto.ProductDto.ResponseProduct;
import com.ecommerce.wonders.dto.RatingDto.CreateRatingDto;
import com.ecommerce.wonders.dto.RatingDto.ResponseRating;
import com.ecommerce.wonders.dto.RatingDto.ResponseRatingGetAll;
import com.ecommerce.wonders.dto.RatingDto.UpdateRatingDto;
import com.ecommerce.wonders.dto.UserDto.ResponseUser;
import com.ecommerce.wonders.exception.BadRequestException;
import com.ecommerce.wonders.mappers.ProductMapper;
import com.ecommerce.wonders.mappers.RatingMapper;
import com.ecommerce.wonders.mappers.UserMapper;
import com.ecommerce.wonders.model.Product;
import com.ecommerce.wonders.model.Rating;
import com.ecommerce.wonders.model.Rating_;
import com.ecommerce.wonders.model.User;
import com.ecommerce.wonders.repository.RatingRepository;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final UserService userService;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final UserMapper userMapper;

    public RatingService(
        RatingRepository ratingRepository,
        RatingMapper ratingMapper,
        UserService userService,
        ProductService productService,
        ProductMapper productMapper,
        UserMapper userMapper
    ) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
        this.userService = userService;
        this.productService = productService;
        this.productMapper = productMapper;
        this.userMapper = userMapper;
    }

    public ResponseRatingGetAll getAllRatingsFromProduct(Long productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, JpaSort.of(Rating_.updatedAt).descending());

        Page<Rating> ratings = this.ratingRepository.getAllByProductId(productId, pageable);

        List<ResponseRating> values = ratings.stream()
            .map(rating -> this.ratingMapper.toDto(rating))
            .toList();

        Long count = ratings.getTotalElements();

        ResponseRatingGetAll result = new ResponseRatingGetAll(values, count);
        
        return result;
    }

    public ResponseRatingGetAll geAlltRatingsFromUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, JpaSort.of(Rating_.updatedAt).descending());

        Page<Rating> ratings = this.ratingRepository.getAllByUserId(userId, pageable);

        List<ResponseRating> values = ratings.stream()
            .map(rating -> this.ratingMapper.toDto(rating))
            .toList();

        Long count = ratings.getTotalElements();

        ResponseRatingGetAll result = new ResponseRatingGetAll(values, count);

        return result;
    }

    public ResponseRating getRatingById(Long id) {
        Rating rating = this.ratingRepository.findById(id).orElseThrow(() -> new BadRequestException("Rating not found with this ID: " + id));

        ResponseRating result = this.ratingMapper.toDto(rating);

        return result;
    }

    public void createRating(Long userId, Long productId, CreateRatingDto rawJson) {
        Rating rating = this.ratingMapper.toEntityFromCreateDto(rawJson);

        ResponseProduct responseProduct = this.productService.getProductById(productId);

        Product product = this.productMapper.toEntity(responseProduct);

        rating.setProduct(product);

        ResponseUser responseUser = this.userService.getUserById(userId);

        User user = this.userMapper.toEntity(responseUser);

        rating.setUser(user);

        this.ratingRepository.save(rating);
    }

    public void updateRating(Long id, UpdateRatingDto rawJson) {
        Rating rating = this.ratingRepository.findById(id).orElseThrow(() -> new BadRequestException("Rating not found with this ID: " + id));

        this.ratingMapper.updateEntityFromDto(rawJson, rating);

        this.ratingRepository.save(rating);
    }

    public void deleteRating(Long id) {
        this.ratingRepository.findById(id).orElseThrow(() -> new BadRequestException("Rating not found with this ID: " + id));

        this.ratingRepository.deleteById(id);
    }
}

