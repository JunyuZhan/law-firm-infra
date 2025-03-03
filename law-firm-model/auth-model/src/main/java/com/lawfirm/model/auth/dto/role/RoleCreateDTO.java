package com.lawfirm.model.auth.dto.role;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RoleCreateDTO extends BaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称长度不能超过50个字符")
    private String name;
    
    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    @Size(max = 100, message = "角色编码长度不能超过100个字符")
    private String code;
    
    /**
     * 角色类型（0-系统角色，1-自定义角色）
     */
    @NotNull(message = "角色类型不能为空")
    private Integer type;
    
    /**
     * 数据范围（0-全部数据，1-本部门及以下数据，2-本部门数据，3-个人数据，4-自定义数据）
     */
    @NotNull(message = "数据范围不能为空")
    private Integer dataScope;
    
    /**
     * 显示顺序
     */
    private Integer sort;
    
    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
    
    /**
     * 权限ID列表
     */
    private Long[] permissionIds;
} 