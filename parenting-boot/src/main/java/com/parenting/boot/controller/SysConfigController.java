package com.parenting.boot.controller;

import com.parenting.core.entity.SysConfig;
import com.parenting.core.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/config")
public class SysConfigController {

    @Autowired
    private SysConfigService configService;

    @PostMapping
    public SysConfig createConfig(@RequestBody SysConfig config) {
        return configService.createConfig(config);
    }

    @PutMapping
    public SysConfig updateConfig(@RequestBody SysConfig config) {
        return configService.updateConfig(config);
    }

    @DeleteMapping("/{id}")
    public void deleteConfig(@PathVariable Long id) {
        configService.deleteConfig(id);
    }

    @GetMapping("/{id}")
    public SysConfig getConfigById(@PathVariable Long id) {
        return configService.getConfigById(id).orElseThrow();
    }

    @GetMapping
    public List<SysConfig> getAllConfigs() {
        return configService.getAllConfigs();
    }

    @GetMapping("/value/{configKey}")
    public String getConfigValue(@PathVariable String configKey) {
        return configService.getConfigValue(configKey);
    }
}
