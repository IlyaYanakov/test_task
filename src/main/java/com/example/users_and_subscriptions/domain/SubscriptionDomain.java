package com.example.users_and_subscriptions.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionDomain {
    Long id;
    String serviceName;
    Long userId;
    Long userCount;


}
