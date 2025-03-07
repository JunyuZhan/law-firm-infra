package com.lawfirm.model.auth.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PermissionVO extends BaseVO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 权限ID
     */
    private Long id;
    
    /**
     * 权限名称
     */
    private String name;
    
    /**
     * 权限标识
     */
    private String code;
    
    /**
     * 上级权限ID
     */
    private Long parentId;
    
    /**
     * 上级权限名称
     */
    private String parentName;
    
    /**
     * 权限类型（1:菜单 2:按钮 3:接口）
     */
    private Integer type;
    
    /**
     * 路由路径
     */
    private String path;
    
    /**
     * 组件路径
     */
    private String component;
    
    /**
     * 重定向路径
     */
    private String redirect;
    
    /**
     * 菜单图标
     */
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
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 子权限列表
     */
    private transient List<PermissionVO> children;
    
    /**
     * 备注
     */
    private String remark;
} 