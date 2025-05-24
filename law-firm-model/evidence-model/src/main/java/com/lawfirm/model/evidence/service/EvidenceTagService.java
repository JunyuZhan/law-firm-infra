package com.lawfirm.model.evidence.service;

import com.lawfirm.model.evidence.dto.EvidenceTagDTO;
import com.lawfirm.model.evidence.vo.EvidenceTagVO;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 证据标签管理Service接口
 * 定义证据标签的增删改查等操作
 */
@Tag(name = "证据标签Service", description = "证据标签相关操作")
public interface EvidenceTagService {
    @Operation(summary = "新增证据标签")
    Long addTag(@Parameter(description = "证据标签DTO") EvidenceTagDTO dto);
    @Operation(summary = "更新证据标签")
    void updateTag(@Parameter(description = "证据标签DTO") EvidenceTagDTO dto);
    @Operation(summary = "删除证据标签")
    void deleteTag(@Parameter(description = "标签ID") Long tagId);
    @Operation(summary = "获取证据标签详情")
    EvidenceTagVO getTag(@Parameter(description = "标签ID") Long tagId);
    @Operation(summary = "查询所有证据标签")
    List<EvidenceTagVO> listAllTags();
} 