package com.parenting.core.service.impl;

import com.parenting.core.entity.SysMenu;
import com.parenting.core.repository.SysMenuRepository;
import com.parenting.core.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuRepository menuRepository;

    @Override
    @Transactional
    public SysMenu createMenu(SysMenu menu) {
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        return menuRepository.save(menu);
    }

    @Override
    @Transactional
    public SysMenu updateMenu(SysMenu menu) {
        menu.setUpdateTime(LocalDateTime.now());
        return menuRepository.save(menu);
    }

    @Override
    @Transactional
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    public Optional<SysMenu> getMenuById(Long id) {
        return menuRepository.findById(id);
    }

    @Override
    public List<SysMenu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public List<SysMenu> getMenusByParentId(Long parentId) {
        return menuRepository.findByParentId(parentId);
    }

    @Override
    public List<SysMenu> getMenusByStatus(Integer status) {
        return menuRepository.findByStatus(status);
    }

    @Override
    public List<SysMenu> getMenusByLevel(Integer level) {
        return menuRepository.findByLevel(level);
    }

    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> rootMenus = menus.stream()
                .filter(menu -> menu.getParentId() == 0)
                .collect(Collectors.toList());

        for (SysMenu rootMenu : rootMenus) {
            buildChildMenus(rootMenu, menus);
        }

        return rootMenus;
    }

    private void buildChildMenus(SysMenu parentMenu, List<SysMenu> allMenus) {
        List<SysMenu> childMenus = allMenus.stream()
                .filter(menu -> menu.getParentId().equals(parentMenu.getId()))
                .collect(Collectors.toList());

        for (SysMenu childMenu : childMenus) {
            buildChildMenus(childMenu, allMenus);
        }
    }
}
