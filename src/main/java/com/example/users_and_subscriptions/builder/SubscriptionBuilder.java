package com.example.users_and_subscriptions.builder;

import com.example.users_and_subscriptions.domain.SubscriptionDomain;
import com.example.users_and_subscriptions.dto.SubscriptionDTO;
import com.example.users_and_subscriptions.entity.SubscriptionEntity;

public interface SubscriptionBuilder {
    SubscriptionEntity toEntity(SubscriptionDTO subscriptionDTO);
    SubscriptionDTO toDto(SubscriptionEntity subscription);
    SubscriptionDomain toDomain(SubscriptionEntity subscription);
    SubscriptionDTO toDto(SubscriptionDomain subscriptionDomain);

    SubscriptionEntity toEntity(SubscriptionDomain subscriptionDomain);
}
