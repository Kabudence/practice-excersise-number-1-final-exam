package com.acme.center.platform.iam.application.internal.eventhandlers;

import com.acme.center.platform.iam.domain.model.commands.SeedRolesCommand;
import com.acme.center.platform.iam.domain.services.RoleCommandService;
import com.acme.center.platform.shared.application.commandservice.DeviceTypeCommandService;
import com.acme.center.platform.shared.domain.model.commands.SeedDeviceTypeCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ApplicationReadyEventHandler {
    private final RoleCommandService roleCommandService;
    private final DeviceTypeCommandService deviceTypeCommandService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);


    public ApplicationReadyEventHandler(RoleCommandService roleCommandService, DeviceTypeCommandService deviceTypeCommandService) {
        this.roleCommandService = roleCommandService;
        this.deviceTypeCommandService = deviceTypeCommandService;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Starting to verify if roles seeding is needed for {} at {}", applicationName, currentTimestamp());
        var deviceTypeCommand = new SeedDeviceTypeCommand();
        var seedRolesCommand = new SeedRolesCommand();
        deviceTypeCommandService.handle(deviceTypeCommand);
        roleCommandService.handle(seedRolesCommand);
        LOGGER.info("Roles seeding verification finished for {} at {}", applicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
