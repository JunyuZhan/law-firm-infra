package com.lawfirm.model.organization.service;

import java.util.List;

/**
 * 组织树服务接口
 * 用于管理组织的层级结构
 */
public interface OrganizationTreeService {

    /**
     * 获取组织树
     *
     * @param rootOrgId 根组织ID，为null时获取全部组织树
     * @return 组织树数据
     */
    Object getOrganizationTree(Long rootOrgId);
    
    /**
     * 获取所有子组织ID
     *
     * @param organizationId 组织ID
     * @param recursive 是否递归获取所有层级的子组织
     * @return 子组织ID列表
     */
    List<Long> getSubOrganizationIds(Long organizationId, boolean recursive);
    
    /**
     * 获取所有父组织ID
     *
     * @param organizationId 组织ID
     * @return 父组织ID列表，从直接父级到顶级
     */
    List<Long> getParentOrganizationIds(Long organizationId);
    
    /**
     * 检查两个组织是否有父子关系
     *
     * @param parentId 可能的父组织ID
     * @param childId 可能的子组织ID
     * @param recursive 是否递归检查（检查所有层级）
     * @return 是否存在父子关系
     */
    boolean hasParentChildRelation(Long parentId, Long childId, boolean recursive);
    
    /**
     * 移动组织节点
     *
     * @param organizationId 要移动的组织ID
     * @param newParentId 新的父组织ID
     * @return 是否移动成功
     */
    boolean moveOrganization(Long organizationId, Long newParentId);
    
    /**
     * 获取组织的完整路径名称
     * 例如：总公司/上海分公司/法务部
     *
     * @param organizationId 组织ID
     * @return 完整路径名称
     */
    String getOrganizationFullPath(Long organizationId);
} 