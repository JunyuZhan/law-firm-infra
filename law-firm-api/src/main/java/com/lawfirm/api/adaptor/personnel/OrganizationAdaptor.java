package com.lawfirm.api.adaptor.personnel;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.organization.service.OrganizationTreeService;
import com.lawfirm.model.organization.service.OrganizationPersonnelRelationService;
import com.lawfirm.model.organization.vo.OrganizationTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 组织管理适配器
 */
@Component
public class OrganizationAdaptor extends BaseAdaptor {

    private final OrganizationTreeService organizationTreeService;
    
    private final OrganizationPersonnelRelationService organizationPersonnelService;

    @Autowired
    public OrganizationAdaptor(
            @Qualifier("organizationTreeServiceImpl") OrganizationTreeService organizationTreeService,
            OrganizationPersonnelRelationService organizationPersonnelService) {
        this.organizationTreeService = organizationTreeService;
        this.organizationPersonnelService = organizationPersonnelService;
    }

    /**
     * 获取组织树
     */
    public OrganizationTreeVO getOrganizationTree(Long rootId) {
        return organizationTreeService.getTree(rootId);
    }

    /**
     * 获取子组织列表
     */
    public List<OrganizationTreeVO> getChildOrganizations(Long parentId) {
        return organizationTreeService.getChildren(parentId);
    }

    /**
     * 获取父组织
     */
    public OrganizationTreeVO getParentOrganization(Long id) {
        return organizationTreeService.getParent(id);
    }

    /**
     * 获取所有父组织
     */
    public List<OrganizationTreeVO> getAllParentOrganizations(Long id) {
        return organizationTreeService.getAllParents(id);
    }

    /**
     * 获取根组织
     */
    public OrganizationTreeVO getRootOrganization(Long id) {
        return organizationTreeService.getRoot(id);
    }
    
    /**
     * 获取同级组织
     */
    public List<OrganizationTreeVO> getSiblingOrganizations(Long id) {
        return organizationTreeService.getSiblings(id);
    }
    
    /**
     * 获取叶子组织
     */
    public List<OrganizationTreeVO> getLeafOrganizations(Long rootId) {
        return organizationTreeService.getLeaves(rootId);
    }

    /**
     * 获取组织下的员工列表
     */
    public List<Long> getOrganizationEmployees(Long organizationId, boolean includeSubOrgs) {
        return organizationPersonnelService.getEmployeeIdsByOrganizationId(organizationId, includeSubOrgs);
    }

    /**
     * 添加员工到组织
     */
    public boolean addEmployeeToOrganization(Long employeeId, Long organizationId, boolean isPrimary) {
        return organizationPersonnelService.addEmployeeToOrganization(employeeId, organizationId, isPrimary);
    }

    /**
     * 从组织中移除员工
     */
    public boolean removeEmployeeFromOrganization(Long employeeId, Long organizationId) {
        return organizationPersonnelService.removeEmployeeFromOrganization(employeeId, organizationId);
    }

    /**
     * 检查员工是否在组织中
     */
    public boolean isEmployeeInOrganization(Long employeeId, Long organizationId, boolean includeSubOrgs) {
        return organizationPersonnelService.isEmployeeInOrganization(employeeId, organizationId, includeSubOrgs);
    }
} 