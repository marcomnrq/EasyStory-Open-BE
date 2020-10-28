package com.dystopiastudios.easystory.resource;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveBookmarkResource {
    @NotNull
    private Long userId;
    @NotNull
    private Long postId;
}
