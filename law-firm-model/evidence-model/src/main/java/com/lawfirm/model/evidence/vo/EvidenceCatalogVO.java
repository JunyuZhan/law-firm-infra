package com.lawfirm.model.evidence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * 证据目录VO
 * 用于前端展示证据目录树结构
 */
@Data
@Schema(description = "证据目录VO")
public class EvidenceCatalogVO {
    @Schema(description = "目录ID")
    private Long id;
    @Schema(description = "目录名称")
    private String name;
    /** 备注说明 */
    private String remark;
    @Schema(description = "父目录ID")
    private Long parentId;
    @Schema(description = "排序号")
    private Integer sort;
    /** 子目录列表 */
    private List<EvidenceCatalogVO> children;
} 