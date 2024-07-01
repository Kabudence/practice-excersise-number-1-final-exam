package com.acme.center.platform.shared.domain.model.entities;

import com.acme.center.platform.shared.domain.model.valueobjects.DeviceTypes;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * DeviceType entity.
 * Represents the type of device.
 */
@Entity
public class DeviceType {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * DeviceTypes name.
     */
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private DeviceTypes name;

    /**
     * Default constructor.
     */
    public DeviceType() {}

    /**
     * Constructor.
     * @param name DeviceTypes
     */

    public DeviceType(DeviceTypes name) {
        this.name = name;
    }

}
