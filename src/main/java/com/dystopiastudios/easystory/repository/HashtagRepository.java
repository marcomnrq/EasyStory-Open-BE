package com.dystopiastudios.easystory.repository;

import com.dystopiastudios.easystory.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag,Long> {
}
