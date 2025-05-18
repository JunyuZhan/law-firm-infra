package com.lawfirm.system.service.impl.monitor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.log.annotation.AuditLog;
import com.lawfirm.model.system.entity.monitor.SysServerMonitor;
import com.lawfirm.model.system.mapper.monitor.SysServerMonitorMapper;
import com.lawfirm.system.config.SystemModuleConfig.MonitorProperties;
import com.lawfirm.model.system.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 服务器监控服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServerMonitorServiceImpl extends ServiceImpl<SysServerMonitorMapper, SysServerMonitor> {

    private final SysServerMonitorMapper serverMonitorMapper;
    private final MonitorProperties monitorProperties;
    private final AlertService alertService;
    
    /**
     * 定时收集服务器监控数据
     */
    @Scheduled(fixedRateString = "${system.monitor.metrics-interval:60}", timeUnit = TimeUnit.SECONDS)
    @Transactional
    @CacheEvict(value = "monitorCache", allEntries = true)
    public void collectServerMetrics() {
        try {
            SysServerMonitor monitor = new SysServerMonitor();
            
            // 获取服务器基本信息
            String serverName = System.getenv("COMPUTERNAME");
            if (serverName == null || serverName.isEmpty()) {
                serverName = "Unknown-Server";
            }
            monitor.setServerName(serverName);
            
            // 获取服务器IP
            try {
                monitor.setServerIp(InetAddress.getLocalHost().getHostAddress());
            } catch (Exception e) {
                monitor.setServerIp("127.0.0.1");
                log.warn("获取服务器IP异常，使用默认值127.0.0.1", e);
            }
            
            // 获取CPU使用率
            com.sun.management.OperatingSystemMXBean osBean = 
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            double cpuLoad = osBean.getCpuLoad();
            if (cpuLoad < 0) cpuLoad = 0; // 处理初始负值情况
            monitor.setCpuUsage(BigDecimal.valueOf(cpuLoad * 100).setScale(2, RoundingMode.HALF_UP));
            
            // 获取内存使用率
            long totalMemory = osBean.getTotalMemorySize();
            long freeMemory = osBean.getFreeMemorySize();
            double memoryUsage = ((double)(totalMemory - freeMemory) / totalMemory) * 100;
            monitor.setMemoryUsage(BigDecimal.valueOf(memoryUsage).setScale(2, RoundingMode.HALF_UP));
            
            // 获取磁盘使用率
            double diskUsage = getDiskUsage();
            monitor.setDiskUsage(BigDecimal.valueOf(diskUsage).setScale(2, RoundingMode.HALF_UP));
            
            // 设置网络流量（目前简化实现，后续可通过JMX或系统API获取实际值）
            monitor.setNetworkRx(0L);
            monitor.setNetworkTx(0L);
            
            // 获取系统负载
            double[] systemLoad = getSystemLoad();
            monitor.setServerLoad(systemLoad[0] + "," + systemLoad[1] + "," + systemLoad[2]);
            
            monitor.setMonitorTime(LocalDateTime.now());
            
            this.baseMapper.insert(monitor);
            log.debug("服务器监控数据已收集: {}", monitor);
            
            // 检查是否需要告警
            checkAlert(monitor);
        } catch (Exception e) {
            log.error("Server monitor data collection failed", e);
        }
    }
    
    /**
     * 获取服务器信息列表
     */
    @Cacheable(value = "monitorCache", key = "'serverList'")
    public List<SysServerMonitor> getServerList() {
        LambdaQueryWrapper<SysServerMonitor> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysServerMonitor::getMonitorTime);
        wrapper.last("LIMIT 100");
        return serverMonitorMapper.selectList(wrapper);
    }
    
    /**
     * 获取最新服务器信息
     */
    @Cacheable(value = "monitorCache", key = "'latestServer_' + #serverIp")
    public SysServerMonitor getLatestServerInfo(String serverIp) {
        return serverMonitorMapper.getLatestByServerIp(serverIp);
    }
    
    /**
     * 获取磁盘使用率
     */
    private double getDiskUsage() {
        try {
            File[] roots = File.listRoots();
            if (roots.length == 0) {
                return 0.0;
            }
            
            long totalSpace = 0;
            long freeSpace = 0;
            
            for (File root : roots) {
                totalSpace += root.getTotalSpace();
                freeSpace += root.getFreeSpace();
            }
            
            if (totalSpace == 0) {
                return 0.0;
            }
            
            return ((double)(totalSpace - freeSpace) / totalSpace) * 100;
        } catch (Exception e) {
            log.error("获取磁盘使用率异常", e);
            return 0.0;
        }
    }
    
    /**
     * 获取系统负载
     */
    private double[] getSystemLoad() {
        try {
            com.sun.management.OperatingSystemMXBean osBean = 
                (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                
            // Windows系统无法获取真实负载，使用CPU使用率代替
            double cpuLoad = osBean.getCpuLoad();
            if (cpuLoad < 0) cpuLoad = 0;
            
            return new double[] { cpuLoad, cpuLoad, cpuLoad };
        } catch (Exception e) {
            log.error("获取系统负载异常", e);
            return new double[] { 0.0, 0.0, 0.0 };
        }
    }
    
    /**
     * 检查并触发告警
     */
    private void checkAlert(SysServerMonitor monitor) {
        try {
            // 检查CPU使用率
            if (monitor.getCpuUsage().doubleValue() > 80) {
                log.warn("服务器CPU使用率超过阈值: {}%, 服务器: {}", 
                    monitor.getCpuUsage(), monitor.getServerName());
                
                // 发送告警
                String message = String.format("服务器CPU使用率超过阈值: %.2f%%", monitor.getCpuUsage().doubleValue());
                alertService.sendServerAlert(monitor.getServerName(), "WARNING", message);
            }
            
            // 检查内存使用率
            if (monitor.getMemoryUsage().doubleValue() > 80) {
                log.warn("服务器内存使用率超过阈值: {}%, 服务器: {}", 
                    monitor.getMemoryUsage(), monitor.getServerName());
                
                // 发送告警
                String message = String.format("服务器内存使用率超过阈值: %.2f%%", monitor.getMemoryUsage().doubleValue());
                alertService.sendServerAlert(monitor.getServerName(), "WARNING", message);
            }
            
            // 检查磁盘使用率
            if (monitor.getDiskUsage().doubleValue() > 90) {
                log.warn("服务器磁盘使用率超过阈值: {}%, 服务器: {}", 
                    monitor.getDiskUsage(), monitor.getServerName());
                
                // 发送告警
                String message = String.format("服务器磁盘使用率超过阈值: %.2f%%", monitor.getDiskUsage().doubleValue());
                alertService.sendServerAlert(monitor.getServerName(), "WARNING", message);
            }
        } catch (Exception e) {
            log.error("检查告警异常", e);
        }
    }
} 