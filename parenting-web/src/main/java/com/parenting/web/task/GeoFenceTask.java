package com.parenting.web.task;

import com.parenting.core.entity.Device;
import com.parenting.core.entity.GeoFence;
import com.parenting.core.entity.LocationLog;
import com.parenting.core.service.DeviceService;
import com.parenting.core.service.GeoFenceService;
import com.parenting.web.handler.WebSocketHandler;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

/**
 * 地理围栏定时任务
 */
@Component
public class GeoFenceTask {

    private static final Logger log = LoggerFactory.getLogger(GeoFenceTask.class);

    private final RedisTemplate<String, Object> redisTemplate;
    private final DeviceService deviceService;
    private final GeoFenceService geoFenceService;
    private final WebSocketHandler webSocketHandler;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public GeoFenceTask(RedisTemplate<String, Object> redisTemplate, DeviceService deviceService,
            GeoFenceService geoFenceService, WebSocketHandler webSocketHandler) {
        this.redisTemplate = redisTemplate;
        this.deviceService = deviceService;
        this.geoFenceService = geoFenceService;
        this.webSocketHandler = webSocketHandler;
    }

    @Scheduled(fixedRate = 60000)
    public void checkGeoFence() {
        try {
            Set<String> keys = redisTemplate.keys("device:latest:*");
            if (keys == null || keys.isEmpty()) {
                return;
            }

            for (String key : keys) {
                String deviceNo = key.replace("device:latest:", "");
                Device device = deviceService.findByDeviceNo(deviceNo);
                if (device == null) {
                    continue;
                }

                LocationLog locationLog = (LocationLog) redisTemplate.opsForValue().get(key);
                if (locationLog == null) {
                    continue;
                }

                List<GeoFence> fences = geoFenceService.findByChildIdAndEnabled(device.getChildId(), true);
                for (GeoFence fence : fences) {
                    if (!isTimeInRange(fence)) {
                        continue;
                    }

                    if (!isPointInFence(locationLog.getLat(), locationLog.getLng(), fence.getFencePoints())) {
                        sendAlert(device, fence);
                    }
                }
            }
        } catch (Exception e) {
            log.error("围栏判断失败", e);
        }
    }

    private boolean isTimeInRange(GeoFence fence) {
        LocalDateTime now = LocalDateTime.now();
        int dayOfWeek = now.getDayOfWeek().getValue();
        String weekDays = fence.getWeekDays();

        if (!weekDays.contains(String.valueOf(dayOfWeek))) {
            return false;
        }

        String currentTime = now.format(DateTimeFormatter.ofPattern("HH:mm"));
        return currentTime.compareTo(fence.getStartTime()) >= 0
                && currentTime.compareTo(fence.getEndTime()) <= 0;
    }

    private boolean isPointInFence(double lat, double lng, String fencePoints) {
        try {
            String[] points = fencePoints.split(";");
            Coordinate[] coordinates = new Coordinate[points.length + 1];

            for (int i = 0; i < points.length; i++) {
                String[] point = points[i].split(",");
                coordinates[i] = new Coordinate(Double.parseDouble(point[0]), Double.parseDouble(point[1]));
            }
            coordinates[points.length] = coordinates[0];

            LinearRing ring = geometryFactory.createLinearRing(coordinates);
            Polygon polygon = geometryFactory.createPolygon(ring);

            Point point = geometryFactory.createPoint(new Coordinate(lng, lat));

            return polygon.contains(point);
        } catch (Exception e) {
            log.error("围栏判断失败", e);
            return false;
        }
    }

    private void sendAlert(Device device, GeoFence fence) {
        String childName = device.getChild() != null ? device.getChild().getName() : "未知";
        String fenceName = fence.getName();

        String message = String.format("{\"type\": \"ALERT\", \"childName\": \"%s\", \"fenceName\": \"%s\", \"time\": \"%s\"}",
                childName,
                fenceName,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        if (device.getChild() != null) {
            webSocketHandler.pushMessage(device.getChild().getUserId(), message);
        }
        log.info("发送围栏告警: 孩子={}, 围栏={}", childName, fenceName);
    }
}
