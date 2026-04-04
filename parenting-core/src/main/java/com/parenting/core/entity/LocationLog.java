package com.parenting.core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "location_log")
public class LocationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id")
    private Long deviceId;

    private Double lat;
    private Double lng;
    private String address;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    public LocationLog() {
    }

    public LocationLog(Long id, Long deviceId, Double lat, Double lng, String address, LocalDateTime createTime) {
        this.id = id;
        this.deviceId = deviceId;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.createTime = createTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long id;
        private Long deviceId;
        private Double lat;
        private Double lng;
        private String address;
        private LocalDateTime createTime;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder deviceId(Long deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder lat(Double lat) {
            this.lat = lat;
            return this;
        }

        public Builder lng(Double lng) {
            this.lng = lng;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public LocationLog build() {
            return new LocationLog(id, deviceId, lat, lng, address, createTime);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
