package com.lawfirm.model.storage.vo.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 分片上传结果VO
 * 
 * @author JunyuZhan
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "分片上传结果VO")
public class ChunkUploadVO {

    /**
     * 文件唯一标识
     */
    @Schema(description = "文件唯一标识")
    private String fileIdentifier;

    /**
     * 当前分片序号
     */
    @Schema(description = "分片序号")
    private Integer chunkNumber;

    /**
     * 分片存储路径
     */
    @Schema(description = "分片存储路径")
    private String chunkPath;

    /**
     * 上传状�?     */
    @Schema(description = "上传状�?, allowableValues = {"success", "failure"})
    private String status;

    /**
     * 错误消息
     */
    @Schema(description = "错误消息")
    private String message;

    /**
     * 分片上传完成后是否所有分片都已上�?     */
    @Schema(description = "是否所有分片都已上�?)
    private Boolean completed;
} 
