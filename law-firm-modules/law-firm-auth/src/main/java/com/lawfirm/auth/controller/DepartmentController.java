package com.lawfirm.auth.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.auth.dto.department.DepartmentCreateDTO;
import com.lawfirm.model.auth.dto.department.DepartmentQueryDTO;
import com.lawfirm.model.auth.dto.department.DepartmentUpdateDTO;
import com.lawfirm.model.auth.service.DepartmentService;
import com.lawfirm.model.auth.vo.DepartmentVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理控制器
 */
@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * 创建部门
     */
    @PostMapping
    @PreAuthorize("hasAuthority('system:department:add')")
    public CommonResult<Long> createDepartment(@RequestBody @Valid DepartmentCreateDTO createDTO) {
        Long departmentId = departmentService.createDepartment(createDTO);
        return CommonResult.success(departmentId);
    }

    /**
     * 更新部门
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('system:department:edit')")
    public CommonResult<Void> updateDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentUpdateDTO updateDTO) {
        departmentService.updateDepartment(id, updateDTO);
        return CommonResult.success();
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:department:remove')")
    public CommonResult<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return CommonResult.success();
    }

    /**
     * 获取部门详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:department:list')")
    public CommonResult<DepartmentVO> getDepartment(@PathVariable Long id) {
        DepartmentVO departmentVO = departmentService.getDepartmentById(id);
        return CommonResult.success(departmentVO);
    }

    /**
     * 查询部门列表
     */
    @GetMapping
    @PreAuthorize("hasAuthority('system:department:list')")
    public CommonResult<List<DepartmentVO>> listDepartments(DepartmentQueryDTO queryDTO) {
        List<DepartmentVO> departments = departmentService.listDepartments(queryDTO);
        return CommonResult.success(departments);
    }

    /**
     * 获取部门树
     */
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('system:department:list')")
    public CommonResult<List<DepartmentVO>> getDepartmentTree() {
        List<DepartmentVO> departmentTree = departmentService.getDepartmentTree();
        return CommonResult.success(departmentTree);
    }
}
