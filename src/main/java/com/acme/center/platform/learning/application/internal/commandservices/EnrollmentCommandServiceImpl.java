package com.acme.center.platform.learning.application.internal.commandservices;

import com.acme.center.platform.learning.domain.exceptions.CourseNotFoundException;
import com.acme.center.platform.learning.domain.model.aggregates.Course;
import com.acme.center.platform.learning.domain.model.aggregates.Enrollment;
import com.acme.center.platform.learning.domain.model.commands.*;
import com.acme.center.platform.learning.domain.services.EnrollmentCommandService;
import com.acme.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseRepository;
import com.acme.center.platform.learning.infrastructure.persistence.jpa.repositories.EnrollmentRepository;
import com.acme.center.platform.learning.infrastructure.persistence.jpa.repositories.StudentRepository;
import org.springframework.stereotype.Service;

/**
 * Implementation of EnrollmentCommandService
 *
 * <p>
 *     This class is the implementation of the EnrollmentCommandService interface.
 *     It is used by the EnrollmentContext to handle commands on the Enrollment aggregate.
 * </p>
 *
 */
@Service
public class EnrollmentCommandServiceImpl implements EnrollmentCommandService {
    private final CourseRepository courseRepository;

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    /**
     * Constructor
     *
     * @param courseRepository     CourseRepository
     * @param studentRepository    StudentRepository
     * @param enrollmentRepository EnrollmentRepository
     */
    public EnrollmentCommandServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * Command handler to request enrollment
     *
     * @param command containing studentRecordId and courseId
     * @return enrollmentId
     */
    public Long handle(RequestEnrollmentCommand command) {
        studentRepository.findByAcmeStudentRecordId(command.studentRecordId()).map(student -> {
            Course course = courseRepository.findById(command.courseId()).orElseThrow(() -> new CourseNotFoundException(command.courseId()));
            Enrollment enrollment = new Enrollment(command.studentRecordId(), course);
            enrollment = enrollmentRepository.save(enrollment);
            return enrollment.getId();
        }).orElseThrow(() -> new RuntimeException("Student not found"));
        return 0L;
    }

    /**
     * Command handler to confirm enrollment
     *
     * @param command containing enrollmentId
     * @return enrollmentId
     */
    @Override
    public Long handle(ConfirmEnrollmentCommand command) {
        enrollmentRepository.findById(command.enrollmentId()).map(enrollment -> {
            enrollment.confirm();
            enrollmentRepository.save(enrollment);
            return command.enrollmentId();
        }).orElseThrow(() -> new RuntimeException("Enrollment not found"));
        return null;
    }

    /**
     * Command handler to reject enrollment
     *
     * @param command containing enrollmentId
     * @return enrollmentId
     */
    @Override
    public Long handle(RejectEnrollmentCommand command) {
        enrollmentRepository.findById(command.enrollmentId()).map(enrollment -> {
            enrollment.reject();
            enrollmentRepository.save(enrollment);
            return enrollment.getId();
        }).orElseThrow(() -> new RuntimeException("Enrollment not found"));
        return null;
    }

    /**
     * Command handler to cancel enrollment
     *
     * @param command containing enrollmentId
     * @return enrollmentId
     */
    @Override
    public Long handle(CancelEnrollmentCommand command) {
        enrollmentRepository.findById(command.enrollmentId()).map(enrollment -> {
            enrollment.cancel();
            enrollmentRepository.save(enrollment);
            return enrollment.getId();
        }).orElseThrow(() -> new RuntimeException("Enrollment not found"));
        return null;
    }

    /**
     * Command handler to complete tutorial for enrollment
     *
     * @param command containing enrollmentId and tutorialId
     * @return enrollmentId
     */
    @Override
    public Long handle(CompleteTutorialForEnrollmentCommand command) {
        enrollmentRepository.findById(command.enrollmentId()).map(enrollment -> {
            enrollment.completeTutorial(command.tutorialId());
            enrollmentRepository.save(enrollment);
            return enrollment.getId();
        }).orElseThrow(() -> new RuntimeException("Enrollment not found"));
        return null;
    }

}
