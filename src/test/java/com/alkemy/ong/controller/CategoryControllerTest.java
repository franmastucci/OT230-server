package com.alkemy.ong.controller;

import com.alkemy.ong.data.CategoryData;
import com.alkemy.ong.service.CategoryService;
import com.amazonaws.services.dynamodbv2.xspec.S;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    public final static String URL_CONTROLLER = "/categories";
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    public void setup() throws Exception{



        MockitoAnnotations.openMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void getAllCategories() throws Exception {
        //Given
        when(categoryService.getCategories()).thenReturn(CategoryData.categoryNameList());
        //When
        mockMvc.perform(MockMvcRequestBuilders.get(CategoryControllerTest.URL_CONTROLLER).contentType(MediaType.APPLICATION_JSON))
        //Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Category 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Category 2"));

        verify(categoryService).getCategories();
    }

    @Test
    void getCategoryDetail() throws Exception {
        //Given
        when(categoryService.getCategoryDetail(3L)).thenReturn(CategoryData.categoryDetail());

        //When
        mockMvc.perform(MockMvcRequestBuilders.get(CategoryControllerTest.URL_CONTROLLER + "/3")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Category 3"));
        verify(categoryService).getCategoryDetail(3L);
    }

    @Test
    void createCategory() {
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }
}