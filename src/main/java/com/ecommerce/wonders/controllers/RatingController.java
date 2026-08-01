package com.ecommerce.wonders.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.wonders.dto.RatingDto.CreateRatingDto;
import com.ecommerce.wonders.dto.RatingDto.ResponseRating;
import com.ecommerce.wonders.dto.RatingDto.ResponseRatingGetAll;
import com.ecommerce.wonders.dto.RatingDto.UpdateRatingDto;
import com.ecommerce.wonders.model.User;
import com.ecommerce.wonders.services.RatingService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;

@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }


    @GetMapping("product/{productId}")
    public ResponseEntity<ResponseRatingGetAll> getAllRatingsFromProduct(
        @PathVariable Long productId, 
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") @Max(value = 200, message = "Size must be less than 200") int size
    ) {
        ResponseRatingGetAll result = this.ratingService.getAllRatingsFromProduct(productId, page, size);

        return ResponseEntity.ok(result);
    }

    @GetMapping("user")
    public ResponseEntity<ResponseRatingGetAll> geAlltRatingsFromUser(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") @Max(value = 200, message = "Size must be less than 200") int size,
        Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        ResponseRatingGetAll result = this.ratingService.geAlltRatingsFromUser(userId, page, size);

        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseRating> getRatingById(
        @PathVariable Long id
    ) {
        ResponseRating result = this.ratingService.getRatingById(id);

        return ResponseEntity.ok(result);
    }


    @PostMapping("user/product/{productId}")
    @PreAuthorize("@ratingService.userAlreadyRatedProduct(#productId, authentication.principal.id) == false")
    public void createRating(
        @PathVariable Long productId, 
        @Valid @RequestBody CreateRatingDto rawJson,
        Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();

        this.ratingService.createRating(userId, productId, rawJson);

    }

    @PatchMapping("{id}")
    @PreAuthorize("@ratingService.isUserRatingOwner(#id, authentication.principal.id)")
    public void updateRating(
        @PathVariable Long id, 
        @Valid @RequestBody UpdateRatingDto rawJson
    ) {
        this.ratingService.updateRating(id, rawJson);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or @ratingService.isUserRatingOwner(#id, authentication.principal.id)")
    public void deleteRating(
        @PathVariable Long id
    ) {
        this.ratingService.deleteRating(id);
    }
    
}
