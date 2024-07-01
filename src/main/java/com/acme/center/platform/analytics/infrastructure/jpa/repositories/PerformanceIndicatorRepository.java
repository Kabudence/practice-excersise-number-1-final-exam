package com.acme.center.platform.analytics.infrastructure.jpa.repositories;

import com.acme.center.platform.analytics.domain.model.aggregates.PerformanceIndicator;
import com.acme.center.platform.shared.domain.model.entities.DeviceType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for PerformanceIndicator entity
 */
@Repository
public interface PerformanceIndicatorRepository extends JpaRepository<PerformanceIndicator, Long> {

    Optional<PerformanceIndicator>findByDeviceType(@NotNull DeviceType deviceType);
    boolean existsByDeviceTypeAndName(DeviceType deviceType, String name);
}
