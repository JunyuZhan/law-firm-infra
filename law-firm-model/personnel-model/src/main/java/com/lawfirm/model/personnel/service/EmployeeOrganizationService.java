package com.lawfirm.model.personnel.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 员工与组织关系服务接口
 * 用于处理员工与组织机构之间的关联
 */
public interface EmployeeOrganizationService {

    /**
     * 获取员工所属的主要组织ID
     *
     * @param employeeId 员工ID
     * @return 主要组织ID
     */
    Long getPrimaryOrganizationId(Long employeeId);
    
    /**
     * 获取员工所属的所有组织ID
     *
     * @param employeeId 员工ID
     * @return 组织ID列表
     */
    List<Long> getEmployeeOrganizationIds(Long employeeId);
    
    /**
     * 获取员工在指定组织中的职位ID
     *
     * @param employeeId 员工ID
     * @param organizationId 组织ID
     * @return 职位ID
     */
    Long getEmployeePositionId(Long employeeId, Long organizationId);
    
    /**
     * 获取组织中的所有员工ID
     *
     * @param organizationId 组织ID
     * @param includeSubOrgs 是否包含子组织
     * @return 员工ID列表
     */
    List<Long> getOrganizationEmployeeIds(Long organizationId, boolean includeSubOrgs);
    
    /**
     * 将员工分配到组织
     *
     * @param employeeId 员工ID
     * @param organizationId 组织ID
     * @param positionId 职位ID
     * @param isPrimary 是否为主要组织
     * @param startDate 开始日期
     * @param endDate 结束日期（可为null表示无结束日期）
     * @return 是否成功
     */
    boolean assignEmployeeToOrganization(Long employeeId, Long organizationId, Long positionId, 
                                         boolean isPrimary, LocalDate startDate, LocalDate endDate);
    
    /**
     * 从组织中移除员工
     *
     * @param employeeId 员工ID
     * @param organizationId 组织ID
     * @param endDate 结束日期
     * @return 是否成功
     */
    boolean removeEmployeeFromOrganization(Long employeeId, Long organizationId, LocalDate endDate);
    
    /**
     * 更新员工在组织中的职位
     *
     * @param employeeId 员工ID
     * @param organizationId 组织ID
     * @param positionId 新职位ID
     * @param reason 变更原因
     * @return 是否成功
     */
    boolean updateEmployeePosition(Long employeeId, Long organizationId, Long positionId, String reason);
    
    /**
     * 获取员工的组织变更历史
     *
     * @param employeeId 员工ID
     * @return 变更历史列表
     */
    List<Map<String, Object>> getEmployeeOrganizationHistory(Long employeeId);
    
    /**
     * 检查员工是否在指定组织中
     *
     * @param employeeId 员工ID
     * @param organizationId 组织ID
     * @param checkSubOrgs 是否检查子组织
     * @return 是否在组织中
     */
    boolean isEmployeeInOrganization(Long employeeId, Long organizationId, boolean checkSubOrgs);
    
    /**
     * 获取员工在所有组织中的职位信息
     *
     * @param employeeId 员工ID
     * @return 组织职位映射，Key为组织ID，Value为职位ID
     */
    Map<Long, Long> getEmployeeOrganizationPositions(Long employeeId);
} 