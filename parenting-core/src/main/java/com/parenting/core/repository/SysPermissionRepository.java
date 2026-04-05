package com.parenting.core.repository;

import com.parenting.core.entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysPermissionRepository extends JpaRepository<SysPermission, Long> {
    Optional<SysPermission> findByPermissionCode(String permissionCode);
    List<SysPermission> findByParentId(Long parentId);
    List<SysPermission> findByType(Integer type);
    List<SysPermission> findByStatus(Integer status);
}
