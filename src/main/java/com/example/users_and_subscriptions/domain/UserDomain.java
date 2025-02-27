package com.example.users_and_subscriptions.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDomain {
    Long id;
    String name;
    String email;


}
