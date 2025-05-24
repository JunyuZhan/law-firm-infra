package com.lawfirm.model.analysis.vo;

import com.lawfirm.model.base.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * 分析报表VO
 */
@SuppressWarnings({"serial", "rawtypes", "unchecked", "all"})
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "分析报表VO")
public class ReportVO extends BaseVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "报表名称")
    private String reportName;
    @Schema(description = "报表类型")
    private String reportType;
    @Schema(description = "统计周期")
    private String period;
    @Schema(description = "维度")
    private String dimension;
    @Schema(description = "数据")
    private List<?> data;
} 