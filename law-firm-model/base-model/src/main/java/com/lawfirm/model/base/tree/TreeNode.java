package com.lawfirm.model.base.tree;

import java.util.List;

public interface TreeNode<T> {
    
    Long getId();  // 获取节点ID
    
    Long getParentId();  // 获取父节点ID
    
    String getName();  // 获取节点名称
    
    Integer getSort();  // 获取排序号
    
    List<T> getChildren();  // 获取子节点列表
    
    void setChildren(List<T> children);  // 设置子节点列表
    
    default boolean isRoot() {  // 判断是否为根节点
        return getParentId() == null || getParentId() == 0L;
    }
    
    default boolean isLeaf() {  // 判断是否为叶子节点
        return getChildren() == null || getChildren().isEmpty();
    }
} 