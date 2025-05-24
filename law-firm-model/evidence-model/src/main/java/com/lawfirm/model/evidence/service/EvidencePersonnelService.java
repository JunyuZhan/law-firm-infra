package com.lawfirm.model.evidence.service;

import com.lawfirm.model.evidence.dto.EvidencePersonnelDTO;
import com.lawfirm.model.evidence.vo.EvidencePersonnelVO;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 证据人员Service接口
 */
@Tag(name = "证据人员Service", description = "证据人员相关操作")
public interface EvidencePersonnelService {
    @Operation(summary = "新增证据人员")
    Long addPersonnel(@Parameter(description = "证据人员DTO") EvidencePersonnelDTO dto);
    @Operation(summary = "更新证据人员")
    void updatePersonnel(@Parameter(description = "证据人员DTO") EvidencePersonnelDTO dto);
    @Operation(summary = "删除证据人员")
    void deletePersonnel(@Parameter(description = "人员ID") Long id);
    @Operation(summary = "获取证据人员详情")
    EvidencePersonnelVO getPersonnel(@Parameter(description = "人员ID") Long id);
    @Operation(summary = "根据证据ID查询人员列表")
    List<EvidencePersonnelVO> listByEvidence(@Parameter(description = "证据ID") Long evidenceId);
} 