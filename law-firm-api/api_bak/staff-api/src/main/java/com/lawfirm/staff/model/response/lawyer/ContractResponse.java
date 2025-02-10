package com.lawfirm.staff.model.response.lawyer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "合同响应")
public class ContractResponse {

    @Schema(description = "合同ID")
    private Long id;

    @Schema(description = "合同标题")
    private String title;

    @Schema(description = "合同编号")
    private String contractNo;

    @Schema(description = "合同类型")
    private Integer type;

    @Schema(description = "合同类型名称")
    private String typeName;

    @Schema(description = "关联案件ID")
    private Long matterId;

    @Schema(description = "关联案件名称")
    private String matterName;

    @Schema(description = "合同金额")
    private BigDecimal amount;

    @Schema(description = "签订日期")
    private String signDate;

    @Schema(description = "生效日期")
    private String effectiveDate;

    @Schema(description = "到期日期")
    private String expiryDate;

    @Schema(description = "合同内容")
    private String content;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "附件列表")
    private List<AttachmentInfo> attachments;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @Schema(description = "创建人姓名")
    private String creatorName;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;

    @Data
    @Schema(description = "附件信息")
    public static class AttachmentInfo {
        
        @Schema(description = "文件名")
        private String fileName;

        @Schema(description = "文件路径")
        private String filePath;

        @Schema(description = "文件大小")
        private Long fileSize;
    }
} 