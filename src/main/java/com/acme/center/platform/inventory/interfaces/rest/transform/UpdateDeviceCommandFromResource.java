package com.acme.center.platform.inventory.interfaces.rest.transform;

import com.acme.center.platform.inventory.domain.model.commands.UpdateDeviceCommand;
import com.acme.center.platform.inventory.domain.model.valueobjects.DeviceStatus;
import com.acme.center.platform.inventory.interfaces.rest.resources.UpdateDeviceCommandResource;
import com.acme.center.platform.shared.domain.model.valueobjects.DeviceTypes;

public class UpdateDeviceCommandFromResource {
    public static UpdateDeviceCommand toCommandFromResource(UpdateDeviceCommandResource resource) {
        return new UpdateDeviceCommand(resource.id(),
                                       resource.serialNumber(),
                                       resource.model(),
                DeviceTypes.valueOf(resource.deviceType()),
                                       resource.installationDate(),
                DeviceStatus.valueOf(resource.status()),
                                       resource.performanceIndicatorId());
    }
}
