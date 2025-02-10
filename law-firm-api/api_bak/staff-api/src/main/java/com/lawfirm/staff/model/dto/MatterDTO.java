package com.lawfirm.staff.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "案件数据传输对象")
public class MatterDTO {

    @Schema(description = "案件ID")
    private Long id;

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

    @Schema(description = "客户名称")
    private String clientName;

    @Schema(description = "主办律师ID")
    private Long lawyerId;

    @Schema(description = "主办律师姓名")
    private String lawyerName;

    @Schema(description = "协办律师列表")
    private List<MatterLawyer> assistantLawyers;

    @Schema(description = "案件描述")
    private String description;

    @Schema(description = "立案时间")
    private String filingTime;

    @Schema(description = "结案时间")
    private String closingTime;

    @Schema(description = "案件金额")
    private Double amount;

    @Schema(description = "案件来源")
    private Integer source;

    @Schema(description = "案件标签")
    private List<String> tags;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;

    @Data
    @Schema(description = "案件律师")
    public static class MatterLawyer {

        @Schema(description = "律师ID")
        private Long lawyerId;

        @Schema(description = "律师姓名")
        private String lawyerName;

        @Schema(description = "律师角色")
        private Integer role;

        @Schema(description = "备注")
        private String remark;
    }
} 