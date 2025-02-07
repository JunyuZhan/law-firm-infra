package com.lawfirm.staff.controller.lawyer;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.annotation.OperationLog;
import com.lawfirm.staff.annotation.OperationType;
import com.lawfirm.staff.client.CaseClient;
import com.lawfirm.staff.model.request.lawyer.cases.*;
import com.lawfirm.staff.model.request.lawyer.cases.document.CaseDocumentCreateRequest;
import com.lawfirm.staff.model.request.lawyer.cases.progress.CaseProgressCreateRequest;
import com.lawfirm.staff.model.request.lawyer.cases.relation.CaseRelationCreateRequest;
import com.lawfirm.staff.model.response.lawyer.cases.*;
import com.lawfirm.staff.model.response.lawyer.cases.document.CaseDocumentResponse;
import com.lawfirm.staff.model.response.lawyer.cases.progress.CaseProgressResponse;
import com.lawfirm.staff.model.response.lawyer.cases.relation.CaseRelationResponse;
import com.lawfirm.staff.model.response.lawyer.cases.statistics.CaseStatisticsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 案件管理
 */
@Tag(name = "律师-案件管理")
@RestController
@RequestMapping("/staff/lawyer/case")
@RequiredArgsConstructor
public class CaseController {

    private final CaseClient caseClient;

    @Operation(summary = "分页查询案件")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('lawyer:case:query')")
    @OperationLog(value = "分页查询案件", type = OperationType.QUERY)
    public Result<PageResult<CaseResponse>> page(CasePageRequest request) {
        return caseClient.page(request);
    }

    @Operation(summary = "创建案件")
    @PostMapping
    @PreAuthorize("hasAuthority('lawyer:case:add')")
    @OperationLog(value = "创建案件", type = OperationType.CREATE)
    public Result<Void> create(@RequestBody @Validated CaseCreateRequest request) {
        return caseClient.create(request);
    }

    @Operation(summary = "修改案件")
    @PutMapping
    @PreAuthorize("hasAuthority('lawyer:case:update')")
    @OperationLog(value = "修改案件", type = OperationType.UPDATE)
    public Result<Void> update(@RequestBody @Validated CaseUpdateRequest request) {
        return caseClient.update(request);
    }

    @Operation(summary = "删除案件")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('lawyer:case:delete')")
    @OperationLog(value = "删除案件", type = OperationType.DELETE)
    public Result<Void> delete(@PathVariable Long id) {
        return caseClient.delete(id);
    }

    @Operation(summary = "获取案件详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('lawyer:case:query')")
    @OperationLog(value = "获取案件详情", type = OperationType.QUERY)
    public Result<CaseResponse> get(@PathVariable Long id) {
        return caseClient.get(id);
    }

    @Operation(summary = "获取我的案件")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('lawyer:case:query')")
    @OperationLog(value = "获取我的案件", type = OperationType.QUERY)
    public Result<PageResult<CaseResponse>> getMyCases(CasePageRequest request) {
        return caseClient.getMyCases(request);
    }

    @Operation(summary = "获取部门案件")
    @GetMapping("/department")
    @PreAuthorize("hasAuthority('lawyer:case:query')")
    @OperationLog(value = "获取部门案件", type = OperationType.QUERY)
    public Result<PageResult<CaseResponse>> getDepartmentCases(CasePageRequest request) {
        return caseClient.getDepartmentCases(request);
    }

    @Operation(summary = "添加案件进度")
    @PostMapping("/{id}/progress")
    @PreAuthorize("hasAuthority('lawyer:case:progress')")
    @OperationLog(value = "添加案件进度", type = OperationType.CREATE)
    public Result<Void> addProgress(@PathVariable Long id, @RequestBody @Validated CaseProgressCreateRequest request) {
        return caseClient.addProgress(id, request);
    }

    @Operation(summary = "获取案件进度列表")
    @GetMapping("/{id}/progress")
    @PreAuthorize("hasAuthority('lawyer:case:query')")
    @OperationLog(value = "获取案件进度列表", type = OperationType.QUERY)
    public Result<List<CaseProgressResponse>> getProgressList(@PathVariable Long id) {
        return caseClient.getProgressList(id);
    }

    @Operation(summary = "上传案件文档")
    @PostMapping("/{id}/document")
    @PreAuthorize("hasAuthority('lawyer:case:document')")
    @OperationLog(value = "上传案件文档", type = OperationType.CREATE)
    public Result<Void> uploadDocument(@PathVariable Long id, @RequestBody @Validated CaseDocumentCreateRequest request) {
        return caseClient.uploadDocument(id, request);
    }

    @Operation(summary = "获取案件文档列表")
    @GetMapping("/{id}/document")
    @PreAuthorize("hasAuthority('lawyer:case:query')")
    @OperationLog(value = "获取案件文档列表", type = OperationType.QUERY)
    public Result<List<CaseDocumentResponse>> getDocumentList(@PathVariable Long id) {
        return caseClient.getDocumentList(id);
    }

    @Operation(summary = "下载案件文档")
    @GetMapping("/document/{documentId}")
    @PreAuthorize("hasAuthority('lawyer:case:document')")
    @OperationLog(value = "下载案件文档", type = OperationType.DOWNLOAD)
    public void downloadDocument(@PathVariable Long documentId) {
        caseClient.downloadDocument(documentId);
    }

    @Operation(summary = "添加案件关联")
    @PostMapping("/{id}/relation")
    @PreAuthorize("hasAuthority('lawyer:case:relation')")
    @OperationLog(value = "添加案件关联", type = OperationType.CREATE)
    public Result<Void> addRelation(@PathVariable Long id, @RequestBody @Validated CaseRelationCreateRequest request) {
        return caseClient.addRelation(id, request);
    }

    @Operation(summary = "获取案件关联列表")
    @GetMapping("/{id}/relation")
    @PreAuthorize("hasAuthority('lawyer:case:query')")
    @OperationLog(value = "获取案件关联列表", type = OperationType.QUERY)
    public Result<List<CaseRelationResponse>> getRelationList(@PathVariable Long id) {
        return caseClient.getRelationList(id);
    }

    @Operation(summary = "删除案件关联")
    @DeleteMapping("/relation/{relationId}")
    @PreAuthorize("hasAuthority('lawyer:case:relation')")
    @OperationLog(value = "删除案件关联", type = OperationType.DELETE)
    public Result<Void> deleteRelation(@PathVariable Long relationId) {
        return caseClient.deleteRelation(relationId);
    }

    @Operation(summary = "获取案件统计")
    @GetMapping("/statistics")
    @PreAuthorize("hasAuthority('lawyer:case:query')")
    @OperationLog(value = "获取案件统计", type = OperationType.QUERY)
    public Result<CaseStatisticsResponse> getStatistics() {
        return caseClient.getStatistics();
    }

    @Operation(summary = "获取案件进度分布")
    @GetMapping("/progress/distribution")
    @PreAuthorize("hasAuthority('lawyer:case:query')")
    @OperationLog(value = "获取案件进度分布", type = OperationType.QUERY)
    public Result<List<CaseStatisticsResponse.ProgressDistribution>> getProgressDistribution() {
        return caseClient.getProgressDistribution();
    }

    @Operation(summary = "获取案件类型分布")
    @GetMapping("/type/distribution")
    @PreAuthorize("hasAuthority('lawyer:case:query')")
    @OperationLog(value = "获取案件类型分布", type = OperationType.QUERY)
    public Result<List<CaseStatisticsResponse.TypeDistribution>> getTypeDistribution() {
        return caseClient.getTypeDistribution();
    }

    @Operation(summary = "获取案件来源分布")
    @GetMapping("/source/distribution")
    @PreAuthorize("hasAuthority('lawyer:case:query')")
    @OperationLog(value = "获取案件来源分布", type = OperationType.QUERY)
    public Result<List<CaseStatisticsResponse.SourceDistribution>> getSourceDistribution() {
        return caseClient.getSourceDistribution();
    }
} 