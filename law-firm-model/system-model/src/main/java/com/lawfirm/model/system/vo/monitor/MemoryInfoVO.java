package com.lawfirm.model.system.vo.monitor;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 内存信息视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MemoryInfoVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 总物理内存(GB)
     */
    private Double totalPhysicalMemory;

    /**
     * 已用物理内存(GB)
     */
    private Double usedPhysicalMemory;

    /**
     * 空闲物理内存(GB)
     */
    private Double freePhysicalMemory;

    /**
     * 物理内存使用率
     */
    private Double physicalMemoryUsage;

    /**
     * 总交换空间(GB)
     */
    private Double totalSwapSpace;

    /**
     * 已用交换空间(GB)
     */
    private Double usedSwapSpace;

    /**
     * 空闲交换空间(GB)
     */
    private Double freeSwapSpace;

    /**
     * 交换空间使用率
     */
    private Double swapSpaceUsage;

    /**
     * 总JVM内存(MB)
     */
    private Double totalJvmMemory;

    /**
     * 已用JVM内存(MB)
     */
    private Double usedJvmMemory;

    /**
     * 空闲JVM内存(MB)
     */
    private Double freeJvmMemory;

    /**
     * JVM内存使用率
     */
    private Double jvmMemoryUsage;

    /**
     * JVM最大内存(MB)
     */
    private Double maxJvmMemory;

    /**
     * 堆内存(MB)
     */
    private Double heapMemory;

    /**
     * 非堆内存(MB)
     */
    private Double nonHeapMemory;
} 