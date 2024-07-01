package com.acme.center.platform.analytics.domain.service;

import com.acme.center.platform.analytics.domain.model.aggregates.PerformanceIndicator;
import com.acme.center.platform.analytics.domain.model.queries.GetPerformanceIndicatorById;

import java.util.Optional;

public interface PerformanceIndicatorQuery {

    Optional<PerformanceIndicator> handle(GetPerformanceIndicatorById query);
}
