package com.dystopiastudios.easystory.resource;


import com.dystopiastudios.easystory.model.AuditModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//Data transfer object (DTO)
//RESTful Api expone resources no sus entidades
//Mapping o Model Mapper es para convertir tus DTO a Entidades y viceversa (en .NET Core es Automap)
public class CommentResource extends AuditModel {
    private Long id;
    private String text;
}
