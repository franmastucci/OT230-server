package com.alkemy.ong.service;

import com.alkemy.ong.models.request.UserUpdateRequest;
import com.alkemy.ong.models.request.AuthRequest;
import com.alkemy.ong.models.request.UserRequest;
import com.alkemy.ong.models.response.AuthResponse;
import com.alkemy.ong.models.response.UserResponse;

public interface UserService {

    UserResponse register(UserRequest userRequest);
    void deleteUser(Long id);
    void updateUser(Long id, UserUpdateRequest request);
    AuthResponse login(AuthRequest authRequest);
}
