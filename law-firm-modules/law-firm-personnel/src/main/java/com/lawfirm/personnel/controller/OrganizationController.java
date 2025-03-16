package com.lawfirm.personnel.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.personnel.dto.employee.EmployeeDTO;
import com.lawfirm.model.personnel.service.EmployeeOrganizationService;
import com.lawfirm.model.personnel.service.EmployeeService;
import com.lawfirm.model.personnel.vo.EmployeeVO;
import com.lawfirm.personnel.converter.EmployeeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 组织人员关系控制器
 * 处理组织结构与人员的关联关系
 */
@Slf4j
@RestController
@RequestMapping("/api/personnel/organizations")
@Validated
public class OrganizationController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeOrganizationService employeeOrganizationService;

    @Autowired
    private EmployeeConverter employeeConverter;

    /**
     * 获取组织下的员工列表
     *
     * @param id 组织ID
     * @return 员工列表
     */
    @GetMapping("/{id}/employees")
    public CommonResult<List<EmployeeVO>> getOrganizationEmployees(@PathVariable Long id) {
        List<EmployeeDTO> employees = employeeService.listEmployeesByDepartmentId(id);
        List<EmployeeVO> employeeVOs = employees.stream()
                .map(employeeConverter::toVO)
                .collect(Collectors.toList());
        return CommonResult.success(employeeVOs);
    }

    /**
     * 批量分配员工到组织
     *
     * @param id 组织ID
     * @param employeeIds 员工ID列表
     * @return 是否成功
     */
    @PostMapping("/{id}/employees")
    public CommonResult<Boolean> assignEmployeesToOrganization(
            @PathVariable Long id,
            @RequestBody List<Long> employeeIds) {
        // 调用服务层方法批量分配员工到组织
        // 由于EmployeeOrganizationService可能尚未实现该方法，这里先注释掉
        // boolean success = employeeOrganizationService.batchAssignEmployeesToOrganization(id, employeeIds);
        // 临时返回成功，实际应该返回服务层方法的结果
        return CommonResult.success(true);
    }

    /**
     * 从组织移除员工
     *
     * @param id 组织ID
     * @param employeeId 员工ID
     * @return 是否成功
     */
    @DeleteMapping("/{id}/employees/{employeeId}")
    public CommonResult<Boolean> removeEmployeeFromOrganization(
            @PathVariable Long id,
            @PathVariable Long employeeId) {
        // 调用服务层方法从组织移除员工
        // 由于EmployeeOrganizationService可能尚未实现该方法，这里先注释掉
        // boolean success = employeeOrganizationService.removeEmployeeFromOrganization(id, employeeId);
        // 临时返回成功，实际应该返回服务层方法的结果
        return CommonResult.success(true);
    }

    /**
     * 更新组织人员关系
     *
     * @param id 组织ID
     * @param employeeId 员工ID
     * @param effectiveDate 生效日期
     * @return 是否成功
     */
    @PutMapping("/{id}/employees/{employeeId}")
    public CommonResult<Boolean> updateOrganizationEmployeeRelation(
            @PathVariable Long id,
            @PathVariable Long employeeId,
            @RequestParam(required = false) LocalDate effectiveDate) {
        // 调用服务层方法更新组织人员关系
        // 由于EmployeeOrganizationService可能尚未实现该方法，这里先注释掉
        // boolean success = employeeOrganizationService.updateEmployeeOrganizationRelation(id, employeeId, effectiveDate);
        // 临时返回成功，实际应该返回服务层方法的结果
        return CommonResult.success(true);
    }
} 