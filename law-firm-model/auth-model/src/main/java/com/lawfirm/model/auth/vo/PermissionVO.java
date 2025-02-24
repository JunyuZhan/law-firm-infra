package com.lawfirm.model.auth.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 权限视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PermissionVO extends BaseVO {
    
    /**
     * 权限名称
     */
    private String name;
    
    /**
     * 权限编码
     */
    private String code;
    
    /**
     * 权限类型（0-菜单，1-按钮，2-API）
     */
    private Integer type;
    
    /**
     * 菜单图标
     */
    private String icon;
    
    /**
     * 路由路径
     */
    private String path;
    
    /**
     * 组件路径
     */
    private String component;
    
    /**
     * 权限标识
     */
    private String permission;
    
    /**
     * 父级ID
     */
    private Long parentId;
    
    /**
     * 父级名称
     */
    private String parentName;
    
    /**
     * 显示顺序
     */
    private Integer sort;
    
    /**
     * 状态（0-正常，1-禁用）
     */
    private Integer status;
    
    /**
     * 是否外链（0-否，1-是）
     */
    private Integer external;
    
    /**
     * 是否缓存（0-否，1-是）
     */
    private Integer cache;
    
    /**
     * 是否显示（0-否，1-是）
     */
    private Integer visible;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 子权限列表
     */
    private List<PermissionVO> children;
    
    /**
     * 角色数量
     */
    private Long roleCount;
} 