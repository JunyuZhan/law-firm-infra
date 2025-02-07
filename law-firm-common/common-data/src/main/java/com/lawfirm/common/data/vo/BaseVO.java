package com.lawfirm.common.data.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础VO类
 */
@Data
public class BaseVO implements Serializable {
    
    /**
     * ID
     */
    private Long id;
    
    private Long createTime;
    
    private Long updateTime;
    
    private String createBy;
    
    private String updateBy;
    
    private Integer deleted;
} 