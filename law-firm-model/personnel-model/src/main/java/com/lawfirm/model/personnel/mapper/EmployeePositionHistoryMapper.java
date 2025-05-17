package com.lawfirm.model.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.personnel.entity.history.EmployeePositionHistory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工职位变更历史数据访问接口
 */
@Mapper
public interface EmployeePositionHistoryMapper extends BaseMapper<EmployeePositionHistory> {

    /**
     * 根据员工ID查询职位变更历史
     *
     * @param employeeId 员工ID
     * @return 职位变更历史列表
     */
    @Select("SELECT * FROM personnel_employee_position_history WHERE employee_id = #{employeeId} ORDER BY effective_date DESC")
    List<EmployeePositionHistory> selectByEmployeeId(Long employeeId);
    
    /**
     * 根据员工ID和组织ID查询职位变更历史
     *
     * @param employeeId 员工ID
     * @param organizationId 组织ID
     * @return 职位变更历史列表
     */
    @Select("SELECT * FROM personnel_employee_position_history WHERE employee_id = #{employeeId} AND organization_id = #{organizationId} ORDER BY effective_date DESC")
    List<EmployeePositionHistory> selectByEmployeeAndOrganization(@Param("employeeId") Long employeeId, @Param("organizationId") Long organizationId);
    
    /**
     * 根据日期范围查询职位变更历史
     *
     * @param startDate 起始日期
     * @param endDate 结束日期
     * @return 职位变更历史列表
     */
    @Select("SELECT * FROM personnel_employee_position_history WHERE effective_date BETWEEN #{startDate} AND #{endDate} ORDER BY effective_date DESC")
    List<EmployeePositionHistory> selectByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    /**
     * 获取员工最近一次职位变更记录
     *
     * @param employeeId 员工ID
     * @param organizationId 组织ID
     * @return 职位变更历史
     */
    @Select("SELECT * FROM personnel_employee_position_history WHERE employee_id = #{employeeId} AND organization_id = #{organizationId} ORDER BY effective_date DESC LIMIT 1")
    EmployeePositionHistory selectLatestChange(@Param("employeeId") Long employeeId, @Param("organizationId") Long organizationId);
} 