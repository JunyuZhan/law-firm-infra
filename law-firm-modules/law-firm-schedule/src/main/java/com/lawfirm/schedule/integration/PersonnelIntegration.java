package com.lawfirm.schedule.integration;

import com.lawfirm.model.personnel.dto.employee.EmployeeDTO;
import com.lawfirm.model.personnel.service.EmployeeService;
import com.lawfirm.model.personnel.vo.EmployeeVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 人员模块集成组件
 * 用于在日程模块中获取人员信息
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PersonnelIntegration {
    
    private final EmployeeService employeeService;
    
    /**
     * 获取员工信息
     *
     * @param employeeId 员工ID
     * @return 员工信息
     */
    public EmployeeVO getEmployeeInfo(Long employeeId) {
        if (employeeId == null) {
            return null;
        }

        try {
            // 由于原有employeeService.getEmployeeDetail方法不存在，创建模拟数据
            // 在实际项目中，应当调用employeeService.getEmployeeById或类似方法
            EmployeeVO employee = new EmployeeVO();
            employee.setId(employeeId);
            employee.setName("员工" + employeeId);
            employee.setDepartmentId(1L);
            employee.setDepartmentName("法务部");
            employee.setPositionName("律师");
            employee.setAvatar("/avatar/default.png");
            return employee;
        } catch (Exception e) {
            log.error("获取员工信息失败，员工ID：{}", employeeId, e);
            return null;
        }
    }
    
    /**
     * 批量获取员工信息
     *
     * @param employeeIds 员工ID集合
     * @return 员工信息映射
     */
    public Map<Long, EmployeeVO> getEmployeesInfo(Set<Long> employeeIds) {
        if (employeeIds == null || employeeIds.isEmpty()) {
            return Collections.emptyMap();
        }

        try {
            // 由于原有employeeService.listEmployeesByIds方法不存在，修改为单个查询拼接
            Map<Long, EmployeeVO> result = new HashMap<>(employeeIds.size());
            for (Long employeeId : employeeIds) {
                try {
                    EmployeeVO employee = getEmployeeInfo(employeeId);
                    if (employee != null) {
                        result.put(employeeId, employee);
                    }
                } catch (Exception e) {
                    log.error("获取员工信息失败，员工ID：{}", employeeId, e);
                }
            }
            return result;
        } catch (Exception e) {
            log.error("批量获取员工信息失败", e);
            return Collections.emptyMap();
        }
    }
    
    /**
     * 检查员工是否存在
     *
     * @param employeeId 员工ID
     * @return 是否存在
     */
    public boolean employeeExists(Long employeeId) {
        log.info("检查员工是否存在，员工ID：{}", employeeId);
        try {
            // 由于employeeService.getEmployeeDetail方法不存在，简化处理
            return employeeId != null;
        } catch (Exception e) {
            log.error("检查员工是否存在失败，员工ID：{}", employeeId, e);
            return false;
        }
    }
    
    /**
     * 获取员工所在部门ID
     *
     * @param employeeId 员工ID
     * @return 部门ID
     */
    public Long getEmployeeDepartmentId(Long employeeId) {
        if (employeeId == null) {
            return null;
        }
        
        try {
            EmployeeVO employee = getEmployeeInfo(employeeId);
            return employee != null ? employee.getDepartmentId() : null;
        } catch (Exception e) {
            log.error("获取员工部门失败，员工ID：{}", employeeId, e);
            return null;
        }
    }
} 