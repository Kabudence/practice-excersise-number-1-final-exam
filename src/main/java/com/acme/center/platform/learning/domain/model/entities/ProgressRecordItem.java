package com.acme.center.platform.learning.domain.model.entities;

import com.acme.center.platform.learning.domain.model.aggregates.Enrollment;
import com.acme.center.platform.learning.domain.model.valueobjects.ProgressStatus;
import com.acme.center.platform.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

/**
 * Represents an item in the progress record.
 */
@Getter
@Entity
public class ProgressRecordItem extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @Getter
    private Long tutorialId;

    private ProgressStatus status;

    private Date startedAt;

    private Date completedAt;


    public ProgressRecordItem(Enrollment enrollment, Long tutorialId) {
        this.enrollment = enrollment;
        this.tutorialId = tutorialId;
        this.status = ProgressStatus.NOT_STARTED;
    }

    public ProgressRecordItem() {

    }

    /**
     * Starts the item.
     */
    public void start() {
        this.status = ProgressStatus.STARTED;
        this.startedAt = new Date();
    }

    /**
     * Completes the item.
     */
    public void complete() {
        this.status = ProgressStatus.COMPLETED;
        this.completedAt = new Date();
    }

    /**
     * Returns a boolean indicating if the item is completed.
     *
     * @return true if the item is completed.
     */
    public boolean isCompleted() {
        return this.status == ProgressStatus.COMPLETED;
    }

    /**
     * Returns a boolean indicating if the item is in progress.
     *
     * @return true if the item is in progress.
     */
    public boolean isInProgress() {
        return this.status == ProgressStatus.STARTED;
    }

    /**
     * Returns a boolean indicating if the item is not started.
     *
     * @return true if the item is not started.
     */
    public boolean isNotStarted() {
        return this.status == ProgressStatus.NOT_STARTED;
    }

    /**
     * Calculates the number of days elapsed since the item was started
     *
     * @return zero if not started. Otherwise, it returns the number of days elapsed between the started date and the completed date (if completed) or today
     */
    public long calculateDaysElapsed() {

        // If not started, return 0
        if (this.status == ProgressStatus.NOT_STARTED) return 0;

        var defaultTimeZone = java.time.ZoneId.systemDefault();
        // Only started items are registered, so it should be the started date
        var fromDate = this.startedAt.toInstant();
        // If completed it should be the completed date, otherwise it should be today
        var toDate = this.completedAt == null ? LocalDate.now().atStartOfDay(defaultTimeZone).toInstant() : this.completedAt.toInstant();

        return java.time.Duration.between(fromDate, toDate).toDays();
    }
}
