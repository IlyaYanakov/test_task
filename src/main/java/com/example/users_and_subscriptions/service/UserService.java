package com.example.users_and_subscriptions.service;

import com.example.users_and_subscriptions.domain.UserDomain;
import com.example.users_and_subscriptions.dto.UserDTO;

public interface UserService {
    UserDomain getUserById(Long id);
    UserDomain createUser(UserDTO userDTO);
    UserDomain updateUser(UserDTO userDTO);
    void deleteUser(Long id);

}
