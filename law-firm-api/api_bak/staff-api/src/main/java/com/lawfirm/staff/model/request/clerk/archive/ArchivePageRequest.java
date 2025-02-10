package com.lawfirm.staff.model.request.clerk.archive;

import com.lawfirm.staff.model.base.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "档案分页查询请求")
public class ArchivePageRequest extends PageQuery {

    @Schema(description = "关键字(档案编号或名称)")
    private String keyword;

    @Schema(description = "档案类型")
    private Integer type;

    @Schema(description = "档案状态")
    private Integer status;

    @Schema(description = "存放位置")
    private String location;

    @Schema(description = "开始创建时间")
    private String createTimeStart;

    @Schema(description = "结束创建时间")
    private String createTimeEnd;
} 