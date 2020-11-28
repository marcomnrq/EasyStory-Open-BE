package com.dystopiastudios.easystory.resource;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SaveCommentResource {
    @NotNull
    @NotBlank
    private String content;
}
