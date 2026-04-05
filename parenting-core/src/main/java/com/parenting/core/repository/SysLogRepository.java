package com.parenting.core.repository;

import com.parenting.core.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SysLogRepository extends JpaRepository<SysLog, Long> {
    List<SysLog> findByAdminId(Long adminId);
    List<SysLog> findByCreateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
    List<SysLog> findByResult(Integer result);
}
