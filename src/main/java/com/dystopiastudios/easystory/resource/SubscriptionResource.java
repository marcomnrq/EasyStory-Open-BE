package com.dystopiastudios.easystory.resource;

import com.dystopiastudios.easystory.domain.model.AuditModel;
import lombok.Data;

import java.util.Date;

@Data
public class SubscriptionResource extends AuditModel {
    private Long userId;
    private Long subscribedId;
}
