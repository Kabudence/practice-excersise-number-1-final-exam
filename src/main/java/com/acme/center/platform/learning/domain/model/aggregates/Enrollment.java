package com.acme.center.platform.learning.domain.model.aggregates;

import com.acme.center.platform.learning.domain.model.events.TutorialCompletedEvent;
import com.acme.center.platform.learning.domain.model.valueobjects.AcmeStudentRecordId;
import com.acme.center.platform.learning.domain.model.valueobjects.EnrollmentStatus;
import com.acme.center.platform.learning.domain.model.valueobjects.ProgressRecord;
import com.acme.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

/**
 * Represents an enrollment.
 * The enrollment is an aggregate root.
 */

@Entity
public class Enrollment extends AuditableAbstractAggregateRoot<Enrollment> {

    @Getter
    @Embedded
    private AcmeStudentRecordId acmeStudentRecordId;

    @Getter
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    /**
     * The progress record for this enrollment.
     */
    @Embedded
    private ProgressRecord progressRecord;

    private EnrollmentStatus status;


    public Enrollment() {
    }

    public Enrollment(AcmeStudentRecordId studentRecordId, Course course) {
        this.acmeStudentRecordId = studentRecordId;
        this.course = course;
        this.status = EnrollmentStatus.REQUESTED;
        this.progressRecord = new ProgressRecord();
    }

    /**
     * Confirms the enrollment.
     */
    public void confirm() {
        this.status = EnrollmentStatus.CONFIRMED;
        this.progressRecord.initializeProgressRecord(this, course.getLearningPath());
        // this.registerEvent(new EnrollmentConfirmedEvent(this));
    }

    /**
     * Rejects the enrollment.
     */
    public void reject() {
        this.status = EnrollmentStatus.REJECTED;
        // this.registerEvent(new EnrollmentRejectedEvent(this));
    }

    /**
     * Cancels the enrollment.
     */
    public void cancel() {
        this.status = EnrollmentStatus.CANCELLED;
        // this.registerEvent(new EnrollmentCancelledEvent(this));
    }

    /**
     * Returns the status of the enrollment.
     * @return The status of the enrollment.
     */
    public String getStatus() {
        return this.status.name().toLowerCase();
    }

    /**
     * Returns how many days have elapsed since the enrollment was confirmed.
     * @return The number of days elapsed since the enrollment was confirmed.
     */
    public long calculateDaysElapsed() {
        return progressRecord.calculateDaysElapsedForEnrollment(this);
    }

    /**
     * Returns if the enrollment is confirmed.
     * @return true if the enrollment is confirmed. Otherwise, false.
     */
    public boolean isConfirmed() {
        return this.status == EnrollmentStatus.CONFIRMED;
    }

    /**
     * Returns if the enrollment is cancelled.
     * @return true if the enrollment is cancelled. Otherwise, false.
     */
    public boolean isRejected() {
        return this.status == EnrollmentStatus.REJECTED;
    }

    /**
     * Marks a tutorial as completed in progress record.
     * @param tutorialId The id of the tutorial to mark as completed.
     *                   The tutorial must be part of the learning path of the course.
     *                   Otherwise, an exception will be thrown.
     */
    public void completeTutorial(Long tutorialId) {
        progressRecord.completeTutorial(tutorialId, course.getLearningPath());
        // Publish a Tutorial Completed Event
        this.registerEvent(new TutorialCompletedEvent(this, this.getId(), tutorialId));
    }
}