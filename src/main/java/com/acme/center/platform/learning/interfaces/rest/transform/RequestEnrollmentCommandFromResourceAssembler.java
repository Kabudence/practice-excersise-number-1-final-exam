package com.acme.center.platform.learning.interfaces.rest.transform;

import com.acme.center.platform.learning.domain.model.commands.RequestEnrollmentCommand;
import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import com.acme.center.platform.learning.interfaces.rest.resources.RequestEnrollmentResource;

public class RequestEnrollmentCommandFromResourceAssembler {
    public static RequestEnrollmentCommand toCommandFromResource(RequestEnrollmentResource resource) {
        return new RequestEnrollmentCommand(new AcmeStudentRecordId(resource.studentRecordId()), resource.courseId());
    }
}