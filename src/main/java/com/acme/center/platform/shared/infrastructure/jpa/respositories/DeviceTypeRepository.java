package com.acme.center.platform.shared.infrastructure.jpa.respositories;

import com.acme.center.platform.shared.domain.model.entities.DeviceType;
import com.acme.center.platform.shared.domain.model.valueobjects.DeviceTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceTypeRepository  extends JpaRepository<DeviceType,Long> {

    boolean existsByName(DeviceTypes deviceType);
    DeviceType findByName(DeviceTypes deviceType);
}
