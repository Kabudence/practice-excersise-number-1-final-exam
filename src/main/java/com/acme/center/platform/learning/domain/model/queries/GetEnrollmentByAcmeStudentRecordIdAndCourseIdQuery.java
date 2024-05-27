package com.acme.center.platform.learning.domain.model.queries;

import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;

public record GetEnrollmentByAcmeStudentRecordIdAndCourseIdQuery(AcmeStudentRecordId acmeStudentRecordId, Long courseId) {
}