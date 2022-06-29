package com.alkemy.ong.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlidesRequest {

    private String imageUrl;
    private String text;
    private Integer sort;
    private Long organizationId;
}
