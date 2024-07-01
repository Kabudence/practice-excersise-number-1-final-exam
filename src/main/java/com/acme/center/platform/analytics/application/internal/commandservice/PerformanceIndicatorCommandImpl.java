package com.acme.center.platform.analytics.application.internal.commandservice;

import com.acme.center.platform.analytics.domain.model.aggregates.PerformanceIndicator;
import com.acme.center.platform.analytics.domain.model.commands.CreatePerformanceIndicatorCommand;
import com.acme.center.platform.analytics.domain.service.PerformanceIndicatorCommand;
import com.acme.center.platform.analytics.infrastructure.jpa.repositories.PerformanceIndicatorRepository;
import com.acme.center.platform.shared.infrastructure.jpa.respositories.DeviceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of Performance Indicator Command
 */
@Service
public class PerformanceIndicatorCommandImpl  implements PerformanceIndicatorCommand {

    private final PerformanceIndicatorRepository performanceIndicatorRepository;
    private final DeviceTypeRepository deviceTypeRepository;

    /**
     * Constructor
     * @param performanceIndicatorRepository repository
     * @param deviceTypeRepository  repository
     */
    public PerformanceIndicatorCommandImpl(PerformanceIndicatorRepository performanceIndicatorRepository, DeviceTypeRepository deviceTypeRepository) {
        this.performanceIndicatorRepository = performanceIndicatorRepository;
        this.deviceTypeRepository = deviceTypeRepository;
    }
    /**
     * Create a new Performance Indicator
        * @param command containing performance indicator details
     *This method creates a new performance indicator.
     * It validates the device type, min and max values, and checks if a performance indicator with the same name and device type already exists.
     *@return Optional<PerformanceIndicator>
     */
    @Override
    public Optional<PerformanceIndicator> handle(CreatePerformanceIndicatorCommand command) {
        var deviceType=deviceTypeRepository.findByName(command.deviceType());
        if(deviceType==null){
            throw new IllegalArgumentException("Device type not found");
        }
        if(command.maxValue() < command.minValue()){
            throw new IllegalArgumentException("Max value must be greater than min value");
        }
        if(performanceIndicatorRepository.existsByDeviceTypeAndName(deviceType,command.name())){
            throw new IllegalArgumentException("Performance with same name and DeviceType indicator already exists");
        }

        var performanceIndicator=new PerformanceIndicator(command,deviceType);
        performanceIndicatorRepository.save(performanceIndicator);
        return Optional.of(performanceIndicator);
    }



}
