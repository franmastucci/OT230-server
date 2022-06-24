package com.alkemy.ong.service.impl;

import com.alkemy.ong.auth.service.JwtUtils;
import com.alkemy.ong.auth.service.impl.UserDetailsServiceImpl;
import com.alkemy.ong.auth.utility.RoleEnum;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.models.entity.RoleEntity;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.models.mapper.UserMapper;
import com.alkemy.ong.models.request.AuthRequest;
import com.alkemy.ong.models.request.UserRequest;
import com.alkemy.ong.models.request.UserUpdateRequest;
import com.alkemy.ong.models.response.AuthResponse;
import com.alkemy.ong.models.response.UserDetailsResponse;
import com.alkemy.ong.models.response.UserResponse;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.EmailService;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserResponse register(UserRequest userRequest) throws UsernameNotFoundException, IOException {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new UsernameNotFoundException("User already exists");
        }
        Set<RoleEntity> roles = roleRepository.findByName(RoleEnum.USER.getFullRoleName());
        if (roles.isEmpty()) {
            throw new NullPointerException();
        }
        UserEntity userEntity = userMapper.toUserEntity(userRequest, roles);
        userRepository.save(userEntity);
        emailService.sendEmailTo(userEntity.getEmail());
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

    public AuthResponse login(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
            String token = jwtUtils.generateToken(userDetails);
            return AuthResponse.builder()
                    .email(authRequest.getEmail())
                    .token(token)
                    .build();
        } catch (Exception e) {
            return AuthResponse.builder().ok(false).build();
        }
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
