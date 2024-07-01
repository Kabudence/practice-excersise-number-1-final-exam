package com.acme.center.platform.inventory.application.internal.outboundedservices;

import com.acme.center.platform.analytics.domain.model.aggregates.PerformanceIndicator;
import com.acme.center.platform.analytics.interfaces.acl.AnalyticsContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalAnalyticsService {

    private final AnalyticsContextFacade analyticsContextFacade;

    public ExternalAnalyticsService(AnalyticsContextFacade analyticsContextFacade) {
        this.analyticsContextFacade = analyticsContextFacade;
    }

    public Optional<PerformanceIndicator> fetchPerformanceIndicatorById(Long id) {
        var performanceIndicator= analyticsContextFacade.fetchPerformanceIndicatorById(id);
        if(performanceIndicator.isEmpty()){
            throw new IllegalArgumentException("Performance Indicator not found");
        }
        return performanceIndicator;
    }
}
