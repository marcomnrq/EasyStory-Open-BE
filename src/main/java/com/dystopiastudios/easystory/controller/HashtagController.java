package com.dystopiastudios.easystory.controller;

import com.dystopiastudios.easystory.domain.model.Hashtag;
import com.dystopiastudios.easystory.resource.HashtagResource;
import com.dystopiastudios.easystory.resource.SaveHashtagResource;
import com.dystopiastudios.easystory.domain.service.HashtagService;
import com.dystopiastudios.easystory.domain.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@Tag(name = "hashtags", description = "Hashtags API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://easystory-open.web.app")
public class HashtagController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private HashtagService hashtagService;

    @Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/hashtags")
    public Page<HashtagResource> getAllHashtags(Pageable pageable) {
        List<HashtagResource> hashtags= hashtagService.getAllHashtags(pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int hashtagcount=hashtags.size();
        return new PageImpl<>(hashtags, pageable, hashtagcount);
    }
    @Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/posts/{postId}/hashtags")
    public Page<HashtagResource> getAllHashtagsByPostId(@PathVariable(name = "postId") Long postId, Pageable pageable){
        List<HashtagResource> hashtags= hashtagService.getAllHashtagsByPostId(postId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int hashtagcount = hashtags.size();
        return new PageImpl<>(hashtags, pageable, hashtagcount);
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/hashtags/{id}")
    public HashtagResource getHashtagById(@PathVariable(name = "id") Long hashtagId){
        return convertToResource(hashtagService.getHashtagById(hashtagId));
    }

    //@Operation(security={ @SecurityRequirement(name="Authorization") })
    @PostMapping("/hashtags")
    public HashtagResource createHashtag(@Valid @RequestBody SaveHashtagResource resource){
        return convertToResource(hashtagService.createHashtag(convertToEntity(resource)));
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") })
    @PutMapping("/hashtags/{id}")
    public HashtagResource updateHashtag(@PathVariable(name = "id") Long hashtagId, @Valid @RequestBody SaveHashtagResource resource){
        return convertToResource(hashtagService.updateHashtag(hashtagId, convertToEntity(resource)));
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") })
    @DeleteMapping("/hashtags/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable(name = "id") Long hashtagId) {

        return hashtagService.deleteHashtag(hashtagId);
    }
    private Hashtag convertToEntity(SaveHashtagResource resource) {
        return mapper.map(resource, Hashtag.class);
    }

    private HashtagResource convertToResource(Hashtag entity) {
        return mapper.map(entity, HashtagResource.class);
    }
}
