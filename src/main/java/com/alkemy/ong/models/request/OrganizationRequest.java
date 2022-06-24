package com.alkemy.ong.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRequest {
    
    @NonNull
    @NotEmpty(message = "the first name can't be null")
    @NotBlank(message = "the first name can't  be blank")
    private String name;

    @NonNull
    @NotEmpty(message = "the image can't be null")
    @NotBlank(message = "the image can't  be blank")
    private String image;

    private String address;
    private Integer phone;

    @NonNull
    @NotEmpty(message = "the email can't be null")
    @NotBlank(message = "the email can't  be blank")
    private String email;

    @NonNull
    @NotEmpty(message = "the welcome text can't be null")
    @NotBlank(message = "the welcome text can't  be blank")
    private String welcomeText;

    private String aboutUsText;
}
