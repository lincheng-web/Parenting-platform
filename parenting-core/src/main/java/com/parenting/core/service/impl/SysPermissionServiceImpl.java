package com.parenting.core.service.impl;

import com.parenting.core.entity.SysPermission;
import com.parenting.core.repository.SysPermissionRepository;
import com.parenting.core.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionRepository permissionRepository;

    @Override
    @Transactional
    public SysPermission createPermission(SysPermission permission) {
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        return permissionRepository.save(permission);
    }

    @Override
    @Transactional
    public SysPermission updatePermission(SysPermission permission) {
        permission.setUpdateTime(LocalDateTime.now());
        return permissionRepository.save(permission);
    }

    @Override
    @Transactional
    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public Optional<SysPermission> getPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Optional<SysPermission> getPermissionByCode(String permissionCode) {
        return permissionRepository.findByPermissionCode(permissionCode);
    }

    @Override
    public List<SysPermission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    public List<SysPermission> getPermissionsByType(Integer type) {
        return permissionRepository.findByType(type);
    }

    @Override
    public List<SysPermission> getPermissionsByParentId(Long parentId) {
        return permissionRepository.findByParentId(parentId);
    }

    @Override
    public List<SysPermission> getPermissionsByStatus(Integer status) {
        return permissionRepository.findByStatus(status);
    }
}
