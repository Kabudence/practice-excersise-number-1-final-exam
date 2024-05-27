package com.acme.center.platform.learning.domain.model.commands;

public record CompleteTutorialForEnrollmentCommand(Long enrollmentId, Long tutorialId) {
}
