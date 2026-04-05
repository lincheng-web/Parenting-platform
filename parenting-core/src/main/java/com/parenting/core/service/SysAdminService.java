package com.parenting.core.service;

import com.parenting.core.entity.SysAdmin;
import com.parenting.core.entity.SysRole;

import java.util.List;
import java.util.Optional;

public interface SysAdminService {
    SysAdmin createAdmin(SysAdmin admin);
    SysAdmin updateAdmin(SysAdmin admin);
    void deleteAdmin(Long id);
    Optional<SysAdmin> getAdminById(Long id);
    Optional<SysAdmin> getAdminByUsername(String username);
    List<SysAdmin> getAllAdmins();
    List<SysAdmin> getAdminsByRoleId(Long roleId);
    void resetPassword(Long adminId, String newPassword);
    void updateStatus(Long adminId, Integer status);
    List<SysRole> getRolesByAdminId(Long adminId);
    void assignRoles(Long adminId, List<Long> roleIds);
}
