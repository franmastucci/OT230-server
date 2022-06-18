package com.alkemy.ong.models.request;

import javax.validation.constraints.Email;
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
public class AuthenticationRequest {
	
	@NonNull
	@Email(message = "Incorrect email")
	private String username;
	@NonNull
	@NotEmpty(message = "Empty password")
	private String password;
		
}
