package com.alkemy.ong.service;

import com.alkemy.ong.models.request.UserUpdateRequest;
import com.alkemy.ong.models.response.UserDetailsResponse;

import java.util.List;

public interface UserService {

    void deleteUser(Long id);
    void updateUser(Long id, UserUpdateRequest request);
    List<UserDetailsResponse> getUsers();
}
