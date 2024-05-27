package com.acme.center.platform.learning.domain.services;

import com.acme.center.platform.learning.domain.model.commands.CreateStudentCommand;
import com.acme.center.platform.learning.domain.model.commands.UpdateStudentMetricsOnTutorialCompletedCommand;
import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;

public interface StudentCommandService {
    AcmeStudentRecordId handle(CreateStudentCommand command);
    AcmeStudentRecordId handle(UpdateStudentMetricsOnTutorialCompletedCommand command);
}
