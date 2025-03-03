package com.lawfirm.model.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 附件实体
 */
@Data
@TableName("knowledge_attachment")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Attachment extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    @TableField("article_id")
    private Long articleId;

    /**
     * 所属文章（延迟加载）
     */
    @TableField(exist = false)
    private Article article;

    /**
     * 附件名称
     */
    @TableField("name")
    private String name;

    /**
     * 文件路径
     */
    @TableField("path")
    private String path;

    /**
     * 文件大小（字节）
     */
    @TableField("size")
    private Long size;

    /**
     * 文件类型
     */
    @TableField("type")
    private String type;

    /**
     * 文件扩展名
     */
    @TableField("extension")
    private String extension;

    /**
     * 存储ID
     */
    @TableField("storage_id")
    private Long storageId;

    /**
     * 下载次数
     */
    @TableField("download_count")
    private Long downloadCount = 0L;

    /**
     * 排序号
     */
    @TableField("sort_number")
    private Integer sortNumber = 0;

    /**
     * 文件描述
     */
    @TableField("description")
    private String description;

    /**
     * 是否可下载
     */
    @TableField("downloadable")
    private Boolean downloadable = true;

    /**
     * 访问权限（0-公开，1-登录，2-角色）
     */
    @TableField("access_level")
    private Integer accessLevel = 0;

    /**
     * 访问角色（逗号分隔的角色ID或代码）
     */
    @TableField("access_roles")
    private String accessRoles;

    /**
     * MD5值
     */
    @TableField("md5")
    private String md5;

    /**
     * 上传者ID
     */
    @TableField("uploader_id")
    private Long uploaderId;

    /**
     * 上传者名称
     */
    @TableField("uploader_name")
    private String uploaderName;

    /**
     * 上传时间
     */
    @TableField("upload_time")
    private Long uploadTime;
} 