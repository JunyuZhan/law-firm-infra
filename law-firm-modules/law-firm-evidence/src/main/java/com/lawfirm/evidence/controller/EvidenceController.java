package com.lawfirm.evidence.controller;

import com.lawfirm.model.evidence.dto.EvidenceDTO;
import com.lawfirm.model.evidence.vo.EvidenceVO;
import com.lawfirm.model.evidence.service.EvidenceService;
import com.lawfirm.evidence.constant.EvidenceConstants;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Tag(name = "证据管理", description = "证据管理相关接口")
@RestController("evidenceController")
@RequestMapping(EvidenceConstants.API_PREFIX)
public class EvidenceController {

    private final EvidenceService evidenceService;

    public EvidenceController(@Qualifier("evidenceService") EvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

    @Operation(summary = "根据ID获取证据详情", description = "根据证据ID获取证据的详细信息")
    @GetMapping("/{id}")
    public EvidenceVO getById(@Parameter(description = "证据ID") @PathVariable Long id) {
        return evidenceService.getEvidence(id);
    }

    @Operation(summary = "新增证据", description = "创建新的证据记录")
    @PostMapping
    public Long create(@Parameter(description = "证据信息DTO") @RequestBody EvidenceDTO dto) {
        return evidenceService.addEvidence(dto);
    }

    @Operation(summary = "根据案件ID获取证据列表", description = "获取指定案件下的所有证据列表")
    @GetMapping("/list/{caseId}")
    public List<EvidenceVO> listByCase(@Parameter(description = "案件ID") @PathVariable Long caseId) {
        return evidenceService.listEvidenceByCase(caseId);
    }
} 