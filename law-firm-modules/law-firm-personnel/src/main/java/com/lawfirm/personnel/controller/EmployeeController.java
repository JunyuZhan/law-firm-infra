package com.lawfirm.personnel.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.personnel.dto.employee.CreateEmployeeDTO;
import com.lawfirm.model.personnel.dto.employee.EmployeeDTO;
import com.lawfirm.model.personnel.dto.employee.UpdateEmployeeDTO;
import com.lawfirm.model.personnel.enums.EmployeeStatusEnum;
import com.lawfirm.model.personnel.enums.EmployeeTypeEnum;
import com.lawfirm.model.personnel.service.EmployeeService;
import com.lawfirm.model.personnel.vo.EmployeeVO;
import com.lawfirm.personnel.converter.EmployeeConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 员工管理控制器
 * 整合了原律师和行政人员的API接口
 */
@Slf4j
@Tag(name = "员工管理", description = "员工信息管理，包括律师和行政人员")
@RestController("employeeController")
@RequestMapping("/api/personnel/employee")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeConverter employeeConverter;

    /**
     * 获取员工列表
     */
    @Operation(summary = "获取员工列表", description = "根据条件查询员工列表")
    @GetMapping
    public CommonResult<List<EmployeeVO>> listEmployees(
            @Parameter(description = "员工类型") @RequestParam(required = false) EmployeeTypeEnum employeeType,
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId,
            @Parameter(description = "员工状态") @RequestParam(required = false) EmployeeStatusEnum status,
            @Parameter(description = "员工姓名，支持模糊查询") @RequestParam(required = false) String name) {
        
        Map<String, Object> params = new HashMap<>();
        if (employeeType != null) {
            params.put("employeeType", employeeType);
        }
        if (departmentId != null) {
            params.put("departmentId", departmentId);
        }
        if (status != null) {
            params.put("status", status);
        }
        if (name != null) {
            params.put("name", name);
        }
        
        List<EmployeeDTO> employees = employeeService.listEmployees(params);
        List<EmployeeVO> employeeVOs = employees.stream()
                .map(employeeConverter::toVO)
                .collect(Collectors.toList());
        
        return CommonResult.success(employeeVOs);
    }

    /**
     * 获取单个员工详情
     */
    @Operation(summary = "获取员工详情", description = "根据ID获取员工详细信息")
    @GetMapping("/{id}")
    public CommonResult<EmployeeVO> getEmployee(
            @Parameter(description = "员工ID") @PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return employee != null ? CommonResult.success(employeeConverter.toVO(employee)) : CommonResult.error("员工不存在");
    }

    /**
     * 创建员工
     */
    @Operation(summary = "创建员工", description = "创建新员工")
    @PostMapping
    public CommonResult<Long> createEmployee(
            @Parameter(description = "员工信息") @RequestBody @Valid CreateEmployeeDTO createDTO) {
        Long employeeId = employeeService.createEmployee(createDTO);
        return CommonResult.success(employeeId);
    }

    /**
     * 更新员工信息
     */
    @Operation(summary = "更新员工信息", description = "根据ID更新员工信息")
    @PutMapping("/{id}")
    public CommonResult<Boolean> updateEmployee(
            @Parameter(description = "员工ID") @PathVariable Long id,
            @Parameter(description = "更新的员工信息") @RequestBody @Valid UpdateEmployeeDTO updateDTO) {
        boolean success = employeeService.updateEmployee(id, updateDTO);
        return CommonResult.success(success);
    }

    /**
     * 删除员工
     */
    @Operation(summary = "删除员工", description = "根据ID删除员工")
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> deleteEmployee(
            @Parameter(description = "员工ID") @PathVariable Long id) {
        boolean success = employeeService.deleteEmployee(id);
        return CommonResult.success(success);
    }

    /**
     * 更新员工状态
     */
    @Operation(summary = "更新员工状态", description = "更新员工的在职状态")
    @PutMapping("/{id}/status")
    public CommonResult<Boolean> updateEmployeeStatus(
            @Parameter(description = "员工ID") @PathVariable Long id,
            @Parameter(description = "员工状态") @RequestParam EmployeeStatusEnum status) {
        boolean success = employeeService.updateEmployeeStatus(id, status);
        return CommonResult.success(success);
    }

    /**
     * 员工离职处理
     */
    @Operation(summary = "员工离职处理", description = "处理员工离职")
    @PutMapping("/{id}/resign")
    public CommonResult<Boolean> resignEmployee(
            @Parameter(description = "员工ID") @PathVariable Long id,
            @Parameter(description = "离职日期") @RequestParam LocalDate resignDate,
            @Parameter(description = "离职原因") @RequestParam(required = false) String reason) {
        boolean success = employeeService.resign(id, resignDate, reason);
        return CommonResult.success(success);
    }

    /**
     * 根据工号查询员工
     */
    @Operation(summary = "根据工号查询员工", description = "通过工号查询员工信息")
    @GetMapping("/by-work-number/{workNumber}")
    public CommonResult<EmployeeVO> getEmployeeByWorkNumber(
            @Parameter(description = "工号") @PathVariable String workNumber) {
        EmployeeDTO employee = employeeService.getEmployeeByWorkNumber(workNumber);
        return employee != null ? CommonResult.success(employeeConverter.toVO(employee)) : CommonResult.error("员工不存在");
    }

    /**
     * 根据邮箱查询员工
     */
    @Operation(summary = "根据邮箱查询员工", description = "通过邮箱查询员工信息")
    @GetMapping("/by-email")
    public CommonResult<EmployeeVO> getEmployeeByEmail(
            @Parameter(description = "邮箱地址") @RequestParam String email) {
        EmployeeDTO employee = employeeService.getEmployeeByEmail(email);
        return employee != null ? CommonResult.success(employeeConverter.toVO(employee)) : CommonResult.error("员工不存在");
    }

    /**
     * 创建行政人员
     */
    @Operation(summary = "创建行政人员", description = "创建新的行政人员")
    @PostMapping("/staff")
    public CommonResult<Long> createStaff(
            @Parameter(description = "行政人员信息") @RequestBody @Valid CreateEmployeeDTO createDTO) {
        Long employeeId = employeeService.createStaff(createDTO);
        return CommonResult.success(employeeId);
    }
} 