package com.dystopiastudios.easystory.repository;

import com.dystopiastudios.easystory.model.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Page<Bookmark>findByUserId(Long userId, Pageable pageable);

    Optional<Bookmark> findByIdAndUserId(Long userId, Long pageable);
}
