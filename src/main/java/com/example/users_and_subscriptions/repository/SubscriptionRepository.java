package com.example.users_and_subscriptions.repository;

import com.example.users_and_subscriptions.entity.SubscriptionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {
    @Query("SELECT s.serviceName, COUNT(s.user) " +
            "FROM SubscriptionEntity s " +
            "GROUP BY s.serviceName " +
            "ORDER BY COUNT(s.user) DESC")
    List<Object[]> findTopSubscriptions(Pageable pageable);

    @Query(value = "SELECT * FROM subscriptions WHERE user_id = :userId", nativeQuery = true)
    Page<SubscriptionEntity> findByUserId(@Param("userId") Long userId, Pageable pageable);



}
