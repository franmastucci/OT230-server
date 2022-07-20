package com.alkemy.ong.user;

import com.alkemy.ong.ContextTests;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserIntegrationTest extends ContextTests {

    private final String URL_USER = "/users";

    //Happy path

    @Test
    public void should_return_OK_status_code_and_all_user_list_when_admin_is_logged() throws Exception {
        //getAllUsers

        mockMvc.perform(get(URL_USER).header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..firstName", notNullValue()))
                .andExpect(jsonPath("$..lastName", notNullValue()))
                .andExpect(jsonPath("$..email", notNullValue()))
                .andExpect(jsonPath("$..photo", notNullValue()))
                .andExpect(status().isOk());
    }

}
