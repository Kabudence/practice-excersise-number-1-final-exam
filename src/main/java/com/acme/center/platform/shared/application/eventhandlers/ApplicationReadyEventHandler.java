package com.acme.center.platform.shared.application.eventhandlers;

import com.acme.center.platform.shared.application.commandservice.DeviceTypeCommandService;
import com.acme.center.platform.shared.domain.model.commands.SeedDeviceTypeCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service("deviceTypeApplicationReadyEventHandler")
public class ApplicationReadyEventHandler {
    private final DeviceTypeCommandService deviceTypeCommandService;
    private static final Logger LOGGER = LoggerFactory.getLogger(com.acme.center.platform.iam.application.internal.eventhandlers.ApplicationReadyEventHandler.class);


    public ApplicationReadyEventHandler(DeviceTypeCommandService deviceTypeCommandService) {
        this.deviceTypeCommandService = deviceTypeCommandService;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        var applicationName = event.getApplicationContext().getId();
        LOGGER.info("Starting to verify if roles seeding is needed for {} at {}", applicationName, currentTimestamp());
        var seedDeviceTypeCommand = new SeedDeviceTypeCommand();
        deviceTypeCommandService.handle(seedDeviceTypeCommand);
        LOGGER.info("Roles seeding verification finished for {} at {}", applicationName, currentTimestamp());
    }
    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

}
