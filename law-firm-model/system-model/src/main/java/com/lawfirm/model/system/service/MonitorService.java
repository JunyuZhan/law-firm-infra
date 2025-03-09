package com.lawfirm.model.system.service;

import com.lawfirm.model.system.dto.monitor.MonitorAlertDTO;
import com.lawfirm.model.system.dto.monitor.MonitorDataDTO;
import com.lawfirm.model.system.dto.monitor.MonitorQueryDTO;
import com.lawfirm.model.system.vo.monitor.*;

import java.util.List;
import java.util.Map;

/**
 * 系统监控服务接口
 */
public interface MonitorService {

    /**
     * 获取服务器信息
     *
     * @return 服务器信息
     */
    ServerInfoVO getServerInfo();

    /**
     * 获取系统信息
     *
     * @return 系统信息
     */
    SystemInfoVO getSystemInfo();

    /**
     * 获取JVM信息
     *
     * @return JVM信息
     */
    JvmInfoVO getJvmInfo();

    /**
     * 获取内存信息
     *
     * @return 内存信息
     */
    MemoryInfoVO getMemoryInfo();

    /**
     * 获取CPU信息
     *
     * @return CPU信息
     */
    CpuInfoVO getCpuInfo();

    /**
     * 获取磁盘信息
     *
     * @return 磁盘信息
     */
    DiskInfoVO getDiskInfo();

    /**
     * 获取网络信息
     *
     * @return 网络信息
     */
    NetworkInfoVO getNetworkInfo();
    
    /**
     * 获取监控数据
     *
     * @param queryDTO 查询参数
     * @return 监控数据列表
     */
    List<MonitorDataDTO> getMonitorData(MonitorQueryDTO queryDTO);
    
    /**
     * 获取监控告警列表
     *
     * @param queryDTO 查询参数
     * @return 告警列表
     */
    List<MonitorAlertDTO> getAlerts(MonitorQueryDTO queryDTO);
    
    /**
     * 处理告警
     *
     * @param alertId 告警ID
     * @param handler 处理人
     * @param result  处理结果
     * @return 是否成功
     */
    boolean handleAlert(String alertId, String handler, String result);
    
    /**
     * 关闭告警
     *
     * @param alertId 告警ID
     * @return 是否成功
     */
    boolean closeAlert(String alertId);
    
    /**
     * 获取系统健康状态
     *
     * @return 健康状态（key: 监控项, value: 状态，UP/DOWN/UNKNOWN）
     */
    Map<String, String> getHealthStatus();
    
    /**
     * 获取系统运行时间
     *
     * @return 运行时间（格式：X天X小时X分X秒）
     */
    String getUptime();
    
    /**
     * 获取在线用户数
     *
     * @return 在线用户数
     */
    int getOnlineUserCount();
    
    /**
     * 获取系统负载
     *
     * @return 系统负载（1分钟、5分钟、15分钟）
     */
    double[] getSystemLoad();
} 