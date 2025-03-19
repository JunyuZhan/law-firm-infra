package com.lawfirm.model.document.dto.permission;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.document.enums.DocumentOperationEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档权限创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PermissionCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 文档ID
     */
    @NotNull(message = "文档ID不能为空")
    private Long documentId;

    /**
     * 权限主体类型（USER-用户、ROLE-角色、DEPT-部门）
     */
    @NotNull(message = "权限主体类型不能为空")
    private String subjectType;

    /**
     * 权限主体ID
     */
    @NotNull(message = "权限主体ID不能为空")
    private Long subjectId;

    /**
     * 权限类型（READ-读取、WRITE-写入、DELETE-删除等）
     */
    @NotNull(message = "权限类型不能为空")
    private DocumentOperationEnum permissionType;

    /**
     * 是否允许
     */
    private Boolean isAllowed = true;

    /**
     * 权限来源（DIRECT-直接授权、INHERIT-继承、SHARE-分享）
     */
    private String permissionSource = "DIRECT";

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 是否启用
     */
    private Boolean isEnabled = true;
} 