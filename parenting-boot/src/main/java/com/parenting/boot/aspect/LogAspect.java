package com.parenting.boot.aspect;

import com.parenting.core.entity.SysLog;
import com.parenting.core.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final SysLogService logService;

    public LogAspect(SysLogService logService) {
        this.logService = logService;
    }

    @Pointcut("execution(* com.parenting.boot.controller..*.*(..))")
    public void logPointcut() {
    }

    @AfterReturning(pointcut = "logPointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        saveLog(joinPoint, null, 1);
    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        saveLog(joinPoint, ex.getMessage(), 0);
    }

    private void saveLog(JoinPoint joinPoint, String errorMsg, Integer result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            SysLog log = new SysLog();
            log.setAdminId(1L); // 这里需要从SecurityContext中获取当前管理员ID
            log.setAdminName("admin"); // 这里需要从SecurityContext中获取当前管理员名称
            log.setOperation(getOperation(joinPoint));
            log.setIp(request.getRemoteAddr());
            log.setMethod(request.getMethod() + " " + request.getRequestURI());
            log.setParams(Arrays.toString(joinPoint.getArgs()));
            log.setResult(result);
            log.setErrorMsg(errorMsg);
            log.setCreateTime(LocalDateTime.now());
            logService.saveLog(log);
        }
    }

    private String getOperation(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        switch (methodName) {
            case "createAdmin":
                return "创建管理员";
            case "updateAdmin":
                return "更新管理员";
            case "deleteAdmin":
                return "删除管理员";
            case "resetPassword":
                return "重置密码";
            case "createRole":
                return "创建角色";
            case "updateRole":
                return "更新角色";
            case "deleteRole":
                return "删除角色";
            case "createPermission":
                return "创建权限";
            case "updatePermission":
                return "更新权限";
            case "deletePermission":
                return "删除权限";
            case "createMenu":
                return "创建菜单";
            case "updateMenu":
                return "更新菜单";
            case "deleteMenu":
                return "删除菜单";
            case "createConfig":
                return "创建配置";
            case "updateConfig":
                return "更新配置";
            case "deleteConfig":
                return "删除配置";
            default:
                return "操作";
        }
    }
}
