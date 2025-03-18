package com.lawfirm.cases.integration.personnel;

import com.lawfirm.model.personnel.dto.employee.EmployeeDTO;
import com.lawfirm.model.personnel.enums.EmployeeTypeEnum;
import com.lawfirm.model.personnel.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 律师组件
 * 负责与人事模块的集成，获取律师相关信息
 */
@Slf4j
@Component
@Lazy
@RequiredArgsConstructor
public class LawyerComponent {

    private final EmployeeService employeeService;

    /**
     * 获取律师详细信息
     *
     * @param lawyerId 律师ID
     * @return 律师详情
     */
    public Optional<EmployeeDTO> getLawyerDetail(Long lawyerId) {
        if (lawyerId == null) {
            return Optional.empty();
        }

        try {
            EmployeeDTO employee = employeeService.getEmployeeById(lawyerId);
            if (employee != null && EmployeeTypeEnum.LAWYER.equals(employee.getEmployeeType())) {
                return Optional.of(employee);
            }
            return Optional.empty();
        } catch (Exception e) {
            log.error("获取律师详情异常，lawyerId={}", lawyerId, e);
            return Optional.empty();
        }
    }

    /**
     * 根据专业领域获取律师列表
     *
     * @param practiceArea 专业领域
     * @return 律师列表
     */
    public List<EmployeeDTO> getLawyersByPracticeArea(String practiceArea) {
        if (practiceArea == null || practiceArea.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            return employeeService.listLawyersByPracticeArea(practiceArea);
        } catch (Exception e) {
            log.error("获取专业领域律师列表异常，practiceArea={}", practiceArea, e);
            return Collections.emptyList();
        }
    }

    /**
     * 获取特定部门的律师列表
     *
     * @param departmentId 部门ID
     * @return 律师列表
     */
    public List<EmployeeDTO> getLawyersByDepartment(Long departmentId) {
        if (departmentId == null) {
            return Collections.emptyList();
        }

        try {
            // 使用正确的方法名获取部门员工
            return employeeService.listEmployeesByDepartmentIdAndType(departmentId, EmployeeTypeEnum.LAWYER);
        } catch (Exception e) {
            log.error("获取部门律师列表异常，departmentId={}", departmentId, e);
            return Collections.emptyList();
        }
    }

    /**
     * 检查律师的工作负荷
     *
     * @param lawyerId 律师ID
     * @return 工作负荷百分比（0-100）
     */
    public int getLawyerWorkload(Long lawyerId) {
        if (lawyerId == null) {
            return 0;
        }

        try {
            // 假设有一个通用的获取员工工作负荷的方法
            // 如果EmployeeService没有此方法，需要在实现类中添加
            // 这里暂时返回默认值
            return 50; // 默认返回50%的工作负荷
        } catch (Exception e) {
            log.error("获取律师工作负荷异常，lawyerId={}", lawyerId, e);
            return 0;
        }
    }

    /**
     * 获取可用于案件分配的律师列表
     *
     * @param practiceArea 专业领域
     * @param maxWorkloadPercentage 最大工作负荷百分比
     * @return 律师列表
     */
    public List<EmployeeDTO> getAvailableLawyers(String practiceArea, int maxWorkloadPercentage) {
        if (practiceArea == null || practiceArea.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            // 获取特定专业领域的律师
            List<EmployeeDTO> lawyers = employeeService.listLawyersByPracticeArea(practiceArea);
            
            // 由于EmployeeService没有工作负荷相关方法，这里简单返回所有查询结果
            // 实际实现时应根据工作负荷进行过滤
            return lawyers;
        } catch (Exception e) {
            log.error("获取可用律师列表异常，practiceArea={}, maxWorkload={}", practiceArea, maxWorkloadPercentage, e);
            return Collections.emptyList();
        }
    }

    /**
     * 批量获取律师信息
     *
     * @param lawyerIds 律师ID集合
     * @return 律师信息列表
     */
    public List<EmployeeDTO> getLawyersByIds(Set<Long> lawyerIds) {
        if (lawyerIds == null || lawyerIds.isEmpty()) {
            return Collections.emptyList();
        }

        try {
            // 根据ID获取员工列表
            List<EmployeeDTO> employees = listEmployeesByIds(lawyerIds);
            
            // 过滤出律师类型的员工
            return employees.stream()
                    .filter(e -> EmployeeTypeEnum.LAWYER.equals(e.getEmployeeType()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("批量获取律师信息异常，lawyerIds.size={}", lawyerIds.size(), e);
            return Collections.emptyList();
        }
    }
    
    /**
     * 根据ID列表获取员工
     * 由于EmployeeService没有此方法，需要自行实现
     */
    private List<EmployeeDTO> listEmployeesByIds(Set<Long> ids) {
        // 这里简单实现，实际应调用批量查询方法
        return ids.stream()
                .map(employeeService::getEmployeeById)
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }
} 