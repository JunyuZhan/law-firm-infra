package com.lawfirm.cases.controller;

import com.lawfirm.model.cases.dto.CaseQueryDTO;
import com.lawfirm.cases.service.CaseService;
import com.lawfirm.common.core.model.Result;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "案件查询统计")
@RestController
@RequestMapping("/case/query")
@RequiredArgsConstructor
public class CaseQueryController {

    private final CaseService caseService;

    @Operation(summary = "获取案件状态统计")
    @GetMapping("/statistics/status")
    public Result<Map<CaseStatusEnum, Long>> countByStatus() {
        return Result.ok(caseService.countByStatus());
    }

    @Operation(summary = "获取案件类型统计")
    @GetMapping("/statistics/type")
    public Result<Map<CaseTypeEnum, Long>> countByType() {
        return Result.ok(caseService.countByType());
    }

    @Operation(summary = "按律师统计案件数量")
    @GetMapping("/count/lawyer")
    public Result<Map<String, Long>> countByLawyer() {
        return Result.ok(caseService.countByLawyer());
    }

    @Operation(summary = "按客户统计案件数量")
    @GetMapping("/count/client")
    public Result<Map<String, Long>> countByClient() {
        return Result.ok(caseService.countByClient());
    }

    @Operation(summary = "查询我的案件")
    @GetMapping("/my")
    public Result<List<CaseDetailVO>> getMyCases() {
        return Result.ok(caseService.findByCurrentLawyer());
    }

    @Operation(summary = "查询客户案件")
    @GetMapping("/client/{clientId}")
    public Result<List<CaseDetailVO>> getClientCases(@PathVariable Long clientId, @Validated CaseQueryDTO query) {
        return Result.ok(caseService.findByClient(clientId, query));
    }

    @Operation(summary = "查询相关案件")
    @GetMapping("/{caseId}/related")
    public Result<List<CaseDetailVO>> getRelatedCases(@PathVariable Long caseId) {
        return Result.ok(caseService.findRelatedCases(caseId));
    }

    @GetMapping("/list")
    public Result<List<CaseDetailVO>> list(@RequestParam CaseQueryDTO query) {
        return Result.ok(caseService.findByQuery(query));
    }

    @GetMapping("/page")
    public Result<Page<CaseDetailVO>> page(@RequestParam CaseQueryDTO query, Pageable pageable) {
        return Result.ok(caseService.findByQuery(query, pageable));
    }

    @GetMapping("/list/lawyer")
    public Result<List<CaseDetailVO>> listByLawyer() {
        return Result.ok(caseService.findByCurrentLawyer());
    }
} 