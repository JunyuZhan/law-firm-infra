package com.lawfirm.evidence.controller;

import com.lawfirm.model.evidence.dto.EvidenceTraceDTO;
import com.lawfirm.model.evidence.vo.EvidenceTraceVO;
import com.lawfirm.model.evidence.service.EvidenceTraceService;
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

@Tag(name = "证据流转管理", description = "证据流转相关接口")
@RestController("evidenceTraceController")
@RequestMapping(EvidenceConstants.API_PREFIX + "/{evidenceId}/traces")
public class EvidenceTraceController {
    @Autowired
    @Qualifier("evidenceTraceService")
    private EvidenceTraceService evidenceTraceService;

    @Operation(summary = "新增证据流转记录", description = "为指定证据新增流转记录")
    @PostMapping
    @PreAuthorize("hasAuthority('" + EVIDENCE_CREATE + "')")
    public Long add(@Parameter(description = "证据ID") @PathVariable Long evidenceId, @Parameter(description = "流转信息DTO") @RequestBody EvidenceTraceDTO dto) {
        dto.setEvidenceId(evidenceId);
        return evidenceTraceService.addTrace(dto);
    }

    @Operation(summary = "更新证据流转记录", description = "更新指定证据的流转信息")
    @PutMapping
    @PreAuthorize("hasAuthority('" + EVIDENCE_EDIT + "')")
    public void update(@Parameter(description = "流转信息DTO") @RequestBody EvidenceTraceDTO dto) {
        evidenceTraceService.updateTrace(dto);
    }

    @Operation(summary = "删除证据流转记录", description = "根据流转ID删除证据流转记录")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + EVIDENCE_DELETE + "')")
    public void delete(@Parameter(description = "流转ID") @PathVariable Long id) {
        evidenceTraceService.deleteTrace(id);
    }

    @Operation(summary = "获取证据流转详情", description = "根据流转ID获取证据流转的详细信息")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + EVIDENCE_VIEW + "')")
    public EvidenceTraceVO get(@Parameter(description = "流转ID") @PathVariable Long id) {
        return evidenceTraceService.getTrace(id);
    }

    @Operation(summary = "获取证据所有流转记录", description = "获取指定证据下的所有流转记录列表")
    @GetMapping
    @PreAuthorize("hasAuthority('" + EVIDENCE_VIEW + "')")
    public List<EvidenceTraceVO> list(@Parameter(description = "证据ID") @PathVariable Long evidenceId) {
        return evidenceTraceService.listByEvidence(evidenceId);
    }
}
