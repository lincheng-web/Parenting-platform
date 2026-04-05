package com.parenting.boot.controller;

import com.parenting.core.entity.SysAdmin;
import com.parenting.core.service.SysAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class SysAdminController {

    @Autowired
    private SysAdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public SysAdmin createAdmin(@RequestBody SysAdmin admin) {
        admin.setPasswordHash(passwordEncoder.encode(admin.getPasswordHash()));
        return adminService.createAdmin(admin);
    }

    @PutMapping
    public SysAdmin updateAdmin(@RequestBody SysAdmin admin) {
        return adminService.updateAdmin(admin);
    }

    @DeleteMapping("/detail/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
    }

    @GetMapping("/detail/{id}")
    public SysAdmin getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id).orElseThrow();
    }

    @GetMapping
    public List<SysAdmin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @PutMapping("/reset-password")
    public void resetPassword(@RequestBody Map<String, Object> data) {
        Long adminId = Long.parseLong(data.get("adminId").toString());
        String newPassword = (String) data.get("newPassword");
        adminService.resetPassword(adminId, passwordEncoder.encode(newPassword));
    }

    @PutMapping("/update-status")
    public void updateStatus(@RequestBody Map<String, Object> data) {
        Long adminId = Long.parseLong(data.get("adminId").toString());
        Integer status = (Integer) data.get("status");
        adminService.updateStatus(adminId, status);
    }

    @PostMapping("/assign-roles")
    public void assignRoles(@RequestBody Map<String, Object> data) {
        Long adminId = Long.parseLong(data.get("adminId").toString());
        List<Long> roleIds = (List<Long>) data.get("roleIds");
        adminService.assignRoles(adminId, roleIds);
    }

    @GetMapping("/roles/{adminId}")
    public List<?> getRolesByAdminId(@PathVariable Long adminId) {
        return adminService.getRolesByAdminId(adminId);
    }
}
