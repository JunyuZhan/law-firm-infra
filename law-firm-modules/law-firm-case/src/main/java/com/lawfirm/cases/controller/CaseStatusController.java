package com.lawfirm.cases.controller;

import com.lawfirm.cases.service.CaseService;
import com.lawfirm.common.core.result.R;
import com.lawfirm.model.cases.vo.CaseStatusVO;
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
    public R<?> getStatusHistory(@PathVariable Long caseId) {
        return R.ok(caseService.getStatusHistory(caseId));
    }

    @Operation(summary = "获取案件当前状态")
    @GetMapping("/current/{caseId}")
    public R<?> getCurrentStatus(@PathVariable Long caseId) {
        return R.ok(caseService.getCurrentStatus(caseId));
    }

    @Operation(summary = "获取案件可用状态转换")
    @GetMapping("/available/{caseId}")
    public R<?> getAvailableStatus(@PathVariable Long caseId) {
        return R.ok(caseService.getAvailableStatus(caseId));
    }

    @Operation(summary = "更新案件状态")
    @PostMapping("/update/{caseId}")
    public R<?> updateStatus(@PathVariable Long caseId,
                            @RequestParam String status,
                            @RequestParam String reason,
                            @RequestParam String operator) {
        return R.ok(caseService.updateStatus(caseId, status, reason, operator));
    }
} 