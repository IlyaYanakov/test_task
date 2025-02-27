package com.example.users_and_subscriptions.builder.impl;

import com.example.users_and_subscriptions.builder.SubscriptionBuilder;
import com.example.users_and_subscriptions.builder.UserBuilder;
import com.example.users_and_subscriptions.domain.SubscriptionDomain;
import com.example.users_and_subscriptions.domain.UserDomain;
import com.example.users_and_subscriptions.dto.SubscriptionDTO;
import com.example.users_and_subscriptions.entity.SubscriptionEntity;
import com.example.users_and_subscriptions.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SubscriptionBuilderImpl implements SubscriptionBuilder {
    @Autowired
    UserService userService;
    @Autowired
    UserBuilder userBuilder;

    @Override
    public SubscriptionEntity toEntity(SubscriptionDTO subscriptionDTO) {
        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setServiceName(subscriptionDTO.getServiceName());
        UserDomain user = userService.getUserById(subscriptionDTO.getUserId());
        subscription.setUser(userBuilder.toEntity(user));
        return subscription;    }

    @Override
    public SubscriptionDTO toDto(SubscriptionEntity subscription) {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setId(subscription.getId());
        subscriptionDTO.setServiceName(subscription.getServiceName());
        subscriptionDTO.setUserId(subscription.getUser().getId());
        return subscriptionDTO;    }

    @Override
    public SubscriptionDomain toDomain(SubscriptionEntity subscription) {
        SubscriptionDomain subscriptionDomain = new SubscriptionDomain();
        subscriptionDomain.setId(subscription.getId());
        subscriptionDomain.setServiceName(subscription.getServiceName());
        subscriptionDomain.setUserId(subscription.getUser().getId());
        return subscriptionDomain;
    }

    @Override
    public SubscriptionDTO toDto(SubscriptionDomain subscriptionDomain) {
        return new SubscriptionDTO(
                subscriptionDomain.getId(),
                subscriptionDomain.getServiceName(),
                subscriptionDomain.getUserId()
        );
    }

    @Override
    public SubscriptionEntity toEntity(SubscriptionDomain subscriptionDomain) {
        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setId(subscriptionDomain.getId());
        subscription.setServiceName(subscriptionDomain.getServiceName());
        UserDomain user = userService.getUserById(subscriptionDomain.getUserId());
        subscription.setUser(userBuilder.toEntity(user));
        return subscription;
    }
}
