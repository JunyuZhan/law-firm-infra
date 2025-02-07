package com.lawfirm.model.system.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * 角色DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    private Integer dataScope;

    /**
     * 是否为系统默认角色
     */
    private Boolean isDefault;

    /**
     * 菜单ID列表
     */
    private List<Long> menuIds;

    /**
     * 部门ID列表
     */
    private List<Long> deptIds;
} 