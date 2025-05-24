package com.lawfirm.model.evidence.service;

import com.lawfirm.model.evidence.dto.EvidenceReviewDTO;
import com.lawfirm.model.evidence.vo.EvidenceReviewVO;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 证据审核Service接口
 */
@Tag(name = "证据审核Service", description = "证据审核相关操作")
public interface EvidenceReviewService {
    @Operation(summary = "新增证据审核")
    Long addReview(@Parameter(description = "证据审核DTO") EvidenceReviewDTO dto);
    @Operation(summary = "更新证据审核")
    void updateReview(@Parameter(description = "证据审核DTO") EvidenceReviewDTO dto);
    @Operation(summary = "删除证据审核")
    void deleteReview(@Parameter(description = "审核ID") Long id);
    @Operation(summary = "获取证据审核详情")
    EvidenceReviewVO getReview(@Parameter(description = "审核ID") Long id);
    @Operation(summary = "根据证据ID查询审核列表")
    List<EvidenceReviewVO> listByEvidence(@Parameter(description = "证据ID") Long evidenceId);
} 