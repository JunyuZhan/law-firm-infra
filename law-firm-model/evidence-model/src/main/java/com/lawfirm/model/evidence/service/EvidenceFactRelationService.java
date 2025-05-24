package com.lawfirm.model.evidence.service;

import com.lawfirm.model.evidence.dto.EvidenceFactRelationDTO;
import com.lawfirm.model.evidence.vo.EvidenceFactRelationVO;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 证据-事实关联Service接口
 */
@Tag(name = "证据-事实关联Service", description = "证据与事实关联相关操作")
public interface EvidenceFactRelationService {
    @Operation(summary = "新增证据-事实关联")
    Long addFactRelation(@Parameter(description = "证据-事实关联DTO") EvidenceFactRelationDTO dto);
    @Operation(summary = "更新证据-事实关联")
    void updateFactRelation(@Parameter(description = "证据-事实关联DTO") EvidenceFactRelationDTO dto);
    @Operation(summary = "删除证据-事实关联")
    void deleteFactRelation(@Parameter(description = "关联ID") Long id);
    @Operation(summary = "获取证据-事实关联详情")
    EvidenceFactRelationVO getFactRelation(@Parameter(description = "关联ID") Long id);
    @Operation(summary = "根据证据ID查询事实关联列表")
    List<EvidenceFactRelationVO> listByEvidence(@Parameter(description = "证据ID") Long evidenceId);
} 