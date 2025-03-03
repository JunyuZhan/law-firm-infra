package com.lawfirm.model.document.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DocumentVO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档类型
     */
    private String docType;

    /**
     * 文档类型名称
     */
    private String docTypeName;

    /**
     * 文件名
     */
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
     * 存储路径
     */
    private String storagePath;

    /**
     * 存储类型（本地、OSS等）
     */
    private String storageType;

    /**
     * 存储类型名称
     */
    private String storageTypeName;

    /**
     * 文档状态（草稿、已发布等）
     */
    private String docStatus;

    /**
     * 文档状态名称
     */
    private String docStatusName;

    /**
     * 文档版本号
     */
    private String documentVersion;

    /**
     * 关键词
     */
    private String keywords;

    /**
     * 文档描述
     */
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
     * 访问权限名称
     */
    private String accessLevelName;

    /**
     * 下载次数
     */
    private Long downloadCount;

    /**
     * 查看次数
     */
    private Long viewCount;

    /**
     * 业务ID（案件ID、合同ID等）
     */
    private Long businessId;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 业务类型名称
     */
    private String businessTypeName;

    /**
     * 业务编号
     */
    private String businessCode;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 创建人名称
     */
    private String createByName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新人ID
     */
    private Long updateBy;

    /**
     * 更新人名称
     */
    private String updateByName;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
} 