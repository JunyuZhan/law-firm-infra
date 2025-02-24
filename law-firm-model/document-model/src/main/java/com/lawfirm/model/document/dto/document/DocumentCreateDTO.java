package com.lawfirm.model.document.dto.document;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文档创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DocumentCreateDTO extends BaseDTO {

    /**
     * 文档标题
     */
    @NotBlank(message = "文档标题不能为空")
    @Size(max = 100, message = "文档标题长度不能超过100个字符")
    private String title;

    /**
     * 文档类型
     */
    @NotBlank(message = "文档类型不能为空")
    private String docType;

    /**
     * 文件名
     */
    @NotBlank(message = "文件名不能为空")
    @Size(max = 100, message = "文件名长度不能超过100个字符")
    private String fileName;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 存储类型（本地、OSS等）
     */
    private String storageType;

    /**
     * 文档状态（草稿、已发布等）
     */
    private String docStatus;

    /**
     * 关键词
     */
    @Size(max = 200, message = "关键词长度不能超过200个字符")
    private String keywords;

    /**
     * 文档描述
     */
    @Size(max = 500, message = "文档描述长度不能超过500个字符")
    private String description;

    /**
     * 是否加密
     */
    private Boolean isEncrypted;

    /**
     * 访问权限（public, private, restricted）
     */
    private String accessLevel;

    /**
     * 业务ID（案件ID、合同ID等）
     */
    private Long businessId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务编号
     */
    private String businessCode;
} 