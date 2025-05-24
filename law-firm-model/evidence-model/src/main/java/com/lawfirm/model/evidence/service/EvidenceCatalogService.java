package com.lawfirm.model.evidence.service;

import com.lawfirm.model.evidence.dto.EvidenceCatalogDTO;
import com.lawfirm.model.evidence.vo.EvidenceCatalogVO;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * 证据目录管理Service接口
 * 定义证据目录的增删改查等操作
 */
@Tag(name = "证据目录Service", description = "证据目录管理相关操作")
public interface EvidenceCatalogService {
    /**
     * 新增证据目录
     * @param dto 目录DTO
     * @return 新增目录ID
     */
    @Operation(summary = "新增证据目录")
    Long addCatalog(@Parameter(description = "证据目录DTO") EvidenceCatalogDTO dto);

    /**
     * 更新证据目录
     * @param dto 目录DTO
     */
    @Operation(summary = "更新证据目录")
    void updateCatalog(@Parameter(description = "证据目录DTO") EvidenceCatalogDTO dto);

    /**
     * 删除证据目录
     * @param catalogId 目录ID
     */
    @Operation(summary = "删除证据目录")
    void deleteCatalog(@Parameter(description = "目录ID") Long catalogId);

    /**
     * 获取单个目录详情
     * @param catalogId 目录ID
     * @return 目录VO
     */
    @Operation(summary = "获取证据目录详情")
    EvidenceCatalogVO getCatalog(@Parameter(description = "目录ID") Long catalogId);

    /**
     * 查询案件下所有证据目录
     * @param caseId 案件ID
     * @return 目录VO列表
     */
    @Operation(summary = "根据案件ID查询目录列表")
    List<EvidenceCatalogVO> listCatalogsByCase(@Parameter(description = "案件ID") Long caseId);
} 