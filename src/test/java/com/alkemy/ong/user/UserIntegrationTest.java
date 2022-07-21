package com.alkemy.ong.user;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.auth.utility.RoleEnum;
import com.alkemy.ong.context.UserContextTest;
import com.alkemy.ong.models.entity.CategoryEntity;
import com.alkemy.ong.models.entity.RoleEntity;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.models.request.SlideRequest;
import com.alkemy.ong.models.request.UserRequest;
import com.alkemy.ong.models.request.UserUpdateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Set;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserIntegrationTest extends ContextTests {

    public final static String URL_USER = "/users";

    //Happy path

    @Test
    public void should_return_OK_status_code_and_all_user_list_when_admin_is_logged() throws Exception {
        //getAllUsers

        mockMvc.perform(get(URL_USER + URL_USER)
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..firstName", notNullValue()))
                .andExpect(jsonPath("$..lastName", notNullValue()))
                .andExpect(jsonPath("$..email", notNullValue()))
                .andExpect(jsonPath("$..photo", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_OK_status_code_and_a_list_of_ten_users_per_page_when_admin_is_logged() throws Exception {


        mockMvc.perform(get(URL_USER).param("page", String.valueOf(1))
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$..firstName", notNullValue()))
                .andExpect(jsonPath("$..lastName", notNullValue()))
                .andExpect(jsonPath("$..email", notNullValue()))
                .andExpect(jsonPath("$..photo", notNullValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_NO_CONTENT_status_code_updating_when_admin_is_logged() throws Exception {

        UserEntity user = saveAdminRandomEmail();
        mockMvc.perform(patch(URL_USER + "/" + user.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest(
                        "Facu",
                        "Dalesio",
                        "12345678",
                        "fotito"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_return_NO_CONTENT_status_code_when_erasing_when_admin_is_logged() throws Exception {

        UserEntity user = saveAdminRandomEmail();
        mockMvc.perform(delete(URL_USER + "/" + user.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    //Error case

    @Test
    public void should_return_FORBIDDEN_status_code_when_standard_user_is_logged_trying_getAllUsers() throws Exception {

        mockMvc.perform(get(URL_USER + URL_USER)
                    .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void should_return_NOT_FOUND_status_code_when_admin_insert_negative_page_number() throws Exception {

        mockMvc.perform(get(URL_USER).param("page", String.valueOf(-1))
                    .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_FORBIDDEN_status_code_when_try_get_a_list_of_users_per_page_when_standard_user_is_logged()throws Exception{

        mockMvc.perform(get(URL_USER).param("page", String.valueOf(1))
                    .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    private String createRequest(String firstName, String lastName, String password, String photo) throws JsonProcessingException {
        return objectMapper.writeValueAsString(UserUpdateRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .photo(photo)
                .build());
    }

}
