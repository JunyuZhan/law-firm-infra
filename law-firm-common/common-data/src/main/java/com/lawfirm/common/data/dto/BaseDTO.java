package com.lawfirm.common.data.dto;

import com.lawfirm.common.core.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.experimental.Accessors;

/**
 * 基础数据传输对象
 */
@Getter
@Setter
@Accessors(chain = true)
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;
}