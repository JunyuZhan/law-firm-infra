package com.lawfirm.evidence.controller;

import com.lawfirm.model.evidence.dto.EvidencePersonnelDTO;
import com.lawfirm.model.evidence.vo.EvidencePersonnelVO;
import com.lawfirm.model.evidence.service.EvidencePersonnelService;
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

@Tag(name = "证据人员管理", description = "证据人员相关接口")
@RestController("evidencePersonnelController")
@RequestMapping(EvidenceConstants.API_PREFIX + "/{evidenceId}/personnel")
public class EvidencePersonnelController {
    @Autowired
    @Qualifier("evidencePersonnelService")
    private EvidencePersonnelService evidencePersonnelService;

    @Operation(summary = "新增证据人员", description = "为指定证据新增人员记录")
    @PostMapping
    @PreAuthorize(EVIDENCE_CREATE)
    public Long add(@Parameter(description = "证据ID") @PathVariable Long evidenceId, @Parameter(description = "人员信息DTO") @RequestBody EvidencePersonnelDTO dto) {
        dto.setEvidenceId(evidenceId);
        return evidencePersonnelService.addPersonnel(dto);
    }

    @Operation(summary = "更新证据人员", description = "更新指定证据的人员信息")
    @PutMapping
    @PreAuthorize(EVIDENCE_EDIT)
    public void update(@Parameter(description = "人员信息DTO") @RequestBody EvidencePersonnelDTO dto) {
        evidencePersonnelService.updatePersonnel(dto);
    }

    @Operation(summary = "删除证据人员", description = "根据人员ID删除证据人员记录")
    @DeleteMapping("/{id}")
    @PreAuthorize(EVIDENCE_DELETE)
    public void delete(@Parameter(description = "人员ID") @PathVariable Long id) {
        evidencePersonnelService.deletePersonnel(id);
    }

    @Operation(summary = "获取证据人员详情", description = "根据人员ID获取证据人员的详细信息")
    @GetMapping("/{id}")
    @PreAuthorize(EVIDENCE_VIEW)
    public EvidencePersonnelVO get(@Parameter(description = "人员ID") @PathVariable Long id) {
        return evidencePersonnelService.getPersonnel(id);
    }

    @Operation(summary = "获取证据所有人员", description = "获取指定证据下的所有人员列表")
    @GetMapping
    @PreAuthorize(EVIDENCE_VIEW)
    public List<EvidencePersonnelVO> list(@Parameter(description = "证据ID") @PathVariable Long evidenceId) {
        return evidencePersonnelService.listByEvidence(evidenceId);
    }
}
