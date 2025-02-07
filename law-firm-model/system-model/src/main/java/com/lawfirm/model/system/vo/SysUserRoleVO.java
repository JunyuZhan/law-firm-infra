package com.lawfirm.model.system.vo;

import com.lawfirm.common.data.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色关联VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRoleVO extends BaseVO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
} 