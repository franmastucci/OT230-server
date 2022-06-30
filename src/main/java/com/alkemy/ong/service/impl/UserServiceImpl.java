package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.models.mapper.UserMapper;
import com.alkemy.ong.models.request.UserUpdateRequest;
import com.alkemy.ong.models.response.UserDetailsResponse;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void deleteUser(Long id) {
        UserEntity user = getById(id);
        userRepository.delete(user);
    }

    @Override
    public void updateUser(Long id, UserUpdateRequest request) {
        UserEntity user = getById(id);

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoto(request.getPhoto());

        userRepository.save(user);
    }

    private UserEntity getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<UserDetailsResponse> getUsers() {
        List<UserEntity> users = userRepository.findBySoftDelete();
        if (users.isEmpty()) {
            throw new NullPointerException("The user list is empty");
        }
        return userMapper.usersToUserDetailsList(users);
    }
}
