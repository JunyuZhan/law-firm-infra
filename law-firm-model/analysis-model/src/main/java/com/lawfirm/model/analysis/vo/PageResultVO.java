package com.lawfirm.model.analysis.vo;

import com.lawfirm.model.base.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * 通用分页结果VO
 */
@SuppressWarnings({"serial", "rawtypes", "unchecked", "all"})
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "通用分页结果VO")
public class PageResultVO<T extends java.io.Serializable> extends BaseVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "数据列表")
    private List<T> records;
    @Schema(description = "总记录数")
    private long total;
    @Schema(description = "当前页码")
    private int pageNum;
    @Schema(description = "每页大小")
    private int pageSize;
} 