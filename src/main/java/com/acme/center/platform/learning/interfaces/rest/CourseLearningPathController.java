package com.acme.center.platform.learning.interfaces.rest;

import com.acme.center.platform.learning.domain.model.commands.AddTutorialToCourseLearningPathCommand;
import com.acme.center.platform.learning.domain.model.queries.GetLearningPathItemByCourseIdAndTutorialIdQuery;
import com.acme.center.platform.learning.domain.services.CourseCommandService;
import com.acme.center.platform.learning.domain.services.CourseQueryService;
import com.acme.center.platform.learning.interfaces.rest.resources.LearningPathItemResource;
import com.acme.center.platform.learning.interfaces.rest.transform.LearningPathItemResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for managing the learning path of a course.
 * <p>
 *     This controller exposes the following endpoints:
 *     <ul>
 *         <li>POST /courses/{courseId}/learning-path/{tutorialId}: Adds a tutorial to the learning path of a course.</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/courses/{courseId}/learning-path-items", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Courses")
public class CourseLearningPathController {
    private final CourseCommandService courseCommandService;
    private final CourseQueryService courseQueryService;

    public CourseLearningPathController(CourseCommandService courseCommandService, CourseQueryService courseQueryService) {
        this.courseCommandService = courseCommandService;
        this.courseQueryService = courseQueryService;
    }

    /**
     * Adds a tutorial to the learning path of a course.
     * @param courseId The course identifier.
     * @param tutorialId The tutorial identifier.
     * @return The learning path item.
     * @see LearningPathItemResource
     */
    @PostMapping("{tutorialId}")
    public ResponseEntity<LearningPathItemResource> addTutorialToCourseLearningPath(@PathVariable Long courseId, @PathVariable Long tutorialId) {
        courseCommandService.handle(new AddTutorialToCourseLearningPathCommand(tutorialId, courseId));
        var getLearningPathItemByCourseIdAndTutorialIdQuery = new GetLearningPathItemByCourseIdAndTutorialIdQuery(courseId, tutorialId);
        var learningPathItem = courseQueryService.handle(getLearningPathItemByCourseIdAndTutorialIdQuery);
        if (learningPathItem.isEmpty()) return ResponseEntity.notFound().build();
        else {
            var learningPathItemResource = LearningPathItemResourceFromEntityAssembler.toResourceFromEntity(learningPathItem.get());
            return ResponseEntity.ok(learningPathItemResource);
        }

    }

}