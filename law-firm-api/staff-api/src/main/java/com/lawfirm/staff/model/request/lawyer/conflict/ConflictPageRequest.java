package com.lawfirm.staff.model.request.lawyer.conflict;

import com.lawfirm.model.base.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "冲突检查分页查询请求")
public class ConflictPageRequest extends PageQuery {

    @Schema(description = "检查类型")
    private Integer type;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "关键字")
    private String keyword;
} 