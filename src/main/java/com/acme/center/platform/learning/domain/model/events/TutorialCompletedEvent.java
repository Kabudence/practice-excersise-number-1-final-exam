package com.acme.center.platform.learning.domain.model.events;

import com.acme.center.platform.learning.application.internal.eventhandlers.TutorialCompletedEventHandler;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * TutorialCompletedEvent
 * <p>
 *     This event is fired when a tutorial is completed.
 *     It contains enrollmentId and tutorialId.
 *     It is used by TutorialCompletedEventHandler to update student metrics.
 * </p>
 * @see TutorialCompletedEventHandler
 */
@Getter
public final class TutorialCompletedEvent extends ApplicationEvent {

    private final Long enrollmentId;

    private final Long tutorialId;

    public TutorialCompletedEvent(Object source, Long enrollmentId, Long tutorialId) {
        super(source);
        this.enrollmentId = enrollmentId;
        this.tutorialId = tutorialId;
    }

}