package com.lawfirm.evidence.controller;

import com.lawfirm.model.evidence.dto.EvidenceTagRelationDTO;
import com.lawfirm.model.evidence.vo.EvidenceTagRelationVO;
import com.lawfirm.model.evidence.service.EvidenceTagRelationService;
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

@Tag(name = "证据标签关联管理", description = "证据标签关联相关接口")
@RestController("evidenceTagRelationController")
@RequestMapping(EvidenceConstants.API_PREFIX + "/{evidenceId}/tags")
public class EvidenceTagRelationController {
    @Autowired
    @Qualifier("evidenceTagRelationService")
    private EvidenceTagRelationService evidenceTagRelationService;

    @Operation(summary = "新增证据标签关联", description = "为指定证据新增标签关联记录")
    @PostMapping
    @PreAuthorize("hasAuthority('" + EVIDENCE_CREATE + "')")
    public Long add(@Parameter(description = "证据ID") @PathVariable Long evidenceId, @Parameter(description = "标签关联信息DTO") @RequestBody EvidenceTagRelationDTO dto) {
        dto.setEvidenceId(evidenceId);
        return evidenceTagRelationService.addTagRelation(dto);
    }

    @Operation(summary = "更新证据标签关联", description = "更新指定证据的标签关联信息")
    @PutMapping
    @PreAuthorize("hasAuthority('" + EVIDENCE_EDIT + "')")
    public void update(@Parameter(description = "标签关联信息DTO") @RequestBody EvidenceTagRelationDTO dto) {
        evidenceTagRelationService.updateTagRelation(dto);
    }

    @Operation(summary = "删除证据标签关联", description = "根据标签关联ID删除证据标签关联记录")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + EVIDENCE_DELETE + "')")
    public void delete(@Parameter(description = "标签关联ID") @PathVariable Long id) {
        evidenceTagRelationService.deleteTagRelation(id);
    }

    @Operation(summary = "获取证据标签关联详情", description = "根据标签关联ID获取证据标签关联的详细信息")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + EVIDENCE_VIEW + "')")
    public EvidenceTagRelationVO get(@Parameter(description = "标签关联ID") @PathVariable Long id) {
        return evidenceTagRelationService.getTagRelation(id);
    }

    @Operation(summary = "获取证据所有标签关联记录", description = "获取指定证据下的所有标签关联记录列表")
    @GetMapping
    @PreAuthorize("hasAuthority('" + EVIDENCE_VIEW + "')")
    public List<EvidenceTagRelationVO> list(@Parameter(description = "证据ID") @PathVariable Long evidenceId) {
        return evidenceTagRelationService.listByEvidence(evidenceId);
    }
} 