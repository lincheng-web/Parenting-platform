package com.parenting.web.controller;

import com.parenting.core.entity.Device;
import com.parenting.core.entity.LocationLog;
import com.parenting.core.service.BaiduMapService;
import com.parenting.core.service.DeviceService;
import com.parenting.core.service.LocationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 硬件控制器，处理位置上报
 */
@RestController
@RequestMapping("/api/hardware")
public class HardwareController {

    private static final Logger log = LoggerFactory.getLogger(HardwareController.class);

    private final DeviceService deviceService;
    private final LocationLogService locationLogService;
    private final BaiduMapService baiduMapService;
    private final RedisTemplate<String, Object> redisTemplate;

    public HardwareController(DeviceService deviceService, LocationLogService locationLogService,
                              BaiduMapService baiduMapService, RedisTemplate<String, Object> redisTemplate) {
        this.deviceService = deviceService;
        this.locationLogService = locationLogService;
        this.baiduMapService = baiduMapService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 接收位置上报
     */
    @PostMapping("/location/upload")
    public String uploadLocation(@RequestBody LocationUploadRequest request) {
        try {
            Device device = deviceService.findByDeviceNo(request.getDeviceNo());
            if (device == null) {
                log.warn("设备未绑定: {}", request.getDeviceNo());
                return "Device not bound";
            }

            String address = baiduMapService.reverseGeocoding(request.getLat(), request.getLng());

            LocationLog locationLog = LocationLog.builder()
                    .deviceId(device.getId())
                    .lat(request.getLat())
                    .lng(request.getLng())
                    .address(address)
                    .createTime(LocalDateTime.now())
                    .build();
            locationLogService.save(locationLog);

            device.setLastActiveTime(LocalDateTime.now());
            deviceService.update(device);

            String redisKey = "device:latest:" + request.getDeviceNo();
            redisTemplate.opsForValue().set(redisKey, locationLog, 5, TimeUnit.MINUTES);

            log.info("位置上报成功: 设备={}, 位置={}, {}", request.getDeviceNo(), request.getLat(), request.getLng());
            return "Success";
        } catch (Exception e) {
            log.error("位置上报处理失败", e);
            return "Error";
        }
    }

    public static class LocationUploadRequest {
        private String deviceNo;
        private double lat;
        private double lng;
        private long timestamp;

        public String getDeviceNo() {
            return deviceNo;
        }

        public void setDeviceNo(String deviceNo) {
            this.deviceNo = deviceNo;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
}