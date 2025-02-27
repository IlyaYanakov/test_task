package com.example.users_and_subscriptions.service.impl;

import com.example.users_and_subscriptions.builder.UserBuilder;
import com.example.users_and_subscriptions.domain.UserDomain;
import com.example.users_and_subscriptions.dto.UserDTO;
import com.example.users_and_subscriptions.entity.UserEntity;
import com.example.users_and_subscriptions.repository.UserRepository;
import com.example.users_and_subscriptions.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserBuilder userBuilder;


    @Override
    public UserDomain getUserById(Long id) {
        log.info("Call UserServiceImpl.getUserById({})", id);
        return userRepository.findById(id)
                .map(user -> {
                    log.info("The user with the ID {} has been found: {}", id, user);
                    return userBuilder.toDomain(user);
                })
                .orElseThrow(() -> {
                    log.warn("User with ID {} not found", id);
                    return new RuntimeException("User not found");
                });   }

    @Override
    public UserDomain createUser(UserDTO userDTO) {
        log.info("Call UserServiceImpl.createUser({})", userDTO);
        UserEntity userEntity = userBuilder.toEntity(userDTO);
        userEntity.setCreatedAt(new Date());
        userEntity = userRepository.save(userEntity);

        log.info("The user was created with the Id: {}", userDTO.getId());

        return userBuilder.toDomain(userEntity);    }

    @Override
    public UserDomain updateUser(UserDTO userDTO) {
        log.info("Call UserServiceImpl.updateUser({})", userDTO.getId());

        UserEntity existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> {
                    log.error("User with Id {} not found", userDTO.getId());
                    return new RuntimeException("User not found");
                });

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());

        existingUser.setUpdatedAt(new Date());

        UserEntity updatedUser = userRepository.save(existingUser);
        log.info("User with Id {} updated", userDTO.getId());

        return userBuilder.toDomain(updatedUser);    }

    @Override
    public void deleteUser(Long id) {
        log.info("Call UserServiceImpl.deleteUser({}):", id);

        if (!userRepository.existsById(id)) {
            log.error("An attempt to delete a non-existent user with an Id: {}", id);
            throw new RuntimeException("User not found");
        }

        log.info("User with Id {} successfully deleted", id);
        userRepository.deleteById(id);
    }
}
