package com.lawfirm.staff.model.request.lawyer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "更新合同请求")
public class UpdateContractRequest {

    @Schema(description = "合同ID")
    @NotNull(message = "合同ID不能为空")
    private Long id;

    @Schema(description = "合同标题")
    @NotBlank(message = "合同标题不能为空")
    private String title;

    @Schema(description = "合同编号")
    @NotBlank(message = "合同编号不能为空")
    private String contractNo;

    @Schema(description = "合同类型")
    @NotNull(message = "合同类型不能为空")
    private Integer type;

    @Schema(description = "关联案件ID")
    @NotNull(message = "案件ID不能为空")
    private Long matterId;

    @Schema(description = "合同金额")
    @NotNull(message = "合同金额不能为空")
    private BigDecimal amount;

    @Schema(description = "签订日期")
    @NotBlank(message = "签订日期不能为空")
    private String signDate;

    @Schema(description = "生效日期")
    @NotBlank(message = "生效日期不能为空")
    private String effectiveDate;

    @Schema(description = "到期日期")
    @NotBlank(message = "到期日期不能为空")
    private String expiryDate;

    @Schema(description = "合同内容")
    private String content;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "附件列表")
    private List<AttachmentInfo> attachments;

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