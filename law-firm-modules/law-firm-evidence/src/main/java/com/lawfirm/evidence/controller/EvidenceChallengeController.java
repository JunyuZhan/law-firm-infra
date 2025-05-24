package com.lawfirm.evidence.controller;

import com.lawfirm.model.evidence.dto.EvidenceChallengeDTO;
import com.lawfirm.model.evidence.vo.EvidenceChallengeVO;
import com.lawfirm.model.evidence.service.EvidenceChallengeService;
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

@Tag(name = "证据质证管理", description = "证据质证相关接口")
@RestController("evidenceChallengeController")
@RequestMapping(EvidenceConstants.API_PREFIX + "/{evidenceId}/challenges")
public class EvidenceChallengeController {
    @Autowired
    @Qualifier("evidenceChallengeService")
    private EvidenceChallengeService evidenceChallengeService;

    @Operation(summary = "新增证据质证", description = "为指定证据新增质证记录")
    @PostMapping
    @PreAuthorize(EVIDENCE_CREATE)
    public Long add(@Parameter(description = "证据ID") @PathVariable Long evidenceId, @Parameter(description = "质证信息DTO") @RequestBody EvidenceChallengeDTO dto) {
        dto.setEvidenceId(evidenceId);
        return evidenceChallengeService.addChallenge(dto);
    }

    @Operation(summary = "更新证据质证", description = "更新指定证据的质证信息")
    @PutMapping
    @PreAuthorize(EVIDENCE_EDIT)
    public void update(@Parameter(description = "质证信息DTO") @RequestBody EvidenceChallengeDTO dto) {
        evidenceChallengeService.updateChallenge(dto);
    }

    @Operation(summary = "删除证据质证", description = "根据质证ID删除证据质证记录")
    @DeleteMapping("/{id}")
    @PreAuthorize(EVIDENCE_DELETE)
    public void delete(@Parameter(description = "质证ID") @PathVariable Long id) {
        evidenceChallengeService.deleteChallenge(id);
    }

    @Operation(summary = "获取证据质证详情", description = "根据质证ID获取证据质证的详细信息")
    @GetMapping("/{id}")
    @PreAuthorize(EVIDENCE_VIEW)
    public EvidenceChallengeVO get(@Parameter(description = "质证ID") @PathVariable Long id) {
        return evidenceChallengeService.getChallenge(id);
    }

    @Operation(summary = "获取证据所有质证记录", description = "获取指定证据下的所有质证记录列表")
    @GetMapping
    @PreAuthorize(EVIDENCE_VIEW)
    public List<EvidenceChallengeVO> list(@Parameter(description = "证据ID") @PathVariable Long evidenceId) {
        return evidenceChallengeService.listByEvidence(evidenceId);
    }
} 