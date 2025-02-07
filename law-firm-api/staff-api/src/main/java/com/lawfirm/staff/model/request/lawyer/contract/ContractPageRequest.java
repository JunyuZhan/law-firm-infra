package com.lawfirm.staff.model.request.lawyer.contract;

import com.lawfirm.model.base.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "合同分页查询请求")
public class ContractPageRequest extends PageQuery {

    @Schema(description = "合同类型")
    private Integer type;

    @Schema(description = "合同状态")
    private Integer status;

    @Schema(description = "关键字")
    private String keyword;
} 