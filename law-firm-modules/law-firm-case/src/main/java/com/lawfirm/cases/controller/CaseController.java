package com.lawfirm.cases.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.cases.constant.CaseBusinessConstants;
import com.lawfirm.model.cases.dto.base.CaseCreateDTO;
import com.lawfirm.model.cases.dto.base.CaseQueryDTO;
import com.lawfirm.model.cases.dto.base.CaseUpdateDTO;
import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.service.base.CaseService;
import com.lawfirm.model.cases.vo.base.CaseDetailVO;
import com.lawfirm.model.cases.vo.base.CaseQueryVO;
import com.lawfirm.cases.service.CaseEvidenceBizService;
import com.lawfirm.model.evidence.vo.EvidenceVO;
import com.lawfirm.model.evidence.dto.EvidenceDTO;
import com.lawfirm.model.evidence.dto.EvidenceReviewDTO;
import com.lawfirm.model.evidence.vo.EvidenceReviewVO;
import com.lawfirm.model.evidence.dto.EvidenceTraceDTO;
import com.lawfirm.model.evidence.vo.EvidenceTraceVO;
import com.lawfirm.model.evidence.dto.EvidenceAttachmentDTO;
import com.lawfirm.model.evidence.vo.EvidenceAttachmentVO;
import com.lawfirm.model.evidence.dto.EvidenceTagRelationDTO;
import com.lawfirm.model.evidence.vo.EvidenceTagRelationVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.lawfirm.model.auth.annotation.RequiresPermission;
import com.lawfirm.model.auth.enums.OperationTypeEnum;
import com.lawfirm.model.auth.enums.ModuleTypeEnum;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;
import com.lawfirm.cases.core.ai.CaseAIManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 案件基本控制器
 */
@Tag(name = "案件管理", description = "案件管理接口")
@RestController("caseController")
@RequestMapping(CaseBusinessConstants.Controller.API_PREFIX)
@RequiredArgsConstructor
public class CaseController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CaseController.class);

    private final CaseService caseService;
    @Autowired
    private CaseEvidenceBizService caseEvidenceBizService;
    @Autowired
    private CaseAIManager caseAIManager;

    @Operation(
        summary = "创建案件",
        description = "创建新的案件记录，包括案件基本信息（案件名称、案由、受理法院等）、当事人信息（原告、被告等）、代理律师、收费信息等"
    )
    @PostMapping
    @PreAuthorize(CASE_CREATE)
    public Long createCase(
            @Parameter(description = "案件创建参数，包括：\n" +
                    "1. 案件基本信息：案件名称、案由、受理法院、案件类型等\n" +
                    "2. 当事人信息：原告、被告的基本信息和联系方式\n" +
                    "3. 代理信息：代理律师、协办律师等\n" +
                    "4. 收费信息：收费方式、预计收费等") 
            @RequestBody @Validated CaseCreateDTO createDTO) {
        log.info("创建案件: {}", createDTO);
        return caseService.createCase(createDTO);
    }

    @Operation(
        summary = "更新案件",
        description = "更新已有案件的基本信息，包括案件进展情况、当事人信息变更、代理律师调整等，注意已归档的案件不能更新"
    )
    @PutMapping("/{id}")
    @PreAuthorize(CASE_EDIT)
    public boolean updateCase(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id, 
            @Parameter(description = "案件更新参数，包括：\n" +
                    "1. 案件基本信息：案件进展、最新状态等\n" +
                    "2. 当事人信息：联系方式变更等\n" +
                    "3. 代理信息：代理律师变更等") 
            @RequestBody @Validated CaseUpdateDTO updateDTO) {
        log.info("更新案件: id={}, {}", id, updateDTO);
        updateDTO.setId(id);
        return caseService.updateCase(updateDTO);
    }

    @Operation(
        summary = "删除案件",
        description = "删除指定的案件记录。注意：\n" +
                "1. 已关联文档、费用等数据的案件不能删除\n" +
                "2. 已开始处理的案件不能删除\n" +
                "3. 已归档的案件不能删除"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize(CASE_DELETE)
    public boolean deleteCase(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id) {
        log.info("删除案件: {}", id);
        return caseService.deleteCase(id);
    }

    @Operation(
        summary = "获取案件详情",
        description = "获取案件的详细信息，包括：\n" +
                "1. 案件基本信息：案件名称、案由、受理法院等\n" +
                "2. 当事人信息：原告、被告信息\n" +
                "3. 代理信息：代理律师、协办律师\n" +
                "4. 案件进度：当前状态、重要节点\n" +
                "5. 相关统计：文档数量、费用总额等"
    )
    @GetMapping("/{id}")
    @PreAuthorize(CASE_VIEW)
    public CaseDetailVO getCaseDetail(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id) {
        log.info("获取案件详情: {}", id);
        return caseService.getCaseDetail(id);
    }

    @Operation(
        summary = "分页查询案件",
        description = "根据条件分页查询案件列表，支持多个维度的筛选条件：\n" +
                "1. 案件信息：案件名称、案由、案件类型等\n" +
                "2. 当事人信息：当事人名称等\n" +
                "3. 代理信息：代理律师等\n" +
                "4. 案件状态：在办、归档等\n" +
                "5. 时间范围：立案时间、归档时间等"
    )
    @GetMapping
    @PreAuthorize(CASE_VIEW)
    public IPage<CaseQueryVO> pageCases(
            @Parameter(description = "查询参数，包括：\n" +
                    "1. 分页参数：页码、每页大小\n" +
                    "2. 排序参数：排序字段、排序方式\n" +
                    "3. 筛选条件：案件类型、状态、时间范围等") 
            @Validated CaseQueryDTO queryDTO) {
        log.info("分页查询案件: {}", queryDTO);
        return caseService.pageCases(queryDTO);
    }

    @Operation(
        summary = "变更案件状态",
        description = "变更案件的状态，需要提供充分的变更原因。各状态说明：\n" +
                "1. 新建：案件刚创建，未开始处理\n" +
                "2. 进行中：案件正在积极处理中\n" +
                "3. 暂停：因特殊原因暂时停止处理\n" +
                "4. 终止：案件非正常结束\n" +
                "5. 完成：案件已正常办结"
    )
    @PutMapping("/{id}/status")
    @PreAuthorize(CASE_EDIT)
    public boolean changeStatus(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id,
            @Parameter(description = "目标状态：1-新建，2-进行中，3-暂停，4-终止，5-完成") 
            @RequestParam Integer targetStatus,
            @Parameter(description = "变更原因，需要详细说明状态变更的具体原因") 
            @RequestParam(required = false) String reason) {
        log.info("变更案件状态: id={}, targetStatus={}, reason={}", id, targetStatus, reason);
        return caseService.changeStatus(id, targetStatus, reason);
    }

    @Operation(
        summary = "审批案件状态变更",
        description = "对案件状态变更申请进行审批。注意：\n" +
                "1. 只有特定状态变更才需要审批\n" +
                "2. 审批时需要提供明确的审批意见\n" +
                "3. 审批通过后状态才会实际变更"
    )
    @PutMapping("/{id}/status/approve")
    @PreAuthorize(CASE_APPROVE)
    public boolean approveStatusChange(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id,
            @Parameter(description = "是否通过：true-通过，false-拒绝") 
            @RequestParam boolean approved,
            @Parameter(description = "审批意见，需要详细说明通过或拒绝的具体理由") 
            @RequestParam(required = false) String opinion) {
        log.info("审批案件状态变更: id={}, approved={}, opinion={}", id, approved, opinion);
        return caseService.approveStatusChange(id, approved, opinion);
    }

    @Operation(
        summary = "归档案件",
        description = "将已完成的案件进行归档。归档要求：\n" +
                "1. 案件状态必须是'完成'\n" +
                "2. 所有必要文档都已上传\n" +
                "3. 所有费用都已结清\n" +
                "4. 归档后案件信息将锁定，不能修改"
    )
    @PutMapping("/{id}/archive")
    @PreAuthorize(CASE_ARCHIVE)
    public boolean archiveCase(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id) {
        log.info("归档案件: {}", id);
        return caseService.archiveCase(id);
    }

    @Operation(
        summary = "重新激活案件",
        description = "重新激活已归档的案件。激活要求：\n" +
                "1. 案件必须是已归档状态\n" +
                "2. 需要提供充分的激活原因\n" +
                "3. 激活后案件状态将恢复为'进行中'"
    )
    @PutMapping("/{id}/reactivate")
    @PreAuthorize(CASE_EDIT)
    public boolean reactivateCase(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id,
            @Parameter(description = "激活原因，需要详细说明重新激活案件的具体原因") 
            @RequestParam String reason) {
        log.info("重新激活案件: id={}, reason={}", id, reason);
        return caseService.reactivateCase(id, reason);
    }

    @Operation(
        summary = "获取用户相关案件",
        description = "获取指定用户参与的所有案件列表。包括用户作为：\n" +
                "1. 主办律师的案件\n" +
                "2. 协办律师的案件\n" +
                "3. 案件审批人的案件\n"
    )
    @GetMapping("/user/{userId}")
    @PreAuthorize(CASE_VIEW)
    public List<CaseQueryVO> getUserCases(
            @Parameter(description = "用户ID") 
            @PathVariable("userId") Long userId) {
        log.info("获取用户相关案件: {}", userId);
        List<Case> cases = caseService.getUserCases(userId);
        return cases.stream().map(this::convertToCaseQueryVO).collect(Collectors.toList());
    }

    @Operation(
        summary = "评估案件风险",
        description = "对案件进行AI风险评估，分析潜在风险点和建议：\n" +
                "1. 案件关键信息分析\n" +
                "2. 潜在法律风险评估\n" +
                "3. 案件进展建议\n" +
                "4. 相似案例参考"
    )
    @GetMapping("/{id}/risk-assessment")
    @PreAuthorize(CASE_VIEW)
    public Map<String, Object> assessCaseRisk(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id) {
        log.info("评估案件风险: {}", id);
        return caseService.assessCaseRisk(id);
    }

    /**
     * 查询案件下所有证据
     */
    @GetMapping("/{caseId}/evidences")
    public List<EvidenceVO> getCaseEvidences(@PathVariable Long caseId) {
        return caseEvidenceBizService.getEvidenceByCaseId(caseId);
    }

    @Operation(summary = "查询案件下单个证据详情")
    @GetMapping("/{caseId}/evidences/{evidenceId}")
    public EvidenceVO getCaseEvidenceDetail(@PathVariable Long caseId, @PathVariable Long evidenceId) {
        EvidenceVO vo = caseEvidenceBizService.getEvidenceDetail(evidenceId);
        if (vo == null || !caseId.equals(vo.getCaseId())) {
            throw new IllegalArgumentException("证据不存在或不属于该案件");
        }
        return vo;
    }

    @Operation(summary = "为案件新增证据")
    @PostMapping("/{caseId}/evidences")
    public ResponseEntity<Long> addEvidence(@PathVariable Long caseId, @RequestBody EvidenceDTO dto) {
        dto.setCaseId(caseId);
        Long evidenceId = caseEvidenceBizService.addEvidence(dto);
        return new ResponseEntity<>(evidenceId, HttpStatus.CREATED);
    }

    @Operation(summary = "删除案件下的证据")
    @DeleteMapping("/{caseId}/evidences/{evidenceId}")
    public ResponseEntity<Void> deleteEvidence(@PathVariable Long caseId, @PathVariable Long evidenceId) {
        EvidenceVO vo = caseEvidenceBizService.getEvidenceDetail(evidenceId);
        if (vo == null || !caseId.equals(vo.getCaseId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        caseEvidenceBizService.deleteEvidence(evidenceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "为证据添加审核记录")
    @PostMapping("/{caseId}/evidences/{evidenceId}/reviews")
    public ResponseEntity<Long> addEvidenceReview(@PathVariable Long caseId, @PathVariable Long evidenceId, @RequestBody EvidenceReviewDTO dto) {
        dto.setEvidenceId(evidenceId);
        Long reviewId = caseEvidenceBizService.addEvidenceReview(dto);
        return new ResponseEntity<>(reviewId, HttpStatus.CREATED);
    }

    @Operation(summary = "查询证据的所有审核记录")
    @GetMapping("/{caseId}/evidences/{evidenceId}/reviews")
    public List<EvidenceReviewVO> listEvidenceReviews(@PathVariable Long caseId, @PathVariable Long evidenceId) {
        EvidenceVO vo = caseEvidenceBizService.getEvidenceDetail(evidenceId);
        if (vo == null || !caseId.equals(vo.getCaseId())) {
            throw new IllegalArgumentException("证据不存在或不属于该案件");
        }
        return caseEvidenceBizService.listEvidenceReviews(evidenceId);
    }

    @Operation(summary = "添加证据流转记录")
    @PostMapping("/{caseId}/evidences/{evidenceId}/traces")
    public ResponseEntity<Long> addEvidenceTrace(@PathVariable Long caseId, @PathVariable Long evidenceId, @RequestBody EvidenceTraceDTO dto) {
        dto.setEvidenceId(evidenceId);
        Long traceId = caseEvidenceBizService.addEvidenceTrace(dto);
        return new ResponseEntity<>(traceId, HttpStatus.CREATED);
    }

    @Operation(summary = "查询证据流转记录")
    @GetMapping("/{caseId}/evidences/{evidenceId}/traces")
    public List<EvidenceTraceVO> listEvidenceTraces(@PathVariable Long caseId, @PathVariable Long evidenceId) {
        EvidenceVO vo = caseEvidenceBizService.getEvidenceDetail(evidenceId);
        if (vo == null || !caseId.equals(vo.getCaseId())) {
            throw new IllegalArgumentException("证据不存在或不属于该案件");
        }
        return caseEvidenceBizService.listEvidenceTraces(evidenceId);
    }

    @Operation(summary = "为证据添加附件")
    @PostMapping("/{caseId}/evidences/{evidenceId}/attachments")
    public ResponseEntity<Long> addEvidenceAttachment(@PathVariable Long caseId, @PathVariable Long evidenceId, @RequestBody EvidenceAttachmentDTO dto) {
        dto.setEvidenceId(evidenceId);
        Long attachmentId = caseEvidenceBizService.addEvidenceAttachment(dto);
        return new ResponseEntity<>(attachmentId, HttpStatus.CREATED);
    }

    @Operation(summary = "删除证据附件")
    @DeleteMapping("/{caseId}/evidences/{evidenceId}/attachments/{attachmentId}")
    public ResponseEntity<Void> deleteEvidenceAttachment(@PathVariable Long caseId, @PathVariable Long evidenceId, @PathVariable Long attachmentId) {
        caseEvidenceBizService.deleteEvidenceAttachment(attachmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "查询证据所有附件")
    @GetMapping("/{caseId}/evidences/{evidenceId}/attachments")
    public List<EvidenceAttachmentVO> listEvidenceAttachments(@PathVariable Long caseId, @PathVariable Long evidenceId) {
        EvidenceVO vo = caseEvidenceBizService.getEvidenceDetail(evidenceId);
        if (vo == null || !caseId.equals(vo.getCaseId())) {
            throw new IllegalArgumentException("证据不存在或不属于该案件");
        }
        return caseEvidenceBizService.listEvidenceAttachments(evidenceId);
    }

    @Operation(summary = "为证据添加标签")
    @PostMapping("/{caseId}/evidences/{evidenceId}/tags")
    public ResponseEntity<Long> addEvidenceTag(@PathVariable Long caseId, @PathVariable Long evidenceId, @RequestBody EvidenceTagRelationDTO dto) {
        dto.setEvidenceId(evidenceId);
        Long tagRelationId = caseEvidenceBizService.addEvidenceTag(dto);
        return new ResponseEntity<>(tagRelationId, HttpStatus.CREATED);
    }

    @Operation(summary = "删除证据标签")
    @DeleteMapping("/{caseId}/evidences/{evidenceId}/tags/{tagRelationId}")
    public ResponseEntity<Void> deleteEvidenceTag(@PathVariable Long caseId, @PathVariable Long evidenceId, @PathVariable Long tagRelationId) {
        caseEvidenceBizService.deleteEvidenceTag(tagRelationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "查询证据所有标签")
    @GetMapping("/{caseId}/evidences/{evidenceId}/tags")
    public List<EvidenceTagRelationVO> listEvidenceTags(@PathVariable Long caseId, @PathVariable Long evidenceId) {
        EvidenceVO vo = caseEvidenceBizService.getEvidenceDetail(evidenceId);
        if (vo == null || !caseId.equals(vo.getCaseId())) {
            throw new IllegalArgumentException("证据不存在或不属于该案件");
        }
        return caseEvidenceBizService.listEvidenceTags(evidenceId);
    }

    @Operation(summary = "批量删除案件下的证据")
    @DeleteMapping("/{caseId}/evidences/batch-delete")
    @PreAuthorize(EVIDENCE_DELETE)
    public ResponseEntity<Void> batchDeleteEvidence(@PathVariable Long caseId, @RequestBody List<Long> evidenceIds) {
        for (Long evidenceId : evidenceIds) {
            EvidenceVO vo = caseEvidenceBizService.getEvidenceDetail(evidenceId);
            if (vo == null || !caseId.equals(vo.getCaseId())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        caseEvidenceBizService.batchDeleteEvidence(evidenceIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "批量新增案件下证据")
    @PostMapping("/{caseId}/evidences/batch-add")
    @PreAuthorize(EVIDENCE_CREATE)
    public ResponseEntity<List<Long>> batchAddEvidence(@PathVariable Long caseId, @RequestBody List<EvidenceDTO> dtos) {
        for (EvidenceDTO dto : dtos) {
            dto.setCaseId(caseId);
        }
        List<Long> ids = caseEvidenceBizService.batchAddEvidence(dtos);
        return new ResponseEntity<>(ids, HttpStatus.CREATED);
    }

    @Operation(summary = "批量归档案件下证据")
    @PostMapping("/{caseId}/evidences/batch-archive")
    @PreAuthorize(EVIDENCE_ARCHIVE)
    public ResponseEntity<Void> batchArchiveEvidence(@PathVariable Long caseId, @RequestBody List<Long> evidenceIds) {
        for (Long evidenceId : evidenceIds) {
            EvidenceVO vo = caseEvidenceBizService.getEvidenceDetail(evidenceId);
            if (vo == null || !caseId.equals(vo.getCaseId())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        caseEvidenceBizService.batchArchiveEvidence(evidenceIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "批量为证据添加标签")
    @PostMapping("/{caseId}/evidences/{evidenceId}/tags/batch-add")
    @PreAuthorize(EVIDENCE_TAG_ADD)
    public ResponseEntity<Void> batchAddEvidenceTags(@PathVariable Long caseId, @PathVariable Long evidenceId, @RequestBody List<EvidenceTagRelationDTO> dtos) {
        for (EvidenceTagRelationDTO dto : dtos) {
            dto.setEvidenceId(evidenceId);
        }
        caseEvidenceBizService.batchAddEvidenceTags(dtos);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "批量删除证据标签")
    @DeleteMapping("/{caseId}/evidences/{evidenceId}/tags/batch-delete")
    @PreAuthorize(EVIDENCE_TAG_DELETE)
    public ResponseEntity<Void> batchDeleteEvidenceTags(@PathVariable Long caseId, @PathVariable Long evidenceId, @RequestBody List<Long> tagRelationIds) {
        caseEvidenceBizService.batchDeleteEvidenceTags(tagRelationIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "批量导出案件下证据")
    @PostMapping("/{caseId}/evidences/batch-export")
    @PreAuthorize(EVIDENCE_EXPORT)
    public ResponseEntity<byte[]> batchExportEvidence(@PathVariable Long caseId, @RequestBody List<Long> evidenceIds) {
        for (Long evidenceId : evidenceIds) {
            EvidenceVO vo = caseEvidenceBizService.getEvidenceDetail(evidenceId);
            if (vo == null || !caseId.equals(vo.getCaseId())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        byte[] fileBytes = caseEvidenceBizService.exportEvidenceBatch(evidenceIds);
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=evidence_export.xlsx")
            .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
            .body(fileBytes);
    }

    @Operation(summary = "批量导出案件下证据（PDF）")
    @PostMapping("/{caseId}/evidences/batch-export-pdf")
    @PreAuthorize(EVIDENCE_EXPORT)
    public ResponseEntity<byte[]> batchExportEvidencePdf(@PathVariable Long caseId, @RequestBody List<Long> evidenceIds) {
        for (Long evidenceId : evidenceIds) {
            EvidenceVO vo = caseEvidenceBizService.getEvidenceDetail(evidenceId);
            if (vo == null || !caseId.equals(vo.getCaseId())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        byte[] fileBytes = caseEvidenceBizService.exportEvidenceBatchPdf(evidenceIds);
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=evidence_export.pdf")
            .header("Content-Type", "application/pdf")
            .body(fileBytes);
    }

    @Operation(summary = "AI要素抽取", description = "对案件描述进行AI要素抽取")
    @GetMapping("/{id}/ai-extract")
    @PreAuthorize(CASE_VIEW)
    public Map<String, Object> aiExtract(@Parameter(description = "案件ID") @PathVariable Long id) {
        return caseService.extractCaseElements(id);
    }

    @Operation(summary = "AI自动摘要", description = "对案件描述进行AI自动摘要")
    @GetMapping("/{id}/ai-summary")
    @PreAuthorize(CASE_VIEW)
    public String aiSummary(@Parameter(description = "案件ID") @PathVariable Long id) {
        return caseService.summarizeCase(id);
    }

    @Operation(summary = "AI自动生成案件文书", description = "根据案件信息自动生成指定类型的法律文书")
    @GetMapping("/{id}/ai-generate-doc")
    @PreAuthorize(CASE_VIEW)
    public String aiGenerateDoc(@Parameter(description = "案件ID") @PathVariable Long id,
                                @Parameter(description = "文书类型，如起诉状/答辩状/委托书等") @RequestParam String type) {
        return caseService.generateCaseDocument(id, type);
    }

    /**
     * AI案件自动生成文书（支持多类型、风格、要素补全、批量生成）
     */
    @PostMapping("/ai/generate-document")
    @io.swagger.v3.oas.annotations.Operation(summary = "AI自动生成案件文书（多类型/风格/批量）")
    public org.springframework.http.ResponseEntity<?> aiGenerateLegalDocument(@RequestBody java.util.Map<String, Object> body) {
        String docType = (String) body.get("docType");
        @SuppressWarnings("unchecked")
        java.util.Map<String, Object> elements = (java.util.Map<String, Object>) body.get("elements");
        String style = (String) body.get("style");
        boolean batch = body.get("batch") != null && (Boolean) body.get("batch");
        @SuppressWarnings("unchecked")
        java.util.List<java.util.Map<String, Object>> batchList = (java.util.List<java.util.Map<String, Object>>) body.get("batchList");
        return org.springframework.http.ResponseEntity.ok(caseAIManager.generateLegalDocument(docType, elements, style, batch, batchList));
    }

    /**
     * AI案件摘要
     */
    @PostMapping("/ai/summary")
    @io.swagger.v3.oas.annotations.Operation(summary = "AI案件摘要")
    public org.springframework.http.ResponseEntity<String> aiCaseSummary(@RequestBody java.util.Map<String, Object> body) {
        String content = (String) body.get("content");
        Integer maxLength = body.get("maxLength") != null ? (Integer) body.get("maxLength") : 200;
        return org.springframework.http.ResponseEntity.ok(caseAIManager.generateDocumentSummary(content, maxLength));
    }

    /**
     * AI案件查重/相似度分析
     */
    @PostMapping("/ai/similarity")
    @io.swagger.v3.oas.annotations.Operation(summary = "AI案件查重/相似度分析")
    public org.springframework.http.ResponseEntity<java.util.List<java.util.Map<String, Object>>> aiCaseSimilarity(@RequestBody java.util.Map<String, Object> body) {
        String description = (String) body.get("description");
        Integer limit = body.get("limit") != null ? (Integer) body.get("limit") : 5;
        return org.springframework.http.ResponseEntity.ok(caseAIManager.getSimilarCaseRecommendations(description, limit));
    }

    /**
     * AI案件要素抽取
     */
    @PostMapping("/ai/extract")
    @io.swagger.v3.oas.annotations.Operation(summary = "AI案件要素抽取")
    public org.springframework.http.ResponseEntity<java.util.Map<String, Object>> aiCaseExtract(@RequestBody java.util.Map<String, Object> body) {
        String content = (String) body.get("content");
        return org.springframework.http.ResponseEntity.ok(caseAIManager.extractCaseKeyElements(content));
    }

    /**
     * AI案件风险评估
     */
    @PostMapping("/ai/risk")
    @io.swagger.v3.oas.annotations.Operation(summary = "AI案件风险评估")
    public org.springframework.http.ResponseEntity<java.util.Map<String, Object>> aiCaseRisk(@RequestBody java.util.Map<String, Object> body) {
        Long caseId = body.get("caseId") != null ? Long.valueOf(body.get("caseId").toString()) : null;
        @SuppressWarnings("unchecked")
        java.util.Map<String, Object> caseData = (java.util.Map<String, Object>) body.get("caseData");
        return org.springframework.http.ResponseEntity.ok(caseAIManager.getCaseRiskAssessment(caseId, caseData));
    }

    /**
     * AI案件智能问答
     */
    @PostMapping("/ai/qa")
    @io.swagger.v3.oas.annotations.Operation(summary = "AI案件智能问答")
    public org.springframework.http.ResponseEntity<String> aiCaseQA(@RequestBody java.util.Map<String, Object> body) {
        // 复用文档问答能力或后续扩展
        // 这里假设有caseAIManager.caseQA(question, content)
        String question = (String) body.get("question");
        String content = (String) body.get("content");
        // 若caseAIManager未实现caseQA，可调用generateDocumentSummary等通用能力
        return org.springframework.http.ResponseEntity.ok("暂未实现专用案件问答，可扩展");
    }

    /**
     * AI相似案例推荐
     */
    @PostMapping("/ai/recommend")
    @io.swagger.v3.oas.annotations.Operation(summary = "AI相似案例推荐")
    public org.springframework.http.ResponseEntity<java.util.List<java.util.Map<String, Object>>> aiCaseRecommend(@RequestBody java.util.Map<String, Object> body) {
        String description = (String) body.get("description");
        Integer limit = body.get("limit") != null ? (Integer) body.get("limit") : 5;
        return org.springframework.http.ResponseEntity.ok(caseAIManager.getSimilarCaseRecommendations(description, limit));
    }

    /**
     * AI案件文书纠错与润色
     */
    @PostMapping("/ai/proofread")
    @io.swagger.v3.oas.annotations.Operation(summary = "AI案件文书纠错与润色")
    public org.springframework.http.ResponseEntity<String> aiCaseProofread(@RequestBody java.util.Map<String, Object> body) {
        // 这里假设有caseAIManager.proofreadAndPolish(content)
        String content = (String) body.get("content");
        return org.springframework.http.ResponseEntity.ok("暂未实现专用案件文书纠错润色，可扩展");
    }

    /**
     * 将Case实体转换为CaseQueryVO
     *
     * @param caseEntity 案件实体
     * @return CaseQueryVO
     */
    private CaseQueryVO convertToCaseQueryVO(Case caseEntity) {
        CaseQueryVO vo = new CaseQueryVO();
        BeanUtils.copyProperties(caseEntity, vo);
        return vo;
    }
} 