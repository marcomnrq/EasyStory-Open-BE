package com.dystopiastudios.easystory.resource;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SaveTagResource {
    @NotNull
    @Size(max = 100)
    private String name;
}
