package com.lawfirm.model.analysis.vo;

import com.lawfirm.model.base.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分组统计VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "分组统计VO")
public class GroupStatVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    @Schema(description = "分组字段key")
    private String groupKey;
    @Schema(description = "分组名称")
    private String groupName;
    @Schema(description = "数量")
    private Long count;
    @Schema(description = "金额（可选）")
    private Double amount;
} 