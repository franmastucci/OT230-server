package com.alkemy.ong.data;

import com.alkemy.ong.models.response.CategoryNameResponse;
import com.alkemy.ong.models.response.CategoryResponse;

import java.util.Arrays;
import java.util.List;

public class CategoryData {

    public static CategoryNameResponse categoryName1(){
        return CategoryNameResponse.builder()
                .name("Category 1")
                .build();
    }
    public static CategoryNameResponse categoryName2(){
        return CategoryNameResponse.builder()
                .name("Category 2")
                .build();
    }

    public static CategoryResponse categoryDetail(){
        return CategoryResponse.builder()
                .id(3L)
                .description("Description")
                .name("Category 3")
                .image("Imagen_url")
                .build();
    }
    public static List<CategoryNameResponse> categoryNameList(){
        return Arrays.asList(categoryName1(), categoryName2());
    }
}
