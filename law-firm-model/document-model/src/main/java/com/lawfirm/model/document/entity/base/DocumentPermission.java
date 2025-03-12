package com.lawfirm.model.document.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文档权限实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("doc_permission")
public class DocumentPermission extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文档ID
     */
    @TableField("document_id")
    private Long documentId;

    /**
     * 权限主体类型（USER-用户、ROLE-角色、DEPT-部门）
     */
    @TableField("subject_type")
    private String subjectType;

    /**
     * 权限主体ID
     */
    @TableField("subject_id")
    private Long subjectId;

    /**
     * 权限类型（READ-读取、WRITE-写入、DELETE-删除等）
     */
    @TableField("permission_type")
    private String permissionType;

    /**
     * 是否允许
     */
    @TableField("is_allowed")
    private Boolean isAllowed;

    /**
     * 权限来源（DIRECT-直接授权、INHERIT-继承、SHARE-分享）
     */
    @TableField("permission_source")
    private String permissionSource;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    private java.time.LocalDateTime expireTime;

    /**
     * 是否启用
     */
    @TableField("is_enabled")
    private Boolean isEnabled;
} 