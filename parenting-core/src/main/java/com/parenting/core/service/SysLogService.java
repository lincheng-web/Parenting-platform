package com.parenting.core.service;

import com.parenting.core.entity.SysLog;

import java.time.LocalDateTime;
import java.util.List;

public interface SysLogService {
    void saveLog(SysLog log);
    List<SysLog> getAllLogs();
    List<SysLog> getLogsByAdminId(Long adminId);
    List<SysLog> getLogsByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
    List<SysLog> getLogsByResult(Integer result);
    void deleteLogsBefore(LocalDateTime time);
}
