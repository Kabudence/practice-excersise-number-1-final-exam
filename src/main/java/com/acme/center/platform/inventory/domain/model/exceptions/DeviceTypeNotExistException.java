package com.acme.center.platform.inventory.domain.model.exceptions;

import com.acme.center.platform.shared.domain.model.valueobjects.DeviceTypes;

public class DeviceTypeNotExistException extends RuntimeException{
    public DeviceTypeNotExistException(DeviceTypes deviceType) {
        super("Device type: " + deviceType + " does not exist");
    }
}
