package com.lawfirm.model.task.query;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 工作任务附件查询对象
 */
@Data
public class WorkTaskAttachmentQuery {
    
    /**
     * 任务ID
     */
    private Long taskId;
    
    /**
     * 文件名称
     */
    private String fileName;
    
    /**
     * 文件类型
     */
    private String fileType;
    
    /**
     * 上传人ID
     */
    private Long uploaderId;
    
    /**
     * 上传时间范围-开始
     */
    private LocalDateTime startTime;
    
    /**
     * 上传时间范围-结束
     */
    private LocalDateTime endTime;
    
    /**
     * 租户ID
     */
    private Long tenantId;
} 