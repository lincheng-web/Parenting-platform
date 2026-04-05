package com.parenting.boot.controller;

import com.parenting.core.entity.SysPermission;
import com.parenting.core.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sys/permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService permissionService;

    @PostMapping
    public SysPermission createPermission(@RequestBody SysPermission permission) {
        return permissionService.createPermission(permission);
    }

    @PutMapping
    public SysPermission updatePermission(@RequestBody SysPermission permission) {
        return permissionService.updatePermission(permission);
    }

    @DeleteMapping("/{id}")
    public void deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
    }

    @GetMapping("/{id}")
    public SysPermission getPermissionById(@PathVariable Long id) {
        return permissionService.getPermissionById(id).orElseThrow();
    }

    @GetMapping
    public List<SysPermission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    @GetMapping("/type/{type}")
    public List<SysPermission> getPermissionsByType(@PathVariable Integer type) {
        return permissionService.getPermissionsByType(type);
    }

    @GetMapping("/parent/{parentId}")
    public List<SysPermission> getPermissionsByParentId(@PathVariable Long parentId) {
        return permissionService.getPermissionsByParentId(parentId);
    }
}
