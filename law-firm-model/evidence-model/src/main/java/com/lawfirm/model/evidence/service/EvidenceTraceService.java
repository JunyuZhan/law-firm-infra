package com.lawfirm.model.evidence.service;

import com.lawfirm.model.evidence.dto.EvidenceTraceDTO;
import com.lawfirm.model.evidence.vo.EvidenceTraceVO;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 证据流转/溯源Service接口
 */
@Tag(name = "证据流转Service", description = "证据流转/溯源相关操作")
public interface EvidenceTraceService {
    @Operation(summary = "新增证据流转")
    Long addTrace(@Parameter(description = "证据流转DTO") EvidenceTraceDTO dto);
    @Operation(summary = "更新证据流转")
    void updateTrace(@Parameter(description = "证据流转DTO") EvidenceTraceDTO dto);
    @Operation(summary = "删除证据流转")
    void deleteTrace(@Parameter(description = "流转ID") Long id);
    @Operation(summary = "获取证据流转详情")
    EvidenceTraceVO getTrace(@Parameter(description = "流转ID") Long id);
    @Operation(summary = "根据证据ID查询流转列表")
    List<EvidenceTraceVO> listByEvidence(@Parameter(description = "证据ID") Long evidenceId);
} 