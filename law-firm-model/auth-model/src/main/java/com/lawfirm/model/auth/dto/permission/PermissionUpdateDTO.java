package com.lawfirm.model.auth.dto.permission;

import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 权限更新DTO
 */
@Data
public class PermissionUpdateDTO {
    
    /**
     * 权限名称
     */
    @Size(max = 50, message = "权限名称长度不能超过50个字符")
    private String name;
    
    /**
     * 权限标识
     */
    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    private String code;
    
    /**
     * 上级权限ID
     */
    private Long parentId;
    
    /**
     * 权限类型（1:菜单 2:按钮 3:接口）
     */
    private Integer type;
    
    /**
     * 路由路径
     */
    @Size(max = 200, message = "路由路径长度不能超过200个字符")
    private String path;
    
    /**
     * 组件路径
     */
    @Size(max = 200, message = "组件路径长度不能超过200个字符")
    private String component;
    
    /**
     * 重定向路径
     */
    @Size(max = 200, message = "重定向路径长度不能超过200个字符")
    private String redirect;
    
    /**
     * 菜单图标
     */
    @Size(max = 100, message = "菜单图标长度不能超过100个字符")
    private String icon;
    
    /**
     * 显示顺序
     */
    private Integer orderNum;
    
    /**
     * 是否隐藏（0显示 1隐藏）
     */
    private Boolean hidden;
    
    /**
     * 是否缓存（0不缓存 1缓存）
     */
    private Boolean keepAlive;
    
    /**
     * 是否外链（0否 1是）
     */
    private Boolean external;
    
    /**
     * 权限状态（0正常 1停用）
     */
    private Integer status;
    
    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
} 