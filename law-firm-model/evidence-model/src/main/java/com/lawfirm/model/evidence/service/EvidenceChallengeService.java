package com.lawfirm.model.evidence.service;

import com.lawfirm.model.evidence.dto.EvidenceChallengeDTO;
import com.lawfirm.model.evidence.vo.EvidenceChallengeVO;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 证据质证Service接口
 */
@Tag(name = "证据质证Service", description = "证据质证相关操作")
public interface EvidenceChallengeService {
    @Operation(summary = "新增证据质证")
    Long addChallenge(@Parameter(description = "证据质证DTO") EvidenceChallengeDTO dto);
    @Operation(summary = "更新证据质证")
    void updateChallenge(@Parameter(description = "证据质证DTO") EvidenceChallengeDTO dto);
    @Operation(summary = "删除证据质证")
    void deleteChallenge(@Parameter(description = "质证ID") Long id);
    @Operation(summary = "获取证据质证详情")
    EvidenceChallengeVO getChallenge(@Parameter(description = "质证ID") Long id);
    @Operation(summary = "根据证据ID查询质证列表")
    List<EvidenceChallengeVO> listByEvidence(@Parameter(description = "证据ID") Long evidenceId);
} 