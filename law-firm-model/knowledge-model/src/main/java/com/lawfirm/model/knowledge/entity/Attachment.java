package com.lawfirm.model.knowledge.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 附件实体
 */
@Data
@Entity
@Table(name = "knowledge_attachment")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Attachment extends ModelBaseEntity {

    /**
     * 文章ID
     */
    @Column(name = "article_id", nullable = false)
    private Long articleId;

    /**
     * 所属文章（延迟加载）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private Article article;

    /**
     * 附件名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 文件路径
     */
    @Column(name = "path", nullable = false)
    private String path;

    /**
     * 文件大小（字节）
     */
    @Column(name = "size")
    private Long size;

    /**
     * 文件类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 文件扩展名
     */
    @Column(name = "extension")
    private String extension;

    /**
     * 存储ID（关联storage-model）
     */
    @Column(name = "storage_id")
    private Long storageId;

    /**
     * 下载次数
     */
    @Column(name = "download_count")
    private Long downloadCount = 0L;

    /**
     * 排序号
     */
    @Column(name = "sort_number")
    private Integer sortNumber = 0;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 是否可下载
     */
    @Column(name = "downloadable", nullable = false)
    private Boolean downloadable = true;

    /**
     * 访问权限（0：公开，1：登录可见，2：指定角色可见）
     */
    @Column(name = "access_level", nullable = false)
    private Integer accessLevel = 0;

    /**
     * 可访问角色列表（逗号分隔）
     */
    @Column(name = "access_roles")
    private String accessRoles;

    /**
     * MD5校验值
     */
    @Column(name = "md5")
    private String md5;

    /**
     * 上传人ID
     */
    @Column(name = "uploader_id")
    private Long uploaderId;

    /**
     * 上传人名称
     */
    @Column(name = "uploader_name")
    private String uploaderName;

    /**
     * 上传时间
     */
    @Column(name = "upload_time")
    private Long uploadTime;
} 