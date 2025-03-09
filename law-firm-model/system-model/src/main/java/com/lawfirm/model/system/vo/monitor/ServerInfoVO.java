package com.lawfirm.model.system.vo.monitor;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 服务器信息视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ServerInfoVO extends BaseVO {

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
     * 操作系统名称
     */
    private String osName;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * 操作系统架构
     */
    private String osArch;

    /**
     * CPU核心数
     */
    private Integer cpuCores;

    /**
     * 总内存(GB)
     */
    private Double totalMemory;

    /**
     * 已用内存(GB)
     */
    private Double usedMemory;

    /**
     * 空闲内存(GB)
     */
    private Double freeMemory;

    /**
     * 内存使用率
     */
    private Double memoryUsage;

    /**
     * 总磁盘空间(GB)
     */
    private Double totalDisk;

    /**
     * 已用磁盘空间(GB)
     */
    private Double usedDisk;

    /**
     * 空闲磁盘空间(GB)
     */
    private Double freeDisk;

    /**
     * 磁盘使用率
     */
    private Double diskUsage;

    /**
     * 系统启动时间
     */
    private String startTime;

    /**
     * 系统运行时间
     */
    private String runTime;
} 