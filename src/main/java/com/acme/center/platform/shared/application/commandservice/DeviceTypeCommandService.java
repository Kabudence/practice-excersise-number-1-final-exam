package com.acme.center.platform.shared.application.commandservice;
import com.acme.center.platform.shared.domain.model.commands.SeedDeviceTypeCommand;
import com.acme.center.platform.shared.domain.model.entities.DeviceType;
import com.acme.center.platform.shared.domain.model.valueobjects.DeviceTypes;
import com.acme.center.platform.shared.domain.service.DeviceTypeCommand;
import com.acme.center.platform.shared.infrastructure.jpa.respositories.DeviceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DeviceTypeCommandService implements DeviceTypeCommand {

    private final DeviceTypeRepository deviceTypeRepository;

    public DeviceTypeCommandService(DeviceTypeRepository deviceTypeRepository) {
        this.deviceTypeRepository = deviceTypeRepository;
    }

    @Override
    public void handle(SeedDeviceTypeCommand command) {
        Arrays.stream(DeviceTypes.values()).forEach(role -> {
            if(!deviceTypeRepository.existsByName(role)) {
                deviceTypeRepository.save(new DeviceType(DeviceTypes.valueOf(role.name())));
            }
        });

    }
}
