package com.parenting.core.service;

import com.parenting.core.entity.SysPermission;

import java.util.List;
import java.util.Optional;

public interface SysPermissionService {
    SysPermission createPermission(SysPermission permission);
    SysPermission updatePermission(SysPermission permission);
    void deletePermission(Long id);
    Optional<SysPermission> getPermissionById(Long id);
    Optional<SysPermission> getPermissionByCode(String permissionCode);
    List<SysPermission> getAllPermissions();
    List<SysPermission> getPermissionsByType(Integer type);
    List<SysPermission> getPermissionsByParentId(Long parentId);
    List<SysPermission> getPermissionsByStatus(Integer status);
}
