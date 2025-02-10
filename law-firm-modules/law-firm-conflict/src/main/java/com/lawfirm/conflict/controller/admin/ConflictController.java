package com.lawfirm.conflict.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.conflict.entity.Conflict;
import com.lawfirm.conflict.service.IConflictService;
import com.lawfirm.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 利益冲突管理接口
 */
@Tag(name = "利益冲突管理接口")
@RestController
@RequestMapping("/admin/conflict")
@RequiredArgsConstructor
@Validated
public class ConflictController {

    private final IConflictService conflictService;

    @Operation(summary = "检查当事人冲突")
    @GetMapping("/check/party/{partyId}")
    public Result<List<Conflict>> checkPartyConflict(@PathVariable String partyId) {
        return Result.success(conflictService.checkPartyConflict(partyId));
    }

    @Operation(summary = "检查案件冲突")
    @GetMapping("/check/case/{caseId}")
    public Result<List<Conflict>> checkCaseConflict(@PathVariable String caseId) {
        return Result.success(conflictService.checkCaseConflict(caseId));
    }

    @Operation(summary = "检查律师冲突")
    @GetMapping("/check/lawyer/{lawyerId}")
    public Result<List<Conflict>> checkLawyerConflict(@PathVariable Long lawyerId) {
        return Result.success(conflictService.checkLawyerConflict(lawyerId));
    }

    @Operation(summary = "处理冲突")
    @PostMapping("/{id}/handle")
    public Result<Void> handleConflict(
            @PathVariable Long id,
            @RequestParam Long handlerId,
            @RequestParam Integer status,
            @RequestParam(required = false) String opinion) {
        conflictService.handleConflict(id, handlerId, status, opinion);
        return Result.success();
    }

    @Operation(summary = "分页查询冲突")
    @GetMapping("/page")
    public Result<IPage<Conflict>> pageConflicts(
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        return Result.success(conflictService.pageConflicts(type, status, startTime, endTime, page, size));
    }
}
