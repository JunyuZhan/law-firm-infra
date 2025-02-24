package com.lawfirm.model.base.support.tree;

import java.util.List;

/**
 * 树节点接口
 */
public interface TreeNode<T> {

    /**
     * 获取节点ID
     */
    Long getId();

    /**
     * 获取父节点ID
     */
    Long getParentId();

    /**
     * 获取节点层级
     */
    Integer getLevel();

    /**
     * 获取节点路径
     */
    String getPath();

    /**
     * 是否叶子节点
     */
    Boolean getLeaf();

    /**
     * 获取子节点列表
     */
    List<T> getChildren();

    /**
     * 设置子节点列表
     */
    void setChildren(List<T> children);
} 