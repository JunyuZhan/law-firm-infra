package com.lawfirm.model.base.storage.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文件处理任务
 */
@Data
@Accessors(chain = true)
public class FileTask {
    
    /**
     * 任务ID
     */
    private String id;
    
    /**
     * 文件ID
     */
    private String fileId;
    
    /**
     * 任务类型（UPLOAD/DOWNLOAD/PROCESS/PREVIEW）
     */
    private String taskType;
    
    /**
     * 任务状态（PENDING/PROCESSING/SUCCESS/FAILED）
     */
    private String status;
    
    /**
     * 进度（0-100）
     */
    private Integer progress;
    
    /**
     * 错误信息
     */
    private String error;
    
    /**
     * 处理结果
     */
    private String result;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 任务参数（JSON格式）
     */
    private String params;
} 