package com.acme.center.platform.analytics.application.internal.queryservice;

import com.acme.center.platform.analytics.domain.model.aggregates.PerformanceIndicator;
import com.acme.center.platform.analytics.domain.model.queries.GetPerformanceIndicatorById;
import com.acme.center.platform.analytics.domain.service.PerformanceIndicatorQuery;
import com.acme.center.platform.analytics.infrastructure.jpa.repositories.PerformanceIndicatorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerformanceIndicatorQueryService implements PerformanceIndicatorQuery {

    private final PerformanceIndicatorRepository performanceIndicatorRepository;

    public PerformanceIndicatorQueryService(PerformanceIndicatorRepository performanceIndicatorRepository) {
        this.performanceIndicatorRepository = performanceIndicatorRepository;
    }

    @Override
    public Optional<PerformanceIndicator> handle(GetPerformanceIndicatorById query) {
        return performanceIndicatorRepository.findById(query.id());
    }
}
