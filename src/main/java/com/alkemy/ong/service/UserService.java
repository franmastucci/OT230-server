package com.alkemy.ong.service;

import com.alkemy.ong.models.request.UserRequest;
import com.alkemy.ong.models.response.UserResponse;

public interface UserService {

    public UserResponse register(UserRequest userRequest);
}
