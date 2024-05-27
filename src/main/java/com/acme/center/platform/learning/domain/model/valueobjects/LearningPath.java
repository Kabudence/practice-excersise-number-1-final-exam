package com.acme.center.platform.learning.domain.model.valueobjects;

import com.acme.center.platform.learning.domain.model.aggregates.Course;
import com.acme.center.platform.learning.domain.model.entities.LearningPathItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a learning path of tutorials for a course
 * It is embedded in the Course aggregate
 * It is a value object that is not persisted in the database
 * It is initialized when a course is created
 * It is updated when a tutorial is added to the course
 * It is the base reference to track the progress of a student in a course
 * It is used to determine the next tutorial to be completed by a student
 */
@Embeddable
public class LearningPath {

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<LearningPathItem> learningPathItems;

    public LearningPath() {
        this.learningPathItems = new ArrayList<>();
    }


    /**
     * Adds the item before the item with the given id
     * @param course The course to add
     * @param tutorialId The tutorial to add
     * @param nextItem The id of the item before which the new item should be added
     */
    public void addItem(Course course, Long tutorialId, LearningPathItem nextItem) {
        // Add the new item before the next item
        System.out.println("Adding item to learning path");
        LearningPathItem learningPathItem = new LearningPathItem(course, tutorialId, nextItem);
        System.out.println("tutorial Id " + learningPathItem.getTutorialId());
        learningPathItems.add(learningPathItem);
    }

    /**
     * Adds the item at the end of the learning path
     * @param course The course to add
     * @param tutorialId The tutorial to add
     */
    public void addItem(Course course, Long tutorialId) {
        System.out.println("Adding item to learning path");
        LearningPathItem learningPathItem = new LearningPathItem(course, tutorialId, null);
        LearningPathItem originalLastItem = null;
        if (!learningPathItems.isEmpty()) {
            originalLastItem = getLastItemInLearningPath();
        } else {
            System.out.println("Learning path is empty");
        }

        learningPathItems.add(learningPathItem);
        System.out.println("tutorial Id " + learningPathItem.getTutorialId());
        System.out.println("Learning path item added");
        if (originalLastItem != null) originalLastItem.updateNextItem(learningPathItem);
    }

    /**
     * Adds the item at the end of the learning path
     * @param course The course to add
     * @param tutorialId The tutorial to add
     * @param nextTutorialId The id of the tutorial before which the new item should be added
     */
    public void addItem(Course course, Long tutorialId, Long nextTutorialId) {
        LearningPathItem nextItem = getLearningPathItemWithTutorialId(nextTutorialId);
        addItem(course, tutorialId, nextItem);
    }

    public Long getFirstTutorialIdInLearningPath() {
        return learningPathItems.get(0).getTutorialId();
    }

    public Long getNextTutorialInLearningPath(Long currentTutorialId) {
        LearningPathItem item = getLearningPathItemWithTutorialId(currentTutorialId);
        return item != null ? item.getTutorialId() : null;
    }

    private LearningPathItem getLearningPathItemWithId(Long itemId) {
        return learningPathItems.stream().filter(learningPathItem -> learningPathItem.getId().equals(itemId))
                .findFirst().orElse(null);
    }

    public LearningPathItem getLearningPathItemWithTutorialId(Long tutorialId) {
        return learningPathItems.stream().filter(learningPathItem -> learningPathItem.getTutorialId().equals(tutorialId))
                .findFirst().orElse(null);
    }

    public boolean isLastTutorialInLearningPath(Long currentTutorialId) {
        return getNextTutorialInLearningPath(currentTutorialId) == null;
    }

    public LearningPathItem getLastItemInLearningPath() {
        return learningPathItems.stream().filter(item -> item.getNextItem() == null)
                .findFirst().orElse(null);
    }

    public boolean isEmpty() {
        return learningPathItems.isEmpty();
    }
}
