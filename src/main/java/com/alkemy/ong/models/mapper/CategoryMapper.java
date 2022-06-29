package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.CategoryEntity;
import com.alkemy.ong.models.request.CategoryRequest;
import com.alkemy.ong.models.response.CategoryNameResponse;
import com.alkemy.ong.models.response.CategoryResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryNameResponse categoryNameResponse(CategoryEntity categoryEntity){
        return CategoryNameResponse.builder()
                .name(categoryEntity.getName())
                .build();
    }
    public List<CategoryNameResponse> categoryNameResponse(List<CategoryEntity> categoryEntities){
        List<CategoryNameResponse> categoryNameResponseList = categoryEntities.stream()
                .map(c -> categoryNameResponse(c)).collect(Collectors.toList());

        return categoryNameResponseList;
    }
    public CategoryResponse categoryDetailsResponse(CategoryEntity categoryEntity){
        return CategoryResponse.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .image(categoryEntity.getImage())
                .timestamp(categoryEntity.getTimestamp())
                .build();
    }

    public CategoryEntity toCategoryEntity(CategoryRequest category){
        return CategoryEntity.builder()
                .name(category.getName())
                .description(category.getDescription())
                .image(category.getImage())
                .build();
    }
    public CategoryEntity updateCategory(CategoryEntity categoryEntity, CategoryRequest categoryRequest){
        categoryEntity.setName(categoryRequest.getName());
        categoryEntity.setDescription(categoryRequest.getDescription());
        categoryEntity.setImage(categoryRequest.getImage());
        return categoryEntity;
    }
}
