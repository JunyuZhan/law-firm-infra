package com.lawfirm.model.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.personnel.entity.relation.EmployeeOrganizationRelation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工与组织关系数据访问接口
 */
public interface EmployeeOrganizationRelationMapper extends BaseMapper<EmployeeOrganizationRelation> {

    /**
     * 获取员工的主要组织关系
     *
     * @param employeeId 员工ID
     * @return 组织关系实体
     */
    @Select("SELECT * FROM personnel_employee_organization WHERE employee_id = #{employeeId} AND is_primary = 1 AND status = 1 " +
            "AND (end_date IS NULL OR end_date >= CURRENT_DATE) LIMIT 1")
    EmployeeOrganizationRelation selectPrimaryRelation(Long employeeId);
    
    /**
     * 获取员工与指定组织的关系
     *
     * @param employeeId 员工ID
     * @param organizationId 组织ID
     * @return 组织关系实体
     */
    @Select("SELECT * FROM personnel_employee_organization WHERE employee_id = #{employeeId} AND organization_id = #{organizationId} " +
            "AND status = 1 AND (end_date IS NULL OR end_date >= CURRENT_DATE) LIMIT 1")
    EmployeeOrganizationRelation selectRelation(@Param("employeeId") Long employeeId, @Param("organizationId") Long organizationId);
    
    /**
     * 获取员工所有有效的组织关系
     *
     * @param employeeId 员工ID
     * @return 组织关系列表
     */
    @Select("SELECT * FROM personnel_employee_organization WHERE employee_id = #{employeeId} AND status = 1 " +
            "AND (end_date IS NULL OR end_date >= CURRENT_DATE)")
    List<EmployeeOrganizationRelation> selectActiveRelations(Long employeeId);
    
    /**
     * 获取组织下所有有效的员工关系
     *
     * @param organizationId 组织ID
     * @return 组织关系列表
     */
    @Select("SELECT * FROM personnel_employee_organization WHERE organization_id = #{organizationId} AND status = 1 " +
            "AND (end_date IS NULL OR end_date >= CURRENT_DATE)")
    List<EmployeeOrganizationRelation> selectRelationsByOrganization(Long organizationId);
    
    /**
     * 获取员工的历史组织关系记录
     *
     * @param employeeId 员工ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 组织关系列表
     */
    List<EmployeeOrganizationRelation> selectHistoryRelations(@Param("employeeId") Long employeeId, 
                                                             @Param("startDate") LocalDate startDate, 
                                                             @Param("endDate") LocalDate endDate);
} 