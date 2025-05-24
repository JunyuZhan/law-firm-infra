package com.lawfirm.model.evidence.service;

import com.lawfirm.model.evidence.dto.EvidenceDTO;
import com.lawfirm.model.evidence.vo.EvidenceVO;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 证据管理Service接口
 * 定义证据的增删改查等核心操作
 */
@Tag(name = "证据管理Service", description = "证据的增删改查等核心操作")
public interface EvidenceService {
    /**
     * 新增证据
     * @param dto 证据DTO
     * @return 新增证据ID
     */
    @Operation(summary = "新增证据")
    Long addEvidence(@Parameter(description = "证据DTO") EvidenceDTO dto);

    /**
     * 更新证据
     * @param dto 证据DTO
     */
    @Operation(summary = "更新证据")
    void updateEvidence(@Parameter(description = "证据DTO") EvidenceDTO dto);

    /**
     * 删除证据
     * @param evidenceId 证据ID
     */
    @Operation(summary = "删除证据")
    void deleteEvidence(@Parameter(description = "证据ID") Long evidenceId);

    /**
     * 获取单条证据详情
     * @param evidenceId 证据ID
     * @return 证据VO
     */
    @Operation(summary = "获取单条证据详情")
    EvidenceVO getEvidence(@Parameter(description = "证据ID") Long evidenceId);

    /**
     * 查询案件下所有证据
     * @param caseId 案件ID
     * @return 证据VO列表
     */
    @Operation(summary = "查询案件下所有证据")
    List<EvidenceVO> listEvidenceByCase(@Parameter(description = "案件ID") Long caseId);

    /**
     * 归档证据
     * @param evidenceId 证据ID
     */
    @Operation(summary = "归档证据")
    void archiveEvidence(@Parameter(description = "证据ID") Long evidenceId);
} 