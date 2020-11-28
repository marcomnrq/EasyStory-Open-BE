package com.dystopiastudios.easystory.domain.repository;

import com.dystopiastudios.easystory.domain.model.Comment;
import com.dystopiastudios.easystory.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByUserId(Long userId, Pageable pageable);

    Optional<Post> findByTitle(String title);

    Optional<Post> findByIdAndUserId(Long id, Long userId);
}

