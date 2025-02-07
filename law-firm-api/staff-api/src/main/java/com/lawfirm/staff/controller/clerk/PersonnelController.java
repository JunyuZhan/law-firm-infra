package com.lawfirm.staff.controller.clerk;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.staff.model.request.clerk.personnel.PersonnelCreateRequest;
import com.lawfirm.staff.model.request.clerk.personnel.PersonnelPageRequest;
import com.lawfirm.staff.model.request.clerk.personnel.PersonnelUpdateRequest;
import com.lawfirm.staff.model.response.clerk.personnel.PersonnelResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "人事管理")
@RestController
@RequestMapping("/clerk/personnel")
@RequiredArgsConstructor
public class PersonnelController {

    @Operation(summary = "分页查询人员")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('clerk:personnel:query')")
    public Result<PageResult<PersonnelResponse>> page(PersonnelPageRequest request) {
        // TODO: 调用人事服务
        return Result.ok();
    }

    @Operation(summary = "创建人员")
    @PostMapping
    @PreAuthorize("hasAuthority('clerk:personnel:create')")
    @OperationLog(value = "创建人员")
    public Result<PersonnelResponse> create(@RequestBody @Validated PersonnelCreateRequest request) {
        // TODO: 调用人事服务
        return Result.ok();
    }

    @Operation(summary = "修改人员")
    @PutMapping
    @PreAuthorize("hasAuthority('clerk:personnel:update')")
    @OperationLog(value = "修改人员")
    public Result<Void> update(@RequestBody @Validated PersonnelUpdateRequest request) {
        // TODO: 调用人事服务
        return Result.ok();
    }

    @Operation(summary = "删除人员")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('clerk:personnel:delete')")
    @OperationLog(value = "删除人员")
    public Result<Void> delete(@PathVariable Long id) {
        // TODO: 调用人事服务
        return Result.ok();
    }

    @Operation(summary = "获取人员详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('clerk:personnel:query')")
    public Result<PersonnelResponse> get(@PathVariable Long id) {
        // TODO: 调用人事服务
        return Result.ok();
    }

    @Operation(summary = "上传人员照片")
    @PostMapping("/upload/photo")
    @PreAuthorize("hasAuthority('clerk:personnel:upload')")
    @OperationLog(value = "上传人员照片")
    public Result<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        // TODO: 调用人事服务
        return Result.ok();
    }

    @Operation(summary = "导入人员")
    @PostMapping("/import")
    @PreAuthorize("hasAuthority('clerk:personnel:import')")
    @OperationLog(value = "导入人员")
    public Result<Void> importPersonnel(@RequestParam("file") MultipartFile file) {
        // TODO: 调用人事服务
        return Result.ok();
    }

    @Operation(summary = "导出人员")
    @GetMapping("/export")
    @PreAuthorize("hasAuthority('clerk:personnel:export')")
    @OperationLog(value = "导出人员")
    public void export(PersonnelPageRequest request) {
        // TODO: 调用人事服务
    }

    @Operation(summary = "离职")
    @PutMapping("/resign/{id}")
    @PreAuthorize("hasAuthority('clerk:personnel:resign')")
    @OperationLog(value = "办理离职")
    public Result<Void> resign(@PathVariable Long id) {
        // TODO: 调用人事服务
        return Result.ok();
    }

    @Operation(summary = "转正")
    @PutMapping("/convert/{id}")
    @PreAuthorize("hasAuthority('clerk:personnel:convert')")
    @OperationLog(value = "办理转正")
    public Result<Void> convert(@PathVariable Long id) {
        // TODO: 调用人事服务
        return Result.ok();
    }

    @Operation(summary = "调岗")
    @PutMapping("/transfer/{id}")
    @PreAuthorize("hasAuthority('clerk:personnel:transfer')")
    @OperationLog(value = "办理调岗")
    public Result<Void> transfer(@PathVariable Long id) {
        // TODO: 调用人事服务
        return Result.ok();
    }
} 