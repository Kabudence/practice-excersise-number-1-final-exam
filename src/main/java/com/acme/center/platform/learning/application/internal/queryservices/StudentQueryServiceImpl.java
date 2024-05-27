package com.acme.center.platform.learning.application.internal.queryservices;

import com.acme.center.platform.learning.domain.model.aggregates.Student;
import com.acme.center.platform.learning.domain.model.queries.GetStudentByAcmeStudentRecordIdQuery;
import com.acme.center.platform.learning.domain.model.queries.GetStudentByProfileIdQuery;
import com.acme.center.platform.learning.domain.services.StudentQueryService;
import com.acme.center.platform.learning.infrastructure.persistence.jpa.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of StudentQueryService
 *
 * <p>
 *     This class is the implementation of the StudentQueryService interface.
 *     It is used by the LearningContext to handle queries on the Student aggregate.
 * </p>
 *
 */
@Service
public class StudentQueryServiceImpl implements StudentQueryService {

    private final StudentRepository studentRepository;

    public StudentQueryServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Query handler to get student by profileId
     *
     * @param query containing profileId
     * @return Student
     */
    @Override
    public Optional<Student> handle(GetStudentByProfileIdQuery query) {
        return studentRepository.findByProfileId(query.profileId());
    }

    @Override
    public Optional<Student> handle(GetStudentByAcmeStudentRecordIdQuery query) {
        return studentRepository.findByAcmeStudentRecordId(query.acmeStudentRecordId());
    }
}