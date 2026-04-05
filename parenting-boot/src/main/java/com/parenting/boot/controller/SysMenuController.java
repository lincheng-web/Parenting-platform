package com.parenting.boot.controller;

import com.parenting.core.entity.SysMenu;
import com.parenting.core.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService menuService;

    @PostMapping
    public SysMenu createMenu(@RequestBody SysMenu menu) {
        return menuService.createMenu(menu);
    }

    @PutMapping
    public SysMenu updateMenu(@RequestBody SysMenu menu) {
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
    }

    @GetMapping("/{id}")
    public SysMenu getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id).orElseThrow();
    }

    @GetMapping
    public List<SysMenu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/tree")
    public List<SysMenu> getMenuTree() {
        List<SysMenu> menus = menuService.getAllMenus();
        return menuService.buildMenuTree(menus);
    }

    @GetMapping("/parent/{parentId}")
    public List<SysMenu> getMenusByParentId(@PathVariable Long parentId) {
        return menuService.getMenusByParentId(parentId);
    }
}
