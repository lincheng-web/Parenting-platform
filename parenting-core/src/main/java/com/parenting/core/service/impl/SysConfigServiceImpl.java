package com.parenting.core.service.impl;

import com.parenting.core.entity.SysConfig;
import com.parenting.core.repository.SysConfigRepository;
import com.parenting.core.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigRepository configRepository;

    @Override
    @Transactional
    public SysConfig createConfig(SysConfig config) {
        config.setCreateTime(LocalDateTime.now());
        config.setUpdateTime(LocalDateTime.now());
        return configRepository.save(config);
    }

    @Override
    @Transactional
    public SysConfig updateConfig(SysConfig config) {
        config.setUpdateTime(LocalDateTime.now());
        return configRepository.save(config);
    }

    @Override
    @Transactional
    public void deleteConfig(Long id) {
        configRepository.deleteById(id);
    }

    @Override
    public Optional<SysConfig> getConfigById(Long id) {
        return configRepository.findById(id);
    }

    @Override
    public Optional<SysConfig> getConfigByKey(String configKey) {
        return configRepository.findByConfigKey(configKey);
    }

    @Override
    public List<SysConfig> getAllConfigs() {
        return configRepository.findAll();
    }

    @Override
    public List<SysConfig> getConfigsByStatus(Integer status) {
        return configRepository.findAll();
    }

    @Override
    public String getConfigValue(String configKey) {
        Optional<SysConfig> config = configRepository.findByConfigKey(configKey);
        return config.map(SysConfig::getConfigValue).orElse(null);
    }
}
