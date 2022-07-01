package com.alkemy.ong.models.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class UpdateMemberRequest {

   @NotEmpty @NotBlank
   private String name;

   @NotEmpty @NotBlank
   private String facebook;

  @NotEmpty @NotBlank
   private String instagram;

   @NotEmpty @NotBlank
   private String linkedIn;

   @NotEmpty @NotBlank
   private String description;

   @NotEmpty @NotBlank
   private String image;
}
