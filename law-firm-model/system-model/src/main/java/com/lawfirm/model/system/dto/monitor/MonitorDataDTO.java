package com.lawfirm.model.system.dto.monitor;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * 监控数据DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MonitorDataDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 监控项类型
     */
    private String type;

    /**
     * 监控项名称
     */
    private String name;

    /**
     * 监控项标题
     */
    private String title;

    /**
     * 监控项单位
     */
    private String unit;

    /**
     * 监控项描述
     */
    private String description;

    /**
     * 时间点列表
     */
    private transient List<String> times;

    /**
     * 数据值列表
     */
    private transient List<Object> values;

    /**
     * 最大值
     */
    private transient Object max;

    /**
     * 最小值
     */
    private transient Object min;

    /**
     * 平均值
     */
    private transient Object avg;

    /**
     * 当前值
     */
    private transient Object current;

    /**
     * 阈值
     */
    private transient Object threshold;

    /**
     * 是否超过阈值
     */
    private Boolean overThreshold;

    /**
     * 附加数据
     */
    private transient Map<String, Object> extra;
} 