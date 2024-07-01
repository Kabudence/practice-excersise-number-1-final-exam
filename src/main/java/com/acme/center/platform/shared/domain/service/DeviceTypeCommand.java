package com.acme.center.platform.shared.domain.service;

import com.acme.center.platform.shared.domain.model.commands.SeedDeviceTypeCommand;

public interface DeviceTypeCommand {

    void handle(SeedDeviceTypeCommand command);

}
