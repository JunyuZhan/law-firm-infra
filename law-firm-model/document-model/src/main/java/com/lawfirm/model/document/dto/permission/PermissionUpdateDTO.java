package com.lawfirm.model.document.dto.permission;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.document.enums.DocumentOperationEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档权限更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PermissionUpdateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @NotNull(message = "权限ID不能为空")
    private Long id;

    /**
     * 文档ID
     */
    private Long documentId;

    /**
     * 权限主体类型（USER-用户、ROLE-角色、DEPT-部门）
     */
    private String subjectType;

    /**
     * 权限主体ID
     */
    private Long subjectId;

    /**
     * 权限类型（READ-读取、WRITE-写入、DELETE-删除等）
     */
    private DocumentOperationEnum permissionType;

    /**
     * 是否允许
     */
    private Boolean isAllowed;

    /**
     * 权限来源（DIRECT-直接授权、INHERIT-继承、SHARE-分享）
     */
    private String permissionSource;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 是否启用
     */
    private Boolean isEnabled;
} 