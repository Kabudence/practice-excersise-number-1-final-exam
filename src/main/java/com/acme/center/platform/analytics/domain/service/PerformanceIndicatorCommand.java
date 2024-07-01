package com.acme.center.platform.analytics.domain.service;

import com.acme.center.platform.analytics.domain.model.aggregates.PerformanceIndicator;
import com.acme.center.platform.analytics.domain.model.commands.CreatePerformanceIndicatorCommand;

import java.util.Optional;

public interface PerformanceIndicatorCommand {

    Optional<PerformanceIndicator>handle(CreatePerformanceIndicatorCommand command);
}
