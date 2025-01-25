package com.lawfirm.cases.controller;

import com.lawfirm.cases.service.CaseService;
import com.lawfirm.common.core.model.Result;
import com.lawfirm.model.cases.query.CaseQuery;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "案件查询统计")
@RestController
@RequestMapping("/cases/query")
@RequiredArgsConstructor
public class CaseQueryController {

    private final CaseService caseService;

    @Operation(summary = "按状态统计案件数量")
    @GetMapping("/count/status")
    public Result<Map<String, Long>> countByStatus() {
        return Result.success(caseService.countByStatus());
    }

    @Operation(summary = "按类型统计案件数量")
    @GetMapping("/count/type")
    public Result<Map<String, Long>> countByType() {
        return Result.success(caseService.countByType());
    }

    @Operation(summary = "按律师统计案件数量")
    @GetMapping("/count/lawyer")
    public Result<Map<String, Long>> countByLawyer() {
        return Result.success(caseService.countByLawyer());
    }

    @Operation(summary = "按客户统计案件数量")
    @GetMapping("/count/client")
    public Result<Map<String, Long>> countByClient() {
        return Result.success(caseService.countByClient());
    }

    @Operation(summary = "查询我的案件")
    @GetMapping("/my")
    public Result<List<CaseDetailVO>> getMyCases(@RequestParam String lawyer, @Validated CaseQuery query) {
        return Result.success(caseService.findByLawyer(lawyer, query));
    }

    @Operation(summary = "查询客户案件")
    @GetMapping("/client/{clientId}")
    public Result<List<CaseDetailVO>> getClientCases(@PathVariable Long clientId, @Validated CaseQuery query) {
        return Result.success(caseService.findByClient(clientId, query));
    }

    @Operation(summary = "查询相关案件")
    @GetMapping("/{caseId}/related")
    public Result<List<CaseDetailVO>> getRelatedCases(@PathVariable Long caseId) {
        return Result.success(caseService.findRelatedCases(caseId));
    }
} 