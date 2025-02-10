package com.lawfirm.personnel.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.personnel.dto.request.EmployeeAddRequest;
import com.lawfirm.personnel.dto.request.EmployeeUpdateRequest;
import com.lawfirm.personnel.dto.response.EmployeeResponse;
import com.lawfirm.personnel.service.IEmployeeService;
import com.lawfirm.common.core.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * 员工管理控制器
 */
@Tag(name = "员工管理", description = "提供员工信息的增删改查等功能")
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeService employeeService;

    @Operation(summary = "添加员工", description = "添加新员工信息")
    @PostMapping
    public BaseResponse<Long> addEmployee(@Validated @RequestBody EmployeeAddRequest request) {
        return BaseResponse.success(employeeService.addEmployee(request));
    }

    @Operation(summary = "更新员工", description = "更新员工信息")
    @PutMapping("/{id}")
    public BaseResponse<Void> updateEmployee(@PathVariable Long id,
                                           @Validated @RequestBody EmployeeUpdateRequest request) {
        request.setId(id);
        employeeService.updateEmployee(request);
        return BaseResponse.success();
    }

    @Operation(summary = "删除员工", description = "删除员工信息")
    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return BaseResponse.success();
    }

    @Operation(summary = "获取员工详情", description = "根据ID获取员工详细信息")
    @GetMapping("/{id}")
    public BaseResponse<EmployeeResponse> getEmployee(@PathVariable Long id) {
        return BaseResponse.success(employeeService.getEmployeeById(id));
    }

    @Operation(summary = "分页查询员工", description = "根据条件分页查询员工列表")
    @GetMapping
    public BaseResponse<IPage<EmployeeResponse>> pageEmployees(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "姓名") @RequestParam(required = false) String name,
            @Parameter(description = "性别") @RequestParam(required = false) Integer gender,
            @Parameter(description = "分支机构ID") @RequestParam(required = false) Long branchId,
            @Parameter(description = "部门ID") @RequestParam(required = false) Long departmentId,
            @Parameter(description = "职位ID") @RequestParam(required = false) Long positionId,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status) {
        return BaseResponse.success(employeeService.pageEmployees(pageNum, pageSize, name, gender,
                branchId, departmentId, positionId, status));
    }

    @Operation(summary = "员工转正", description = "办理员工转正")
    @PostMapping("/{id}/regular")
    public BaseResponse<Void> regularEmployee(
            @PathVariable Long id,
            @Parameter(description = "转正日期") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate regularDate) {
        employeeService.regularEmployee(id, regularDate);
        return BaseResponse.success();
    }

    @Operation(summary = "员工离职", description = "办理员工离职")
    @PostMapping("/{id}/leave")
    public BaseResponse<Void> leaveEmployee(
            @PathVariable Long id,
            @Parameter(description = "离职日期") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate leaveDate) {
        employeeService.leaveEmployee(id, leaveDate);
        return BaseResponse.success();
    }
} 