package com.parenting.core.repository;

import com.parenting.core.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findByDeviceNo(String deviceNo);
}