package com.parenting.core.service.impl;

import com.parenting.core.entity.SysLog;
import com.parenting.core.repository.SysLogRepository;
import com.parenting.core.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogRepository logRepository;

    @Override
    @Transactional
    public void saveLog(SysLog log) {
        logRepository.save(log);
    }

    @Override
    public List<SysLog> getAllLogs() {
        return logRepository.findAll();
    }

    @Override
    public List<SysLog> getLogsByAdminId(Long adminId) {
        return logRepository.findByAdminId(adminId);
    }

    @Override
    public List<SysLog> getLogsByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return logRepository.findByCreateTimeBetween(startTime, endTime);
    }

    @Override
    public List<SysLog> getLogsByResult(Integer result) {
        return logRepository.findByResult(result);
    }

    @Override
    @Transactional
    public void deleteLogsBefore(LocalDateTime time) {
        List<SysLog> logs = logRepository.findByCreateTimeBetween(LocalDateTime.MIN, time);
        logRepository.deleteAll(logs);
    }
}
