package com.parenting.boot;

import com.parenting.core.entity.SysAdmin;
import com.parenting.core.service.SysAdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication(scanBasePackages = {"com.parenting"})
@EnableJpaRepositories(basePackages = {"com.parenting.core.repository"})
@EntityScan(basePackages = {"com.parenting.core.entity"})
@EnableScheduling
public class ParentingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParentingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initAdmin(SysAdminService adminService, PasswordEncoder passwordEncoder) {
        return args -> {
            // 检查是否存在管理员用户
            if (adminService.getAdminByUsername("admin").isEmpty()) {
                // 创建默认管理员用户
                SysAdmin admin = new SysAdmin();
                admin.setUsername("admin");
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                admin.setRealName("系统管理员");
                admin.setPhone("13800138000");
                admin.setEmail("admin@example.com");
                admin.setStatus(1);
                admin.setCreateTime(LocalDateTime.now());
                admin.setUpdateTime(LocalDateTime.now());
                adminService.createAdmin(admin);
                System.out.println("默认管理员用户创建成功");
            }
        };
    }
}
