package com.parenting.core.service;

import com.parenting.core.entity.SysConfig;

import java.util.List;
import java.util.Optional;

public interface SysConfigService {
    SysConfig createConfig(SysConfig config);
    SysConfig updateConfig(SysConfig config);
    void deleteConfig(Long id);
    Optional<SysConfig> getConfigById(Long id);
    Optional<SysConfig> getConfigByKey(String configKey);
    List<SysConfig> getAllConfigs();
    List<SysConfig> getConfigsByStatus(Integer status);
    String getConfigValue(String configKey);
}
