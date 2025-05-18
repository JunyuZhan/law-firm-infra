package com.lawfirm.system.service.impl.monitor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lawfirm.common.cache.annotation.SimpleCache;
import com.lawfirm.common.log.annotation.AuditLog;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.system.dto.monitor.MonitorAlertDTO;
import com.lawfirm.model.system.dto.monitor.MonitorDataDTO;
import com.lawfirm.model.system.dto.monitor.MonitorQueryDTO;
import com.lawfirm.model.system.entity.monitor.SysMonitorAlert;
import com.lawfirm.model.system.entity.monitor.SysMonitorData;
import com.lawfirm.model.system.mapper.monitor.SysMonitorAlertMapper;
import com.lawfirm.model.system.mapper.monitor.SysMonitorDataMapper;
import com.lawfirm.model.system.service.MonitorService;
import com.lawfirm.model.system.vo.monitor.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.io.File;
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.NetworkInterface;
import java.util.Enumeration;

@Slf4j
@Service("systemMonitorServiceImpl")
public class MonitorServiceImpl extends BaseServiceImpl<SysMonitorDataMapper, SysMonitorData> implements MonitorService {
    
    private final SysMonitorDataMapper dataMapper;
    private final SysMonitorAlertMapper alertMapper;
    
    private static final Instant startTime = Instant.now();
    private static final Map<String, String> healthStatus = new ConcurrentHashMap<>();
    private static final String CACHE_NAME = "monitorCache";
    private static final String ONLINE_USER_PREFIX = "online_user:";
    
    private final RedisTemplate<String, Object> redisTemplate;

    public MonitorServiceImpl(SysMonitorDataMapper dataMapper, SysMonitorAlertMapper alertMapper, 
                             @Qualifier("commonCacheRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.dataMapper = dataMapper;
        this.alertMapper = alertMapper;
        this.redisTemplate = redisTemplate;
    }

    @Cacheable(value = CACHE_NAME, key = "'monitorData'")
    @Override
    public List<MonitorDataDTO> getMonitorData(MonitorQueryDTO queryDTO) {
        QueryWrapper<SysMonitorData> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.hasText(queryDTO.getType()), "type", queryDTO.getType())
               .orderByDesc("collect_time")
               .last("limit " + queryDTO.getLimit());
        
        List<SysMonitorData> dbData = dataMapper.selectList(wrapper);
        return convertToDTOs(dbData);
    }

    @Cacheable(value = CACHE_NAME, key = "'alerts'")
    @Override
    public List<MonitorAlertDTO> getAlerts(MonitorQueryDTO queryDTO) {
        QueryWrapper<SysMonitorAlert> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.hasText(queryDTO.getType()), "type", queryDTO.getType())
               .orderByDesc("create_time")
               .last("limit " + queryDTO.getLimit());
        
        List<SysMonitorAlert> dbAlerts = alertMapper.selectList(wrapper);
        return convertToAlertDTOs(dbAlerts);
    }

    @AuditLog(description = "处理告警", operateType = "UPDATE", businessType = "MONITOR")
    @Transactional
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    @Override
    public boolean handleAlert(String alertId, String handler, String result) {
        try {
            SysMonitorAlert alert = new SysMonitorAlert();
            alert.setAlertId(alertId);
            alert.setAlertStatus("HANDLED");
            alert.setHandler(handler);
            alert.setHandleResult(result);
            alert.setHandleTime(new Date());
            
            return alertMapper.updateById(alert) > 0;
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
            SysMonitorAlert alert = alertMapper.selectById(alertId);
            if (alert == null) {
                log.error("告警不存在，告警ID：{}", alertId);
                return false;
            }
            
            // 更新告警状态为关闭
            alert.setAlertStatus("CLOSED");
            alert.setUpdateTime(LocalDateTime.now());
            
            // 更新数据库
            int result = alertMapper.updateById(alert);
            
            // 发送通知（可以通过消息队列异步处理）
            if (result > 0) {
                log.info("告警已关闭，告警ID：{}", alertId);
                // 发布告警关闭事件，由事件监听器处理消息发送
                // eventPublisher.publishEvent(new AlertClosedEvent(alert));
            }
            
            return result > 0;
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
        try {
            Set<String> keys = redisTemplate.keys(ONLINE_USER_PREFIX + "*");
            return keys != null ? keys.size() : 0;
        } catch (Exception e) {
            log.error("获取在线用户数量失败", e);
            return 0;
        }
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
            BeanUtils.copyProperties(data, dto);
            dto.setCurrent(data.getValue());
            result.add(dto);
        });
        return result;
    }

    private List<MonitorAlertDTO> convertToAlertDTOs(List<SysMonitorAlert> alerts) {
        List<MonitorAlertDTO> result = new ArrayList<>();
        alerts.forEach(alert -> {
            MonitorAlertDTO dto = new MonitorAlertDTO();
            BeanUtils.copyProperties(alert, dto);
            dto.setContent(alert.getMessage());
            dto.setAlertTime(alert.getCreateTime());
            dto.setStatus(alert.getAlertStatus());
            result.add(dto);
        });
        return result;
    }

    @Override
    public ServerInfoVO getServerInfo() {
        ServerInfoVO info = new ServerInfoVO();
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        
        info.setServerName(System.getenv("COMPUTERNAME"));
        info.setOsName(osBean.getName());
        info.setOsVersion(System.getProperty("os.version"));
        info.setOsArch(osBean.getArch());
        info.setCpuCores(osBean.getAvailableProcessors());
        
        // 获取内存信息
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOsBean = (com.sun.management.OperatingSystemMXBean) osBean;
            double totalMemoryGB = sunOsBean.getTotalMemorySize() / (1024.0 * 1024 * 1024);
            double freeMemoryGB = sunOsBean.getFreeMemorySize() / (1024.0 * 1024 * 1024);
            double usedMemoryGB = totalMemoryGB - freeMemoryGB;
            
            info.setTotalMemory(totalMemoryGB);
            info.setUsedMemory(usedMemoryGB);
            info.setFreeMemory(freeMemoryGB);
            info.setMemoryUsage((usedMemoryGB / totalMemoryGB) * 100);
        }
        
        return info;
    }

    @Override
    public SystemInfoVO getSystemInfo() {
        SystemInfoVO info = new SystemInfoVO();
        
        info.setSystemName("Law Firm Management System");
        info.setSystemVersion("1.0.0");
        info.setJdkVersion(System.getProperty("java.version"));
        info.setJvmName(System.getProperty("java.vm.name"));
        info.setJvmVersion(System.getProperty("java.vm.version"));
        info.setOsName(System.getProperty("os.name"));
        info.setOsVersion(System.getProperty("os.version"));
        info.setOsArch(System.getProperty("os.arch"));
        info.setUserName(System.getProperty("user.name"));
        info.setUserHome(System.getProperty("user.home"));
        info.setUserDir(System.getProperty("user.dir"));
        info.setStartTime(startTime.toString());
        info.setRunTime(getUptime());
        
        return info;
    }

    @Override
    public JvmInfoVO getJvmInfo() {
        JvmInfoVO info = new JvmInfoVO();
        Runtime runtime = Runtime.getRuntime();
        
        info.setName(System.getProperty("java.vm.name"));
        info.setVersion(System.getProperty("java.vm.version"));
        info.setVendor(System.getProperty("java.vm.vendor"));
        info.setStartTime(startTime.toString());
        info.setRunTime(getUptime());
        
        double maxMemory = runtime.maxMemory() / (1024.0 * 1024);
        double totalMemory = runtime.totalMemory() / (1024.0 * 1024);
        double freeMemory = runtime.freeMemory() / (1024.0 * 1024);
        double usedMemory = totalMemory - freeMemory;
        
        info.setMaxMemory(maxMemory);
        info.setTotalMemory(totalMemory);
        info.setUsedMemory(usedMemory);
        info.setFreeMemory(freeMemory);
        info.setMemoryUsage((usedMemory / totalMemory) * 100);
        
        return info;
    }

    @Override
    public MemoryInfoVO getMemoryInfo() {
        MemoryInfoVO info = new MemoryInfoVO();
        Runtime runtime = Runtime.getRuntime();
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOsBean = (com.sun.management.OperatingSystemMXBean) osBean;
            
            double totalPhysicalMemoryGB = sunOsBean.getTotalMemorySize() / (1024.0 * 1024 * 1024);
            double freePhysicalMemoryGB = sunOsBean.getFreeMemorySize() / (1024.0 * 1024 * 1024);
            double usedPhysicalMemoryGB = totalPhysicalMemoryGB - freePhysicalMemoryGB;
            
            info.setTotalPhysicalMemory(totalPhysicalMemoryGB);
            info.setUsedPhysicalMemory(usedPhysicalMemoryGB);
            info.setFreePhysicalMemory(freePhysicalMemoryGB);
            info.setPhysicalMemoryUsage((usedPhysicalMemoryGB / totalPhysicalMemoryGB) * 100);
            
            double totalSwapGB = sunOsBean.getTotalSwapSpaceSize() / (1024.0 * 1024 * 1024);
            double freeSwapGB = sunOsBean.getFreeSwapSpaceSize() / (1024.0 * 1024 * 1024);
            double usedSwapGB = totalSwapGB - freeSwapGB;
            
            info.setTotalSwapSpace(totalSwapGB);
            info.setUsedSwapSpace(usedSwapGB);
            info.setFreeSwapSpace(freeSwapGB);
            info.setSwapSpaceUsage((usedSwapGB / totalSwapGB) * 100);
        }
        
        // JVM内存信息
        double totalJvmMemoryMB = runtime.totalMemory() / (1024.0 * 1024);
        double freeJvmMemoryMB = runtime.freeMemory() / (1024.0 * 1024);
        double usedJvmMemoryMB = totalJvmMemoryMB - freeJvmMemoryMB;
        double maxJvmMemoryMB = runtime.maxMemory() / (1024.0 * 1024);
        
        info.setTotalJvmMemory(totalJvmMemoryMB);
        info.setUsedJvmMemory(usedJvmMemoryMB);
        info.setFreeJvmMemory(freeJvmMemoryMB);
        info.setJvmMemoryUsage((usedJvmMemoryMB / totalJvmMemoryMB) * 100);
        info.setMaxJvmMemory(maxJvmMemoryMB);
        
        return info;
    }

    @Override
    public CpuInfoVO getCpuInfo() {
        CpuInfoVO info = new CpuInfoVO();
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        
        info.setArch(osBean.getArch());
        info.setLogicalCount(osBean.getAvailableProcessors());
        
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOsBean = (com.sun.management.OperatingSystemMXBean) osBean;
            info.setUsage(sunOsBean.getCpuLoad() * 100);
            info.setSystemUsage(sunOsBean.getCpuLoad() * 100);
        }
        
        double[] systemLoad = getSystemLoad();
        info.setLoad(systemLoad[0]);
        
        return info;
    }

    @Override
    public DiskInfoVO getDiskInfo() {
        DiskInfoVO info = new DiskInfoVO();
        List<DiskInfoVO.DiskPartitionVO> partitions = new ArrayList<>();
        
        File[] roots = File.listRoots();
        double totalSpace = 0;
        double usedSpace = 0;
        double freeSpace = 0;
        
        for (File root : roots) {
            DiskInfoVO.DiskPartitionVO partition = new DiskInfoVO.DiskPartitionVO();
            
            double total = root.getTotalSpace() / (1024.0 * 1024 * 1024);
            double free = root.getFreeSpace() / (1024.0 * 1024 * 1024);
            double used = total - free;
            
            partition.setName(root.getPath());
            partition.setMountPoint(root.getAbsolutePath());
            partition.setTotalSpace(total);
            partition.setUsedSpace(used);
            partition.setFreeSpace(free);
            partition.setUsage((used / total) * 100);
            
            totalSpace += total;
            usedSpace += used;
            freeSpace += free;
            
            partitions.add(partition);
        }
        
        info.setTotalDiskSpace(totalSpace);
        info.setUsedDiskSpace(usedSpace);
        info.setFreeDiskSpace(freeSpace);
        info.setDiskUsage((usedSpace / totalSpace) * 100);
        info.setPartitions(partitions);
        
        return info;
    }

    @Override
    public NetworkInfoVO getNetworkInfo() {
        NetworkInfoVO info = new NetworkInfoVO();
        
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            info.setHostName(localhost.getHostName());
            // 不直接设置IP地址，而是通过网络接口列表来展示
            
            List<NetworkInfoVO.NetworkInterfaceVO> interfaces = new ArrayList<>();
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            
            while (nets.hasMoreElements()) {
                NetworkInterface netint = nets.nextElement();
                if (netint.isUp() && !netint.isLoopback()) {
                    NetworkInfoVO.NetworkInterfaceVO netInfo = new NetworkInfoVO.NetworkInterfaceVO();
                    
                    netInfo.setName(netint.getName());
                    netInfo.setDisplayName(netint.getDisplayName());
                    netInfo.setMacAddress(getMacAddress(netint));
                    netInfo.setEnabled(netint.isUp());
                    netInfo.setLoopback(netint.isLoopback());
                    netInfo.setPointToPoint(netint.isPointToPoint());
                    netInfo.setVirtual(netint.isVirtual());
                    
                    List<String> ipv4Addresses = new ArrayList<>();
                    List<String> ipv6Addresses = new ArrayList<>();
                    
                    Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if (inetAddress instanceof Inet4Address) {
                            ipv4Addresses.add(inetAddress.getHostAddress());
                        } else if (inetAddress instanceof Inet6Address) {
                            ipv6Addresses.add(inetAddress.getHostAddress());
                        }
                    }
                    
                    netInfo.setIpv4Addresses(ipv4Addresses);
                    netInfo.setIpv6Addresses(ipv6Addresses);
                    
                    interfaces.add(netInfo);
                }
            }
            
            info.setInterfaces(interfaces);
            
        } catch (Exception e) {
            log.error("获取网络信息失败", e);
        }
        
        return info;
    }

    private String getMacAddress(NetworkInterface netint) {
        try {
            byte[] mac = netint.getHardwareAddress();
            if (mac != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                return sb.toString();
            }
        } catch (Exception e) {
            log.error("获取MAC地址失败", e);
        }
        return null;
    }
} 