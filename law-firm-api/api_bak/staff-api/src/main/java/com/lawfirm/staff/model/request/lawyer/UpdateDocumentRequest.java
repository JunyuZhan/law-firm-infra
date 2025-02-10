package com.lawfirm.staff.model.request.lawyer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "更新文档请求")
public class UpdateDocumentRequest {

    @Schema(description = "文档ID")
    @NotNull(message = "文档ID不能为空")
    private Long id;

    @Schema(description = "文档标题")
    @NotBlank(message = "文档标题不能为空")
    private String title;

    @Schema(description = "文档内容")
    private String content;

    @Schema(description = "文档类型")
    @NotNull(message = "文档类型不能为空")
    private Integer type;

    @Schema(description = "关联案件ID")
    private Long matterId;

    @Schema(description = "标签")
    private List<String> tags;

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