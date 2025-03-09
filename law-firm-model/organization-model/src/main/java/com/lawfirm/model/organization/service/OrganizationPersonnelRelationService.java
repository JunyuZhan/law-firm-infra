package com.lawfirm.model.organization.service;

import java.util.List;

/**
 * 组织-人员关系服务接口
 * 用于处理组织与人员之间的关联关系
 */
public interface OrganizationPersonnelRelationService {

    /**
     * 获取指定组织下的所有员工ID
     *
     * @param organizationId 组织ID
     * @param includeSubOrgs 是否包含子组织的员工
     * @return 员工ID列表
     */
    List<Long> getEmployeeIdsByOrganizationId(Long organizationId, boolean includeSubOrgs);
    
    /**
     * 获取指定员工所属的所有组织ID
     *
     * @param employeeId 员工ID
     * @return 组织ID列表
     */
    List<Long> getOrganizationIdsByEmployeeId(Long employeeId);
    
    /**
     * 检查员工是否属于指定组织
     *
     * @param employeeId 员工ID
     * @param organizationId 组织ID
     * @param includeSubOrgs 是否检查子组织
     * @return 是否属于该组织
     */
    boolean isEmployeeInOrganization(Long employeeId, Long organizationId, boolean includeSubOrgs);
    
    /**
     * 添加员工到组织
     *
     * @param employeeId 员工ID
     * @param organizationId 组织ID
     * @param isPrimary 是否为主要组织
     * @return 是否添加成功
     */
    boolean addEmployeeToOrganization(Long employeeId, Long organizationId, boolean isPrimary);
    
    /**
     * 从组织中移除员工
     *
     * @param employeeId 员工ID
     * @param organizationId 组织ID
     * @return 是否移除成功
     */
    boolean removeEmployeeFromOrganization(Long employeeId, Long organizationId);
} 