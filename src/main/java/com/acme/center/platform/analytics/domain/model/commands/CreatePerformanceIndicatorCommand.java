package com.acme.center.platform.analytics.domain.model.commands;

import com.acme.center.platform.shared.domain.model.valueobjects.DeviceTypes;

public record CreatePerformanceIndicatorCommand(String name,
                                                String description,
                                                Double minValue,
                                                Double maxValue,
                                                DeviceTypes deviceType) {
}
