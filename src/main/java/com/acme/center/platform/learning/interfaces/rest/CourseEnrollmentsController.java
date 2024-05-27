package com.acme.center.platform.learning.interfaces.rest;

import com.acme.center.platform.learning.domain.model.queries.GetAllEnrollmentsByCourseIdQuery;
import com.acme.center.platform.learning.domain.services.EnrollmentQueryService;
import com.acme.center.platform.learning.interfaces.rest.resources.EnrollmentResource;
import com.acme.center.platform.learning.interfaces.rest.transform.EnrollmentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * CourseEnrollmentsController
 *
 * <p>Controller that handles the endpoints for course enrollments.
 * It uses the {@link EnrollmentQueryService} to handle the queries
 * for enrollments.
 * <ul>
 *     <li>GET /api/v1/course/{courseId}/enrollments</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/courses/{courseId}/enrollments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Courses")
public class CourseEnrollmentsController {
    private final EnrollmentQueryService enrollmentQueryService;

    public CourseEnrollmentsController(EnrollmentQueryService enrollmentQueryService) {
        this.enrollmentQueryService = enrollmentQueryService;
    }

    /**
     * GET /api/v1/course/{courseId}/enrollments
     *
     * <p>Endpoint that returns the enrollments for a course</p>
     *
     * @param courseId the course ID
     * @return the enrollment resources for the course with given ID
     * @see EnrollmentResource
     */
    @GetMapping
    public ResponseEntity<List<EnrollmentResource>> getAllEnrollmentsByCourseId(@PathVariable Long courseId) {
        var getAllEnrollmentsByCourseIdQuery = new GetAllEnrollmentsByCourseIdQuery(courseId);
        var enrollments = enrollmentQueryService.handle(getAllEnrollmentsByCourseIdQuery);
        var enrollmentResources = enrollments.stream().map(EnrollmentResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(enrollmentResources);

    }
}