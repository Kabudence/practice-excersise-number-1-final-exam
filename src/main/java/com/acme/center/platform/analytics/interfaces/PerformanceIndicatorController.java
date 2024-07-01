package com.acme.center.platform.analytics.interfaces;


import com.acme.center.platform.analytics.domain.service.PerformanceIndicatorCommand;
import com.acme.center.platform.analytics.interfaces.rest.resources.CreateCommandResource;
import com.acme.center.platform.analytics.interfaces.rest.resources.PerformanceIndicatorResource;
import com.acme.center.platform.analytics.interfaces.rest.transform.CreateCommandFromResource;
import com.acme.center.platform.analytics.interfaces.rest.transform.CreateResourceFromEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is a REST controller that exposes the performance indicator resource.
 * It includes the following operations:
 * - POST /api/v1/performance-indicator: creates a new performance indicator
 */
@RestController
@RequestMapping(value = "/ap/v1/performance-indicator", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "PerformanceIndicator", description = "Performance Indicator Management Endpoints")
public class PerformanceIndicatorController {

    private final PerformanceIndicatorCommand performanceIndicatorCommand;

    public PerformanceIndicatorController(PerformanceIndicatorCommand performanceIndicatorCommand) {
        this.performanceIndicatorCommand = performanceIndicatorCommand;
    }

    /**
     * This method creates a new performance indicator.
     * It validates the device type, min and max values, and checks if a performance indicator with the same name and device type already exists.
     * @param resource containing performance indicator details
     * @return the created performance indicator
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<PerformanceIndicatorResource> createPerformanceIndicator(@RequestBody CreateCommandResource resource){
        var createCommand= CreateCommandFromResource.toCommandFromResource(resource);
        var performanceIndicatorEntity=performanceIndicatorCommand.handle(createCommand);
        if(performanceIndicatorEntity.isEmpty()) return ResponseEntity.badRequest().build();
        var resourceToResponse= CreateResourceFromEntity.toResourceFromEntity(performanceIndicatorEntity.get());
        return new ResponseEntity<>(resourceToResponse, HttpStatus.CREATED);

    }

}
