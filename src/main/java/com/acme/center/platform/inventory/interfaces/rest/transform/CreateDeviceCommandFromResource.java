package com.acme.center.platform.inventory.interfaces.rest.transform;

import com.acme.center.platform.inventory.domain.model.commands.CreateDeviceCommand;
import com.acme.center.platform.inventory.domain.model.valueobjects.DeviceStatus;
import com.acme.center.platform.inventory.interfaces.rest.resources.CreateDeviceCommandResource;
import com.acme.center.platform.shared.domain.model.valueobjects.DeviceTypes;

public class CreateDeviceCommandFromResource {

    public static CreateDeviceCommand toCommandFromResource(CreateDeviceCommandResource resource) {
        return new CreateDeviceCommand( resource.serialNumber(),
                                        resource.model(),
                                        DeviceTypes.valueOf(resource.deviceType()),
                                        resource.installationDate(),
                                        DeviceStatus.valueOf(resource.status()),
                                        resource.performanceIndicatorId() );
    }
}
