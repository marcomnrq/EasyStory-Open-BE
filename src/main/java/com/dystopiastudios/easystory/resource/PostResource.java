package com.dystopiastudios.easystory.resource;

import com.dystopiastudios.easystory.model.AuditModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResource extends AuditModel {
    private Long id;
    private String title;
    private String description;
    private String content;
}
