package com.parenting.core.service.impl;

import com.parenting.core.entity.SysAdmin;
import com.parenting.core.entity.SysAdminRole;
import com.parenting.core.entity.SysRole;
import com.parenting.core.repository.SysAdminRepository;
import com.parenting.core.repository.SysAdminRoleRepository;
import com.parenting.core.repository.SysRoleRepository;
import com.parenting.core.service.SysAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SysAdminServiceImpl implements SysAdminService {

    @Autowired
    private SysAdminRepository adminRepository;
    
    @Autowired
    private SysAdminRoleRepository adminRoleRepository;
    
    @Autowired
    private SysRoleRepository roleRepository;

    @Override
    @Transactional
    public SysAdmin createAdmin(SysAdmin admin) {
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        return adminRepository.save(admin);
    }

    @Override
    @Transactional
    public SysAdmin updateAdmin(SysAdmin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        return adminRepository.save(admin);
    }

    @Override
    @Transactional
    public void deleteAdmin(Long id) {
        adminRoleRepository.deleteByAdminId(id);
        adminRepository.deleteById(id);
    }

    @Override
    public Optional<SysAdmin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Optional<SysAdmin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public List<SysAdmin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public List<SysAdmin> getAdminsByRoleId(Long roleId) {
        List<SysAdminRole> adminRoles = adminRoleRepository.findByRoleId(roleId);
        List<SysAdmin> admins = new ArrayList<>();
        for (SysAdminRole adminRole : adminRoles) {
            adminRepository.findById(adminRole.getAdminId()).ifPresent(admins::add);
        }
        return admins;
    }

    @Override
    @Transactional
    public void resetPassword(Long adminId, String newPassword) {
        SysAdmin admin = adminRepository.findById(adminId).orElseThrow();
        admin.setPasswordHash(newPassword);
        admin.setUpdateTime(LocalDateTime.now());
        adminRepository.save(admin);
    }

    @Override
    @Transactional
    public void updateStatus(Long adminId, Integer status) {
        SysAdmin admin = adminRepository.findById(adminId).orElseThrow();
        admin.setStatus(status);
        admin.setUpdateTime(LocalDateTime.now());
        adminRepository.save(admin);
    }

    @Override
    public List<SysRole> getRolesByAdminId(Long adminId) {
        List<SysAdminRole> adminRoles = adminRoleRepository.findByAdminId(adminId);
        List<SysRole> roles = new ArrayList<>();
        for (SysAdminRole adminRole : adminRoles) {
            roleRepository.findById(adminRole.getRoleId()).ifPresent(roles::add);
        }
        return roles;
    }

    @Override
    @Transactional
    public void assignRoles(Long adminId, List<Long> roleIds) {
        adminRoleRepository.deleteByAdminId(adminId);
        for (Long roleId : roleIds) {
            SysAdminRole adminRole = new SysAdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            adminRoleRepository.save(adminRole);
        }
    }
}
