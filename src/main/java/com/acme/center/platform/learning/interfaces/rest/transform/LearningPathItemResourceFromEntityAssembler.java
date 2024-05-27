package com.acme.center.platform.learning.interfaces.rest.transform;

import com.acme.center.platform.learning.domain.model.entities.LearningPathItem;
import com.acme.center.platform.learning.interfaces.rest.resources.LearningPathItemResource;

public class LearningPathItemResourceFromEntityAssembler {
    public static LearningPathItemResource toResourceFromEntity(LearningPathItem entity) {
        return new LearningPathItemResource(entity.getId(), entity.getCourse().getId(), entity.getTutorialId());
    }
}