package com.lawfirm.evidence.controller;

import com.lawfirm.model.evidence.dto.EvidenceFactRelationDTO;
import com.lawfirm.model.evidence.vo.EvidenceFactRelationVO;
import com.lawfirm.model.evidence.service.EvidenceFactRelationService;
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

@Tag(name = "证据事实关联管理", description = "证据事实关联相关接口")
@RestController("evidenceFactRelationController")
@RequestMapping(EvidenceConstants.API_PREFIX + "/{evidenceId}/facts")
public class EvidenceFactRelationController {
    @Autowired
    @Qualifier("evidenceFactRelationService")
    private EvidenceFactRelationService evidenceFactRelationService;

    @Operation(summary = "新增证据事实关联", description = "为指定证据新增事实关联记录")
    @PostMapping
    @PreAuthorize(EVIDENCE_CREATE)
    public Long add(@Parameter(description = "证据ID") @PathVariable Long evidenceId, @Parameter(description = "事实关联信息DTO") @RequestBody EvidenceFactRelationDTO dto) {
        dto.setEvidenceId(evidenceId);
        return evidenceFactRelationService.addFactRelation(dto);
    }

    @Operation(summary = "更新证据事实关联", description = "更新指定证据的事实关联信息")
    @PutMapping
    @PreAuthorize(EVIDENCE_EDIT)
    public void update(@Parameter(description = "事实关联信息DTO") @RequestBody EvidenceFactRelationDTO dto) {
        evidenceFactRelationService.updateFactRelation(dto);
    }

    @Operation(summary = "删除证据事实关联", description = "根据事实关联ID删除证据事实关联记录")
    @DeleteMapping("/{id}")
    @PreAuthorize(EVIDENCE_DELETE)
    public void delete(@Parameter(description = "事实关联ID") @PathVariable Long id) {
        evidenceFactRelationService.deleteFactRelation(id);
    }

    @Operation(summary = "获取证据事实关联详情", description = "根据事实关联ID获取证据事实关联的详细信息")
    @GetMapping("/{id}")
    @PreAuthorize(EVIDENCE_VIEW)
    public EvidenceFactRelationVO get(@Parameter(description = "事实关联ID") @PathVariable Long id) {
        return evidenceFactRelationService.getFactRelation(id);
    }

    @Operation(summary = "获取证据所有事实关联记录", description = "获取指定证据下的所有事实关联记录列表")
    @GetMapping
    @PreAuthorize(EVIDENCE_VIEW)
    public List<EvidenceFactRelationVO> list(@Parameter(description = "证据ID") @PathVariable Long evidenceId) {
        return evidenceFactRelationService.listByEvidence(evidenceId);
    }
}
