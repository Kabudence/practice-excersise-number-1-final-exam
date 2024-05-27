package com.acme.center.platform.learning.interfaces.rest;

import com.acme.center.platform.learning.domain.model.queries.GetStudentByAcmeStudentRecordIdQuery;
import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import com.acme.center.platform.learning.domain.services.StudentCommandService;
import com.acme.center.platform.learning.domain.services.StudentQueryService;
import com.acme.center.platform.learning.interfaces.rest.resources.CreateStudentResource;
import com.acme.center.platform.learning.interfaces.rest.resources.StudentResource;
import com.acme.center.platform.learning.interfaces.rest.transform.CreateStudentCommandFromResourceAssembler;
import com.acme.center.platform.learning.interfaces.rest.transform.StudentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * StudentsController
 *
 * <p>Controller that handles the endpoints for students.
 * It uses the {@link StudentCommandService} and {@link StudentQueryService} to handle the commands and queries
 * for students.
 * <ul>
 *     <li>POST /api/v1/students</li>
 *     <li>GET /api/v1/students/{studentRecordId}</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/students", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Students", description = "Student Management Endpoints")
public class StudentsController {
    private final StudentCommandService studentCommandService;
    private final StudentQueryService studentQueryService;


    public StudentsController(StudentCommandService studentCommandService, StudentQueryService studentQueryService) {
        this.studentCommandService = studentCommandService;
        this.studentQueryService = studentQueryService;
    }

    /**
     * POST /api/v1/students
     *
     * <p>Endpoint that creates a student</p>
     *
     * @param resource the resource with the information to create the student
     * @return the created student
     * @see CreateStudentResource
     * @see StudentResource
     */
    @PostMapping
    public ResponseEntity<StudentResource> createStudent(@RequestBody CreateStudentResource resource) {
        var createStudentCommand = CreateStudentCommandFromResourceAssembler.toCommandFromResource(resource);
        var studentId = studentCommandService.handle(createStudentCommand);
        if (studentId.studentRecordId().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var getStudentByAcmeStudentRecordIdQuery = new GetStudentByAcmeStudentRecordIdQuery(studentId);
        var student = studentQueryService.handle(getStudentByAcmeStudentRecordIdQuery);
        if (student.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var studentResource = StudentResourceFromEntityAssembler.toResourceFromEntity(student.get());
        return new ResponseEntity<>(studentResource, HttpStatus.CREATED);

    }

    /**
     * GET /api/v1/students/{studentRecordId}
     *
     * <p>Endpoint that gets a student by its acme student record id</p>
     *
     * @param studentRecordId the acme student record id
     * @return the student resource
     * @see StudentResource
     */
    @GetMapping("/{studentRecordId}")
    public ResponseEntity<StudentResource> getStudentByAcmeStudentRecordId(@PathVariable String studentRecordId) {
        var acmeStudentRecordId = new AcmeStudentRecordId(studentRecordId);
        var getStudentByAcmeStudentRecordIdQuery = new GetStudentByAcmeStudentRecordIdQuery(acmeStudentRecordId);
        var student = studentQueryService.handle(getStudentByAcmeStudentRecordIdQuery);
        if (student.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var studentResource = StudentResourceFromEntityAssembler.toResourceFromEntity(student.get());
        return ResponseEntity.ok(studentResource);
    }
}