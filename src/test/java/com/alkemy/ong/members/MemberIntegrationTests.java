package com.alkemy.ong.members;

import com.alkemy.ong.models.entity.MemberEntity;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.alkemy.ong.context.ProfileTest.ADMIN_AUTH;
import static com.alkemy.ong.context.ProfileTest.USER_AUTH;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class MemberIntegrationTests extends MemberContextTests {

   @Autowired
   private MockMvc mockMvc;

   private static final String MEMBER_PATH = "/members";
   private static final String PAGE_PATH = "/members/get-all";
   private static final String ID_PATH = "/members/{id}";
   private static final String RANDOM_NAME = "name";
   private static final String BAD_NAME = "numeric";
   private static final String IG = "instagram";
   private static final String EMPTY_STRING = " ";
   private static final String EQUAL = "equal";

   @Before
   public void setup() {
      createUtilMember();
   }

   @Test
   @WithUserDetails(ADMIN_AUTH)
   public void getAllMemberWithAdminAuth() throws Exception {
      mockMvc.perform(get(MEMBER_PATH))
         .andExpect(content().contentType(APPLICATION_JSON))
         .andExpect(jsonPath("$", isA(List.class)))
         .andExpect(status().isOk());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void getAllMemberWithoutAuth() throws Exception {
      mockMvc.perform(get(MEMBER_PATH))
         .andDo(print())
         .andExpect(status().isForbidden());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void getPageWithoutParamRequest() throws Exception {
      mockMvc.perform(get(PAGE_PATH))
         .andExpect(content().contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(jsonPath("$.members", hasSize(10)))
         .andExpect(jsonPath("$.next", equalTo("/get-all?page=2")));
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void getPageWithParamRequestEqualToTwo() throws Exception {
      mockMvc.perform(get(PAGE_PATH)
            .param("page", String.valueOf(2)))
         .andExpect(content().contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(jsonPath("$.members", hasSize(10)))
         .andExpect(jsonPath("$.previous", equalTo("/get-all?page=1")));
   }

   @Test
   @WithUserDetails(ADMIN_AUTH)
   public void getPageWithoutAuth() throws Exception {
      mockMvc.perform(get(PAGE_PATH))
         .andDo(print())
         .andExpect(status().isForbidden());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void createMember() throws Exception {
      MemberEntity member = buildMember(RANDOM_NAME, IG);

      mockMvc.perform(post(MEMBER_PATH)
            .content(createRequest(member)).contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(jsonPath("$.name", equalTo(member.getName())))
         .andExpect(jsonPath("$.facebook", equalTo("facebook")))
         .andExpect(jsonPath("$.instagram", equalTo("instagram")))
         .andExpect(jsonPath("$.linkedIn", equalTo("linkedIn")))
         .andExpect(status().isOk());
   }

   @Test
   @WithUserDetails(ADMIN_AUTH)
   public void createMemberWithoutAuth() throws Exception {
      MemberEntity member = buildMember(RANDOM_NAME, IG);

      mockMvc.perform(post(MEMBER_PATH)
            .content(createRequest(member)).contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(status().isForbidden());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void createMemberWithAlphanumericName() throws Exception {
      MemberEntity member = buildMember(BAD_NAME, IG);

      mockMvc.perform(post(MEMBER_PATH)
            .content(createRequest(member)).contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(jsonPath("$.name", equalTo("The name has to contain only letters")))
         .andExpect(status().isBadRequest());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void createMemberWithEmptyString() throws Exception {
      MemberEntity member = buildMember(RANDOM_NAME, EMPTY_STRING);

      mockMvc.perform(post(MEMBER_PATH)
            .content(createRequest(member)).contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(jsonPath("$.instagram", equalTo("must not be blank")))
         .andExpect(status().isBadRequest());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void updateMember() throws Exception {
      MemberEntity member = buildMember(RANDOM_NAME, IG);

      mockMvc.perform(put(ID_PATH, "1")
            .accept(APPLICATION_JSON)
            .content( createRequest(member) ).contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(jsonPath("$.instagram", equalTo("instagram")))
         .andExpect(jsonPath("$.linkedIn", equalTo("linkedIn")))
         .andExpect(jsonPath("$.facebook", equalTo("facebook")))
         .andExpect(status().isOk());
   }

   @Test
   @WithUserDetails(ADMIN_AUTH)
   public void updateMemberWithoutAuth() throws Exception {
      MemberEntity member = buildMember(RANDOM_NAME, IG);

      mockMvc.perform(put(ID_PATH, "1")
            .accept(APPLICATION_JSON)
            .content( createRequest(member) ).contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(status().isForbidden());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void updateMemberWithNonexistentId() throws Exception {
      MemberEntity member = buildMember(RANDOM_NAME, IG);

      mockMvc.perform(put(ID_PATH, "-1")
            .content( createRequest(member) ).contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(jsonPath("$.message", equalTo("Member not found with id: -1")))
         .andExpect(status().isNotFound());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void updateMemberWithRepeatedName() throws Exception {
      MemberEntity member = buildMember(EQUAL, IG);

      mockMvc.perform(put(ID_PATH, "21")
            .content( createRequest(member) ).contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(jsonPath("$.message", equalTo("There are a member with the same name")))
         .andExpect(status().isConflict());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void updateMemberWithAlphanumericName() throws Exception {
      MemberEntity member = buildMember(BAD_NAME, IG);

      mockMvc.perform(put(ID_PATH, "1")
            .content( createRequest(member) ).contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(jsonPath("$.name", equalTo("The name has to contain only letters")))
         .andExpect(status().isBadRequest());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void updateMemberWithEmptyString() throws Exception {
      MemberEntity member = buildMember(RANDOM_NAME, EMPTY_STRING);

      mockMvc.perform(put(ID_PATH, "1")
            .content( createRequest(member) ).contentType(APPLICATION_JSON))
         .andDo(print())
         .andExpect(jsonPath("$.instagram", equalTo("must not be blank")))
         .andExpect(status().isBadRequest());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void deleteMember() throws Exception {
      MemberEntity member = buildAndSave(RANDOM_NAME);

      mockMvc.perform(delete(ID_PATH, member.getId()))
         .andDo(print())
         .andExpect(status().isOk());
   }

   @Test
   public void deleteMemberWithoutAuth() throws Exception {
      mockMvc.perform(delete(ID_PATH, "2"))
         .andDo(print())
         .andExpect(status().isForbidden());
   }

   @Test
   @WithUserDetails(USER_AUTH)
   public void deleteMemberWithNonexistentId() throws Exception {
      mockMvc.perform(delete(ID_PATH, "-1"))
         .andDo(print())
         .andExpect(jsonPath("$.message", equalTo("Member not found with id: -1")))
         .andExpect(status().isNotFound());
   }
}