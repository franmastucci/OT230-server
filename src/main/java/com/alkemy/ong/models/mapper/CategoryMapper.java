package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.CategoryEntity;
import com.alkemy.ong.models.response.CategoryNameResponse;
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
}
