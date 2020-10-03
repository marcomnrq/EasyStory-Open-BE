package com.dystopiastudios.easystory.service;

import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.model.Hashtag;
import com.dystopiastudios.easystory.repository.HashtagRepository;
import com.dystopiastudios.easystory.repository.PostRepository;
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
    public Hashtag getHashtagById(Long HashtagId) {
        return hashtagRepository.findById(HashtagId)
                .orElseThrow(() -> new ResourceNotFoundException("Hashtag", "Id", HashtagId));

    }

    @Override
    public Hashtag createHashtag(Hashtag Hashtag) {
        return hashtagRepository.save(Hashtag);
    }

    @Override
    public Hashtag updateHashtag(Long HashtagId, Hashtag hashtagDetails) {
        return hashtagRepository.findById(HashtagId).map(hashtag -> {
            hashtag.setName(hashtagDetails.getName());
            return hashtagRepository.save(hashtag);
        }).orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", HashtagId));

    }

    @Override
    public ResponseEntity<?> deleteHashtag(Long HashtagId) {
        return hashtagRepository.findById(HashtagId).map(tag -> {
            hashtagRepository.delete(tag);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Tag", "Id", HashtagId));

    }
}
