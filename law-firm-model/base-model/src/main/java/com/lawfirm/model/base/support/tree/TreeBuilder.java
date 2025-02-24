package com.lawfirm.model.base.support.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 树构建器
 */
public class TreeBuilder {

    /**
     * 构建树形结构
     *
     * @param nodes 节点列表
     * @return 树形结构
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return new ArrayList<>();
        }

        // 按父ID分组
        Map<Long, List<T>> parentMap = nodes.stream()
            .collect(Collectors.groupingBy(TreeNode::getParentId));

        // 找出根节点
        List<T> roots = parentMap.get(0L);
        if (roots == null || roots.isEmpty()) {
            return new ArrayList<>();
        }

        // 递归设置子节点
        roots.forEach(root -> setChildren(root, parentMap));

        return roots;
    }

    /**
     * 递归设置子节点
     */
    private static <T extends TreeNode<T>> void setChildren(T node, Map<Long, List<T>> parentMap) {
        List<T> children = parentMap.get(node.getId());
        if (children != null && !children.isEmpty()) {
            node.setChildren(children);
            children.forEach(child -> setChildren(child, parentMap));
        } else {
            node.setChildren(new ArrayList<>());
        }
    }
} 