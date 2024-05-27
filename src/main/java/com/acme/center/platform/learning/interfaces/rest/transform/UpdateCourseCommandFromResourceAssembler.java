package com.acme.center.platform.learning.interfaces.rest.transform;

import com.acme.center.platform.learning.domain.model.commands.UpdateCourseCommand;
import com.acme.center.platform.learning.interfaces.rest.resources.UpdateCourseResource;

public class UpdateCourseCommandFromResourceAssembler {
    public static UpdateCourseCommand toCommandFromResource(Long courseId, UpdateCourseResource resource) {
        return new UpdateCourseCommand(courseId, resource.title(), resource.description());
    }
}