package com.parenting.core.repository;

import com.parenting.core.entity.SysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenu, Long> {
    List<SysRoleMenu> findByRoleId(Long roleId);
    List<SysRoleMenu> findByMenuId(Long menuId);
    void deleteByRoleId(Long roleId);
    void deleteByMenuId(Long menuId);
}
