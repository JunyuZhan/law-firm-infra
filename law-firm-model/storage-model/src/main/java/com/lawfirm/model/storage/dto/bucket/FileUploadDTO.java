package com.lawfirm.model.storage.dto.bucket;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 文件上传DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FileUploadDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    @NotBlank(message = "文件名不能为空")
    private String fileName;

    /**
     * 存储桶ID
     */
    @NotNull(message = "存储桶ID不能为空")
    private Long bucketId;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 文件标签（逗号分隔）
     */
    private String tags;

    /**
     * 文件元数据（JSON格式）
     */
    private String metadata;

    /**
     * 文件内容（Base64编码）
     */
    @NotBlank(message = "文件内容不能为空")
    private String content;
} 