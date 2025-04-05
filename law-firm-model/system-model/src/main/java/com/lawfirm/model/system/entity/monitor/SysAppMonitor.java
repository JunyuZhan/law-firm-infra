package com.lawfirm.model.system.entity.monitor;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 应用监控实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_app_monitor")
public class SysAppMonitor extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 实例ID
     */
    private String instanceId;

    /**
     * JVM内存使用(bytes)
     */
    private Long jvmMemoryUsed;

    /**
     * JVM最大内存(bytes)
     */
    private Long jvmMemoryMax;

    /**
     * JVM线程数
     */
    private Integer jvmThreads;

    /**
     * 堆内存使用(bytes)
     */
    private Long heapUsed;

    /**
     * 非堆内存使用(bytes)
     */
    private Long nonHeapUsed;

    /**
     * 活动线程数
     */
    private Integer threadActiveCount;

    /**
     * 峰值线程数
     */
    private Integer threadPeakCount;

    /**
     * GC次数
     */
    private Integer gcCount;

    /**
     * GC耗时(ms)
     */
    private Long gcTime;

    /**
     * HTTP会话数
     */
    private Integer httpSessions;

    /**
     * 监控时间
     */
    private LocalDateTime monitorTime;
} 