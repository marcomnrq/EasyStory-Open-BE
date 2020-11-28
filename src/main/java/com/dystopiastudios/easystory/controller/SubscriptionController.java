package com.dystopiastudios.easystory.controller;

import com.dystopiastudios.easystory.domain.model.Bookmark;
import com.dystopiastudios.easystory.domain.model.Subscription;
import com.dystopiastudios.easystory.domain.service.BookmarkService;
import com.dystopiastudios.easystory.domain.service.SubscriptionService;
import com.dystopiastudios.easystory.resource.BookmarkResource;
import com.dystopiastudios.easystory.resource.SaveBookmarkResource;
import com.dystopiastudios.easystory.resource.SaveSubscriptionResource;
import com.dystopiastudios.easystory.resource.SubscriptionResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "subscriptions", description = "Subscriptions desc")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://easystory-open.web.app")
public class SubscriptionController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/users/{userId}/subscriptions")
    public Page<SubscriptionResource> getAllSubscriptionsByUserId(
            @PathVariable(name = "userId") Long userId, Pageable pageable){

        List<SubscriptionResource> subscriptions= subscriptionService.getAllSubscriptionsByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int subscriptionsCount = subscriptions.size();
        return new PageImpl<>(subscriptions, pageable, subscriptionsCount);
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/users/{userId}/subscribers")
    public Page<SubscriptionResource> getAllSubscribersByUserId(
            @PathVariable(name = "userId") Long userId, Pageable pageable){

        List<SubscriptionResource> subscribers = subscriptionService.getAllSubscribersByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int subscribersCount = subscribers.size();
        return new PageImpl<>(subscribers, pageable, subscribersCount);
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/users/{userId}/subscriptions/{subscribedId}")
    public SubscriptionResource getSubscriptionByUserIdAndSubscribedId(@PathVariable(name = "userId") Long userId,
                                                         @PathVariable(name= "subscribedId") Long subscribedId){
        return convertToResource(subscriptionService.getSubscriptionByUserIdAndSubscribedId(userId, subscribedId));
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") })
    @PostMapping("/users/{userId}/subscriptions/{subscribedId}")
    public SubscriptionResource createSubscription(@PathVariable(name = "userId")Long userId,
                                           @PathVariable(name = "subscribedId") Long subscribedId,
                                           @Valid @RequestBody SaveSubscriptionResource resource){
        return convertToResource(subscriptionService.createSubscription(userId, subscribedId,convertToEntity(resource)));
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") })
    @DeleteMapping("/users/{userId}/subscriptions/{subscribedId}")
    public ResponseEntity<?> deleteSubscription(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name= "subscribedId") Long subscribedId) {
        return subscriptionService.deleteSubscription(userId, subscribedId);
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/subscriptions")
    public Page<SubscriptionResource> getAllSubscriptions(
            @Parameter(description = "Pageable Parameter")
                    Pageable pageable){
        Page<Subscription> subscriptionsPage = subscriptionService.getAllSubscriptions(pageable);
        List<SubscriptionResource> resources = subscriptionsPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    private SubscriptionResource convertToResource(Subscription entity){
        return mapper.map(entity, SubscriptionResource.class);
    }
    private Subscription convertToEntity(SaveSubscriptionResource resource) {
        return mapper.map(resource, Subscription.class);
    }

}