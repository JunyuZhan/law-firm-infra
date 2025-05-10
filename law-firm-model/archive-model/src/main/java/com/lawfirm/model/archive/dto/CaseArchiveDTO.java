package com.lawfirm.model.archive.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 案件归档数据传输对象
 */
@Data
@Schema(description = "案件归档DTO")
public class CaseArchiveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    @NotNull(message = "案件ID不能为空")
    @Schema(description = "案件ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long caseId;

    /**
     * 案件标题
     */
    @NotBlank(message = "案件标题不能为空")
    @Schema(description = "案件标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String caseTitle;

    /**
     * 案件编号
     */
    @Schema(description = "案件编号")
    private String caseNo;

    /**
     * 案件类型
     */
    @Schema(description = "案件类型")
    private String caseType;

    /**
     * 负责律师ID
     */
    @Schema(description = "负责律师ID")
    private Long lawyerId;

    /**
     * 负责律师姓名
     */
    @Schema(description = "负责律师姓名")
    private String lawyerName;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long departmentId;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String departmentName;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private Long clientId;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    private String clientName;

    /**
     * 开始时间(YYYY-MM-DD)
     */
    @Schema(description = "开始时间")
    private String startTime;

    /**
     * 结束时间(YYYY-MM-DD)
     */
    @Schema(description = "结束时间")
    private String endTime;

    /**
     * 归档人ID
     */
    @Schema(description = "归档人ID")
    private Long handlerId;

    /**
     * 归档人姓名
     */
    @Schema(description = "归档人姓名")
    private String handlerName;

    /**
     * 案件状态
     */
    @Schema(description = "案件状态")
    private String caseStatus;

    /**
     * 案件金额
     */
    @Schema(description = "案件金额")
    private Double caseAmount;

    /**
     * 相关文件列表
     */
    @Schema(description = "相关文件列表")
    private transient List<ArchiveFileDTO> fileList;

    /**
     * 归档备注
     */
    @Schema(description = "归档备注")
    private String remark;
}