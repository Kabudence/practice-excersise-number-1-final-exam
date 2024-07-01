package com.acme.center.platform.inventory.domain.model.commands;

import com.acme.center.platform.analytics.domain.model.aggregates.PerformanceIndicator;
import com.acme.center.platform.inventory.domain.model.valueobjects.DeviceStatus;
import com.acme.center.platform.shared.domain.model.valueobjects.DeviceTypes;

import java.util.Date;

public record CreateDeviceCommand(String serialNumber,
                                  String model,
                                  DeviceTypes deviceType,
                                  Date installationDate,
                                  DeviceStatus status,
                                  Long performanceIndicatorId) {
}
