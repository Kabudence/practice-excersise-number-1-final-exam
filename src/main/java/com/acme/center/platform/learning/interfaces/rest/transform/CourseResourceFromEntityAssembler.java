package com.acme.center.platform.learning.interfaces.rest.transform;

import com.acme.center.platform.learning.domain.model.aggregates.Course;
import com.acme.center.platform.learning.interfaces.rest.resources.CourseResource;

public class CourseResourceFromEntityAssembler {
    public static CourseResource toResourceFromEntity(Course entity) {
        return new CourseResource(entity.getId(), entity.getTitle(), entity.getDescription());
    }
}
