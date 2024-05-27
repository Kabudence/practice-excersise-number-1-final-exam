package com.acme.center.platform.learning.domain.model.commands;

public record UpdateCourseCommand(Long id, String title, String description) {
}