package com.lawfirm.api.adaptor.personnel;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.personnel.dto.organization.OrganizationCreateDTO;
import com.lawfirm.model.personnel.dto.organization.OrganizationUpdateDTO;
import com.lawfirm.model.personnel.entity.Organization;
import com.lawfirm.model.personnel.service.OrganizationService;
import com.lawfirm.model.personnel.vo.OrganizationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 组织管理适配器
 */
@Component
public class OrganizationAdaptor extends BaseAdaptor {

    @Autowired
    private OrganizationService organizationService;

    /**
     * 创建组织
     */
    public OrganizationVO createOrganization(OrganizationCreateDTO dto) {
        Organization organization = organizationService.createOrganization(dto);
        return convert(organization, OrganizationVO.class);
    }

    /**
     * 更新组织信息
     */
    public OrganizationVO updateOrganization(Long id, OrganizationUpdateDTO dto) {
        Organization organization = organizationService.updateOrganization(id, dto);
        return convert(organization, OrganizationVO.class);
    }

    /**
     * 删除组织
     */
    public void deleteOrganization(Long id) {
        organizationService.deleteOrganization(id);
    }

    /**
     * 获取组织详情
     */
    public OrganizationVO getOrganization(Long id) {
        Organization organization = organizationService.getOrganization(id);
        return convert(organization, OrganizationVO.class);
    }

    /**
     * 获取所有组织
     */
    public List<OrganizationVO> listOrganizations() {
        List<Organization> organizations = organizationService.listOrganizations();
        return organizations.stream()
                .map(organization -> convert(organization, OrganizationVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 获取子组织列表
     */
    public List<OrganizationVO> getChildOrganizations(Long parentId) {
        List<Organization> organizations = organizationService.getChildOrganizations(parentId);
        return organizations.stream()
                .map(organization -> convert(organization, OrganizationVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 获取父组织
     */
    public OrganizationVO getParentOrganization(Long id) {
        Organization organization = organizationService.getParentOrganization(id);
        return convert(organization, OrganizationVO.class);
    }

    /**
     * 获取所有父组织
     */
    public List<OrganizationVO> getAllParentOrganizations(Long id) {
        List<Organization> organizations = organizationService.getAllParentOrganizations(id);
        return organizations.stream()
                .map(organization -> convert(organization, OrganizationVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 获取组织路径
     */
    public String getOrganizationPath(Long id) {
        return organizationService.getOrganizationPath(id);
    }

    /**
     * 获取组织层级
     */
    public int getOrganizationLevel(Long id) {
        return organizationService.getOrganizationLevel(id);
    }

    /**
     * 检查组织是否存在
     */
    public boolean existsOrganization(Long id) {
        return organizationService.existsOrganization(id);
    }

    /**
     * 获取组织数量
     */
    public long countOrganizations() {
        return organizationService.countOrganizations();
    }

    /**
     * 获取子组织数量
     */
    public long countChildOrganizations(Long parentId) {
        return organizationService.countChildOrganizations(parentId);
    }

    /**
     * 获取组织下的员工数量
     */
    public long countEmployeesByOrganizationId(Long organizationId) {
        return organizationService.countEmployeesByOrganizationId(organizationId);
    }

    /**
     * 获取组织下的职位数量
     */
    public long countPositionsByOrganizationId(Long organizationId) {
        return organizationService.countPositionsByOrganizationId(organizationId);
    }

    /**
     * 移动组织
     */
    public void moveOrganization(Long id, Long targetParentId) {
        organizationService.moveOrganization(id, targetParentId);
    }
} 