package com.lawfirm.model.document.dto.document;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DocumentQueryDTO extends BaseDTO {

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档类型
     */
    private String docType;

    /**
     * 文件名
     */
    private String fileName;

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
    private String keywords;

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

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 创建时间范围（开始）
     */
    private LocalDateTime createTimeStart;

    /**
     * 创建时间范围（结束）
     */
    private LocalDateTime createTimeEnd;

    /**
     * 更新人ID
     */
    private Long updateBy;

    /**
     * 更新时间范围（开始）
     */
    private LocalDateTime updateTimeStart;

    /**
     * 更新时间范围（结束）
     */
    private LocalDateTime updateTimeEnd;
} 