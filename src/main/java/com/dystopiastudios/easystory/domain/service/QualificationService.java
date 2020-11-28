package com.dystopiastudios.easystory.domain.service;

import com.dystopiastudios.easystory.domain.model.Qualification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface QualificationService {
    Qualification getQualificationByUserIdAndPostId(Long userId, Long postId);
    Qualification createQualification(Long userId, Long postId,Qualification qualification);
    Page<Qualification> getAllQualificationsByUserId(Long userId, Pageable pageable);
    Page<Qualification> getAllQualificationsByPostId(Long postId, Pageable pageable);
    Qualification editQualification(Long userId,Long postId, Qualification qualificationDetails);
    ResponseEntity<?> deleteQualification(Long userId, Long postId);
    Page<Qualification>getAllQualifications(Pageable pageable);
}
