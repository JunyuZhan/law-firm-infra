package com.lawfirm.staff.model.request.finance.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 费用类别创建请求
 */
@Data
@Schema(description = "费用类别创建请求")
public class ExpenseCategoryCreateRequest {

    @Schema(description = "类别名称")
    @NotBlank(message = "类别名称不能为空")
    @Size(max = 50, message = "类别名称长度不能超过50个字符")
    private String name;

    @Schema(description = "类别编码")
    @NotBlank(message = "类别编码不能为空")
    @Size(max = 50, message = "类别编码长度不能超过50个字符")
    private String code;

    @Schema(description = "父级ID")
    private Long parentId;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注")
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
} 