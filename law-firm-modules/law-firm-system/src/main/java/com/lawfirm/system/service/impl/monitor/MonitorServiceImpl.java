package com.lawfirm.system.service.impl.monitor;

import com.lawfirm.common.log.annotation.AuditLog;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import com.lawfirm.core.message.service.MessageService;
import com.lawfirm.model.system.dto.monitor.MonitorAlertDTO;
import com.lawfirm.model.system.dto.monitor.MonitorDataDTO;
import com.lawfirm.model.system.dto.monitor.MonitorQueryDTO;
import com.lawfirm.model.system.entity.monitor.SysMonitorAlert;
import com.lawfirm.model.system.entity.monitor.SysMonitorData;
import com.lawfirm.model.system.mapper.monitor.SysMonitorAlertMapper;
import com.lawfirm.model.system.mapper.monitor.SysMonitorDataMapper;
import com.lawfirm.model.system.service.MonitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonitorServiceImpl implements MonitorService {
    
    private final SysMonitorDataMapper dataMapper;
    private final SysMonitorAlertMapper alertMapper;
    private final MessageService messageService;
    
    private static final Instant startTime = Instant.now();
    private static final Map<String, String> healthStatus = new ConcurrentHashMap<>();
    private static final String CACHE_NAME = "monitorCache";

    @Cacheable(cacheNames = CACHE_NAME, key = "'monitorData'")
    @Override
    public List<MonitorDataDTO> getMonitorData(MonitorQueryDTO queryDTO) {
        List<SysMonitorData> dbData = dataMapper.selectRecentData(queryDTO.getType(), queryDTO.getHours());
        return convertToDTOs(dbData);
    }

    @Cacheable(cacheNames = CACHE_NAME, key = "'alerts'")
    @Override
    public List<MonitorAlertDTO> getAlerts(MonitorQueryDTO queryDTO) {
        List<SysMonitorAlert> dbAlerts = alertMapper.selectRecentAlerts(queryDTO.getType(), queryDTO.getHours());
        return convertToAlertDTOs(dbAlerts);
    }

    @AuditLog(description = "处理告警", operateType = "UPDATE", businessType = "MONITOR")
    @Transactional
    @CacheEvict(cacheNames = CACHE_NAME, allEntries = true)
    @Override
    public boolean handleAlert(String alertId, String handler, String result) {
        try {
            alertMapper.updateAlertStatus(alertId, "HANDLED", handler, result);
            messageService.sendSystemAlert("告警处理通知", 
                "告警ID: " + alertId + " 已由 " + handler + " 处理，结果：" + result);
            return true;
        } catch (Exception e) {
            log.error("告警处理失败", e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean closeAlert(String alertId) {
        log.info("关闭告警，告警ID：{}", alertId);
        try {
            // 关闭告警
            // TODO: 实现告警关闭逻辑，包括更新数据库和发送通知
            return true;
        } catch (Exception e) {
            log.error("关闭告警失败", e);
            return false;
        }
    }

    @Override
    public Map<String, String> getHealthStatus() {
        Map<String, String> status = new HashMap<>();
        
        // 检查CPU健康状态
        double cpuUsage = getCpuUsage();
        status.put("CPU", cpuUsage > 80 ? "DOWN" : "UP");

        // 检查内存健康状态
        double memoryUsage = getMemoryUsage();
        status.put("Memory", memoryUsage > 80 ? "DOWN" : "UP");

        // 检查系统负载
        double[] systemLoad = getSystemLoad();
        status.put("SystemLoad", systemLoad[0] > 10 ? "DOWN" : "UP");

        return status;
    }

    @Override
    public String getUptime() {
        Duration uptime = Duration.between(startTime, Instant.now());
        long days = uptime.toDays();
        long hours = uptime.toHoursPart();
        long minutes = uptime.toMinutesPart();
        long seconds = uptime.toSecondsPart();
        
        return String.format("%d天%d小时%d分钟%d秒", days, hours, minutes, seconds);
    }

    @Override
    public int getOnlineUserCount() {
        // TODO: 实现在线用户统计逻辑
        return 0;
    }

    @Override
    public double[] getSystemLoad() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        double systemLoadAverage = osBean.getSystemLoadAverage();
        
        // 由于Windows系统不支持获取1分钟、5分钟、15分钟的负载值
        // 这里简单返回当前系统负载作为1分钟负载，其他两个值设为-1
        return new double[]{
            systemLoadAverage > 0 ? systemLoadAverage : 0,
            -1,
            -1
        };
    }

    private double getCpuUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            return ((com.sun.management.OperatingSystemMXBean) osBean).getCpuLoad() * 100;
        }
        return -1;
    }

    private double getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        return ((double) usedMemory / totalMemory) * 100;
    }

    private List<MonitorDataDTO> convertToDTOs(List<SysMonitorData> dataList) {
        List<MonitorDataDTO> result = new ArrayList<>();
        dataList.forEach(data -> {
            MonitorDataDTO dto = new MonitorDataDTO();
            dto.setType(data.getType());
            dto.setName(data.getName());
            dto.setTitle(data.getTitle());
            dto.setCurrent(data.getValue());
            dto.setUnit(data.getUnit());
            result.add(dto);
        });
        return result;
    }

    private List<MonitorAlertDTO> convertToAlertDTOs(List<SysMonitorAlert> alerts) {
        List<MonitorAlertDTO> result = new ArrayList<>();
        alerts.forEach(alert -> {
            MonitorAlertDTO dto = new MonitorAlertDTO();
            dto.setAlertId(alert.getAlertId());
            dto.setType(alert.getType());
            dto.setLevel(alert.getLevel());
            dto.setMessage(alert.getMessage());
            dto.setCreateTime(alert.getCreateTime());
            result.add(dto);
        });
        return result;
    }
} 