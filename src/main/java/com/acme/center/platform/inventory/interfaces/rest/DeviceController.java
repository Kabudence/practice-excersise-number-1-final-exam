package com.acme.center.platform.inventory.interfaces.rest;

import com.acme.center.platform.inventory.domain.services.DeviceCommandService;
import com.acme.center.platform.inventory.interfaces.rest.resources.CreateDeviceCommandResource;
import com.acme.center.platform.inventory.interfaces.rest.resources.DeviceResource;
import com.acme.center.platform.inventory.interfaces.rest.resources.UpdateDeviceCommandResource;
import com.acme.center.platform.inventory.interfaces.rest.transform.CreateDeviceCommandFromResource;
import com.acme.center.platform.inventory.interfaces.rest.transform.DeviceResourceFromEntity;
import com.acme.center.platform.inventory.interfaces.rest.transform.UpdateDeviceCommandFromResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a REST controller that exposes the device resource.
 * It includes the following operations:
 * - POST /api/v1/devices: creates a new device
 * - PUT /api/v1/devices/{id}: updates a device
 */
@RestController
@RequestMapping(value = "/ap/v1/devices", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Devices", description = "Devices Management Endpoints")
public class DeviceController {

    private final DeviceCommandService deviceCommandService;

    public DeviceController(DeviceCommandService deviceCommandService) {
        this.deviceCommandService = deviceCommandService;
    }

    /**
     * This method creates a new device.
     * It validates the device type, min and max values, and checks if a device with the same name and device type already exists.
     * @param resource containing device details
     * @return the created device
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<DeviceResource> createDevice(@RequestBody CreateDeviceCommandResource resource){
        var createCommand= CreateDeviceCommandFromResource.toCommandFromResource(resource);
        var deviceEntity=deviceCommandService.handle(createCommand);
        if(deviceEntity.isEmpty()) return ResponseEntity.badRequest().build();
        var resourceToResponse= DeviceResourceFromEntity.toResourceFromEntity(deviceEntity.get());
        return  new ResponseEntity<>(resourceToResponse, HttpStatus.CREATED);
    }
    /**
     * This method updates a device.
     * It validates the device type, min and max values, and checks if a device with the same name and device type already exists.
     * @param id of the device to update
     * @param resource containing device details
     * @return the updated device
     */
    @PreAuthorize("hasRole('ROLE_INSTRUCTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<DeviceResource> updateDevice(@PathVariable Long id, @RequestBody UpdateDeviceCommandResource resource){

        var updateCommand= UpdateDeviceCommandFromResource.toCommandFromResource(resource);
        var deviceEntity=deviceCommandService.handle(updateCommand);
        if(deviceEntity.isEmpty()) return ResponseEntity.notFound().build();
        var resourceToResponse= DeviceResourceFromEntity.toResourceFromEntity(deviceEntity.get());
        return new ResponseEntity<>(resourceToResponse, HttpStatus.OK);
    }
}
