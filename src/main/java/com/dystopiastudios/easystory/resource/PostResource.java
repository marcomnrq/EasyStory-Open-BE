package com.dystopiastudios.easystory.resource;

import com.dystopiastudios.easystory.domain.model.AuditModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PostResource extends AuditModel {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String content;
}
