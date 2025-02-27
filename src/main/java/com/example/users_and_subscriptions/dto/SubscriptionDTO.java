package com.example.users_and_subscriptions.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionDTO {
    Long id;
    String serviceName;
    Long userId;
}
