package com.parenting.core.service;

import com.parenting.core.entity.Device;
import com.parenting.core.repository.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device findByDeviceNo(String deviceNo) {
        return deviceRepository.findByDeviceNo(deviceNo);
    }

    public void update(Device device) {
        deviceRepository.save(device);
    }
}