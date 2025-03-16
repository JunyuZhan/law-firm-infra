package com.lawfirm.personnel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lawfirm.model.organization.entity.department.Department;
import com.lawfirm.model.organization.mapper.DepartmentMapper;
import com.lawfirm.model.organization.service.OrganizationPersonnelRelationService;
import com.lawfirm.model.personnel.entity.Employee;
import com.lawfirm.model.personnel.mapper.EmployeeMapper;
import com.lawfirm.model.personnel.service.EmployeeOrganizationService;
import com.lawfirm.common.core.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 组织与人员关系服务实现类
 * 实现organization-model中的OrganizationPersonnelRelationService接口
 */
@Slf4j
@Service
public class OrganizationPersonnelRelationServiceImpl implements OrganizationPersonnelRelationService {

    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Autowired
    private DepartmentMapper departmentMapper;
    
    @Autowired
    private EmployeeOrganizationService employeeOrganizationService;

    @Override
    public List<Long> getEmployeeIdsByOrganizationId(Long organizationId, boolean includeSubOrgs) {
        return employeeOrganizationService.getOrganizationEmployeeIds(organizationId, includeSubOrgs);
    }

    @Override
    public List<Long> getOrganizationIdsByEmployeeId(Long employeeId) {
        return employeeOrganizationService.getEmployeeOrganizationIds(employeeId);
    }

    @Override
    public boolean isEmployeeInOrganization(Long employeeId, Long organizationId, boolean includeSubOrgs) {
        return employeeOrganizationService.isEmployeeInOrganization(employeeId, organizationId, includeSubOrgs);
    }

    @Override
    public boolean addEmployeeToOrganization(Long employeeId, Long organizationId, boolean isPrimary) {
        return employeeOrganizationService.assignEmployeeToOrganization(
                employeeId, organizationId, null, isPrimary, LocalDate.now(), null);
    }

    @Override
    public boolean removeEmployeeFromOrganization(Long employeeId, Long organizationId) {
        return employeeOrganizationService.removeEmployeeFromOrganization(employeeId, organizationId, LocalDate.now());
    }

    // 以下是旧接口的兼容方法
    
    public List<Long> getOrganizationEmployeeIds(Long organizationId) {
        return employeeOrganizationService.getOrganizationEmployeeIds(organizationId, false);
    }

    public List<Long> getOrganizationEmployeeIdsWithSubOrgs(Long organizationId) {
        return employeeOrganizationService.getOrganizationEmployeeIds(organizationId, true);
    }

    public List<Employee> getOrganizationEmployees(Long organizationId) {
        List<Long> employeeIds = getOrganizationEmployeeIds(organizationId);
        
        if (employeeIds.isEmpty()) {
            return List.of();
        }
        
        return employeeMapper.selectBatchIds(employeeIds);
    }

    public List<Employee> getOrganizationEmployeesWithSubOrgs(Long organizationId) {
        List<Long> employeeIds = getOrganizationEmployeeIdsWithSubOrgs(organizationId);
        
        if (employeeIds.isEmpty()) {
            return List.of();
        }
        
        return employeeMapper.selectBatchIds(employeeIds);
    }

    public List<Department> getEmployeeOrganizations(Long employeeId) {
        List<Long> organizationIds = employeeOrganizationService.getEmployeeOrganizationIds(employeeId);
        
        if (organizationIds.isEmpty()) {
            return List.of();
        }
        
        return departmentMapper.selectBatchIds(organizationIds);
    }

    public Department getEmployeePrimaryOrganization(Long employeeId) {
        Long organizationId = employeeOrganizationService.getPrimaryOrganizationId(employeeId);
        
        if (organizationId == null) {
            return null;
        }
        
        return departmentMapper.selectById(organizationId);
    }

    public boolean assignEmployeeToOrganization(Long employeeId, Long organizationId, Long positionId, boolean isPrimary) {
        return employeeOrganizationService.assignEmployeeToOrganization(
                employeeId, organizationId, positionId, isPrimary, LocalDate.now(), null);
    }

    public boolean isEmployeeInOrganization(Long employeeId, Long organizationId) {
        return employeeOrganizationService.isEmployeeInOrganization(employeeId, organizationId, false);
    }

    public boolean isEmployeeInOrganizationWithSubOrgs(Long employeeId, Long organizationId) {
        return employeeOrganizationService.isEmployeeInOrganization(employeeId, organizationId, true);
    }

    public List<Map<String, Object>> getEmployeeOrganizationPositions(Long employeeId) {
        Map<Long, Long> positionMap = employeeOrganizationService.getEmployeeOrganizationPositions(employeeId);
        
        // 转换为所需的返回格式
        return positionMap.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new java.util.HashMap<>();
                    result.put("organizationId", entry.getKey());
                    result.put("positionId", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }

    public boolean updateEmployeePosition(Long employeeId, Long organizationId, Long positionId, String reason) {
        return employeeOrganizationService.updateEmployeePosition(employeeId, organizationId, positionId, reason);
    }

    public Long getEmployeePosition(Long employeeId, Long organizationId) {
        return employeeOrganizationService.getEmployeePositionId(employeeId, organizationId);
    }

    public List<Map<String, Object>> getEmployeeOrganizationHistory(Long employeeId) {
        return employeeOrganizationService.getEmployeeOrganizationHistory(employeeId);
    }
}