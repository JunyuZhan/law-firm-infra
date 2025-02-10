package com.lawfirm.document.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 文档响应
 */
@Data
public class DocumentResponse {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 文档编号
     */
    private String docNo;
    
    /**
     * 文档名称
     */
    private String docName;
    
    /**
     * 文档类型
     */
    private Integer docType;
    
    /**
     * 文档类型名称
     */
    private String docTypeName;
    
    /**
     * 文件类型
     */
    private String fileType;
    
    /**
     * 文件大小（字节）
     */
    private Long fileSize;
    
    /**
     * 存储路径
     */
    private String storagePath;
    
    /**
     * 当前版本号
     */
    private Integer version;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 状态名称
     */
    private String statusName;
    
    /**
     * 关键词
     */
    private String keywords;
    
    /**
     * 文档描述
     */
    private String description;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建者
     */
    private String createBy;
    
    /**
     * 更新者
     */
    private String updateBy;
} 