package com.example.users_and_subscriptions.builder;

import com.example.users_and_subscriptions.domain.UserDomain;
import com.example.users_and_subscriptions.dto.UserDTO;
import com.example.users_and_subscriptions.entity.UserEntity;

public interface UserBuilder {
    UserEntity toEntity(UserDTO userDTO);
    UserDTO toDto(UserEntity user);
    UserDomain toDomain(UserEntity user);
    UserDTO toDto(UserDomain userDomain);

    UserEntity toEntity(UserDomain userDomain);
}
