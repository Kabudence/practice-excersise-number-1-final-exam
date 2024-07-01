package com.acme.center.platform.inventory.interfaces.rest.transform;

import com.acme.center.platform.inventory.domain.model.aggregates.Device;
import com.acme.center.platform.inventory.interfaces.rest.resources.DeviceResource;

public class DeviceResourceFromEntity {
    public static DeviceResource toResourceFromEntity(Device device) {
        return new DeviceResource(device.getId(),
                                  device.getSerialNumber(),
                                  device.getModel(),
                                  device.getDeviceType(),
                                  device.getInstallationDate(),
                                  device.getStatus(),
                                  device.getPerformanceIndicatorId());
    }
}
