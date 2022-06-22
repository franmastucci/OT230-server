package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.RoleEntity;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.models.request.UserRequest;
import com.alkemy.ong.models.response.UserDetailsResponse;
import com.alkemy.ong.models.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity toUserEntity(UserRequest userRequest, Set<RoleEntity> roles) {
        return UserEntity.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .roleId(roles)
                .softDelete(false)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    public UserResponse toUserResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .build();
    }

    public UserDetailsResponse userToUserDetail(UserEntity update) {
        return UserDetailsResponse.builder()
           .firstName(update.getFirstName())
           .lastName(update.getLastName())
           .email(update.getEmail())
           .photo(update.getPhoto())
           .timestamp(update.getTimestamp())
           .build();
    }

    public List<UserDetailsResponse> usersToUserDetailsList(List<UserEntity> users) {
        List<UserDetailsResponse> list = new ArrayList<>();

        for(UserEntity user : users) {
            list.add( userToUserDetail(user) );
        }

        return list;
    }
}
