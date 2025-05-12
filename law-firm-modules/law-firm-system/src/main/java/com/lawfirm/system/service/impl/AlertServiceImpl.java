package com.lawfirm.system.service.impl;

import com.lawfirm.common.log.annotation.AuditLog;
import com.lawfirm.model.system.entity.monitor.SysMonitorAlert;
import com.lawfirm.model.system.mapper.monitor.SysMonitorAlertMapper;
import com.lawfirm.model.system.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * 系统告警服务实现类
 */
@Slf4j
@Service("systemAlertServiceImpl")
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final SysMonitorAlertMapper alertMapper;

    @Override
    @Transactional
    @AuditLog(description = "发送系统告警", operateType = "CREATE", businessType = "MONITOR")
    public String sendAlert(String type, String level, String message) {
        try {
            String alertId = UUID.randomUUID().toString().replace("-", "");
            
            SysMonitorAlert alert = new SysMonitorAlert();
            alert.setAlertId(alertId);
            alert.setType(type);
            alert.setLevel(level);
            alert.setMessage(message);
            alert.setAlertStatus("PENDING");  // 待处理状态
            alert.setCreateTime(LocalDateTime.now());
            alert.setUpdateTime(LocalDateTime.now());
            
            alertMapper.insert(alert);
            
            log.info("已发送告警: [{}] {}, 级别: {}", type, message, level);
            
            // 记录告警日志，实际项目中可以扩展为发送邮件、短信等
            if ("ERROR".equals(level)) {
                log.error("系统告警: [{}] {}", type, message);
            } else if ("WARNING".equals(level)) {
                log.warn("系统告警: [{}] {}", type, message);
            } else {
                log.info("系统告警: [{}] {}", type, message);
            }
            
            return alertId;
        } catch (Exception e) {
            log.error("发送告警失败: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    @Transactional
    public String sendDbAlert(String dbName, String level, String message) {
        String formattedMessage = String.format("数据库[%s]告警: %s", dbName, message);
        return sendAlert("DATABASE", level, formattedMessage);
    }

    @Override
    @Transactional
    @AuditLog(description = "关闭系统告警", operateType = "UPDATE", businessType = "MONITOR")
    public boolean closeAlert(String alertId) {
        try {
            SysMonitorAlert alert = new SysMonitorAlert();
            alert.setAlertId(alertId);
            alert.setAlertStatus("CLOSED");
            alert.setHandler("system");
            alert.setHandleTime(new Date());
            alert.setHandleResult("系统自动关闭");
            alert.setUpdateTime(LocalDateTime.now());
            
            int result = alertMapper.updateById(alert);
            
            log.info("已关闭告警: {}", alertId);
            
            return result > 0;
        } catch (Exception e) {
            log.error("关闭告警失败: " + e.getMessage(), e);
            return false;
        }
    }
} 