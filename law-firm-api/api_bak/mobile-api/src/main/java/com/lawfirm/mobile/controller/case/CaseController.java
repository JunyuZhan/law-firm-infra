package com.lawfirm.mobile.controller.case;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.case.model.dto.CaseDTO;
import com.lawfirm.case.model.query.CaseQuery;
import com.lawfirm.case.model.vo.CaseVO;
import com.lawfirm.case.service.CaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "移动端-案件管理")
@RestController
@RequestMapping("/mobile/case")
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    @Operation(summary = "分页查询案件")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('mobile:case:query')")
    public Result<PageResult<CaseVO>> page(CaseQuery query) {
        return Result.ok(caseService.page(query));
    }

    @Operation(summary = "获取案件详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('mobile:case:query')")
    public Result<CaseVO> get(@PathVariable Long id) {
        return Result.ok(caseService.get(id));
    }

    @Operation(summary = "更新案件进度")
    @PutMapping("/progress/{id}")
    @PreAuthorize("hasAuthority('mobile:case:update')")
    @OperationLog("更新案件进度")
    public Result<Void> updateProgress(@PathVariable Long id, @RequestParam String progress, @RequestParam String remark) {
        caseService.updateProgress(id, progress, remark);
        return Result.ok();
    }

    @Operation(summary = "获取我的案件")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('mobile:case:query')")
    public Result<List<CaseVO>> getMyCases() {
        return Result.ok(caseService.getMyCases());
    }

    @Operation(summary = "获取案件提醒")
    @GetMapping("/reminders")
    @PreAuthorize("hasAuthority('mobile:case:query')")
    public Result<List<CaseVO>> getReminders() {
        return Result.ok(caseService.getReminders());
    }

    @Operation(summary = "获取案件文档")
    @GetMapping("/{id}/documents")
    @PreAuthorize("hasAuthority('mobile:case:query')")
    public Result<List<DocumentVO>> getDocuments(@PathVariable Long id) {
        return Result.ok(caseService.getDocuments(id));
    }
} 