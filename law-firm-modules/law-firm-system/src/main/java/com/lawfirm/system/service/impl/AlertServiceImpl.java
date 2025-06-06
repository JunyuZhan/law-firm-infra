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
            SysMonitorAlert alert = new SysMonitorAlert();
            
            // 设置默认配置ID（后续可以改为实际配置ID）
            alert.setConfigId(1L);
            
            // 设置告警信息
            alert.setType(type);
            alert.setLevel(level);
            alert.setMessage(message);
            
            // 转换告警级别
            alert.setAlertLevel(convertAlertLevel(level));
            alert.setAlertTitle(String.format("%s告警", type));
            alert.setAlertContent(message);
            alert.setAlertTime(LocalDateTime.now());
            alert.setAlertStatus("PENDING"); // 待处理
            
            alertMapper.insert(alert);
            
            // 生成业务ID
            String businessId = "ALERT_" + System.currentTimeMillis() + "_" + alert.getId();
            alert.setAlertId(businessId);
            alertMapper.updateById(alert);
            
            log.info("已发送告警: [{}] {}, 级别: {}, ID: {}", type, message, level, businessId);
            
            // 记录告警日志
            if ("ERROR".equals(level)) {
                log.error("系统告警: [{}] {}", type, message);
            } else if ("WARNING".equals(level)) {
                log.warn("系统告警: [{}] {}", type, message);
            } else {
                log.info("系统告警: [{}] {}", type, message);
            }
            
            return businessId;
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
    public String sendServerAlert(String serverName, String level, String message) {
        String formattedMessage = String.format("服务器[%s]告警: %s", serverName, message);
        return sendAlert("SERVER", level, formattedMessage);
    }

    @Override
    @Transactional
    @AuditLog(description = "关闭系统告警", operateType = "UPDATE", businessType = "MONITOR")
    public boolean closeAlert(String alertId) {
        try {
            // 尝试按业务ID查找
            SysMonitorAlert alert = alertMapper.selectById(alertId);
            if (alert == null) {
                // 如果按ID查找不到，尝试按数字ID查找（兼容旧数据）
                try {
                    Long id = Long.valueOf(alertId);
                    alert = alertMapper.selectById(id);
                } catch (NumberFormatException e) {
                    log.error("无效的告警ID格式: {}", alertId);
                    return false;
                }
            }
            
            if (alert == null) {
                log.error("告警不存在，告警ID：{}", alertId);
                return false;
            }
            
            alert.setAlertStatus("CLOSED"); // 已关闭
            alert.setHandlerName("system");
            alert.setHandleTime(new java.util.Date());
            alert.setHandleResult("系统自动关闭");
            
            int result = alertMapper.updateById(alert);
            
            log.info("已关闭告警: {}", alertId);
            
            return result > 0;
        } catch (Exception e) {
            log.error("关闭告警失败: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 转换告警级别字符串为数字
     */
    private Integer convertAlertLevel(String level) {
        switch (level.toUpperCase()) {
            case "WARNING":
                return 1; // 警告
            case "ERROR":
                return 2; // 严重
            case "CRITICAL":
                return 3; // 紧急
            default:
                return 1; // 默认警告
        }
    }
} 