package com.lawfirm.mini.controller.case;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.case.model.query.CaseQuery;
import com.lawfirm.case.model.vo.CaseVO;
import com.lawfirm.case.service.CaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "小程序-案件查看")
@RestController
@RequestMapping("/mini/case")
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    @Operation(summary = "查询我的案件")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('mini:case:query')")
    public Result<List<CaseVO>> getMyCases() {
        return Result.ok(caseService.getClientCases());
    }

    @Operation(summary = "获取案件详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('mini:case:query')")
    public Result<CaseVO> get(@PathVariable Long id) {
        return Result.ok(caseService.getClientCaseDetail(id));
    }

    @Operation(summary = "获取案件进度")
    @GetMapping("/{id}/progress")
    @PreAuthorize("hasAuthority('mini:case:query')")
    public Result<List<CaseProgressVO>> getProgress(@PathVariable Long id) {
        return Result.ok(caseService.getCaseProgress(id));
    }

    @Operation(summary = "获取案件授权文件")
    @GetMapping("/{id}/auth-documents")
    @PreAuthorize("hasAuthority('mini:case:query')")
    public Result<List<DocumentVO>> getAuthDocuments(@PathVariable Long id) {
        return Result.ok(caseService.getAuthDocuments(id));
    }

    @Operation(summary = "获取案件证据材料")
    @GetMapping("/{id}/evidence")
    @PreAuthorize("hasAuthority('mini:case:query')")
    public Result<List<DocumentVO>> getEvidence(@PathVariable Long id) {
        return Result.ok(caseService.getEvidence(id));
    }
} 