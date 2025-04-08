package com.lawfirm.model.task.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 工作任务附件数据传输对象
 */
@Data
public class WorkTaskAttachmentDTO {
    
    /**
     * 附件ID
     */
    private Long id;
    
    /**
     * 任务ID
     */
    private Long taskId;
    
    /**
     * 文件名称
     */
    private String fileName;
    
    /**
     * 文件路径
     */
    private String filePath;
    
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    
    /**
     * 文件类型
     */
    private String fileType;
    
    /**
     * 文件MD5
     */
    private String fileMd5;
    
    /**
     * 上传人ID
     */
    private Long uploaderId;
    
    /**
     * 上传人姓名
     */
    private String uploaderName;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
} 