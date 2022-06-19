package com.alkemy.ong.service.impl;

import com.alkemy.ong.auth.utility.RoleEnum;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.models.entity.RoleEntity;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.models.mapper.UserMapper;
import com.alkemy.ong.models.request.UserRequest;
import com.alkemy.ong.models.request.UserUpdateRequest;
import com.alkemy.ong.models.response.UserResponse;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(UserRequest userRequest) throws UsernameNotFoundException{
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new UsernameNotFoundException("User already exists");
        }
        Set<RoleEntity> roles = roleRepository.findByName(RoleEnum.USER.getFullRoleName());
        if (roles.isEmpty()) {
            throw new NullPointerException();
        }
        UserEntity userEntity = userMapper.toUserEntity(userRequest, roles);
        userRepository.save(userEntity);
        if (userEntity != null){
            emailService.sendEmailTo(userEntity.getEmail());
        }
        return userMapper.toUserResponse(userEntity);
    }

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

    public UserEntity getById(Long id) {
        return userRepository.findById(id)
           .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
