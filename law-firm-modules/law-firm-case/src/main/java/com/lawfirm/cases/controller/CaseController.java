package com.lawfirm.cases.controller;

import com.lawfirm.model.cases.entity.Case;
import com.lawfirm.model.cases.entity.CaseFile;
import com.lawfirm.model.cases.enums.*;
import com.lawfirm.model.cases.vo.CaseDetailVO;
import com.lawfirm.model.cases.vo.CaseStatusVO;
import com.lawfirm.model.base.result.Result;
import com.lawfirm.cases.service.CaseDocumentService;
import com.lawfirm.cases.service.CaseService;
import com.lawfirm.cases.service.CaseStatusService;
import com.lawfirm.model.cases.dto.CaseCreateDTO;
import com.lawfirm.model.cases.dto.CaseUpdateDTO;
import com.lawfirm.model.cases.dto.CaseQueryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/cases")
@RequiredArgsConstructor
@Tag(name = "案件管理", description = "案件相关接口")
public class CaseController {

    private final CaseService caseService;
    private final CaseDocumentService caseFileService;
    private final CaseStatusService caseStatusService;

    @Operation(summary = "创建案件")
    @PostMapping
    @PreAuthorize("hasAuthority('case:create')")
    public Result<CaseDetailVO> createCase(@Valid @RequestBody CaseCreateDTO createDTO) {
        return Result.ok(caseService.createCase(createDTO));
    }

    @Operation(summary = "更新案件")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('case:update')")
    public Result<CaseDetailVO> updateCase(
            @Parameter(description = "案件ID") @PathVariable Long id,
            @Valid @RequestBody CaseUpdateDTO updateDTO) {
        return Result.ok(caseService.updateCase(id, updateDTO));
    }

    @Operation(summary = "删除案件")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('case:delete')")
    public Result<Void> deleteCase(@Parameter(description = "案件ID") @PathVariable Long id) {
        caseService.deleteCase(id);
        return Result.ok();
    }

    @Operation(summary = "获取案件详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('case:read')")
    public Result<CaseDetailVO> getCaseById(@Parameter(description = "案件ID") @PathVariable Long id) {
        return Result.ok(caseService.getCaseById(id));
    }

    @Operation(summary = "根据案号获取案件")
    @GetMapping("/number/{caseNumber}")
    @PreAuthorize("hasAuthority('case:read')")
    public Result<CaseDetailVO> getCaseByCaseNumber(
            @Parameter(description = "案号") @PathVariable String caseNumber) {
        return Result.ok(caseService.getCaseByCaseNumber(caseNumber));
    }

    @Operation(summary = "查询案件列表")
    @GetMapping
    @PreAuthorize("hasAuthority('case:read')")
    public Result<Page<CaseDetailVO>> findCases(
            @Parameter(description = "查询条件") CaseQueryDTO queryDTO,
            @Parameter(description = "分页参数") Pageable pageable) {
        return Result.ok(caseService.findCases(queryDTO, pageable));
    }

    @Operation(summary = "获取案件状态统计")
    @GetMapping("/statistics/status")
    public Result<Map<CaseStatusEnum, Long>> getCaseStatistics() {
        return Result.ok(caseService.getCaseStatistics());
    }

    @Operation(summary = "更新案件状态")
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam String status, @RequestParam String reason, @RequestParam String remark) {
        caseService.updateStatus(id, status, reason, remark);
        return Result.ok();
    }

    @Operation(summary = "获取案件进展")
    @GetMapping("/{id}/progress")
    public Result<CaseProgressEnum> getCurrentProgress(@PathVariable Long id) {
        return Result.ok(caseService.getCurrentProgress(id));
    }

    @Operation(summary = "获取案件状态")
    @GetMapping("/{id}/status")
    public Result<CaseStatusEnum> getCurrentStatus(@PathVariable Long id) {
        return Result.ok(caseService.getCurrentStatus(id));
    }

    @Operation(summary = "获取当前律师的案件")
    @GetMapping("/my")
    public Result<List<CaseDetailVO>> findByCurrentLawyer() {
        return Result.ok(caseService.findByCurrentLawyer());
    }

    @Operation(summary = "获取客户的案件")
    @GetMapping("/client/{clientId}")
    public Result<List<CaseDetailVO>> findByClient(@PathVariable Long clientId, CaseQueryDTO queryDTO) {
        return Result.ok(caseService.findByClient(clientId, queryDTO));
    }

    @PostMapping("/{id}/files")
    @Operation(summary = "上传案件文件")
    public Result<CaseFile> uploadFile(
            @PathVariable Long id, 
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "false") Boolean isConfidential) {
        return Result.ok(caseFileService.uploadFile(id, file, description, isConfidential));
    }

    @GetMapping("/{id}/files")
    @Operation(summary = "获取案件文件列表")
    public Result<List<CaseFile>> getCaseFiles(@PathVariable Long id) {
        return Result.ok(caseFileService.getCaseFiles(id));
    }

    @GetMapping("/{id}/files/confidential")
    @Operation(summary = "获取案件保密文件列表")
    public Result<List<CaseFile>> getConfidentialFiles(@PathVariable Long id) {
        return Result.ok(caseFileService.getConfidentialFiles(id));
    }

    @GetMapping("/files/{fileId}")
    @Operation(summary = "下载案件文件")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId) {
        CaseFile caseFile = caseFileService.getFile(fileId);
        byte[] content = caseFileService.downloadFile(fileId);
        ByteArrayResource resource = new ByteArrayResource(content);
        
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + caseFile.getFileName())
                .contentType(MediaType.parseMediaType(caseFile.getContentType()))
                .contentLength(caseFile.getFileSize())
                .body(resource);
    }

    @Operation(summary = "更新案件进展")
    @PutMapping("/{id}/progress")
    public Result<CaseDetailVO> updateProgress(@PathVariable Long id, @RequestParam CaseProgressEnum progress, @RequestParam String remark) {
        return Result.ok(caseService.updateProgress(id, progress, remark));
    }

    @Operation(summary = "获取可用进展状态")
    @GetMapping("/{id}/available-progress")
    @PreAuthorize("hasAuthority('case:read')")
    public Result<List<CaseProgressEnum>> getAvailableProgress(
            @Parameter(description = "案件ID") @PathVariable Long id) {
        return Result.ok(caseService.getAvailableProgress(id));
    }

    @Operation(summary = "更新案件办理方式")
    @PutMapping("/{id}/handle-type")
    public Result<CaseDetailVO> updateHandleType(@PathVariable Long id, @RequestParam CaseHandleTypeEnum handleType, @RequestParam String remark) {
        return Result.ok(caseService.updateHandleType(id, handleType, remark));
    }

    @Operation(summary = "更新案件难度")
    @PutMapping("/{id}/difficulty")
    public Result<CaseDetailVO> updateDifficulty(@PathVariable Long id, @RequestParam CaseDifficultyEnum difficulty, @RequestParam String remark) {
        return Result.ok(caseService.updateDifficulty(id, difficulty, remark));
    }

    @Operation(summary = "更新案件重要程度")
    @PutMapping("/{id}/importance")
    public Result<CaseDetailVO> updateImportance(@PathVariable Long id, @RequestParam CaseImportanceEnum importance, @RequestParam String remark) {
        return Result.ok(caseService.updateImportance(id, importance, remark));
    }

    @Operation(summary = "更新案件优先级")
    @PutMapping("/{id}/priority")
    public Result<CaseDetailVO> updatePriority(@PathVariable Long id, @RequestParam CasePriorityEnum priority, @RequestParam String remark) {
        return Result.ok(caseService.updatePriority(id, priority, remark));
    }

    @Operation(summary = "更新案件收费方式")
    @PutMapping("/{id}/fee-type")
    public Result<CaseDetailVO> updateFeeType(@PathVariable Long id, @RequestParam CaseFeeTypeEnum feeType, @RequestParam String remark) {
        return Result.ok(caseService.updateFeeType(id, feeType, remark));
    }

    @Operation(summary = "更新案件来源")
    @PutMapping("/{id}/source")
    public Result<CaseDetailVO> updateSource(@PathVariable Long id, @RequestParam CaseSourceEnum source, @RequestParam String remark) {
        return Result.ok(caseService.updateSource(id, source, remark));
    }

    @Operation(summary = "按进展状态查询案件")
    @GetMapping("/by-progress/{progress}")
    @PreAuthorize("hasAuthority('case:read')")
    public Result<List<CaseDetailVO>> findCasesByProgress(
            @Parameter(description = "进展状态") @PathVariable CaseProgressEnum progress) {
        return Result.ok(caseService.findCasesByProgress(progress));
    }

    @Operation(summary = "按办理方式查询案件")
    @GetMapping("/by-handle-type/{handleType}")
    @PreAuthorize("hasAuthority('case:read')")
    public Result<List<CaseDetailVO>> findCasesByHandleType(
            @Parameter(description = "办理方式") @PathVariable CaseHandleTypeEnum handleType) {
        return Result.ok(caseService.findCasesByHandleType(handleType));
    }

    @Operation(summary = "按难度等级查询案件")
    @GetMapping("/by-difficulty/{difficulty}")
    @PreAuthorize("hasAuthority('case:read')")
    public Result<List<CaseDetailVO>> findCasesByDifficulty(
            @Parameter(description = "难度等级") @PathVariable CaseDifficultyEnum difficulty) {
        return Result.ok(caseService.findCasesByDifficulty(difficulty));
    }

    @Operation(summary = "按重要程度查询案件")
    @GetMapping("/by-importance/{importance}")
    @PreAuthorize("hasAuthority('case:read')")
    public Result<List<CaseDetailVO>> findCasesByImportance(
            @Parameter(description = "重要程度") @PathVariable CaseImportanceEnum importance) {
        return Result.ok(caseService.findCasesByImportance(importance));
    }

    @Operation(summary = "获取进展统计")
    @GetMapping("/statistics/progress")
    public Result<Map<CaseProgressEnum, Long>> getProgressStatistics() {
        return Result.ok(caseService.getProgressStatistics());
    }

    @Operation(summary = "获取办理方式统计")
    @GetMapping("/statistics/handle-type")
    public Result<Map<CaseHandleTypeEnum, Long>> getHandleTypeStatistics() {
        return Result.ok(caseService.getHandleTypeStatistics());
    }

    @Operation(summary = "获取难度统计")
    @GetMapping("/statistics/difficulty")
    public Result<Map<CaseDifficultyEnum, Long>> getDifficultyStatistics() {
        return Result.ok(caseService.getDifficultyStatistics());
    }

    @Operation(summary = "获取重要程度统计")
    @GetMapping("/statistics/importance")
    public Result<Map<CaseImportanceEnum, Long>> getImportanceStatistics() {
        return Result.ok(caseService.getImportanceStatistics());
    }

    @Operation(summary = "获取优先级统计")
    @GetMapping("/statistics/priority")
    public Result<Map<CasePriorityEnum, Long>> getPriorityStatistics() {
        return Result.ok(caseService.getPriorityStatistics());
    }

    @Operation(summary = "获取收费方式统计")
    @GetMapping("/statistics/fee-type")
    public Result<Map<CaseFeeTypeEnum, Long>> getFeeTypeStatistics() {
        return Result.ok(caseService.getFeeTypeStatistics());
    }

    @Operation(summary = "获取案件来源统计")
    @GetMapping("/statistics/source")
    public Result<Map<CaseSourceEnum, Long>> getSourceStatistics() {
        return Result.ok(caseService.getSourceStatistics());
    }

    private CaseDetailVO convertToDetailVO(Case caseEntity) {
        // TODO: 实现转换逻辑
        CaseDetailVO vo = new CaseDetailVO();
        vo.setId(caseEntity.getId());
        vo.setCaseNumber(caseEntity.getCaseNumber());
        // ... 设置其他属性
        return vo;
    }
} 