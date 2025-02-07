package com.lawfirm.model.system.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色关联DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRoleDTO extends BaseDTO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
} 