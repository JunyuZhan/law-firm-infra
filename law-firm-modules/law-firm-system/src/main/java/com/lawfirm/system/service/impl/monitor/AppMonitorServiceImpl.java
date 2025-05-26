package com.lawfirm.system.service.impl.monitor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.log.annotation.AuditLog;
import com.lawfirm.model.system.entity.monitor.SysAppMonitor;
import com.lawfirm.model.system.mapper.monitor.SysAppMonitorMapper;
import com.lawfirm.system.config.SystemModuleConfig.MonitorProperties;
import com.lawfirm.model.system.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 应用监控服务实现
 */
@Slf4j
@Service("systemAppMonitorServiceImpl")
@RequiredArgsConstructor
public class AppMonitorServiceImpl extends ServiceImpl<SysAppMonitorMapper, SysAppMonitor> {

    private final SysAppMonitorMapper appMonitorMapper;
    private final MonitorProperties monitorProperties;
    private final AlertService alertService;
    
    /**
     * 应用名称
     */
    private static final String APP_NAME = "law-firm-system";
    
    /**
     * 定时收集应用监控数据
     */
    @Scheduled(fixedRateString = "${system.monitor.metrics-interval:60}", timeUnit = TimeUnit.SECONDS)
    @Transactional
    @CacheEvict(value = "monitorCache", allEntries = true)
    public void collectAppMetrics() {
        try {
            SysAppMonitor monitor = new SysAppMonitor();
            monitor.setAppName(APP_NAME);
            monitor.setInstanceId(getInstanceId());
            
            // 收集JVM内存数据
            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            monitor.setJvmMemoryUsed(memoryBean.getHeapMemoryUsage().getUsed());
            monitor.setJvmMemoryMax(memoryBean.getHeapMemoryUsage().getMax());
            monitor.setHeapUsed(memoryBean.getHeapMemoryUsage().getUsed());
            monitor.setNonHeapUsed(memoryBean.getNonHeapMemoryUsage().getUsed());
            
            // 收集线程数据
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            monitor.setJvmThreads(threadBean.getThreadCount());
            monitor.setThreadActiveCount(threadBean.getThreadCount());
            monitor.setThreadPeakCount(threadBean.getPeakThreadCount());
            
            // 收集GC数据
            long gcCount = 0;
            long gcTime = 0;
            for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans()) {
                long count = gcBean.getCollectionCount();
                if (count >= 0) {
                    gcCount += count;
                }
                long time = gcBean.getCollectionTime();
                if (time >= 0) {
                    gcTime += time;
                }
            }
            monitor.setGcCount((int) gcCount);
            monitor.setGcTime(gcTime);
            
            // HTTP会话数（模拟值）
            monitor.setHttpSessions(100);
            
            monitor.setMonitorTime(LocalDateTime.now());
            
            appMonitorMapper.insert(monitor);
            log.debug("应用监控数据已收集: {}", monitor);
            
            // 检查是否超过阈值，若超过则触发告警
            checkAlert(monitor);
        } catch (Exception e) {
            log.error("收集应用监控数据异常", e);
        }
    }
    
    /**
     * 获取应用信息列表
     */
    @Cacheable(value = "monitorCache", key = "'appList'")
    public List<SysAppMonitor> getAppList() {
        LambdaQueryWrapper<SysAppMonitor> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysAppMonitor::getMonitorTime);
        wrapper.last("LIMIT 100");
        return appMonitorMapper.selectList(wrapper);
    }
    
    /**
     * 获取最新应用信息
     */
    @Cacheable(value = "monitorCache", key = "'latestApp_' + #appName + '_' + #instanceId")
    public SysAppMonitor getLatestAppInfo(String appName, String instanceId) {
        return appMonitorMapper.getLatestByAppAndInstance(appName, instanceId);
    }
    
    /**
     * 获取实例ID
     */
    private String getInstanceId() {
        try {
            return ManagementFactory.getRuntimeMXBean().getName();
        } catch (Exception e) {
            log.error("获取实例ID异常", e);
            return "unknown-instance";
        }
    }
    
    /**
     * 检查并触发告警
     */
    private void checkAlert(SysAppMonitor monitor) {
        try {
            // 检查JVM内存使用情况，若使用率超过80%则告警
            if (monitor.getJvmMemoryMax() > 0) {
                double memoryUsage = (double) monitor.getJvmMemoryUsed() / monitor.getJvmMemoryMax() * 100;
                if (memoryUsage > monitorProperties.getAlertThreshold().getMemory()) {
                    log.warn("JVM内存使用率超过阈值: {}%, 应用: {}, 实例: {}", 
                        String.format("%.2f", memoryUsage), monitor.getAppName(), monitor.getInstanceId());
                    
                    // 发送告警
                    String message = String.format("JVM内存使用率超过阈值: %.2f%%", memoryUsage);
                    alertService.sendAlert("APPLICATION", "WARNING", message);
                }
            }
            
            // 检查线程数，若超过200则告警
            if (monitor.getThreadActiveCount() > 200) {
                log.warn("线程数超过阈值: {}, 应用: {}, 实例: {}", 
                    monitor.getThreadActiveCount(), monitor.getAppName(), monitor.getInstanceId());
                
                // 发送告警
                String message = String.format("线程数超过阈值: %d", monitor.getThreadActiveCount());
                alertService.sendAlert("APPLICATION", "WARNING", message);
            }
        } catch (Exception e) {
            log.error("检查告警异常", e);
        }
    }
} 