package com.dystopiastudios.easystory.service;

import com.dystopiastudios.easystory.domain.service.HashtagService;
import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.domain.model.Hashtag;
import com.dystopiastudios.easystory.domain.repository.HashtagRepository;
import com.dystopiastudios.easystory.domain.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashtagServiceImpl implements HashtagService {
    @Autowired
    HashtagRepository hashtagRepository;
    @Autowired
    PostRepository postRepository;

    @Override
    public Page<Hashtag> getAllHashtags(Pageable pageable) {
      return hashtagRepository.findAll(pageable);
    }

    @Override
    public Page<Hashtag> getAllHashtagsByPostId(Long postId, Pageable pageable) {
        return postRepository.findById(postId).map(post -> {
            List<Hashtag> hashtags = post.getHashtags();
            int tagsCount = hashtags.size();
            return new PageImpl<>(hashtags, pageable, tagsCount);
        })
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

    }

    @Override
    public Hashtag getHashtagById(Long hashtagId) {
        return hashtagRepository.findById(hashtagId)
                .orElseThrow(() -> new ResourceNotFoundException("Hashtag", "Id", hashtagId));

    }

    @Override
    public Hashtag createHashtag(Hashtag hashtag) {
        return hashtagRepository.save(hashtag);
    }

    @Override
    public Hashtag updateHashtag(Long hashtagId, Hashtag hashtagDetails) {
        return hashtagRepository.findById(hashtagId).map(hashtag -> {
            hashtag.setName(hashtagDetails.getName());
            return hashtagRepository.save(hashtag);
        }).orElseThrow(() -> new ResourceNotFoundException("Hashtag", "Id", hashtagId));

    }

    @Override
    public ResponseEntity<?> deleteHashtag(Long hashtagId) {
        return hashtagRepository.findById(hashtagId).map(hashtag -> {
            hashtagRepository.delete(hashtag);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Hashtag", "Id", hashtagId));

    }

    @Override
    public Hashtag getHashtagByName(String name){
        return hashtagRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Hashtag", "Name", name));

    }
}
