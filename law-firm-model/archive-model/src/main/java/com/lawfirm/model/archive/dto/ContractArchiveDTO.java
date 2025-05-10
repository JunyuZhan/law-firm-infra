package com.lawfirm.model.archive.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 合同归档数据传输对象
 */
@Data
@Schema(description = "合同归档DTO")
public class ContractArchiveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 合同ID
     */
    @NotNull(message = "合同ID不能为空")
    @Schema(description = "合同ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long contractId;

    /**
     * 合同标题
     */
    @NotBlank(message = "合同标题不能为空")
    @Schema(description = "合同标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String contractTitle;

    /**
     * 合同编号
     */
    @Schema(description = "合同编号")
    private String contractNo;

    /**
     * 合同类型
     */
    @Schema(description = "合同类型")
    private String contractType;

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
     * 相关案件ID
     */
    @Schema(description = "相关案件ID")
    private Long caseId;

    /**
     * 相关案件名称
     */
    @Schema(description = "相关案件名称")
    private String caseName;

    /**
     * 合同状态
     */
    @Schema(description = "合同状态")
    private String contractStatus;

    /**
     * 合同金额
     */
    @Schema(description = "合同金额")
    private Double contractAmount;

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