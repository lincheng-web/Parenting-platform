package com.parenting.core.service.impl;

import com.parenting.core.entity.SysMenu;
import com.parenting.core.entity.SysPermission;
import com.parenting.core.entity.SysRole;
import com.parenting.core.entity.SysRoleMenu;
import com.parenting.core.entity.SysRolePermission;
import com.parenting.core.repository.*;
import com.parenting.core.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleRepository roleRepository;

    @Autowired
    private SysRolePermissionRepository rolePermissionRepository;

    @Autowired
    private SysRoleMenuRepository roleMenuRepository;

    @Autowired
    private SysPermissionRepository permissionRepository;

    @Autowired
    private SysMenuRepository menuRepository;

    @Override
    @Transactional
    public SysRole createRole(SysRole role) {
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public SysRole updateRole(SysRole role) {
        role.setUpdateTime(LocalDateTime.now());
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        rolePermissionRepository.deleteByRoleId(id);
        roleMenuRepository.deleteByRoleId(id);
        roleRepository.deleteById(id);
    }

    @Override
    public Optional<SysRole> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<SysRole> getRoleByCode(String roleCode) {
        return roleRepository.findByRoleCode(roleCode);
    }

    @Override
    public List<SysRole> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<SysRole> getRolesByStatus(Integer status) {
        return roleRepository.findAll();
    }

    @Override
    public List<SysPermission> getPermissionsByRoleId(Long roleId) {
        List<SysRolePermission> rolePermissions = rolePermissionRepository.findByRoleId(roleId);
        List<SysPermission> permissions = new ArrayList<>();
        for (SysRolePermission rolePermission : rolePermissions) {
            permissionRepository.findById(rolePermission.getPermissionId()).ifPresent(permissions::add);
        }
        return permissions;
    }

    @Override
    public List<SysMenu> getMenusByRoleId(Long roleId) {
        List<SysRoleMenu> roleMenus = roleMenuRepository.findByRoleId(roleId);
        List<SysMenu> menus = new ArrayList<>();
        for (SysRoleMenu roleMenu : roleMenus) {
            menuRepository.findById(roleMenu.getMenuId()).ifPresent(menus::add);
        }
        return menus;
    }

    @Override
    @Transactional
    public void assignPermissions(Long roleId, List<Long> permissionIds) {
        rolePermissionRepository.deleteByRoleId(roleId);
        for (Long permissionId : permissionIds) {
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionRepository.save(rolePermission);
        }
    }

    @Override
    @Transactional
    public void assignMenus(Long roleId, List<Long> menuIds) {
        roleMenuRepository.deleteByRoleId(roleId);
        for (Long menuId : menuIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuRepository.save(roleMenu);
        }
    }
}
