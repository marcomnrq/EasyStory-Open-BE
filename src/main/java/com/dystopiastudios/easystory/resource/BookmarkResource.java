package com.dystopiastudios.easystory.resource;

import com.dystopiastudios.easystory.domain.model.AuditModel;
import lombok.Data;

@Data
public class BookmarkResource extends AuditModel  {
    private Long userId;
    private Long postId;
}
