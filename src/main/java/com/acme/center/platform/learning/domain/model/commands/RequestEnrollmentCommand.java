package com.acme.center.platform.learning.domain.model.commands;

import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;

public record RequestEnrollmentCommand(AcmeStudentRecordId studentRecordId, Long courseId) { }

