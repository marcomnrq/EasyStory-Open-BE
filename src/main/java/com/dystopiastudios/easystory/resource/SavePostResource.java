package com.dystopiastudios.easystory.resource;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SavePostResource {
    @NotNull
    @NotBlank
    @Size(max = 100)
    private String title;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Lob
    private String content;

}
