package com.lawfirm.model.organization.service;

import com.lawfirm.model.organization.vo.OrganizationTreeVO;

import java.util.List;

/**
 * 组织树服务接口
 * 用于管理组织的层级结构
 */
public interface OrganizationTreeService {

    /**
     * 获取组织树
     *
     * @param rootId 根组织ID
     * @return 组织树
     */
    OrganizationTreeVO getTree(Long rootId);

    /**
     * 获取组织路径
     *
     * @param id 组织ID
     * @return 组织路径
     */
    String getPath(Long id);

    /**
     * 获取组织层级
     *
     * @param id 组织ID
     * @return 组织层级
     */
    int getLevel(Long id);

    /**
     * 获取子组织列表
     *
     * @param parentId 父组织ID
     * @return 子组织列表
     */
    List<OrganizationTreeVO> getChildren(Long parentId);

    /**
     * 获取父组织
     *
     * @param id 组织ID
     * @return 父组织
     */
    OrganizationTreeVO getParent(Long id);

    /**
     * 获取所有父组织
     *
     * @param id 组织ID
     * @return 父组织列表
     */
    List<OrganizationTreeVO> getAllParents(Long id);

    /**
     * 移动组织
     *
     * @param id 组织ID
     * @param targetParentId 目标父组织ID
     */
    void move(Long id, Long targetParentId);

    /**
     * 检查是否是子组织
     *
     * @param parentId 父组织ID
     * @param childId 子组织ID
     * @return 是否是子组织
     */
    boolean isChild(Long parentId, Long childId);

    /**
     * 检查是否是父组织
     *
     * @param childId 子组织ID
     * @param parentId 父组织ID
     * @return 是否是父组织
     */
    boolean isParent(Long childId, Long parentId);

    /**
     * 获取组织的所有子组织ID
     *
     * @param parentId 父组织ID
     * @return 子组织ID列表
     */
    List<Long> getAllChildIds(Long parentId);

    /**
     * 获取组织的所有父组织ID
     *
     * @param childId 子组织ID
     * @return 父组织ID列表
     */
    List<Long> getAllParentIds(Long childId);

    /**
     * 获取组织的根组织
     *
     * @param id 组织ID
     * @return 根组织
     */
    OrganizationTreeVO getRoot(Long id);

    /**
     * 获取组织的根组织ID
     *
     * @param id 组织ID
     * @return 根组织ID
     */
    Long getRootId(Long id);

    /**
     * 检查组织树是否有环
     *
     * @param id 组织ID
     * @return 是否有环
     */
    boolean hasCycle(Long id);

    /**
     * 获取组织树的深度
     *
     * @param rootId 根组织ID
     * @return 树的深度
     */
    int getDepth(Long rootId);

    /**
     * 获取组织的兄弟组织
     *
     * @param id 组织ID
     * @return 兄弟组织列表
     */
    List<OrganizationTreeVO> getSiblings(Long id);

    /**
     * 获取组织的叶子节点
     *
     * @param rootId 根组织ID
     * @return 叶子节点列表
     */
    List<OrganizationTreeVO> getLeaves(Long rootId);

    /**
     * 检查是否是叶子节点
     *
     * @param id 组织ID
     * @return 是否是叶子节点
     */
    boolean isLeaf(Long id);
} 