package com.lawfirm.model.evidence.service;

import com.lawfirm.model.evidence.dto.EvidenceTagRelationDTO;
import com.lawfirm.model.evidence.vo.EvidenceTagRelationVO;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 证据-标签关联Service接口
 */
@Tag(name = "证据-标签关联Service", description = "证据与标签关联相关操作")
public interface EvidenceTagRelationService {
    @Operation(summary = "新增证据-标签关联")
    Long addTagRelation(@Parameter(description = "证据-标签关联DTO") EvidenceTagRelationDTO dto);
    @Operation(summary = "更新证据-标签关联")
    void updateTagRelation(@Parameter(description = "证据-标签关联DTO") EvidenceTagRelationDTO dto);
    @Operation(summary = "删除证据-标签关联")
    void deleteTagRelation(@Parameter(description = "关联ID") Long id);
    @Operation(summary = "获取证据-标签关联详情")
    EvidenceTagRelationVO getTagRelation(@Parameter(description = "关联ID") Long id);
    @Operation(summary = "根据证据ID查询标签关联列表")
    List<EvidenceTagRelationVO> listByEvidence(@Parameter(description = "证据ID") Long evidenceId);
} 