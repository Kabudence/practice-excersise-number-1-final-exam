package com.acme.center.platform.analytics.interfaces.rest.resources;

public record PerformanceIndicatorResource (Long id,
                                            String name,
                                            String description,
                                            Double minValue,
                                            Double maxValue,
                                            String deviceType) {
}
