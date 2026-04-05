package com.parenting.core.service;

import com.parenting.core.entity.SysMenu;

import java.util.List;
import java.util.Optional;

public interface SysMenuService {
    SysMenu createMenu(SysMenu menu);
    SysMenu updateMenu(SysMenu menu);
    void deleteMenu(Long id);
    Optional<SysMenu> getMenuById(Long id);
    List<SysMenu> getAllMenus();
    List<SysMenu> getMenusByParentId(Long parentId);
    List<SysMenu> getMenusByStatus(Integer status);
    List<SysMenu> getMenusByLevel(Integer level);
    List<SysMenu> buildMenuTree(List<SysMenu> menus);
}
