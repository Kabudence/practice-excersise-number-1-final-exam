package com.acme.center.platform.learning.application.internal.eventhandlers;

import com.acme.center.platform.learning.domain.model.commands.UpdateStudentMetricsOnTutorialCompletedCommand;
import com.acme.center.platform.learning.domain.model.events.TutorialCompletedEvent;
import com.acme.center.platform.learning.domain.model.queries.GetEnrollmentByIdQuery;
import com.acme.center.platform.learning.domain.services.EnrollmentQueryService;
import com.acme.center.platform.learning.domain.services.StudentCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * TutorialCompletedEventHandler
 * <p>
 *     This event handler is responsible for handling TutorialCompletedEvent.
 *     It uses EventListener from Spring Boot Context Event Bus to listen to TutorialCompletedEvent.
 * </p>
 */
@Service
public class TutorialCompletedEventHandler {
    private final StudentCommandService studentCommandService;
    private final EnrollmentQueryService enrollmentQueryService;

    public TutorialCompletedEventHandler(StudentCommandService studentCommandService, EnrollmentQueryService enrollmentQueryService) {
        this.studentCommandService = studentCommandService;
        this.enrollmentQueryService = enrollmentQueryService;
    }

    /**
     * Event handler for TutorialCompletedEvent
     * <p>
     *     This method is called when TutorialCompletedEvent is fired.
     *     It fetches the enrollment by enrollmentId and updates the student metrics.
     *     It uses the {@link EnrollmentQueryService} to fetch the enrollment.
     *     It uses the {@link StudentCommandService} to update the student metrics.
     * </p>
     * @param event TutorialCompletedEvent containing enrollmentId and tutorialId
     * @see TutorialCompletedEvent
     */
    @EventListener(TutorialCompletedEvent.class)
    public void on(TutorialCompletedEvent event) {
        // Fetch enrollment by enrollmentId
        var getEnrollmentByIdQuery = new GetEnrollmentByIdQuery(event.getEnrollmentId());
        var enrollment = enrollmentQueryService.handle(getEnrollmentByIdQuery);
        if (enrollment.isPresent()) {
            // Update student metrics on tutorial completed
            var updateStudentMetricsOnTutorialCompletedCommand = new UpdateStudentMetricsOnTutorialCompletedCommand(enrollment.get().getAcmeStudentRecordId());
            studentCommandService.handle(updateStudentMetricsOnTutorialCompletedCommand);
        }
        System.out.println("TutorialCompletedEventHandler executed");
    }
}
