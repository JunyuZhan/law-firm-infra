package com.lawfirm.model.task.dto;

import lombok.Data;

import java.util.List;

/**
 * 工作任务分类数据传输对象
 */
@Data
public class WorkTaskCategoryDTO {
    
    /**
     * 分类ID
     */
    private Long id;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 分类编码
     */
    private String code;
    
    /**
     * 分类描述
     */
    private String description;
    
    /**
     * 分类类型：0-系统分类，1-自定义分类
     */
    private Integer type;
    
    /**
     * 父分类ID
     */
    private Long parentId;
    
    /**
     * 父分类名称
     */
    private String parentName;
    
    /**
     * 排序号
     */
    private Integer sort;
    
    /**
     * 是否启用：0-禁用，1-启用
     */
    private Integer enabled;
    
    /**
     * 子分类列表
     */
    private List<WorkTaskCategoryDTO> children;
} 