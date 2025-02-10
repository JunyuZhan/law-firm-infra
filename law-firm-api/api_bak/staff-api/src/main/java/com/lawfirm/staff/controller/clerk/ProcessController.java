package com.lawfirm.staff.controller.clerk;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.process.model.dto.ProcessDTO;
import com.lawfirm.process.model.query.ProcessQuery;
import com.lawfirm.process.model.vo.ProcessVO;
import com.lawfirm.process.service.ProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "流程管理")
@RestController
@RequestMapping("/clerk/process")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService processService;

    @Operation(summary = "分页查询流程")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('clerk:process:query')")
    public Result<PageResult<ProcessVO>> page(ProcessQuery query) {
        return Result.ok(processService.page(query));
    }

    @Operation(summary = "添加流程")
    @PostMapping
    @PreAuthorize("hasAuthority('clerk:process:add')")
    @OperationLog("添加流程")
    public Result<Void> add(@RequestBody @Validated ProcessDTO processDTO) {
        processService.add(processDTO);
        return Result.ok();
    }

    @Operation(summary = "修改流程")
    @PutMapping
    @PreAuthorize("hasAuthority('clerk:process:update')")
    @OperationLog("修改流程")
    public Result<Void> update(@RequestBody @Validated ProcessDTO processDTO) {
        processService.update(processDTO);
        return Result.ok();
    }

    @Operation(summary = "删除流程")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('clerk:process:delete')")
    @OperationLog("删除流程")
    public Result<Void> delete(@PathVariable Long id) {
        processService.delete(id);
        return Result.ok();
    }

    @Operation(summary = "获取流程详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('clerk:process:query')")
    public Result<ProcessVO> get(@PathVariable Long id) {
        return Result.ok(processService.get(id));
    }

    @Operation(summary = "启动流程")
    @PostMapping("/start/{id}")
    @PreAuthorize("hasAuthority('clerk:process:start')")
    @OperationLog("启动流程")
    public Result<Void> start(@PathVariable Long id) {
        processService.start(id);
        return Result.ok();
    }

    @Operation(summary = "暂停流程")
    @PutMapping("/suspend/{id}")
    @PreAuthorize("hasAuthority('clerk:process:suspend')")
    @OperationLog("暂停流程")
    public Result<Void> suspend(@PathVariable Long id) {
        processService.suspend(id);
        return Result.ok();
    }

    @Operation(summary = "恢复流程")
    @PutMapping("/resume/{id}")
    @PreAuthorize("hasAuthority('clerk:process:resume')")
    @OperationLog("恢复流程")
    public Result<Void> resume(@PathVariable Long id) {
        processService.resume(id);
        return Result.ok();
    }

    @Operation(summary = "终止流程")
    @PutMapping("/terminate/{id}")
    @PreAuthorize("hasAuthority('clerk:process:terminate')")
    @OperationLog("终止流程")
    public Result<Void> terminate(@PathVariable Long id, @RequestParam String reason) {
        processService.terminate(id, reason);
        return Result.ok();
    }

    @Operation(summary = "导出流程")
    @GetMapping("/export")
    @PreAuthorize("hasAuthority('clerk:process:export')")
    @OperationLog("导出流程")
    public void export(ProcessQuery query) {
        processService.export(query);
    }
} 