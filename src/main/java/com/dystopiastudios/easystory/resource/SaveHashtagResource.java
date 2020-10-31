package com.dystopiastudios.easystory.resource;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
public class SaveHashtagResource {
    @NotNull
    @Size(max = 100)
    private String name;
}
