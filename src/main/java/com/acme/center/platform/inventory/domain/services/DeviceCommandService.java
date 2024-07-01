package com.acme.center.platform.inventory.domain.services;

import com.acme.center.platform.inventory.domain.model.aggregates.Device;
import com.acme.center.platform.inventory.domain.model.commands.CreateDeviceCommand;
import com.acme.center.platform.inventory.domain.model.commands.UpdateDeviceCommand;

import java.util.Optional;

public interface DeviceCommandService {

    Optional<Device> handle(CreateDeviceCommand command);
    Optional<Device> handle(UpdateDeviceCommand command);

}
