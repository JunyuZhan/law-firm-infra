package com.lawfirm.staff.model.request.lawyer.knowledge;

import com.lawfirm.staff.model.base.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "知识库分页查询请求")
public class KnowledgePageRequest extends PageQuery {

    @Schema(description = "关键字(标题或内容)")
    private String keyword;

    @Schema(description = "知识类型")
    private Integer type;

    @Schema(description = "知识状态")
    private Integer status;

    @Schema(description = "标签")
    private String tag;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @Schema(description = "开始创建时间")
    private String createTimeStart;

    @Schema(description = "结束创建时间")
    private String createTimeEnd;
} 