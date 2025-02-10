package com.lawfirm.common.data.vo;

import com.lawfirm.common.core.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 基础VO类
 */
@Getter
@Setter
@Accessors(chain = true)
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
    
    private String remark;
    
    private StatusEnum status;
} 