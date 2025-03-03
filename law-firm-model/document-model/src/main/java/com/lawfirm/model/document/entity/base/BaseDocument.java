package com.lawfirm.model.document.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文档基类
 * 所有文档实体的基类，包含文档通用属性
 */
@Getter
@Setter
@Accessors(chain = true)
public class BaseDocument extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文档标题
     */
    @TableField("title")
    private String title;

    /**
     * 文档类型
     */
    @TableField("doc_type")
    private String docType;

    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件大小（字节）
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 存储路径
     */
    @TableField("storage_path")
    private String storagePath;

    /**
     * 存储类型（本地、OSS等）
     */
    @TableField("storage_type")
    private String storageType;

    /**
     * 文档状态（草稿、已发布等）
     */
    @TableField("doc_status")
    private String docStatus;

    /**
     * 文档版本号
     */
    @TableField("document_version")
    private String documentVersion;

    /**
     * 关键词
     */
    @TableField("keywords")
    private String keywords;

    /**
     * 文档描述
     */
    @TableField("description")
    private String description;

    /**
     * 是否加密
     */
    @TableField("is_encrypted")
    private Boolean isEncrypted;

    /**
     * 访问权限（public, private, restricted）
     */
    @TableField("access_level")
    private String accessLevel;

    /**
     * 下载次数
     */
    @TableField("download_count")
    private Long downloadCount;

    /**
     * 查看次数
     */
    @TableField("view_count")
    private Long viewCount;
} 