package com.lawfirm.model.system.dto.monitor;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 监控查询参数DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MonitorQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 时间间隔（秒）
     */
    private Integer interval;

    /**
     * 是否实时数据
     */
    private Boolean realtime;

    /**
     * 监控项类型（server/system/jvm/memory/cpu/disk/network）
     */
    private String type;

    /**
     * 监控项名称
     */
    private String name;

    /**
     * 最大返回条数
     */
    private Integer limit;
} 