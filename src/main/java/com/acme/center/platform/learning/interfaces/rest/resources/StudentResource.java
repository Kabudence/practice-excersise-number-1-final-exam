package com.acme.center.platform.learning.interfaces.rest.resources;

public record StudentResource(String acmeStudentRecordId, Long profileId, Integer totalCompletedCourses, Integer totalTutorials) {
}