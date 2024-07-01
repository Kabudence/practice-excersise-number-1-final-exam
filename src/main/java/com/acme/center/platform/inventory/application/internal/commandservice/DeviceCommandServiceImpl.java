package com.acme.center.platform.inventory.application.internal.commandservice;

import com.acme.center.platform.inventory.application.internal.outboundedservices.ExternalAnalyticsService;
import com.acme.center.platform.inventory.domain.model.aggregates.Device;
import com.acme.center.platform.inventory.domain.model.commands.CreateDeviceCommand;
import com.acme.center.platform.inventory.domain.model.commands.UpdateDeviceCommand;
import com.acme.center.platform.inventory.domain.model.valueobjects.DeviceStatus;
import com.acme.center.platform.inventory.domain.services.DeviceCommandService;
import com.acme.center.platform.inventory.infrastructure.jpa.repositories.DeviceRepository;
import com.acme.center.platform.shared.domain.model.valueobjects.DeviceTypes;
import com.acme.center.platform.shared.infrastructure.jpa.respositories.DeviceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * DeviceCommandServiceImpl class is responsible for handling the device commands
 * This class implements the DeviceCommandService interface
 */
@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {

    private final DeviceRepository deviceRepository;
    private final ExternalAnalyticsService externalAnalyticsService;
    private final DeviceTypeRepository deviceTypeRepository;

    public DeviceCommandServiceImpl(DeviceRepository deviceRepository, ExternalAnalyticsService externalAnalyticsService, DeviceTypeRepository deviceTypeRepository) {
        this.deviceRepository = deviceRepository;
        this.externalAnalyticsService = externalAnalyticsService;
        this.deviceTypeRepository = deviceTypeRepository;
    }

    /**
     * Command handler to create device
     * @param command containing device details
     * This method validates the device details and creates a new device
     * @return Device
     */
    @Override
    public Optional<Device> handle(CreateDeviceCommand command) {
     validation(command.serialNumber(), command.installationDate(), command.deviceType());
     var performanceIndicator = externalAnalyticsService.fetchPerformanceIndicatorById(command.performanceIndicatorId());
     var deviceType = deviceTypeRepository.findByName(command.deviceType());
     var device = new Device(command, deviceType, performanceIndicator.get());
        deviceRepository.save(device);
        return Optional.of(device);

    }
    /**
     * Command handler to update device
     * @param command containing device details
     * This method validates the device details and updates the device
     * @return Device
     */
    @Override
    public Optional<Device> handle(UpdateDeviceCommand command) {
        validation(command.serialNumber(), command.installationDate(), command.deviceType());
        var performanceIndicator = externalAnalyticsService.fetchPerformanceIndicatorById(command.performanceIndicatorId());
        var deviceType = deviceTypeRepository.findByName(command.deviceType());
        var result = deviceRepository.findById(command.id());
        if (result.isEmpty()) throw new IllegalArgumentException("Device does not exist");
        var deviceToUpdate = result.get();
        try{
            var updatedDevice=deviceRepository.save(deviceToUpdate.updateDevice(command, deviceType, performanceIndicator.get()));
            return Optional.of(updatedDevice);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Error while updating device: " + e.getMessage());
        }
    }
        /**
         * Validation method to check if the device already exists, if the installationDate is before the currentDate and if the deviceType already exists
         * @param serialNumber its represents the serial number of the device
         * @param date its represents the installation date of the device
         * @param deviceTypes its represents the type of the device
         **/
    private void validation(String serialNumber, Date date , DeviceTypes deviceTypes) {
        Date currentDate = new Date();
        if (deviceRepository.existsBySerialNumber(serialNumber)) {
            throw new IllegalArgumentException("Serial number already exists");
        }
        if(!deviceTypeRepository.existsByName(deviceTypes)){
            throw new IllegalArgumentException("Device type does not exist");
        }
        if(date.before(currentDate)|| date.equals(currentDate)){
            throw new IllegalArgumentException("Installation date cannot be in the past");
        }


    }

}
