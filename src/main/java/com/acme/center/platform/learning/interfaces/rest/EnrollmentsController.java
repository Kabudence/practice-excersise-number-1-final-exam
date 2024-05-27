package com.acme.center.platform.learning.interfaces.rest;

import com.acme.center.platform.learning.domain.model.commands.CancelEnrollmentCommand;
import com.acme.center.platform.learning.domain.model.commands.ConfirmEnrollmentCommand;
import com.acme.center.platform.learning.domain.model.commands.RejectEnrollmentCommand;
import com.acme.center.platform.learning.domain.model.queries.GetAllEnrollmentsQuery;
import com.acme.center.platform.learning.domain.model.queries.GetEnrollmentByAcmeStudentRecordIdAndCourseIdQuery;
import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import com.acme.center.platform.learning.domain.services.EnrollmentCommandService;
import com.acme.center.platform.learning.domain.services.EnrollmentQueryService;
import com.acme.center.platform.learning.interfaces.rest.resources.EnrollmentResource;
import com.acme.center.platform.learning.interfaces.rest.resources.RequestEnrollmentResource;
import com.acme.center.platform.learning.interfaces.rest.transform.EnrollmentResourceFromEntityAssembler;
import com.acme.center.platform.learning.interfaces.rest.transform.RequestEnrollmentCommandFromResourceAssembler;
import com.acme.center.platform.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Inbound service for the Enrollment aggregate.
 * <p>
 * This controller is responsible for handling requests related to the Enrollment aggregate.
 * It uses the {@link EnrollmentCommandService} and {@link EnrollmentQueryService} to handle the commands and queries
 * for enrollments.
 * <ul>
 *     <li>POST /api/v1/enrollments</li>
 *     <li>POST /api/v1/enrollments/{enrollmentId}/confirmations</li>
 *     <li>POST /api/v1/enrollments/{enrollmentId}/rejections</li>
 *     <li>POST /api/v1/enrollments/{enrollmentId}/cancellations</li>
 * </ul>
 * <p>
 */
@RestController
@RequestMapping(value = "/api/v1/enrollments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Enrollments", description = "Enrollment Management Endpoints")
public class EnrollmentsController {
    private final EnrollmentCommandService enrollmentCommandService;
    private final EnrollmentQueryService enrollmentQueryService;

    public EnrollmentsController(EnrollmentCommandService enrollmentCommandService, EnrollmentQueryService enrollmentQueryService) {
        this.enrollmentCommandService = enrollmentCommandService;
        this.enrollmentQueryService = enrollmentQueryService;
    }

    /**
     * Handles a request to enroll a student in a course.
     *
     * @param resource The request body containing the student record ID and the course ID.
     * @return The enrollment resource.
     * @see RequestEnrollmentResource
     * @see EnrollmentResource
     */
    @PostMapping
    public ResponseEntity<EnrollmentResource> requestEnrollment(@RequestBody RequestEnrollmentResource resource) {
        var command = RequestEnrollmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var enrollmentId = enrollmentCommandService.handle(command);
        System.out.println("Enrollment ID: " + enrollmentId);
        var getEnrollmentByAcmeStudentRecordIdAndCourseIdQuery = new GetEnrollmentByAcmeStudentRecordIdAndCourseIdQuery(new AcmeStudentRecordId(resource.studentRecordId()), resource.courseId());
        var enrollment = enrollmentQueryService.handle(getEnrollmentByAcmeStudentRecordIdAndCourseIdQuery);
        if (enrollment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var enrollmentResource = EnrollmentResourceFromEntityAssembler.toResourceFromEntity(enrollment.get());
        return new ResponseEntity<>(enrollmentResource, HttpStatus.CREATED);
    }

    /**
     * Handles a request to confirm an enrollment.
     *
     * @param enrollmentId The enrollment ID.
     * @return MessageResource with The enrollment ID.
     * @see MessageResource
     */
    @PostMapping("/{enrollmentId}/confirmations")
    public ResponseEntity<MessageResource> confirmEnrollment(@PathVariable Long enrollmentId) {
        var confirmEnrollmentCommand = new ConfirmEnrollmentCommand(enrollmentId);
        enrollmentCommandService.handle(confirmEnrollmentCommand);
        return ResponseEntity.ok(new MessageResource("Confirmed Enrollment ID: " + enrollmentId));
    }

    /**
     * Handles a request to reject an enrollment.
     *
     * @param enrollmentId The enrollment ID.
     * @return MessageResource with the enrollment ID.
     * @see MessageResource
     */
    @PostMapping("/{enrollmentId}/rejections")
    public ResponseEntity<MessageResource> rejectEnrollment(@PathVariable Long enrollmentId) {
        var rejectEnrollmentCommand = new RejectEnrollmentCommand(enrollmentId);
        enrollmentCommandService.handle(rejectEnrollmentCommand);
        return ResponseEntity.ok(new MessageResource("Rejected Enrollment ID: " + enrollmentId));
    }

    /**
     * Handles a request to cancel an enrollment.
     *
     * @param enrollmentId The enrollment ID.
     * @return MessageResource with the enrollment ID.
     * @see MessageResource
     *
     */
    @PostMapping("/{enrollmentId}/cancellations")
    public ResponseEntity<MessageResource> cancelEnrollment(@PathVariable Long enrollmentId) {
        var cancelEnrollmentCommand = new CancelEnrollmentCommand(enrollmentId);
        enrollmentCommandService.handle(cancelEnrollmentCommand);
        return ResponseEntity.ok(new MessageResource("Cancelled Enrollment ID: "+ enrollmentId));
    }

    /**
     * Gets all the enrollments.
     *
     * @return The list of all the enrollment resources available.
     * @see EnrollmentResource
     */
    @GetMapping
    public ResponseEntity<List<EnrollmentResource>> getAllEnrollments() {
        var getAllEnrollmentsQuery = new GetAllEnrollmentsQuery();
        var enrollments = enrollmentQueryService.handle(getAllEnrollmentsQuery);
        var enrollmentResources = enrollments.stream().map(EnrollmentResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(enrollmentResources);
    }
}