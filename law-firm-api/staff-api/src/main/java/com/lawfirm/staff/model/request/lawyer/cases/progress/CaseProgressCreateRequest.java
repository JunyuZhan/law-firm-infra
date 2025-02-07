package com.lawfirm.staff.model.request.lawyer.cases.progress;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件进度创建请求
 */
@Data
@Schema(description = "案件进度创建请求")
public class CaseProgressCreateRequest {

    @Schema(description = "进度内容")
    @NotBlank(message = "进度内容不能为空")
    private String content;

    @Schema(description = "进度类型(1:立案 2:庭前准备 3:开庭 4:判决 5:执行 6:结案)")
    @NotNull(message = "进度类型不能为空")
    private Integer type;

    @Schema(description = "进度时间")
    @NotNull(message = "进度时间不能为空")
    private LocalDateTime progressTime;

    @Schema(description = "附件列表")
    private List<String> attachments;

    @Schema(description = "备注")
    private String remark;
} 