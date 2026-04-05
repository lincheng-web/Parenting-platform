package com.parenting.core.repository;

import com.parenting.core.entity.SysRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRolePermissionRepository extends JpaRepository<SysRolePermission, Long> {
    List<SysRolePermission> findByRoleId(Long roleId);
    List<SysRolePermission> findByPermissionId(Long permissionId);
    void deleteByRoleId(Long roleId);
    void deleteByPermissionId(Long permissionId);
}
