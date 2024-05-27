package com.acme.center.platform.learning.domain.model.commands;

public record AddTutorialToCourseLearningPathCommand(Long tutorialId, Long courseId) {
}
