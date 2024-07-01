package com.acme.center.platform.inventory.infrastructure.jpa.repositories;

import com.acme.center.platform.inventory.domain.model.aggregates.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository  extends JpaRepository<Device,Long> {

    boolean existsBySerialNumber(String serialNumber);
}
