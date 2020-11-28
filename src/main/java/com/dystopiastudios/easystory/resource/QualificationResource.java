package com.dystopiastudios.easystory.resource;

import com.dystopiastudios.easystory.domain.model.AuditModel;
import lombok.Data;

@Data
public class QualificationResource extends AuditModel {
  private Long id;
  private Long userId;
  private Long postId;
  private Double qualification;
}
