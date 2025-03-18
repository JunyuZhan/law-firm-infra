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