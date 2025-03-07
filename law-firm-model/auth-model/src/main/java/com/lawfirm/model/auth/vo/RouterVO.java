package com.lawfirm.model.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 路由视图对象
 * 用于前端动态路由生成，适配vue-vben-admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouterVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 路由路径
     */
    private String path;
    
    /**
     * 路由名称
     */
    private String name;
    
    /**
     * 组件
     */
    private String component;
    
    /**
     * 重定向地址
     */
    private String redirect;
    
    /**
     * 路由元信息
     */
    private MetaVO meta;
    
    /**
     * 子路由
     */
    private transient List<RouterVO> children;
    
    /**
     * 是否有参数
     */
    private Boolean paramPath;
    
    /**
     * 是否为layout组件
     */
    private Boolean layout;
    
    /**
     * 组件名称
     */
    private String componentName;
} 