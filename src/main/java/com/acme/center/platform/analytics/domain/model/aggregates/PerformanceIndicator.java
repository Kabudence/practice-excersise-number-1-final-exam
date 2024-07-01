package com.acme.center.platform.analytics.domain.model.aggregates;

import com.acme.center.platform.analytics.domain.model.commands.CreatePerformanceIndicatorCommand;
import com.acme.center.platform.inventory.domain.model.commands.CreateDeviceCommand;
import com.acme.center.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.acme.center.platform.shared.domain.model.entities.DeviceType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * Performance Indicator entity.
 * Represents the performance indicator.
 */
@Entity
public class PerformanceIndicator extends AuditableAbstractAggregateRoot<PerformanceIndicator> {

    @NotBlank
    @Getter
    @Size(max = 40)
    private String name;

    @NotBlank
    @Getter
    @Size(max = 40)
    private String description;

    @NotNull
    @Getter
    @Min(0)
    private Double minValue;

    @NotNull
    @Getter
    @Min(1)
    private Double maxValue;

    /**
     * Device type.
     * Represents the type of device.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "device_type_id")
    private DeviceType deviceType;

    /**
     * Default constructor.
     */
    public PerformanceIndicator() {
    }

    /**
     * Constructor.
     * @param command CreatePerformanceIndicatorCommand.
     * @param deviceType DeviceType.
     */
    public PerformanceIndicator(CreatePerformanceIndicatorCommand command, DeviceType deviceType){
        this.name = command.name();
        this.description = command.description();
        this.minValue = command.minValue();
        this.maxValue = command.maxValue();
        this.deviceType = deviceType;
    }

    /**
     * Get device type.
     * @return String.
     */
    public String getDeviceType() {
        return deviceType.getName().toString();
    }

}
