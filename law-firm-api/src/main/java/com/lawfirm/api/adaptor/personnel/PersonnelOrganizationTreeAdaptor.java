package com.lawfirm.api.adaptor.personnel;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.organization.service.OrganizationTreeService;
import com.lawfirm.model.organization.vo.OrganizationTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 人事组织树管理适配器
 */
@Component
public class PersonnelOrganizationTreeAdaptor extends BaseAdaptor {

    @Autowired
    private OrganizationTreeService organizationTreeService;

    /**
     * 获取组织树
     */
    public OrganizationTreeVO getTree(Long rootId) {
        return organizationTreeService.getTree(rootId);
    }

    /**
     * 获取组织路径
     */
    public String getPath(Long id) {
        return organizationTreeService.getPath(id);
    }

    /**
     * 获取组织层级
     */
    public int getLevel(Long id) {
        return organizationTreeService.getLevel(id);
    }

    /**
     * 获取子组织列表
     */
    public List<OrganizationTreeVO> getChildren(Long parentId) {
        return organizationTreeService.getChildren(parentId);
    }

    /**
     * 获取父组织
     */
    public OrganizationTreeVO getParent(Long id) {
        return organizationTreeService.getParent(id);
    }

    /**
     * 获取所有父组织
     */
    public List<OrganizationTreeVO> getAllParents(Long id) {
        return organizationTreeService.getAllParents(id);
    }

    /**
     * 移动组织
     */
    public void move(Long id, Long targetParentId) {
        organizationTreeService.move(id, targetParentId);
    }

    /**
     * 检查是否是子组织
     */
    public boolean isChild(Long parentId, Long childId) {
        return organizationTreeService.isChild(parentId, childId);
    }

    /**
     * 检查是否是父组织
     */
    public boolean isParent(Long childId, Long parentId) {
        return organizationTreeService.isParent(childId, parentId);
    }

    /**
     * 获取组织的所有子组织ID
     */
    public List<Long> getAllChildIds(Long parentId) {
        return organizationTreeService.getAllChildIds(parentId);
    }

    /**
     * 获取组织的所有父组织ID
     */
    public List<Long> getAllParentIds(Long childId) {
        return organizationTreeService.getAllParentIds(childId);
    }

    /**
     * 获取组织的根组织
     */
    public OrganizationTreeVO getRoot(Long id) {
        return organizationTreeService.getRoot(id);
    }

    /**
     * 获取组织的根组织ID
     */
    public Long getRootId(Long id) {
        return organizationTreeService.getRootId(id);
    }

    /**
     * 检查组织树是否有环
     */
    public boolean hasCycle(Long id) {
        return organizationTreeService.hasCycle(id);
    }

    /**
     * 获取组织树的深度
     */
    public int getDepth(Long rootId) {
        return organizationTreeService.getDepth(rootId);
    }

    /**
     * 获取组织的兄弟组织
     */
    public List<OrganizationTreeVO> getSiblings(Long id) {
        return organizationTreeService.getSiblings(id);
    }

    /**
     * 获取组织的叶子节点
     */
    public List<OrganizationTreeVO> getLeaves(Long rootId) {
        return organizationTreeService.getLeaves(rootId);
    }

    /**
     * 检查是否是叶子节点
     */
    public boolean isLeaf(Long id) {
        return organizationTreeService.isLeaf(id);
    }
} 