package com.lawfirm.model.analysis.vo;

import com.lawfirm.model.base.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * 树形报表VO
 */
@SuppressWarnings({"serial", "rawtypes", "unchecked", "all"})
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "树形报表VO")
public class TreeReportVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    @Schema(description = "节点key")
    private String nodeKey;
    @Schema(description = "节点名称")
    private String nodeName;
    @Schema(description = "统计值")
    private Double value;
    @Schema(description = "子节点")
    private List<TreeReportVO> children;
} 