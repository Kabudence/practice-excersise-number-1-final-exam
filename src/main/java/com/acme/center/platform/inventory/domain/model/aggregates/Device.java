package com.acme.center.platform.inventory.domain.model.aggregates;

import com.acme.center.platform.analytics.domain.model.aggregates.PerformanceIndicator;
import com.acme.center.platform.inventory.domain.model.commands.CreateDeviceCommand;
import com.acme.center.platform.inventory.domain.model.commands.UpdateDeviceCommand;
import com.acme.center.platform.inventory.domain.model.valueobjects.DeviceStatus;
import com.acme.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.center.platform.shared.domain.model.entities.DeviceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

/**
 * Device entity.
 * Represents the device.
 */
@Entity
public class Device extends AuditableAbstractAggregateRoot<Device> {

    @NotNull
    @Getter
    @Size(max = 30, message = "Serial number must be maximum 30 characters")
    @NotBlank(message = "Serial number is required")
    private String serialNumber;

    @NotNull
    @Getter
    @Size(max = 50, message = "Model must be maximum 50 characters")
    @NotBlank(message = "Model is required")
    private String model;

    /**
     * Device type.
     * Represents the type of device.
     */
    @ManyToOne
    @NotNull(message = "Device type is required")
    @JoinTable(name = "device_device_type", joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "device_type_id"))
    private DeviceType deviceType;

    @NotNull(message = "Installation date is required")
    @Getter
    @FutureOrPresent(message = "Installation date must be in the future or be today")
    private Date installationDate;

    /**
     * Device status.
     * Represents the status of the device.
     */
    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;

    /**
     * Performance indicator.
     * Represents the performance indicator.
     */
    @ManyToOne
    @NotNull(message = "Performance indicator is required")
    @JoinTable(name = "device_performance_indicator", joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "performance_indicator_id"))
    private PerformanceIndicator performanceIndicator;



    /**
     * Default constructor.
     */
    public Device() {
    }

    /**
     * Constructor.
     * @param command CreateDeviceCommand
     * @param deviceType DeviceType
     *@param performanceIndicator PerformanceIndicator
     */
    public Device(CreateDeviceCommand command, DeviceType deviceType, PerformanceIndicator performanceIndicator) {
        this.serialNumber = command.serialNumber();
        this.model = command.model();
        this.deviceType = deviceType;
        this.installationDate = command.installationDate();
        this.status = command.status();
        this.performanceIndicator = performanceIndicator;
    }

    /**
     * Update device.
     * @param command CreateDeviceCommand
     * @param deviceType DeviceType
     * @return Device
     * @param performanceIndicator PerformanceIndicator
     */
    public Device updateDevice(UpdateDeviceCommand command, DeviceType deviceType, PerformanceIndicator performanceIndicator) {
        this.serialNumber = command.serialNumber();
        this.model = command.model();
        this.deviceType = deviceType;
        this.installationDate = command.installationDate();
        this.status = command.status();
        this.performanceIndicator = performanceIndicator;

        return this;
    }

    /**
     * Get device type.
     * @return String
     */
    public String getDeviceType() {

        return deviceType.getName().toString();
    }
    public String getStatus() {
        return status.toString();
    }
    public Long getPerformanceIndicatorId() {
        return performanceIndicator.getId();
    }


}
