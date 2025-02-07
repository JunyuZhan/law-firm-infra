package com.lawfirm.personnel.service.impl;

import com.lawfirm.model.organization.entity.Branch;
import com.lawfirm.model.organization.entity.Department;
import com.lawfirm.model.organization.entity.Position;
import com.lawfirm.personnel.repository.OrganizationRepository;
import com.lawfirm.personnel.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 组织信息查询服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    public Branch getBranch(Long id) {
        if (id == null) {
            return null;
        }
        try {
            return organizationRepository.findBranchById(id);
        } catch (Exception e) {
            log.error("获取分所信息失败, id: {}", id, e);
            return null;
        }
    }

    @Override
    public Department getDepartment(Long id) {
        if (id == null) {
            return null;
        }
        try {
            return organizationRepository.findDepartmentById(id);
        } catch (Exception e) {
            log.error("获取部门信息失败, id: {}", id, e);
            return null;
        }
    }

    @Override
    public Position getPosition(Long id) {
        if (id == null) {
            return null;
        }
        try {
            return organizationRepository.findPositionById(id);
        } catch (Exception e) {
            log.error("获取职位信息失败, id: {}", id, e);
            return null;
        }
    }

    @Override
    public boolean validateOrganization(Long branchId, Long departmentId, Long positionId) {
        // 获取组织信息
        Branch branch = getBranch(branchId);
        Department department = getDepartment(departmentId);
        Position position = getPosition(positionId);

        // 校验必要信息
        if (branch == null || department == null || position == null) {
            log.warn("组织信息不完整, branchId: {}, departmentId: {}, positionId: {}", 
                    branchId, departmentId, positionId);
            return false;
        }

        // 校验部门和分所是否属于同一个律所
        if (!department.getLawFirmId().equals(branch.getLawFirmId())) {
            log.warn("部门和分所不属于同一个律所, branchId: {}, departmentId: {}, branchLawFirmId: {}, departmentLawFirmId: {}", 
                    branchId, departmentId, branch.getLawFirmId(), department.getLawFirmId());
            return false;
        }

        // 校验组织状态
        boolean valid = branch.getEnabled() && department.getEnabled();
        if (!valid) {
            log.warn("组织状态无效, branch enabled: {}, department enabled: {}", 
                    branch.getEnabled(), department.getEnabled());
        }
        return valid;
    }
} 