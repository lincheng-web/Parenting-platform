package com.parenting.boot.controller;

import com.parenting.core.entity.SysRole;
import com.parenting.core.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @PostMapping
    public SysRole createRole(@RequestBody SysRole role) {
        return roleService.createRole(role);
    }

    @PutMapping
    public SysRole updateRole(@RequestBody SysRole role) {
        return roleService.updateRole(role);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }

    @GetMapping("/{id}")
    public SysRole getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id).orElseThrow();
    }

    @GetMapping
    public List<SysRole> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping("/assign-permissions")
    public void assignPermissions(@RequestBody Map<String, Object> data) {
        Long roleId = Long.parseLong(data.get("roleId").toString());
        List<Long> permissionIds = (List<Long>) data.get("permissionIds");
        roleService.assignPermissions(roleId, permissionIds);
    }

    @PostMapping("/assign-menus")
    public void assignMenus(@RequestBody Map<String, Object> data) {
        Long roleId = Long.parseLong(data.get("roleId").toString());
        List<Long> menuIds = (List<Long>) data.get("menuIds");
        roleService.assignMenus(roleId, menuIds);
    }

    @GetMapping("/permissions/{roleId}")
    public List<?> getPermissionsByRoleId(@PathVariable Long roleId) {
        return roleService.getPermissionsByRoleId(roleId);
    }

    @GetMapping("/menus/{roleId}")
    public List<?> getMenusByRoleId(@PathVariable Long roleId) {
        return roleService.getMenusByRoleId(roleId);
    }
}
