package com.parenting.core.repository;

import com.parenting.core.entity.SysConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysConfigRepository extends JpaRepository<SysConfig, Long> {
    Optional<SysConfig> findByConfigKey(String configKey);
    boolean existsByConfigKey(String configKey);
}
