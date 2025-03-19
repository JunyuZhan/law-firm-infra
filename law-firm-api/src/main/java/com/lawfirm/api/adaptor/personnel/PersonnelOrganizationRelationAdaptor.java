package com.lawfirm.api.adaptor.personnel;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.personnel.service.EmployeeOrganizationService;
import com.lawfirm.model.personnel.vo.EmployeeOrganizationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 人事组织关系管理适配器
 */
@Component
public class PersonnelOrganizationRelationAdaptor extends BaseAdaptor {

    @Autowired
    private EmployeeOrganizationService employeeOrganizationService;

    /**
     * 获取员工所属的主要组织ID
     */
    public Long getPrimaryOrganizationId(Long employeeId) {
        return employeeOrganizationService.getPrimaryOrganizationId(employeeId);
    }

    /**
     * 获取员工所属的所有组织ID
     */
    public List<Long> getEmployeeOrganizationIds(Long employeeId) {
        return employeeOrganizationService.getEmployeeOrganizationIds(employeeId);
    }

    /**
     * 获取员工在指定组织中的职位ID
     */
    public Long getEmployeePositionId(Long employeeId, Long organizationId) {
        return employeeOrganizationService.getEmployeePositionId(employeeId, organizationId);
    }

    /**
     * 获取组织中的所有员工ID
     */
    public List<Long> getOrganizationEmployeeIds(Long organizationId, boolean includeSubOrgs) {
        return employeeOrganizationService.getOrganizationEmployeeIds(organizationId, includeSubOrgs);
    }

    /**
     * 将员工分配到组织
     */
    public boolean assignEmployeeToOrganization(Long employeeId, Long organizationId, Long positionId, 
                                              boolean isPrimary, LocalDate startDate, LocalDate endDate) {
        return employeeOrganizationService.assignEmployeeToOrganization(
                employeeId, organizationId, positionId, isPrimary, startDate, endDate);
    }

    /**
     * 从组织中移除员工
     */
    public boolean removeEmployeeFromOrganization(Long employeeId, Long organizationId, LocalDate endDate) {
        return employeeOrganizationService.removeEmployeeFromOrganization(employeeId, organizationId, endDate);
    }

    /**
     * 更新员工在组织中的职位
     */
    public boolean updateEmployeePosition(Long employeeId, Long organizationId, Long positionId, String reason) {
        return employeeOrganizationService.updateEmployeePosition(employeeId, organizationId, positionId, reason);
    }

    /**
     * 获取员工的组织变更历史
     */
    public List<Map<String, Object>> getEmployeeOrganizationHistory(Long employeeId) {
        return employeeOrganizationService.getEmployeeOrganizationHistory(employeeId);
    }

    /**
     * 检查员工是否在指定组织中
     */
    public boolean isEmployeeInOrganization(Long employeeId, Long organizationId, boolean checkSubOrgs) {
        return employeeOrganizationService.isEmployeeInOrganization(employeeId, organizationId, checkSubOrgs);
    }

    /**
     * 获取员工在所有组织中的职位信息
     */
    public Map<Long, Long> getEmployeeOrganizationPositions(Long employeeId) {
        return employeeOrganizationService.getEmployeeOrganizationPositions(employeeId);
    }
} 