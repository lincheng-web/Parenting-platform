package com.parenting.core.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "geo_fence")
public class GeoFence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "child_id")
    private Long childId;
    
    private String name;
    
    @Column(name = "fence_points")
    private String fencePoints;
    
    @Column(name = "start_time")
    private String startTime;
    
    @Column(name = "end_time")
    private String endTime;
    
    @Column(name = "week_days")
    private String weekDays;
    
    private Boolean enabled;
    
    @ManyToOne
    @JoinColumn(name = "child_id", insertable = false, updatable = false)
    private Child child;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFencePoints() {
        return fencePoints;
    }

    public void setFencePoints(String fencePoints) {
        this.fencePoints = fencePoints;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(String weekDays) {
        this.weekDays = weekDays;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
}