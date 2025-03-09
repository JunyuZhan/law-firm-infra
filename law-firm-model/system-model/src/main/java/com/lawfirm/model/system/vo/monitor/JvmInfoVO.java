package com.lawfirm.model.system.vo.monitor;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * JVM信息视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class JvmInfoVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * JVM名称
     */
    private String name;

    /**
     * JVM版本
     */
    private String version;

    /**
     * JVM供应商
     */
    private String vendor;

    /**
     * JVM启动时间
     */
    private String startTime;

    /**
     * JVM运行时间
     */
    private String runTime;

    /**
     * JVM最大内存(MB)
     */
    private Double maxMemory;

    /**
     * JVM已分配内存(MB)
     */
    private Double totalMemory;

    /**
     * JVM已用内存(MB)
     */
    private Double usedMemory;

    /**
     * JVM空闲内存(MB)
     */
    private Double freeMemory;

    /**
     * JVM内存使用率
     */
    private Double memoryUsage;

    /**
     * JVM启动参数
     */
    private String inputArguments;

    /**
     * JVM类加载数
     */
    private Integer loadedClassCount;

    /**
     * JVM类加载总数
     */
    private Long totalLoadedClassCount;

    /**
     * JVM类卸载数
     */
    private Long unloadedClassCount;

    /**
     * 当前线程数
     */
    private Integer threadCount;

    /**
     * 峰值线程数
     */
    private Integer peakThreadCount;

    /**
     * 守护线程数
     */
    private Integer daemonThreadCount;

    /**
     * 启动线程总数
     */
    private Long totalStartedThreadCount;
} 