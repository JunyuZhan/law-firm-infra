package com.lawfirm.auth.controller;

import com.lawfirm.common.model.Result;
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
    public Result<Long> createDepartment(@RequestBody @Valid DepartmentCreateDTO createDTO) {
        Long departmentId = departmentService.createDepartment(createDTO);
        return Result.ok().data(departmentId);
    }

    /**
     * 更新部门
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('system:department:edit')")
    public Result<Void> updateDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentUpdateDTO updateDTO) {
        departmentService.updateDepartment(id, updateDTO);
        return Result.ok();
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:department:remove')")
    public Result<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return Result.ok();
    }

    /**
     * 获取部门详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:department:list')")
    public Result<DepartmentVO> getDepartment(@PathVariable Long id) {
        DepartmentVO departmentVO = departmentService.getDepartmentById(id);
        return Result.ok().data(departmentVO);
    }

    /**
     * 查询部门列表
     */
    @GetMapping
    @PreAuthorize("hasAuthority('system:department:list')")
    public Result<List<DepartmentVO>> listDepartments(DepartmentQueryDTO queryDTO) {
        List<DepartmentVO> departments = departmentService.listDepartments(queryDTO);
        return Result.ok().data(departments);
    }

    /**
     * 获取部门树
     */
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('system:department:list')")
    public Result<List<DepartmentVO>> getDepartmentTree() {
        List<DepartmentVO> departmentTree = departmentService.getDepartmentTree();
        return Result.ok().data(departmentTree);
    }
}
