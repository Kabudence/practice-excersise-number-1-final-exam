package com.acme.center.platform.analytics.interfaces.acl;

import com.acme.center.platform.analytics.domain.model.aggregates.PerformanceIndicator;
import com.acme.center.platform.analytics.domain.model.queries.GetPerformanceIndicatorById;
import com.acme.center.platform.analytics.domain.service.PerformanceIndicatorCommand;
import com.acme.center.platform.analytics.domain.service.PerformanceIndicatorQuery;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Analytics Context Facade
 */
@Service
public class AnalyticsContextFacade {
    private final PerformanceIndicatorQuery performanceIndicatorQuery;
    private final PerformanceIndicatorCommand performanceIndicatorCommand;

    public AnalyticsContextFacade(PerformanceIndicatorQuery performanceIndicatorQuery, PerformanceIndicatorCommand performanceIndicatorCommand) {
        this.performanceIndicatorQuery = performanceIndicatorQuery;
        this.performanceIndicatorCommand = performanceIndicatorCommand;
    }

    /**
     * Create a new Performance Indicator
     * @param id containing id of performance indicator
     * This method creates a new performance indicator.
     * It validates the objects exists
     * @return Optional<PerformanceIndicator>
     */
    public Optional<PerformanceIndicator> fetchPerformanceIndicatorById(Long id) {
        var getPerformanceIndicatorById = new GetPerformanceIndicatorById(id);
        var performanceIndicator =  performanceIndicatorQuery.handle(getPerformanceIndicatorById);
        if(performanceIndicator.isEmpty()){
            throw new IllegalArgumentException("Performance Indicator not found");
        }
        return performanceIndicator;
    }

}
