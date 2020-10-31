package com.dystopiastudios.easystory.service;

import com.dystopiastudios.easystory.domain.model.Subscription;
import com.dystopiastudios.easystory.domain.model.User;
import com.dystopiastudios.easystory.domain.repository.SubscriptionRepository;
import com.dystopiastudios.easystory.domain.repository.UserRepository;
import com.dystopiastudios.easystory.domain.service.SubscriptionService;
import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Page<Subscription> getAllSubscriptionsByUserId(Long userId, Pageable pageable) {
        return subscriptionRepository.findByUserId(userId, pageable);
    }

    @Override
    public Page<Subscription> getAllSubscribersByUserId(Long userId, Pageable pageable) {
        return subscriptionRepository.findBySubscribedId(userId, pageable);
    }

    @Override
    public Subscription getSubscriptionByUserIdAndSubscribedId(Long userId, Long subscribedId) {
        return subscriptionRepository.findByUserIdAndSubscribedId(userId, subscribedId).orElseThrow(()->new ResourceNotFoundException("Subscription not found with User Id" + userId +  " and SubscribedId" + subscribedId));
    }

    @Override
    public Subscription createSubscription(Long userId, Long subscribedId, Subscription subscription) {
        if(userId == subscribedId) {
            throw new IllegalArgumentException("No se puede suscribir a el mismo");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        User subscribed = userRepository.findById(subscribedId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", subscribedId));
        Optional<Subscription> existingSubscription = subscriptionRepository.findByUserIdAndSubscribedId(userId, subscribedId);
        if (!existingSubscription.isEmpty()){
            throw new IllegalArgumentException("El usuario ya esta suscrito");
        }
        // Si to do fue correcto entonces...
        subscription.setUser(user);
        subscription.setSubscribed(subscribed);

        return subscriptionRepository.save(subscription);
    }

    @Override
    public ResponseEntity<?> deleteSubscription(Long userId, Long subscribedId) {
        Subscription subscription = subscriptionRepository.findByUserIdAndSubscribedId(userId, subscribedId).orElseThrow(()->
                new ResourceNotFoundException("User", "Id", userId));
        subscriptionRepository.delete(subscription);
        return ResponseEntity.ok().build();
    }

    @Override
    public Page<Subscription> getAllSubscriptions(Pageable pageable) {
        return subscriptionRepository.findAll(pageable);
    }
}
