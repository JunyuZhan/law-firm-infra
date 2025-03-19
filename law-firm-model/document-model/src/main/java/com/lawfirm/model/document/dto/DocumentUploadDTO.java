package com.lawfirm.model.document.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 文档上传数据传输对象
 */
@Data
@Builder
@Accessors(chain = true)
public class DocumentUploadDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文档类型
     */
    private String docType;

    /**
     * 文档状态
     */
    private String docStatus;

    /**
     * 关键词
     */
    private String keywords;

    /**
     * 是否加密
     */
    private Boolean isEncrypted;

    /**
     * 访问级别
     */
    private String accessLevel;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务ID
     */
    private Long businessId;

    /**
     * 上传人ID
     */
    private Long uploaderId;

    /**
     * 文件内容（Base64编码）
     */
    private String content;

    /**
     * 文件描述
     */
    private String description;
} 