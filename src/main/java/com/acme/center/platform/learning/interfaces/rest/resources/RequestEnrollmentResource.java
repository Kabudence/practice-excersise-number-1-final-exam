package com.acme.center.platform.learning.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record RequestEnrollmentResource(
        @NotNull
        String studentRecordId,
        @NotNull
        Long courseId
) {
}