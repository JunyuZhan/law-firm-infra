package com.lawfirm.staff.model.response.finance.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 费用类别响应
 */
@Data
@Schema(description = "费用类别响应")
public class ExpenseCategoryResponse {

    @Schema(description = "类别ID")
    private Long id;

    @Schema(description = "类别名称")
    private String name;

    @Schema(description = "类别编码")
    private String code;

    @Schema(description = "父级ID")
    private Long parentId;

    @Schema(description = "父级名称")
    private String parentName;

    @Schema(description = "层级")
    private Integer level;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "子类别")
    private List<ExpenseCategoryResponse> children;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 