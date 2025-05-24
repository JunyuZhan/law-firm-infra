package com.lawfirm.model.evidence.service;

import com.lawfirm.model.evidence.dto.EvidenceAttachmentDTO;
import com.lawfirm.model.evidence.vo.EvidenceAttachmentVO;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 证据附件Service接口
 */
@Tag(name = "证据附件Service", description = "证据附件相关操作")
public interface EvidenceAttachmentService {
    @Operation(summary = "新增证据附件")
    Long addAttachment(@Parameter(description = "证据附件DTO") EvidenceAttachmentDTO dto);
    @Operation(summary = "更新证据附件")
    void updateAttachment(@Parameter(description = "证据附件DTO") EvidenceAttachmentDTO dto);
    @Operation(summary = "删除证据附件")
    void deleteAttachment(@Parameter(description = "附件ID") Long id);
    @Operation(summary = "获取证据附件详情")
    EvidenceAttachmentVO getAttachment(@Parameter(description = "附件ID") Long id);
    @Operation(summary = "根据证据ID查询附件列表")
    List<EvidenceAttachmentVO> listByEvidence(@Parameter(description = "证据ID") Long evidenceId);
} 