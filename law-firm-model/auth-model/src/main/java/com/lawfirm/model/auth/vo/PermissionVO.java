package com.lawfirm.model.auth.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限视图对象
 * 适配vue-vben-admin前端框架
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
    
    /**
     * 以下是vue-vben-admin所需的额外字段
     */
    
    /**
     * 是否固定标签
     */
    private Boolean affix;
    
    /**
     * 当前激活的菜单。用于配置详情页时左侧激活的菜单路径
     */
    private String currentActiveMenu;
    
    /**
     * 是否在ROUTE_MAPPING以及BACK_END_MENU中忽略该项
     */
    private Boolean ignoreRoute;
    
    /**
     * 是否在子级菜单的完整path中忽略本级path
     */
    private Boolean hidePathForChildren;
    
    /**
     * 是否隐藏标签页
     */
    private Boolean hideTab;
    
    /**
     * 是否是外链
     */
    private Boolean isLink;
    
    /**
     * 内嵌iframe的地址
     */
    private String frameSrc;
    
    /**
     * 该路由切换的动画名
     */
    private String transitionName;
    
    /**
     * 动态路由可打开Tab页数
     */
    private Integer dynamicLevel;
    
    /**
     * 权限标识列表
     */
    private List<String> permissions;
    
    /**
     * 角色列表
     */
    private List<String> roles;
    
    /**
     * 是否隐藏面包屑
     */
    private Boolean hideBreadcrumb;
    
    /**
     * 是否不缓存
     */
    private Boolean noCache;
} 