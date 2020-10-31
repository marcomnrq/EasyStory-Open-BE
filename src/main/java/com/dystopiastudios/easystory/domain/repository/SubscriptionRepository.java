package com.dystopiastudios.easystory.domain.repository;

import com.dystopiastudios.easystory.domain.model.Bookmark;
import com.dystopiastudios.easystory.domain.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Page<Subscription> findByUserId(Long userId, Pageable pageable);

    Page<Subscription> findBySubscribedId(Long subscribedId, Pageable pageable);

    Optional<Subscription> findByUserIdAndSubscribedId(Long userId, Long subscribedId);

    Optional<Subscription> findByIdAndUserId(Long id, Long userId);
}
