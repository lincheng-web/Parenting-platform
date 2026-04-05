package com.parenting.boot.controller;

import com.parenting.core.entity.SysAdmin;
import com.parenting.core.service.SysAdminService;
import com.parenting.core.service.SysLogService;
import com.parenting.boot.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminLoginController {

    @Autowired
    private SysAdminService adminService;
    
    @Autowired
    private SysLogService logService;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginData, HttpServletRequest request) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        SysAdmin admin = adminService.getAdminByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户名不存在"));

        if (admin.getStatus() != 1) {
            throw new RuntimeException("账号已禁用");
        }

        if (!passwordEncoder.matches(password, admin.getPasswordHash())) {
            throw new RuntimeException("密码错误");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("adminId", admin.getId());
        claims.put("username", admin.getUsername());

        String token = jwtUtils.generateToken(admin.getId(), admin.getUsername(), claims);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("admin", admin);

        return response;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout() {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "退出成功");
        return response;
    }
}
