package com.dystopiastudios.easystory.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
@Setter
public class SaveHashtagResource {
    @NotNull
    @Size(max = 100)
    private String name;
}
