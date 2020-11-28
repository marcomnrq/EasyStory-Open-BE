package com.dystopiastudios.easystory.service;

import com.dystopiastudios.easystory.domain.service.QualificationService;
import com.dystopiastudios.easystory.exception.ResourceNotFoundException;
import com.dystopiastudios.easystory.domain.model.Bookmark;
import com.dystopiastudios.easystory.domain.model.Post;
import com.dystopiastudios.easystory.domain.model.Qualification;
import com.dystopiastudios.easystory.domain.model.User;
import com.dystopiastudios.easystory.domain.repository.PostRepository;
import com.dystopiastudios.easystory.domain.repository.QualificationRepository;
import com.dystopiastudios.easystory.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QualificationServiceImpl implements QualificationService {
    @Autowired
    private QualificationRepository qualificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Override
    public Qualification getQualificationByUserIdAndPostId(Long userId, Long postId) {
        return qualificationRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(()->new ResourceNotFoundException("Qualification not found with Id" + postId +  "and UserId" + userId));
    }

    @Override
    public Qualification createQualification(Long userId, Long postId, Qualification qualification) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Optional<Qualification> existingQualification = qualificationRepository.findByUserIdAndPostId(userId, postId);
        if (!existingQualification.isEmpty()){
            throw new IllegalArgumentException("El usuario ya tiene un qualification con este postId");
        }else {
            qualification.setUser(user);
            qualification.setPost(post);
            return qualificationRepository.save(qualification);
        }

    }

    @Override
    public Page<Qualification> getAllQualificationsByUserId(Long userId, Pageable pageable) {
        return qualificationRepository.findByUserId(userId,pageable);
    }

    @Override
    public Page<Qualification> getAllQualificationsByPostId(Long postId, Pageable pageable) {
        return qualificationRepository.findByPostId(postId,pageable);
    }

    @Override
    public Qualification editQualification(Long userId, Long postId, Qualification qualificationDetails) {
        Qualification qualification = qualificationRepository.findByUserIdAndPostId(userId,postId).orElseThrow(()->
                new ResourceNotFoundException("Qualification not found with Id" + postId +  "and UserId" + userId));
        qualification.setQualification(qualificationDetails.getQualification());
        return qualificationRepository.save(qualification);
    }

    @Override
    public ResponseEntity<?> deleteQualification(Long userId, Long postId) {
        Qualification qualification = qualificationRepository.findByUserIdAndPostId(userId,postId).orElseThrow(()->
                new ResourceNotFoundException("Qualification not found with Id" + postId +  "and UserId" + userId));
        qualificationRepository.delete(qualification);
        return ResponseEntity.ok().build();

    }

    @Override
    public Page<Qualification> getAllQualifications(Pageable pageable) {
        return qualificationRepository.findAll(pageable);
    }
}
