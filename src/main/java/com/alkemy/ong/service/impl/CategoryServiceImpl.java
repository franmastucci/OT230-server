package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.OrgNotFoundException;
import com.alkemy.ong.models.entity.CategoryEntity;
import com.alkemy.ong.models.mapper.CategoryMapper;
import com.alkemy.ong.models.response.CategoryNameResponse;
import com.alkemy.ong.models.response.CategoryResponse;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import com.amazonaws.services.kms.model.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryNameResponse> getCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();

        List<CategoryNameResponse> categoriesResponse = categoryMapper.categoryNameResponse(categories);
        return categoriesResponse;
    }

    @Override
    public CategoryResponse getCategoryDetail(Long id) {
        CategoryEntity entityDB = categoryRepository.findById(id)
                .orElseThrow(() -> new OrgNotFoundException("Category not found"));
        CategoryResponse response = categoryMapper.categoryDetailsResponse(entityDB);
        return response;
    }
}
