package com.lawfirm.personnel.service;

import com.lawfirm.model.organization.entity.Branch;
import com.lawfirm.model.organization.entity.Department;
import com.lawfirm.model.organization.entity.Position;

/**
 * 组织信息查询服务
 */
public interface OrganizationService {

    /**
     * 获取分所信息
     *
     * @param id 分所ID
     * @return 分所信息
     */
    Branch getBranch(Long id);

    /**
     * 获取部门信息
     *
     * @param id 部门ID
     * @return 部门信息
     */
    Department getDepartment(Long id);

    /**
     * 获取职位信息
     *
     * @param id 职位ID
     * @return 职位信息
     */
    Position getPosition(Long id);

    /**
     * 校验组织信息是否有效
     *
     * @param branchId 分所ID
     * @param departmentId 部门ID
     * @param positionId 职位ID
     * @return 是否有效
     */
    boolean validateOrganization(Long branchId, Long departmentId, Long positionId);
} 