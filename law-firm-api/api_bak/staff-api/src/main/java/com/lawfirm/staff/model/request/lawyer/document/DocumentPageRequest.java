package com.lawfirm.staff.model.request.lawyer.document;

import com.lawfirm.common.data.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "文档分页查询请求")
public class DocumentPageRequest extends PageQuery {

    @Schema(description = "文档名称")
    private String name;

    @Schema(description = "文档类型")
    private Integer type;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "关键字")
    private String keyword;
} 