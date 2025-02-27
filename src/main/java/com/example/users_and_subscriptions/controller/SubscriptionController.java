package com.example.users_and_subscriptions.controller;

import com.example.users_and_subscriptions.builder.SubscriptionBuilder;
import com.example.users_and_subscriptions.domain.SubscriptionDomain;
import com.example.users_and_subscriptions.dto.ResponseDTO;
import com.example.users_and_subscriptions.dto.SubscriptionDTO;
import com.example.users_and_subscriptions.service.SubscriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SubscriptionController {

     SubscriptionService subscriptionService;
     SubscriptionBuilder subscriptionBuilder;

    @PostMapping("/users/{userId}/subscriptions")
    public ResponseEntity<ResponseDTO> addSubscription(@PathVariable Long userId, @RequestBody SubscriptionDTO subscriptionDTO) {
        try {
            log.info("Call SubscriptionController.addSubscription({})", userId, subscriptionDTO);
            SubscriptionDomain subscription = subscriptionService.createSubscription(userId, subscriptionDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(true, "Subscription added successfully"));
        } catch (Exception e) {
            log.error("Error adding subscription: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(false, "Error adding subscription: " + e.getMessage()));
        }
    }

    @GetMapping("/users/subscriptions")
    public ResponseEntity<List<SubscriptionDTO>> getUserSubscriptions(
            @RequestParam Long userId,
            @ParameterObject Pageable pageable) {  // <-- ДЛЯ КОРРЕКТНОЙ ОТРИСОВКИ В SWAGGER
        log.info("GET /users/subscriptions - Получение подписок пользователя {}", userId);
        List<SubscriptionDTO> subscriptions = subscriptionService.getSubscriptionsByUserId(userId, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(subscriptions);

    }
    @GetMapping("/top")
    public ResponseEntity<ResponseDTO> getTopSubscriptions(@ParameterObject Pageable pageable) {
        try {
            log.info("Call SubscriptionController.getTopSubscriptions");

            List<SubscriptionDTO> topSubscriptions = subscriptionService.getTopSubscriptions( pageable.getPageNumber(), pageable.getPageSize());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(true, "get subscription successfully"));        }
        catch (Exception e) {
            log.error("not found subscription: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(false, "not foundsubscription: " + e.getMessage()));
        }

    }

    @DeleteMapping("/users/{userId}/subscriptions/{subscriptionId}")
    public ResponseEntity<ResponseDTO> deleteSubscription(@PathVariable Long userId, @PathVariable Long subscriptionId) {
        try {
            log.info("Call SubscriptionController.deleteSubscription({})", userId, subscriptionId);
            subscriptionService.deleteSubscription(userId, subscriptionId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ResponseDTO(true, "Subscription deleted successfully"));
        } catch (Exception e) {
            log.error("Error deleting subscription: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(false, "Error deleting subscription: " + e.getMessage()));
        }
    }

}
