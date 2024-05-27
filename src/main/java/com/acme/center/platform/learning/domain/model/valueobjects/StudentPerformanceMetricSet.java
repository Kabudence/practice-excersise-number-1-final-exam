package com.acme.center.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record StudentPerformanceMetricSet(Integer totalCompletedCourses, Integer totalTutorials) {
    public StudentPerformanceMetricSet() {
        this(0, 0);
    }

    public StudentPerformanceMetricSet {
        if (totalCompletedCourses < 0) {
            throw new IllegalArgumentException("Total completed courses cannot be negative");
        }
        if (totalTutorials < 0) {
            throw new IllegalArgumentException("Total tutorials cannot be negative");
        }
    }

    public StudentPerformanceMetricSet incrementTotalCompletedCourses() {
        return new StudentPerformanceMetricSet(totalCompletedCourses + 1, totalTutorials);
    }

    public StudentPerformanceMetricSet incrementTotalTutorials() {
        return new StudentPerformanceMetricSet(totalCompletedCourses, totalTutorials + 1);
    }
}
