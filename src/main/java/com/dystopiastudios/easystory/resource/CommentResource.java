package com.dystopiastudios.easystory.resource;

import com.dystopiastudios.easystory.domain.model.AuditModel;
import com.dystopiastudios.easystory.domain.model.Post;
import com.dystopiastudios.easystory.domain.model.User;
import lombok.Data;

@Data
public class CommentResource extends AuditModel {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
}
