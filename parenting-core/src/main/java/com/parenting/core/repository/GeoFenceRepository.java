package com.parenting.core.repository;

import com.parenting.core.entity.GeoFence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeoFenceRepository extends JpaRepository<GeoFence, Long> {
    List<GeoFence> findByChildIdAndEnabled(Long childId, Boolean enabled);
}