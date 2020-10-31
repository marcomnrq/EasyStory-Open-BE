package com.dystopiastudios.easystory.resource;

import com.dystopiastudios.easystory.domain.model.AuditModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class HashtagResource extends AuditModel {
    private Long Id;
    private String name;
}
