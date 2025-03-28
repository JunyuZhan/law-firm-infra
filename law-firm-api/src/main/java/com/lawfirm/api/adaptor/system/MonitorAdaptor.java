package com.lawfirm.api.adaptor.system;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.system.service.MonitorService;
import com.lawfirm.model.system.vo.monitor.ServerInfoVO;
import com.lawfirm.model.system.vo.monitor.SystemInfoVO;
import com.lawfirm.model.system.vo.monitor.JvmInfoVO;
import com.lawfirm.model.system.vo.monitor.MemoryInfoVO;
import com.lawfirm.model.system.vo.monitor.CpuInfoVO;
import com.lawfirm.model.system.vo.monitor.DiskInfoVO;
import com.lawfirm.model.system.vo.monitor.NetworkInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 系统监控适配器
 * 负责系统监控相关的数据转换和服务调用
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MonitorAdaptor extends BaseAdaptor {

    private final MonitorService monitorService;

    /**
     * 获取服务器信息
     */
    public ServerInfoVO getServerInfo() {
        log.info("获取服务器信息");
        return monitorService.getServerInfo();
    }

    /**
     * 获取系统信息
     */
    public SystemInfoVO getSystemInfo() {
        log.info("获取系统信息");
        return monitorService.getSystemInfo();
    }

    /**
     * 获取JVM信息
     */
    public JvmInfoVO getJvmInfo() {
        log.info("获取JVM信息");
        return monitorService.getJvmInfo();
    }

    /**
     * 获取内存信息
     */
    public MemoryInfoVO getMemoryInfo() {
        log.info("获取内存信息");
        return monitorService.getMemoryInfo();
    }

    /**
     * 获取CPU信息
     */
    public CpuInfoVO getCpuInfo() {
        log.info("获取CPU信息");
        return monitorService.getCpuInfo();
    }

    /**
     * 获取磁盘信息
     */
    public DiskInfoVO getDiskInfo() {
        log.info("获取磁盘信息");
        return monitorService.getDiskInfo();
    }

    /**
     * 获取网络信息
     */
    public NetworkInfoVO getNetworkInfo() {
        log.info("获取网络信息");
        return monitorService.getNetworkInfo();
    }
} 