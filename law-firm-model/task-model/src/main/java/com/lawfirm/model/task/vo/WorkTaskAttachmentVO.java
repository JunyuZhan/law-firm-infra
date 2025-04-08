package com.lawfirm.model.task.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 工作任务附件展示对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkTaskAttachmentVO extends BaseVO {
    
    private static final long serialVersionUID = 1L;
    
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
     * 上传人头像
     */
    private String uploaderAvatar;
    
    /**
     * 预览URL
     */
    private String previewUrl;
    
    /**
     * 下载URL
     */
    private String downloadUrl;
    
    /**
     * 下载次数
     */
    private Integer downloadCount;
    
    /**
     * 是否支持预览
     */
    private Boolean previewable;
    
    /**
     * 文件图标
     */
    private String fileIcon;
    
    /**
     * 文件后缀
     */
    private String fileSuffix;
} 