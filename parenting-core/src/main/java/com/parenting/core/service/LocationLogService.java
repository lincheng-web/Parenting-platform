package com.parenting.core.service;

import com.parenting.core.entity.LocationLog;
import com.parenting.core.repository.LocationLogRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationLogService {
    private final LocationLogRepository locationLogRepository;

    public LocationLogService(LocationLogRepository locationLogRepository) {
        this.locationLogRepository = locationLogRepository;
    }
    
    public void save(LocationLog locationLog) {
        locationLogRepository.save(locationLog);
    }
}