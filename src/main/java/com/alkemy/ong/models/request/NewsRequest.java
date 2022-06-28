package com.alkemy.ong.models.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequest {
	
	@NotBlank(message = "Name can't be null or empty")
	private String name;

	@NotBlank(message = "Content can't be null or empty")
	private String content;
	

	@NotBlank(message = "Image can't be null or empty")
	private String image;
	
	@NotNull(message = "CategoryId can't be null")
	private Long categoryId;	

}	
