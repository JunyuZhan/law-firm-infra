package com.lawfirm.evidence.controller;

import com.lawfirm.model.evidence.dto.EvidenceReviewDTO;
import com.lawfirm.model.evidence.vo.EvidenceReviewVO;
import com.lawfirm.model.evidence.service.EvidenceReviewService;
import com.lawfirm.evidence.constant.EvidenceConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.access.prepost.PreAuthorize;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;

@Tag(name = "证据审核管理", description = "证据审核相关接口")
@RestController("evidenceReviewController")
@RequestMapping(EvidenceConstants.API_PREFIX + "/{evidenceId}/reviews")
public class EvidenceReviewController {
    @Autowired
    @Qualifier("evidenceReviewService")
    private EvidenceReviewService evidenceReviewService;

    @Operation(summary = "新增证据审核", description = "为指定证据新增审核记录")
    @PostMapping
    @PreAuthorize(EVIDENCE_CREATE)
    public Long add(@Parameter(description = "证据ID") @PathVariable Long evidenceId, @Parameter(description = "审核信息DTO") @RequestBody EvidenceReviewDTO dto) {
        dto.setEvidenceId(evidenceId);
        return evidenceReviewService.addReview(dto);
    }

    @Operation(summary = "更新证据审核", description = "更新指定证据的审核信息")
    @PutMapping
    @PreAuthorize(EVIDENCE_EDIT)
    public void update(@Parameter(description = "审核信息DTO") @RequestBody EvidenceReviewDTO dto) {
        evidenceReviewService.updateReview(dto);
    }

    @Operation(summary = "删除证据审核", description = "根据审核ID删除证据审核记录")
    @DeleteMapping("/{id}")
    @PreAuthorize(EVIDENCE_DELETE)
    public void delete(@Parameter(description = "审核ID") @PathVariable Long id) {
        evidenceReviewService.deleteReview(id);
    }

    @Operation(summary = "获取证据审核详情", description = "根据审核ID获取证据审核的详细信息")
    @GetMapping("/{id}")
    @PreAuthorize(EVIDENCE_VIEW)
    public EvidenceReviewVO get(@Parameter(description = "审核ID") @PathVariable Long id) {
        return evidenceReviewService.getReview(id);
    }

    @Operation(summary = "获取证据所有审核记录", description = "获取指定证据下的所有审核记录列表")
    @GetMapping
    @PreAuthorize(EVIDENCE_VIEW)
    public List<EvidenceReviewVO> list(@Parameter(description = "证据ID") @PathVariable Long evidenceId) {
        return evidenceReviewService.listByEvidence(evidenceId);
    }
}
