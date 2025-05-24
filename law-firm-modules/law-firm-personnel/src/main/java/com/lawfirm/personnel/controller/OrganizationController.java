package com.lawfirm.personnel.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.personnel.dto.employee.EmployeeDTO;
import com.lawfirm.model.personnel.service.EmployeeOrganizationService;
import com.lawfirm.model.personnel.service.EmployeeService;
import com.lawfirm.model.personnel.vo.EmployeeVO;
import com.lawfirm.personnel.converter.EmployeeConverter;
import com.lawfirm.personnel.constant.PersonnelConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 组织人员关系控制器
 * 处理组织结构与人员的关联关系
 */
@Slf4j
@Tag(name = "组织机构管理", description = "提供组织机构管理相关接口")
@RestController("organizationController")
@RequestMapping(PersonnelConstants.API_ORGANIZATION_PREFIX)
@Validated
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLERK')")
public class OrganizationController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeOrganizationService employeeOrganizationService;

    @Autowired
    private EmployeeConverter employeeConverter;

    /**
     * 获取组织下的员工列表
     */
    @Operation(summary = "获取组织下的员工列表", description = "获取指定组织下的所有员工")
    @GetMapping("/{id}/employees")
    public CommonResult<List<EmployeeVO>> getOrganizationEmployees(
            @Parameter(description = "组织ID") @PathVariable Long id) {
        List<EmployeeDTO> employees = employeeService.listEmployeesByDepartmentId(id);
        List<EmployeeVO> employeeVOs = employees.stream()
                .map(employeeConverter::toVO)
                .collect(Collectors.toList());
        return CommonResult.success(employeeVOs);
    }

    /**
     * 批量分配员工到组织
     */
    @Operation(summary = "批量分配员工到组织", description = "将多个员工分配到指定组织")
    @PostMapping("/{id}/employees")
    public CommonResult<Boolean> assignEmployeesToOrganization(
            @Parameter(description = "组织ID") @PathVariable Long id,
            @Parameter(description = "员工ID列表") @RequestBody List<Long> employeeIds) {
        if (employeeIds == null || employeeIds.isEmpty()) {
            return CommonResult.error("员工ID列表不能为空");
        }
        boolean allSuccess = true;
        for (Long employeeId : employeeIds) {
            // 这里只做最基础分配，positionId/isPrimary等可后续扩展
            boolean success = employeeOrganizationService.assignEmployeeToOrganization(employeeId, id, null, false, null, null);
            if (!success) {
                allSuccess = false;
            }
        }
        return CommonResult.success(allSuccess);
    }

    /**
     * 从组织移除员工
     */
    @Operation(summary = "从组织移除员工", description = "将指定员工从组织中移除")
    @DeleteMapping("/{id}/employees/{employeeId}")
    public CommonResult<Boolean> removeEmployeeFromOrganization(
            @Parameter(description = "组织ID") @PathVariable Long id,
            @Parameter(description = "员工ID") @PathVariable Long employeeId) {
        boolean success = employeeOrganizationService.removeEmployeeFromOrganization(employeeId, id, null);
        return CommonResult.success(success);
    }

    /**
     * 更新组织人员关系
     */
    @Operation(summary = "更新组织人员关系", description = "更新员工与组织的关联关系")
    @PutMapping("/{id}/employees/{employeeId}")
    public CommonResult<Boolean> updateOrganizationEmployeeRelation(
            @Parameter(description = "组织ID") @PathVariable Long id,
            @Parameter(description = "员工ID") @PathVariable Long employeeId,
            @Parameter(description = "职位ID") @RequestParam(required = false) Long positionId,
            @Parameter(description = "变更原因") @RequestParam(required = false) String reason) {
        if (positionId == null) {
            return CommonResult.error("职位ID不能为空");
        }
        boolean success = employeeOrganizationService.updateEmployeePosition(employeeId, id, positionId, reason);
        return CommonResult.success(success);
    }
} 