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
@RestController
@RequestMapping("/api/personnel/employees")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeConverter employeeConverter;

    /**
     * 获取员工列表
     *
     * @param employeeType 员工类型（可选）
     * @param departmentId 部门ID（可选）
     * @param status 员工状态（可选）
     * @param name 员工姓名（可选，支持模糊查询）
     * @return 员工列表
     */
    @GetMapping
    public CommonResult<List<EmployeeVO>> listEmployees(
            @RequestParam(required = false) EmployeeTypeEnum employeeType,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) EmployeeStatusEnum status,
            @RequestParam(required = false) String name) {
        
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
     *
     * @param id 员工ID
     * @return 员工详情
     */
    @GetMapping("/{id}")
    public CommonResult<EmployeeVO> getEmployee(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return employee != null ? CommonResult.success(employeeConverter.toVO(employee)) : CommonResult.error("员工不存在");
    }

    /**
     * 创建员工
     *
     * @param createDTO 创建员工参数
     * @return 创建的员工ID
     */
    @PostMapping
    public CommonResult<Long> createEmployee(@RequestBody @Valid CreateEmployeeDTO createDTO) {
        Long employeeId = employeeService.createEmployee(createDTO);
        return CommonResult.success(employeeId);
    }

    /**
     * 更新员工信息
     *
     * @param id 员工ID
     * @param updateDTO 更新员工参数
     * @return 是否成功
     */
    @PutMapping("/{id}")
    public CommonResult<Boolean> updateEmployee(@PathVariable Long id, @RequestBody @Valid UpdateEmployeeDTO updateDTO) {
        boolean success = employeeService.updateEmployee(id, updateDTO);
        return CommonResult.success(success);
    }

    /**
     * 删除员工
     *
     * @param id 员工ID
     * @return 是否成功
     */
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> deleteEmployee(@PathVariable Long id) {
        boolean success = employeeService.deleteEmployee(id);
        return CommonResult.success(success);
    }

    /**
     * 更新员工状态
     *
     * @param id 员工ID
     * @param status 员工状态
     * @return 是否成功
     */
    @PutMapping("/{id}/status")
    public CommonResult<Boolean> updateEmployeeStatus(@PathVariable Long id, @RequestParam EmployeeStatusEnum status) {
        boolean success = employeeService.updateEmployeeStatus(id, status);
        return CommonResult.success(success);
    }

    /**
     * 员工离职处理
     *
     * @param id 员工ID
     * @param resignDate 离职日期
     * @param reason 离职原因
     * @return 是否成功
     */
    @PutMapping("/{id}/resign")
    public CommonResult<Boolean> resignEmployee(
            @PathVariable Long id,
            @RequestParam LocalDate resignDate,
            @RequestParam(required = false) String reason) {
        boolean success = employeeService.resign(id, resignDate, reason);
        return CommonResult.success(success);
    }

    /**
     * 根据工号查询员工
     *
     * @param workNumber 工号
     * @return 员工信息
     */
    @GetMapping("/by-work-number/{workNumber}")
    public CommonResult<EmployeeVO> getEmployeeByWorkNumber(@PathVariable String workNumber) {
        EmployeeDTO employee = employeeService.getEmployeeByWorkNumber(workNumber);
        return employee != null ? CommonResult.success(employeeConverter.toVO(employee)) : CommonResult.error("员工不存在");
    }

    /**
     * 根据邮箱查询员工
     *
     * @param email 邮箱
     * @return 员工信息
     */
    @GetMapping("/by-email")
    public CommonResult<EmployeeVO> getEmployeeByEmail(@RequestParam String email) {
        EmployeeDTO employee = employeeService.getEmployeeByEmail(email);
        return employee != null ? CommonResult.success(employeeConverter.toVO(employee)) : CommonResult.error("员工不存在");
    }

    /**
     * 创建行政人员
     *
     * @param createDTO 创建行政人员参数
     * @return 创建的行政人员ID
     */
    @PostMapping("/staff")
    public CommonResult<Long> createStaff(@RequestBody @Valid CreateEmployeeDTO createDTO) {
        Long employeeId = employeeService.createStaff(createDTO);
        return CommonResult.success(employeeId);
    }
} 