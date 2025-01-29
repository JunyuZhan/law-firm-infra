package com.lawfirm.admin.controller.auth;

import com.lawfirm.common.core.model.ApiResult;
import com.lawfirm.auth.model.dto.SysDeptDTO;
import com.lawfirm.auth.model.vo.SysDeptVO;
import com.lawfirm.auth.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 系统部门Controller
 */
@Api(tags = "系统部门")
@RestController
@RequestMapping("/auth/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService deptService;

    @ApiOperation("创建部门")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> createDept(@Valid @RequestBody SysDeptDTO dto) {
        deptService.createDept(dto);
        return ApiResult.ok();
    }

    @ApiOperation("更新部门")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> updateDept(@PathVariable Long id, @Valid @RequestBody SysDeptDTO dto) {
        deptService.updateDept(id, dto);
        return ApiResult.ok();
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Void> deleteDept(@PathVariable Long id) {
        deptService.deleteDept(id);
        return ApiResult.ok();
    }

    @ApiOperation("获取部门详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<SysDeptVO> getDept(@PathVariable Long id) {
        return ApiResult.ok(deptService.getDept(id));
    }

    @ApiOperation("获取部门树")
    @GetMapping("/tree")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<List<SysDeptVO>> getDeptTree() {
        return ApiResult.ok(deptService.getDeptTree());
    }

    @ApiOperation("获取当前用户的部门树")
    @GetMapping("/current/tree")
    public ApiResult<List<SysDeptVO>> getCurrentUserDeptTree() {
        return ApiResult.ok(deptService.getCurrentUserDeptTree());
    }

    @ApiOperation("检查部门编码是否存在")
    @GetMapping("/check/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResult<Boolean> checkDeptCodeExists(@PathVariable String code) {
        return ApiResult.ok(deptService.checkDeptCodeExists(code));
    }

    @ApiOperation("导出部门数据")
    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportDepts() {
        deptService.exportDepts();
    }
} 