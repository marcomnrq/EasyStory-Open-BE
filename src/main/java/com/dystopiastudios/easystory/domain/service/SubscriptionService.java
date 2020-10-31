package com.dystopiastudios.easystory.domain.service;

import com.dystopiastudios.easystory.domain.model.Post;
import com.dystopiastudios.easystory.domain.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SubscriptionService {
    Page<Subscription> getAllSubscriptionsByUserId(Long userId, Pageable pageable);

    Page<Subscription> getAllSubscribersByUserId(Long userId, Pageable pageable);

    Subscription getSubscriptionByUserIdAndSubscribedId(Long userId, Long subscribedId);

    Subscription createSubscription(Long userId, Long subscribedId, Subscription subscription);

    ResponseEntity<?> deleteSubscription(Long userId, Long subscribedId);

    Page<Subscription> getAllSubscriptions(Pageable pageable);

}
