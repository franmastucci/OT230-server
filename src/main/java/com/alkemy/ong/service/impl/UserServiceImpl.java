package com.alkemy.ong.service.impl;

import com.alkemy.ong.auth.utility.RoleEnum;
import com.alkemy.ong.models.entity.RoleEntity;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.models.mapper.UserMapper;
import com.alkemy.ong.models.request.UserRequest;
import com.alkemy.ong.models.response.UserResponse;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponse register(UserRequest userRequest) throws UsernameNotFoundException{
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new UsernameNotFoundException("User already exists");
        }
        List<RoleEntity> roles = roleRepository.findByName(RoleEnum.USER.getFullRoleName());
        if (roles.isEmpty()) {
            throw new NullPointerException();
        }
        UserEntity userEntity = userMapper.toUserEntity(userRequest, roles);
        userRepository.save(userEntity);
        return userMapper.toUserResponse(userEntity);
    }
}
