package com.parenting.core.service;

import com.parenting.core.entity.GeoFence;
import com.parenting.core.repository.GeoFenceRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GeoFenceService {

    private final GeoFenceRepository geoFenceRepository;

    public GeoFenceService(GeoFenceRepository geoFenceRepository) {
        this.geoFenceRepository = geoFenceRepository;
    }

    public List<GeoFence> findByChildIdAndEnabled(Long childId, Boolean enabled) {
        return geoFenceRepository.findByChildIdAndEnabled(childId, enabled);
    }
}
