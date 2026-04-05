package com.parenting.boot.controller;

import com.parenting.core.entity.SysLog;
import com.parenting.core.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService logService;

    @GetMapping
    public List<SysLog> getAllLogs() {
        return logService.getAllLogs();
    }

    @GetMapping("/admin/{adminId}")
    public List<SysLog> getLogsByAdminId(@PathVariable Long adminId) {
        return logService.getLogsByAdminId(adminId);
    }

    @GetMapping("/time-range")
    public List<SysLog> getLogsByTimeRange(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        return logService.getLogsByTimeRange(startTime, endTime);
    }

    @DeleteMapping("/clean")
    public void cleanLogs(@RequestParam LocalDateTime time) {
        logService.deleteLogsBefore(time);
    }
}
