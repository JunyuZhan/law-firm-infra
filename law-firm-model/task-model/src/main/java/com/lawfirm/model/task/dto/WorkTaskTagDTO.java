package com.lawfirm.model.task.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 工作任务标签数据传输对象
 */
@Data
public class WorkTaskTagDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 标签ID
     */
    private Long id;
    
    /**
     * 标签名称
     */
    private String name;
    
    /**
     * 标签描述
     */
    private String description;
    
    /**
     * 标签颜色
     */
    private String color;
} 