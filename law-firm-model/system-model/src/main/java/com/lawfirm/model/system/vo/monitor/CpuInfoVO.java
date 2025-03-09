package com.lawfirm.model.system.vo.monitor;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * CPU信息视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CpuInfoVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * CPU型号
     */
    private String model;

    /**
     * CPU厂商
     */
    private String vendor;

    /**
     * CPU架构
     */
    private String arch;

    /**
     * CPU频率(MHz)
     */
    private Double frequency;

    /**
     * 物理CPU数量
     */
    private Integer physicalCount;

    /**
     * 逻辑CPU数量
     */
    private Integer logicalCount;

    /**
     * CPU核心数
     */
    private Integer coreCount;

    /**
     * CPU使用率
     */
    private Double usage;

    /**
     * 系统CPU使用率
     */
    private Double systemUsage;

    /**
     * 用户CPU使用率
     */
    private Double userUsage;

    /**
     * 空闲CPU使用率
     */
    private Double idleUsage;

    /**
     * 等待IO的CPU使用率
     */
    private Double ioWaitUsage;

    /**
     * CPU负载
     */
    private Double load;

    /**
     * 每个CPU核心的使用率
     */
    private transient List<Double> coreUsages;
} 