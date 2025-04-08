package com.lawfirm.model.task.dto;

import lombok.Data;

/**
 * 工作任务与标签关联关系数据传输对象
 */
@Data
public class WorkTaskTagRelationDTO {
    
    /**
     * 关联ID
     */
    private Long id;
    
    /**
     * 任务ID
     */
    private Long taskId;
    
    /**
     * 标签ID
     */
    private Long tagId;
    
    /**
     * 标签名称
     */
    private String tagName;
    
    /**
     * 标签描述
     */
    private String tagDescription;
    
    /**
     * 标签颜色
     */
    private String tagColor;
}