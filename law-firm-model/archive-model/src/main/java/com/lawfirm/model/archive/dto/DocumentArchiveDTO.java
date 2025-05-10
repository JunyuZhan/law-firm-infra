package com.lawfirm.model.archive.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 文档归档数据传输对象
 */
@Data
@Schema(description = "文档归档DTO")
public class DocumentArchiveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文档ID
     */
    @NotNull(message = "文档ID不能为空")
    @Schema(description = "文档ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long documentId;

    /**
     * 文档标题
     */
    @NotBlank(message = "文档标题不能为空")
    @Schema(description = "文档标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String documentTitle;

    /**
     * 文档编号
     */
    @Schema(description = "文档编号")
    private String documentNo;

    /**
     * 文档类型
     */
    @Schema(description = "文档类型")
    private String documentType;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long creatorId;

    /**
     * 创建人姓名
     */
    @Schema(description = "创建人姓名")
    private String creatorName;

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
     * 创建时间(YYYY-MM-DD)
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 修改时间(YYYY-MM-DD)
     */
    @Schema(description = "修改时间")
    private String updateTime;

    /**
     * 经办人ID
     */
    @Schema(description = "经办人ID")
    private Long handlerId;

    /**
     * 经办人姓名
     */
    @Schema(description = "经办人姓名")
    private String handlerName;

    /**
     * 关联案件ID
     */
    @Schema(description = "关联案件ID")
    private Long caseId;

    /**
     * 关联案件名称
     */
    @Schema(description = "关联案件名称")
    private String caseName;

    /**
     * 关联合同ID
     */
    @Schema(description = "关联合同ID")
    private Long contractId;

    /**
     * 关联合同名称
     */
    @Schema(description = "关联合同名称")
    private String contractName;

    /**
     * 文档内容摘要
     */
    @Schema(description = "文档内容摘要")
    private String contentSummary;

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