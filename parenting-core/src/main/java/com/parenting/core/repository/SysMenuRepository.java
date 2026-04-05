package com.parenting.core.repository;

import com.parenting.core.entity.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuRepository extends JpaRepository<SysMenu, Long> {
    List<SysMenu> findByParentId(Long parentId);
    List<SysMenu> findByStatus(Integer status);
    List<SysMenu> findByLevel(Integer level);
}
