package com.example.users_and_subscriptions.builder.impl;

import com.example.users_and_subscriptions.builder.UserBuilder;
import com.example.users_and_subscriptions.domain.UserDomain;
import com.example.users_and_subscriptions.dto.UserDTO;
import com.example.users_and_subscriptions.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserBuilderImpl implements UserBuilder {
    @Override
    public UserEntity toEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setName(userDTO.getName());
        return userEntity;
    }

    @Override
    public UserDTO toDto(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        return userDTO;
    }

    @Override
    public UserDomain toDomain(UserEntity user) {
        UserDomain userDomain = new UserDomain();
        userDomain.setId(user.getId());
        userDomain.setEmail(user.getEmail());
        userDomain.setName(user.getName());
        return userDomain;
    }

    @Override
    public UserDTO toDto(UserDomain userDomain) {
        return new UserDTO(
                userDomain.getId(),
                userDomain.getName(),
                userDomain.getEmail()
        );
    }

    @Override
    public UserEntity toEntity(UserDomain userDomain) {
        UserEntity user = new UserEntity();
        user.setId(userDomain.getId());
        user.setName(userDomain.getName());
        user.setEmail(userDomain.getEmail());
        return user;
    }
}
