package com.dystopiastudios.easystory.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SaveCommentResource {
    @NotNull
    @Lob
    private String text;
}
