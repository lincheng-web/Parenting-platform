package com.parenting.core.service;

import com.parenting.core.entity.SysPermission;
import com.parenting.core.entity.SysRole;
import com.parenting.core.entity.SysMenu;

import java.util.List;
import java.util.Optional;

public interface SysRoleService {
    SysRole createRole(SysRole role);
    SysRole updateRole(SysRole role);
    void deleteRole(Long id);
    Optional<SysRole> getRoleById(Long id);
    Optional<SysRole> getRoleByCode(String roleCode);
    List<SysRole> getAllRoles();
    List<SysRole> getRolesByStatus(Integer status);
    List<SysPermission> getPermissionsByRoleId(Long roleId);
    List<SysMenu> getMenusByRoleId(Long roleId);
    void assignPermissions(Long roleId, List<Long> permissionIds);
    void assignMenus(Long roleId, List<Long> menuIds);
}
