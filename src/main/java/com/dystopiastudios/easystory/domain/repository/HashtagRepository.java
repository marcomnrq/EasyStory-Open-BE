package com.dystopiastudios.easystory.domain.repository;

import com.dystopiastudios.easystory.domain.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag,Long> {
}
