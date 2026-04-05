package com.parenting.core.repository;

import com.parenting.core.entity.SysAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysAdminRepository extends JpaRepository<SysAdmin, Long> {
    Optional<SysAdmin> findByUsername(String username);
    Optional<SysAdmin> findByPhone(String phone);
    Optional<SysAdmin> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
}
