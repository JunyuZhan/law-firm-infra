package com.lawfirm.common.data.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础VO类
 */
@Data
public class BaseVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
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
     * 备注
     */
    private String remark;
    
    /**
     * 删除标记（0：正常；1：删除）
     */
    private Integer delFlag;
} 