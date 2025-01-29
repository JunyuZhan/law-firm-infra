package com.lawfirm.cases.controller;

import com.lawfirm.cases.service.CaseService;
import com.lawfirm.model.base.result.Result;
import com.lawfirm.model.cases.vo.CaseStatusVO;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "案件状态管理")
@RestController
@RequestMapping("/case/status")
@RequiredArgsConstructor
public class CaseStatusController {

    private final CaseService caseService;

    @Operation(summary = "获取案件状态历史")
    @GetMapping("/history/{caseId}")
    public Result<List<CaseStatusVO>> getStatusHistory(@PathVariable Long caseId) {
        return Result.ok(caseService.getStatusHistory(caseId));
    }

    @Operation(summary = "获取案件当前状态")
    @GetMapping("/current/{caseId}")
    public Result<CaseStatusEnum> getCurrentStatus(@PathVariable Long caseId) {
        return Result.ok(caseService.getCurrentStatus(caseId));
    }

    @Operation(summary = "获取案件可用状态转换")
    @GetMapping("/available/{caseId}")
    public Result<List<CaseStatusEnum>> getAvailableStatus(@PathVariable Long caseId) {
        return Result.ok(caseService.getAvailableStatus(caseId));
    }

    @Operation(summary = "更新案件状态")
    @PostMapping("/update/{caseId}")
    public Result<Void> updateStatus(@PathVariable Long caseId,
                            @RequestParam String status,
                            @RequestParam String reason,
                            @RequestParam String operator) {
        caseService.updateStatus(caseId, status, reason, operator);
        return Result.ok();
    }
} 