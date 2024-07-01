package com.acme.center.platform.analytics.interfaces.rest.transform;

import com.acme.center.platform.analytics.domain.model.aggregates.PerformanceIndicator;
import com.acme.center.platform.analytics.interfaces.rest.resources.PerformanceIndicatorResource;

public class CreateResourceFromEntity {
    public static PerformanceIndicatorResource toResourceFromEntity(PerformanceIndicator entity) {
      return new PerformanceIndicatorResource(entity.getId(),
                                              entity.getName(),
                                              entity.getDescription(),
                                              entity.getMinValue(),
                                              entity.getMaxValue(),
                                              entity.getDeviceType());
    }
}
