package com.dystopiastudios.easystory.controller;

import com.dystopiastudios.easystory.domain.model.Qualification;
import com.dystopiastudios.easystory.resource.QualificationResource;
import com.dystopiastudios.easystory.resource.SaveQualificationResource;
import com.dystopiastudios.easystory.domain.service.QualificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "qualifications", description = "Qualifications API")
@RestController
@RequestMapping("/api")
public class QualificationController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private QualificationService qualificationService;
    @GetMapping("/users/{userId}/qualifications")
    public Page<QualificationResource> getAllQualificationByUserId(
            @PathVariable(name = "userId") Long userId, Pageable pageable){
        List<QualificationResource> qualifications=qualificationService.getAllQualificationsByUserId(userId,pageable).
                getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int qualification_count=qualifications.size();
        return  new PageImpl<>(qualifications,pageable,qualification_count);
    }
    @GetMapping("/posts/{postId}/qualifications")
    public Page<QualificationResource> getAllQualificationByPostId(
            @PathVariable(name = "postId") Long postId, Pageable pageable){
        List<QualificationResource> qualifications=qualificationService.getAllQualificationsByPostId(postId,pageable).
                getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int qualification_count=qualifications.size();
        return  new PageImpl<>(qualifications,pageable,qualification_count);
    }
    @PostMapping("/users/{userId}/posts/{postId}/qualifications")
    public QualificationResource createQualification(@PathVariable(name = "userId")Long userId,
                                           @PathVariable(name = "postId") Long postId,
                                           @Valid @RequestBody SaveQualificationResource resource){
        return convertToResource(qualificationService.createQualification(userId,postId,convertToEntity(resource)));
    }
    @GetMapping("/users/{userId}/posts/{postId}/qualifications")
    public QualificationResource getQualificationByUserIdAndPostId(@PathVariable(name = "userId") Long userId,
                                                         @PathVariable(name= "postId") Long postId){
        return convertToResource(qualificationService.getQualificationByPostIdAndUserId(userId, postId));
    }
    @DeleteMapping("/users/{userId}/posts/{postId}/qualifications")
    public ResponseEntity<?> deleteQualification(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name= "postId") Long postId) {
        return qualificationService.deleteQualification(userId, postId);
    }
    private QualificationResource convertToResource(Qualification entity){
        return mapper.map(entity, QualificationResource.class);
    }
    private Qualification convertToEntity(SaveQualificationResource resource) {
        return mapper.map(resource, Qualification.class);
    }
}
