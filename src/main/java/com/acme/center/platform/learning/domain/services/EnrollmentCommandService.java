package com.acme.center.platform.learning.domain.services;

import com.acme.center.platform.learning.domain.model.commands.*;

public interface EnrollmentCommandService {
    Long handle(RequestEnrollmentCommand command);
    Long handle(ConfirmEnrollmentCommand command);
    Long handle(RejectEnrollmentCommand command);

    Long handle(CancelEnrollmentCommand command);

    Long handle(CompleteTutorialForEnrollmentCommand command);

}