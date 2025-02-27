package com.example.users_and_subscriptions.controller;

import com.example.users_and_subscriptions.builder.UserBuilder;
import com.example.users_and_subscriptions.domain.UserDomain;
import com.example.users_and_subscriptions.dto.ResponseDTO;
import com.example.users_and_subscriptions.dto.UserDTO;
import com.example.users_and_subscriptions.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {

    UserService userService;
    UserBuilder userBuilder;

    @PostMapping
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO userDTO) {
        try {
            log.info("Call UserController.createUser({})", userDTO);
            UserDomain userDomain = userService.createUser(userDTO);
            UserDTO createdUserDTO = userBuilder.toDto(userDomain);

            log.info("");
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(true, null));

        } catch (Exception e) {
            log.error("Error during user creation: {}", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(false, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.info("Call UserController.getUser{}", id);
        UserDomain userDomain = userService.getUserById(id);
        return ResponseEntity.ok(userBuilder.toDto(userDomain));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UserDTO userDTO) {
        try {
            log.info("Call UserController.updateUser({})", userDTO.getId());
            UserDomain updatedUser = userService.updateUser(userDTO);
            return ResponseEntity.ok(new ResponseDTO(true, "User updated successfully"));
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(false, "Error updating user: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable Long id) {
        try {
            log.info("Call UserController.deleteUser({})", id);
            userService.deleteUser(id);
            return ResponseEntity.ok(new ResponseDTO(true, "User deleted successfully"));
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(false, "Error deleting user: " + e.getMessage()));
        }
    }
}
