package com.lawfirm.staff.model.query;

import com.lawfirm.common.data.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "案件查询对象")
public class MatterQuery extends PageQuery {

    @Schema(description = "案件编号")
    private String code;

    @Schema(description = "案件标题")
    private String title;

    @Schema(description = "案件类型")
    private Integer type;

    @Schema(description = "案件状态")
    private Integer status;

    @Schema(description = "客户ID")
    private Long clientId;

    @Schema(description = "主办律师ID")
    private Long lawyerId;

    @Schema(description = "案件来源")
    private Integer source;

    @Schema(description = "立案时间-起始")
    private String filingTimeBegin;

    @Schema(description = "立案时间-结束")
    private String filingTimeEnd;

    @Schema(description = "结案时间-起始")
    private String closingTimeBegin;

    @Schema(description = "结案时间-结束")
    private String closingTimeEnd;

    @Schema(description = "创建时间-起始")
    private String createTimeBegin;

    @Schema(description = "创建时间-结束")
    private String createTimeEnd;

    @Schema(description = "关键字")
    private String keyword;
} 