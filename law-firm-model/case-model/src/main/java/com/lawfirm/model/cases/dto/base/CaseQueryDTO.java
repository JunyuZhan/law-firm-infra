package com.lawfirm.model.cases.dto.base;

import com.lawfirm.common.data.query.PageQuery;
import com.lawfirm.model.cases.enums.base.CaseStatusEnum;
import com.lawfirm.model.cases.enums.base.CaseTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "案件查询DTO")
public class CaseQueryDTO extends PageQuery {

    @Schema(description = "页码", defaultValue = "1")
    private Integer pageNum = 1;

    @Schema(description = "每页大小", defaultValue = "10")
    private Integer pageSize = 10;

    @Schema(description = "案件名称")
    private String name;

    @Schema(description = "案件编号")
    private String caseNo;

    @Schema(description = "客户ID")
    private Long clientId;

    @Schema(description = "主办律师ID")
    private Long leaderId;

    @Schema(description = "案件类型")
    private CaseTypeEnum caseType;

    @Schema(description = "案件状态")
    private CaseStatusEnum status;

    @Schema(description = "是否归档")
    private Boolean archived;

    @Schema(description = "标签")
    private String tag;

    @Schema(description = "立案时间起始")
    private LocalDate filingTimeStart;

    @Schema(description = "立案时间结束")
    private LocalDate filingTimeEnd;

    @Schema(description = "创建时间起始")
    private String createTimeStart;

    @Schema(description = "创建时间结束")
    private String createTimeEnd;

    // 兼容旧的方法名
    public String getCaseName() {
        return name;
    }

    public String getCaseNumber() {
        return caseNo;
    }

    public CaseStatusEnum getCaseStatus() {
        return status;
    }

    public LocalDate getFilingTimeStart() {
        return filingTimeStart;
    }

    public LocalDate getFilingTimeEnd() {
        return filingTimeEnd;
    }

    // 分页相关方法
    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }
} 