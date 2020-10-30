package com.dystopiastudios.easystory.repository;

import com.dystopiastudios.easystory.model.Qualification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface QualificationRepository extends JpaRepository<Qualification,Long> {
    Page<Qualification> findByUserId(Long userId, Pageable pageable);

    Optional<Qualification> findByPostIdAndUserId(Long userId, Long postId);

    Page<Qualification> findByPostId(Long postId, Pageable pageable);

}
