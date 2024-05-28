package com.acme.center.platform.learning.interfaces.rest.transform;

import com.acme.center.platform.learning.domain.model.aggregates.Student;
import com.acme.center.platform.learning.interfaces.rest.resources.StudentResource;

public class StudentResourceFromEntityAssembler {
    public static StudentResource toResourceFromEntity(Student student) {
        return new StudentResource(
                student.getStudentRecordId(),
                student.getProfileId(),
                student.getTotalCompletedCourses(),
                student.getTotalTutorials()
        );
    }
}