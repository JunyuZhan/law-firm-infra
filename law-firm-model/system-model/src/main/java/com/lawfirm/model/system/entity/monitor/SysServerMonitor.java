package com.lawfirm.model.system.entity.monitor;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 服务器监控实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_server_monitor")
public class SysServerMonitor extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 服务器名称
     */
    private String serverName;

    /**
     * 服务器IP
     */
    private String serverIp;

    /**
     * CPU使用率(%)
     */
    private BigDecimal cpuUsage;

    /**
     * 内存使用率(%)
     */
    private BigDecimal memoryUsage;

    /**
     * 磁盘使用率(%)
     */
    private BigDecimal diskUsage;

    /**
     * 网络接收量(bytes)
     */
    private Long networkRx;

    /**
     * 网络发送量(bytes)
     */
    private Long networkTx;

    /**
     * 服务器负载
     */
    private String serverLoad;

    /**
     * 监控时间
     */
    private LocalDateTime monitorTime;
} 