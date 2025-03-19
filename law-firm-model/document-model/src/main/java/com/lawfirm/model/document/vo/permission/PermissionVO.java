package com.lawfirm.model.document.vo.permission;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.document.enums.DocumentOperationEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档权限视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PermissionVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 文档ID
     */
    private Long documentId;

    /**
     * 文档标题
     */
    private String documentTitle;

    /**
     * 权限主体类型（USER-用户、ROLE-角色、DEPT-部门）
     */
    private String subjectType;

    /**
     * 权限主体类型名称
     */
    private String subjectTypeName;

    /**
     * 权限主体ID
     */
    private Long subjectId;

    /**
     * 权限主体名称
     */
    private String subjectName;

    /**
     * 权限类型（READ-读取、WRITE-写入、DELETE-删除等）
     */
    private DocumentOperationEnum permissionType;

    /**
     * 权限类型名称
     */
    private String permissionTypeName;

    /**
     * 是否允许
     */
    private Boolean isAllowed;

    /**
     * 权限来源（DIRECT-直接授权、INHERIT-继承、SHARE-分享）
     */
    private String permissionSource;

    /**
     * 权限来源名称
     */
    private String permissionSourceName;

    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 创建人名称
     */
    private String createByName;

    /**
     * 更新人名称
     */
    private String updateByName;
} 