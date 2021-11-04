package com.dystopiastudios.easystory.controller;
import com.dystopiastudios.easystory.domain.model.Post;
import com.dystopiastudios.easystory.domain.model.User;
import com.dystopiastudios.easystory.resource.PostResource;
import com.dystopiastudios.easystory.resource.SavePostResource;
import com.dystopiastudios.easystory.resource.UserResource;
import com.dystopiastudios.easystory.resource.SaveUserResource;
import com.dystopiastudios.easystory.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@Tag(name = "users", description = "Users API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://easystory-open.web.app")
public class UserController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @Operation(summary = "Get Users", description = "Get All Users by Pages", tags = { "users" },security={ @SecurityRequirement(name="Authorization") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Posts returned", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/users")
    public Page<UserResource> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userService.getAllUsers(pageable);
        List<UserResource> resources = usersPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }


    @Operation(summary = "Get User by Id", description = "Get a User by specifying Id", tags = { "users" },security={ @SecurityRequirement(name="Authorization") })
    @GetMapping("/users/{id}")
    public UserResource getUserById(
            @Parameter(description="User Id")
            @PathVariable(name = "id") Long userId) {
        return convertToResource(userService.getUserById(userId));
    }

    //@Operation(security={ @SecurityRequirement(name="Authorization") })
    @Operation(summary = "Create a user")
    @PostMapping("/users")
    public UserResource createUser(@Valid @RequestBody SaveUserResource resource)  {
        User user = convertToEntity(resource);
        return convertToResource(userService.createUser(user));
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") }, summary = "Update a user")
    @PutMapping("/users/{id}")
    public UserResource updateUser(@PathVariable(name = "id") Long userId, @Valid @RequestBody SaveUserResource resource) {
        User user = convertToEntity(resource);
        return convertToResource(userService.updateUser(userId, user));
    }

    @Operation(security={ @SecurityRequirement(name="Authorization") }, summary = "Delete a user")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long userId) {
        return userService.deleteUser(userId);
    }
    // Auto Mapper
    private User convertToEntity(SaveUserResource resource) {
        return mapper.map(resource, User.class);
    }

    private UserResource convertToResource(User entity) {
        return mapper.map(entity, UserResource.class);
    }
}