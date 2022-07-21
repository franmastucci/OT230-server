package com.alkemy.ong.news;

import com.alkemy.ong.context.NewsContextTest;
import com.alkemy.ong.models.entity.CommentEntity;
import com.alkemy.ong.models.entity.NewsEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 class NewsIntegrationTests extends NewsContextTest {

    public final static String URL_CONTROLLER = "/news";

    @Test
    public void shouldReturnNewsListPageWhenAdminRoleAreValid() throws Exception{

        mockMvc.perform(get(URL_CONTROLLER).param("page", String.valueOf(1))
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..news", notNullValue()))
                .andExpect(status().isOk());
    }

     @Test
     public void shouldReturnNewsListPageWhenStandardRoleAreValid() throws Exception{

         mockMvc.perform(get(URL_CONTROLLER).param("page", String.valueOf(1))
                         .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$..news", notNullValue()))
                 .andExpect(status().isOk());
     }

     @Test
     public void shouldReturnNewsListWhenAdminRoleAreValid() throws Exception{

         mockMvc.perform(get(URL_CONTROLLER)
                         .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$..news", notNullValue()))
                 .andExpect(status().isOk());
     }

    @Test
     public void shouldReturnCreatedStatusAndNewsResponseWhenRoleAdminAreValid() throws Exception{

        mockMvc.perform(post(URL_CONTROLLER).header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest("news test", "content test", "imageTest", 1L))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("news test")))
                .andExpect(jsonPath("$.content", equalTo("content test")))
                .andExpect(jsonPath("$.image", equalTo("imageTest")))
                .andExpect(jsonPath("$.categoryId", equalTo(1)))
                .andExpect(status().isCreated());

    }

     @Test
     public void shouldReturnForbiddenStatusWhenRoleAdminAreNotValidInCreate() throws Exception{

         mockMvc.perform(post(URL_CONTROLLER).header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                         .content(createRequest("news test", "content test", "imageTest", 1L))
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isForbidden());

     }

     @Test
     public void shouldReturnBadRequestInCreateWhenRoleAdminAreValid() throws Exception{

         mockMvc.perform(post(URL_CONTROLLER).header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                         .content(createRequest(null, "content test", "imageTest", 1L))
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isBadRequest());

     }

    @Test
    public void shouldReturnNewsById() throws Exception{
        NewsEntity news = createNewsParams("find by id test", "content find by id", "image find by id", 1L);

        mockMvc.perform(get(URL_CONTROLLER + "/" + news.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("find by id test")))
                .andExpect(jsonPath("$.content", equalTo("content find by id")))
                .andExpect(jsonPath("$.image", equalTo("image find by id")))
                .andExpect(jsonPath("$.categoryId", equalTo(1)))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnNotFoundWhenIdNoExist() throws Exception{
        mockMvc.perform(get(URL_CONTROLLER + "/1000")
                        .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnOkStatusAndNewsResponseUpdateWhenRoleAdminAreValid() throws Exception{
        NewsEntity news = createNewsParams("update test", "content update", "image update", 1L);
        mockMvc.perform(put(URL_CONTROLLER + "/" + news.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .content(createRequest("news update", "content update", "image update", 2L))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", equalTo("news update")))
                .andExpect(jsonPath("$.content", equalTo("content update")))
                .andExpect(jsonPath("$.image", equalTo("image update")))
                .andExpect(jsonPath("$.categoryId", equalTo(2)))
                .andExpect(status().isOk());
    }

     @Test
     public void shouldReturnNotFoundWhenIdUpdateNotExist() throws Exception{
         mockMvc.perform(put(URL_CONTROLLER + "/10000")
                         .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                         .content(createRequest("news update", "content update", "image update", 2L))
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isNotFound());
     }

     @Test
     public void shouldReturnForbiddenStatusWhenRoleAdminAreNotValidInUpdate() throws Exception{
         NewsEntity news = createNewsParams("forbidden update", "content forbidden", "image forbidden", 1L);
         mockMvc.perform(put(URL_CONTROLLER + "/" + news.getId())
                         .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                         .content(createRequest("news update", "content update", "image update", 2L))
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isForbidden());
     }

    @Test
    public void shouldReturnOkStatusWhenAdminRoleAreValidDeleteNews() throws Exception{
        NewsEntity news = createNewsParams("delete test", "content delete", "image delete", 1L);

        mockMvc.perform(delete(URL_CONTROLLER + "/" + news.getId())
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

     @Test
     public void shouldReturnNotFoundStatusWhenIdNotExistInDeleteNews() throws Exception{
         mockMvc.perform(delete(URL_CONTROLLER + "/1000")
                         .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isNotFound());
     }

     @Test
     public void shouldReturnForbiddenStatusWhenAdminRoleAreNotValidDeleteNews() throws Exception{
         NewsEntity news = createNewsParams("delete forbidden", "content delete forbidden", "image delete forbidden", 1L);

         mockMvc.perform(delete(URL_CONTROLLER + "/" + news.getId())
                         .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForStandardUser())
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isForbidden());
     }

    @Test
    public void shouldReturnCommentsListAndStatusOk() throws Exception{
        NewsEntity news = createNewsParams("coments test", "content comments", "image comments", 1L);
        CommentEntity comment = createComment(news);

        mockMvc.perform(get(URL_CONTROLLER + "/" + news.getId() + "/comments")
                .header(HttpHeaders.AUTHORIZATION, BEARER + getAuthorizationTokenForAdminUser())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$..comment", notNullValue()))
                .andExpect(status().isOk());

    }

     private String createRequest(String name, String content, String image, Long categoryId) throws JsonProcessingException {

         return objectMapper.writeValueAsString(NewsEntity.builder()
                 .name(name)
                 .content(content)
                 .image(image)
                 .categoryId(categoryId)
                 .build());
     }
}
