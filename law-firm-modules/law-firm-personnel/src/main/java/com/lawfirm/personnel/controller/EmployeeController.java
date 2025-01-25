package com.lawfirm.personnel.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.personnel.dto.EmployeeQueryDTO;
import com.lawfirm.personnel.entity.Employee;
import com.lawfirm.personnel.service.EmployeeService;
import com.lawfirm.personnel.vo.EmployeeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 员工管理控制器
 */
@Tag(name = "员工管理")
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "分页查询员工列表")
    @GetMapping("/page")
    public IPage<EmployeeVO> pageEmployees(@Validated EmployeeQueryDTO queryDTO) {
        return employeeService.pageEmployees(queryDTO);
    }

    @Operation(summary = "获取员工详情")
    @GetMapping("/{id}")
    public EmployeeVO getEmployeeDetail(@Parameter(description = "员工ID") @PathVariable Long id) {
        return employeeService.getEmployeeDetail(id);
    }

    @Operation(summary = "新增员工")
    @PostMapping
    public Long createEmployee(@Validated @RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @Operation(summary = "更新员工信息")
    @PutMapping("/{id}")
    public void updateEmployee(@Parameter(description = "员工ID") @PathVariable Long id,
                             @Validated @RequestBody Employee employee) {
        employee.setId(id);
        employeeService.updateEmployee(employee);
    }

    @Operation(summary = "删除员工")
    @DeleteMapping("/{id}")
    public void deleteEmployee(@Parameter(description = "员工ID") @PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @Operation(summary = "更新员工状态")
    @PutMapping("/{id}/status/{status}")
    public void updateEmployeeStatus(@Parameter(description = "员工ID") @PathVariable Long id,
                                   @Parameter(description = "状态") @PathVariable Integer status) {
        employeeService.updateEmployeeStatus(id, status);
    }
} 