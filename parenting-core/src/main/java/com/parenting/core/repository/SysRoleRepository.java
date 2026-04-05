package com.parenting.core.repository;

import com.parenting.core.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, Long> {
    Optional<SysRole> findByRoleName(String roleName);
    Optional<SysRole> findByRoleCode(String roleCode);
    boolean existsByRoleName(String roleName);
    boolean existsByRoleCode(String roleCode);
}
