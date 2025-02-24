package com.lawfirm.admin.controller.system;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.admin.client.system.SysDeptClient;
import com.lawfirm.admin.model.request.system.dept.*;
import com.lawfirm.admin.model.response.system.dept.SysDeptResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "系统部门管理")
@RestController
@RequestMapping("/system/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptClient sysDeptClient;

    @Operation(summary = "获取部门树")
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('system:dept:query')")
    @OperationLog(description = "获取部门树", operationType = "QUERY")
    public Result<List<SysDeptResponse>> tree() {
        return sysDeptClient.tree();
    }

    @Operation(summary = "创建部门")
    @PostMapping
    @PreAuthorize("hasAuthority('system:dept:create')")
    @OperationLog(description = "创建部门", operationType = "CREATE")
    public Result<Void> create(@RequestBody @Validated SysDeptCreateRequest request) {
        return sysDeptClient.create(request);
    }

    @Operation(summary = "修改部门")
    @PutMapping
    @PreAuthorize("hasAuthority('system:dept:update')")
    @OperationLog(description = "修改部门", operationType = "UPDATE")
    public Result<Void> update(@RequestBody @Validated SysDeptUpdateRequest request) {
        return sysDeptClient.update(request);
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:dept:delete')")
    @OperationLog(description = "删除部门", operationType = "DELETE")
    public Result<Void> delete(@PathVariable Long id) {
        return sysDeptClient.delete(id);
    }

    @Operation(summary = "获取部门详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:dept:query')")
    @OperationLog(description = "获取部门详情", operationType = "QUERY")
    public Result<SysDeptResponse> get(@PathVariable Long id) {
        return sysDeptClient.get(id);
    }

    @Operation(summary = "修改部门状态")
    @PutMapping("/status")
    @PreAuthorize("hasAuthority('system:dept:update')")
    @OperationLog(description = "修改部门状态", operationType = "UPDATE")
    public Result<Void> updateStatus(@RequestBody @Validated SysDeptUpdateStatusRequest request) {
        return sysDeptClient.updateStatus(request);
    }
} 
import com.lawfirm.model.base.enums.BaseEnum  
