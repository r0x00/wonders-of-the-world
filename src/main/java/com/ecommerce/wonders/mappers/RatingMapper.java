package com.ecommerce.wonders.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ecommerce.wonders.dto.RatingDto.CreateRatingDto;
import com.ecommerce.wonders.dto.RatingDto.ResponseRating;
import com.ecommerce.wonders.dto.RatingDto.UpdateRatingDto;
import com.ecommerce.wonders.model.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "userId", source = "user.id")
    ResponseRating toDto(Rating rating);

    Rating toEntity(ResponseRating responseRating);

    Rating toEntityFromCreateDto(CreateRatingDto createRatingDto);

    void updateEntityFromDto(UpdateRatingDto responseRating, @MappingTarget Rating rating);
    
}
