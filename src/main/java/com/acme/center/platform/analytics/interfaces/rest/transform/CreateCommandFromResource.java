package com.acme.center.platform.analytics.interfaces.rest.transform;

import com.acme.center.platform.analytics.domain.model.commands.CreatePerformanceIndicatorCommand;
import com.acme.center.platform.analytics.interfaces.rest.resources.CreateCommandResource;
import com.acme.center.platform.shared.domain.model.valueobjects.DeviceTypes;

public class CreateCommandFromResource {
    public static CreatePerformanceIndicatorCommand toCommandFromResource(CreateCommandResource resource) {
        return new CreatePerformanceIndicatorCommand(resource.name(),
                                                     resource.description(),
                                                     resource.minValue(),
                                                     resource.maxValue(),
                                                     DeviceTypes.valueOf(resource.deviceType()));
    }
}
