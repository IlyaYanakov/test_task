package com.example.users_and_subscriptions.service;

import com.example.users_and_subscriptions.domain.SubscriptionDomain;
import com.example.users_and_subscriptions.dto.SubscriptionDTO;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDomain createSubscription(Long userId, SubscriptionDTO subscriptionDTO);
    List<SubscriptionDTO> getSubscriptionsByUserId(Long userId, int page, int size);
    void deleteSubscription(Long userId, Long subscriptionId);
    List<SubscriptionDTO> getTopSubscriptions(int page, int size);


    }
