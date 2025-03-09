package com.lawfirm.model.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 路由元数据视图对象
 * 用于前端路由meta配置，适配vue-vben-admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 图标
     */
    private String icon;
    
    /**
     * 是否隐藏菜单
     */
    private Boolean hideMenu;
    
    /**
     * 是否缓存
     */
    private Boolean ignoreKeepAlive;
    
    /**
     * 是否固定在标签栏
     */
    private Boolean affix;
    
    /**
     * 当前激活的菜单
     */
    private String currentActiveMenu;
    
    /**
     * 菜单排序，值越高排序越后
     */
    private Integer orderNo;
    
    /**
     * 忽略路由。用于在ROUTE_MAPPING以及BACK权限模式下，生成对应的菜单而忽略路由
     */
    private Boolean ignoreRoute;
    
    /**
     * 是否在子级菜单的完整path中忽略本级path
     */
    private Boolean hidePathForChildren;
    
    /**
     * 菜单是否可见（隐藏菜单显示在折叠菜单里）
     */
    private Boolean hideTab;
    
    /**
     * 是否支持外链
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
     * 是否有动态父级菜单
     */
    private Boolean dynamicLevel;
    
    /**
     * 权限标识
     */
    private transient List<String> permissions;
    
    /**
     * 指定该路由的实际角色
     */
    private transient List<String> roles;
    
    /**
     * 内嵌iframe的地址
     */
    private Boolean hideBreadcrumb;
    
    /**
     * 菜单类型（目录、菜单、权限）
     */
    private Integer type;

    private boolean noCache;
} 