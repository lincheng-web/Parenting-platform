package com.parenting.core.repository;

import com.parenting.core.entity.SysAdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAdminRoleRepository extends JpaRepository<SysAdminRole, Long> {
    List<SysAdminRole> findByAdminId(Long adminId);
    List<SysAdminRole> findByRoleId(Long roleId);
    void deleteByAdminId(Long adminId);
    void deleteByRoleId(Long roleId);
}
