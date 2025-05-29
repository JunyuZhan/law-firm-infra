package com.lawfirm.evidence.controller;

import com.lawfirm.model.evidence.dto.EvidenceAttachmentDTO;
import com.lawfirm.model.evidence.vo.EvidenceAttachmentVO;
import com.lawfirm.model.evidence.service.EvidenceAttachmentService;
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

@Tag(name = "证据附件管理", description = "证据附件相关接口")
@RestController("evidenceAttachmentController")
@RequestMapping(EvidenceConstants.API_PREFIX + "/{evidenceId}/attachments")
public class EvidenceAttachmentController {
    @Autowired
    @Qualifier("evidenceAttachmentService")
    private EvidenceAttachmentService evidenceAttachmentService;

    @Operation(summary = "新增证据附件", description = "为指定证据新增附件")
    @PostMapping
    @PreAuthorize("hasAuthority('" + EVIDENCE_CREATE + "')")
    public Long add(@Parameter(description = "证据ID") @PathVariable Long evidenceId, @Parameter(description = "附件信息DTO") @RequestBody EvidenceAttachmentDTO dto) {
        dto.setEvidenceId(evidenceId);
        return evidenceAttachmentService.addAttachment(dto);
    }

    @Operation(summary = "更新证据附件", description = "更新指定证据的附件信息")
    @PutMapping
    @PreAuthorize("hasAuthority('" + EVIDENCE_EDIT + "')")
    public void update(@Parameter(description = "附件信息DTO") @RequestBody EvidenceAttachmentDTO dto) {
        evidenceAttachmentService.updateAttachment(dto);
    }

    @Operation(summary = "删除证据附件", description = "根据附件ID删除证据附件")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + EVIDENCE_DELETE + "')")
    public void delete(@Parameter(description = "附件ID") @PathVariable Long id) {
        evidenceAttachmentService.deleteAttachment(id);
    }

    @Operation(summary = "获取证据附件详情", description = "根据附件ID获取证据附件的详细信息")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + EVIDENCE_VIEW + "')")
    public EvidenceAttachmentVO get(@Parameter(description = "附件ID") @PathVariable Long id) {
        return evidenceAttachmentService.getAttachment(id);
    }

    @Operation(summary = "获取证据所有附件", description = "获取指定证据下的所有附件列表")
    @GetMapping
    @PreAuthorize("hasAuthority('" + EVIDENCE_VIEW + "')")
    public List<EvidenceAttachmentVO> list(@Parameter(description = "证据ID") @PathVariable Long evidenceId) {
        return evidenceAttachmentService.listByEvidence(evidenceId);
    }
} 