package com.example.users_and_subscriptions.service.impl;

import com.example.users_and_subscriptions.builder.SubscriptionBuilder;
import com.example.users_and_subscriptions.domain.SubscriptionDomain;
import com.example.users_and_subscriptions.dto.SubscriptionDTO;
import com.example.users_and_subscriptions.entity.SubscriptionEntity;
import com.example.users_and_subscriptions.entity.UserEntity;
import com.example.users_and_subscriptions.repository.SubscriptionRepository;
import com.example.users_and_subscriptions.repository.UserRepository;
import com.example.users_and_subscriptions.service.SubscriptionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Autowired
    SubscriptionBuilder subscriptionBuilder;
    @Autowired
    UserRepository userRepository;

    @Override
    public SubscriptionDomain createSubscription(Long userId, SubscriptionDTO subscriptionDTO) {
        log.info("Call SubscriptionServiceImpl.createSubscription({})", subscriptionDTO);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User with ID {} not found", userId);
                    return new RuntimeException("user not found");
                });

        SubscriptionEntity subscriptionEntity = subscriptionBuilder.toEntity(subscriptionDTO);
        subscriptionEntity.setUser(user);

        subscriptionEntity.setCreatedAt(new Date());
        subscriptionEntity = subscriptionRepository.save(subscriptionEntity);

        log.info("Subscription create ID: {}", subscriptionDTO.getId());
        return subscriptionBuilder.toDomain(subscriptionEntity);    }
    @Transactional
    @Override
    public List<SubscriptionDTO> getSubscriptionsByUserId(Long userId, int page, int size) {
        log.info("Call SubscriptionServiceImpl.getSubscriptionsByUserId({})", userId);

        Pageable pageable = PageRequest.of(page, size);
        log.debug("Use Pageable: {}", pageable);

        Page<SubscriptionEntity> subscriptions = subscriptionRepository.findByUserId(userId,pageable );

        List<SubscriptionDTO> subscriptionDomains = subscriptions.stream()
                .map(subscriptionBuilder::toDto)
                .collect(Collectors.toList());

        log.info("Subscriptions found for a user with the ID:{}", subscriptionDomains.size(), userId);
        return subscriptionDomains;
    }

    @Override
    public void deleteSubscription(Long userId, Long subscriptionId) {
        log.info("Call SubscriptionServiceImpl.deleteSubscription({}) {}", subscriptionId, userId);
        SubscriptionEntity subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> {
                    log.warn("Subscription with ID {} not found", subscriptionId);
                    return new RuntimeException("Subscription not found");
                });

        if (subscription.getUser().getId() != userId) {
            log.warn("Attempt to delete a subscription with ID that does not belong to a user with ID {}", subscriptionId, userId);
            throw new RuntimeException("The subscription does not belong to the user");
        }

        log.info("Subscription with ID {} successfully deleted for user with ID ", subscriptionId, userId);
        subscriptionRepository.deleteById(subscriptionId);
    }

    @Override
    public List<SubscriptionDTO> getTopSubscriptions(int page, int size) {
        log.info("Call SubscriptionServiceImpl.getTopSubscriptions()");

        Pageable pageable = PageRequest.of(page, Math.min(size, 3)); // Ограничиваем максимум 3

        List<Object[]> results = subscriptionRepository.findTopSubscriptions(pageable);
        log.info("The results were obtained for: {} records", results.size());

        List<SubscriptionDTO> topSubscriptions = new ArrayList<>();

        for (Object[] result : results) {
            String serviceName = (String) result[0];
            Long userCount = (Long) result[1];

            log.debug("Subscription: service = {}, number of users = {}", serviceName, userCount);

            SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
            subscriptionDTO.setServiceName(serviceName);

            topSubscriptions.add(subscriptionDTO);
        }

        log.info("The TOP 3 subscriptions are received and converted to SubscriptionDTO.");

        return topSubscriptions;
    }


}
