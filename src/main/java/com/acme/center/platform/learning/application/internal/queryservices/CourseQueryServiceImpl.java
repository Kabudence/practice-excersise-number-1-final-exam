package com.acme.center.platform.learning.application.internal.queryservices;

import com.acme.center.platform.learning.domain.model.aggregates.Course;
import com.acme.center.platform.learning.domain.model.entities.LearningPathItem;
import com.acme.center.platform.learning.domain.model.queries.GetAllCoursesQuery;
import com.acme.center.platform.learning.domain.model.queries.GetCourseByIdQuery;
import com.acme.center.platform.learning.domain.model.queries.GetLearningPathItemByCourseIdAndTutorialIdQuery;
import com.acme.center.platform.learning.domain.services.CourseQueryService;
import com.acme.center.platform.learning.infrastructure.persistence.jpa.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseQueryServiceImpl implements CourseQueryService {

    private final CourseRepository courseRepository;

    public CourseQueryServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Optional<Course> handle(GetCourseByIdQuery query) {
        return courseRepository.findById(query.courseId());
    }

    @Override
    public List<Course> handle(GetAllCoursesQuery query) {
        return courseRepository.findAll();
    }

    @Override
    public Optional<LearningPathItem> handle(GetLearningPathItemByCourseIdAndTutorialIdQuery query) {
        return courseRepository.findById(query.courseId()).map(course -> course.getLearningPath().getLearningPathItemWithTutorialId(query.tutorialId()));
    }
}