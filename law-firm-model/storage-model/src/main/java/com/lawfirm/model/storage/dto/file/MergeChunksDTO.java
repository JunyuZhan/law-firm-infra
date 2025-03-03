package com.lawfirm.model.storage.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 分片合并DTO
 * 
 * @author AI助手
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "分片合并参数DTO")
public class MergeChunksDTO {

    /**
     * 文件唯一标识
     */
    @NotBlank(message = "文件唯一标识不能为空")
    @Schema(description = "文件唯一标识", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileIdentifier;

    /**
     * 上传的文件名
     */
    @NotBlank(message = "文件名不能为空")
    @Schema(description = "原始文件名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String filename;

    /**
     * 文件大小（字节）
     */
    @NotNull(message = "文件大小不能为空")
    @Min(value = 1, message = "文件大小必须大于0")
    @Schema(description = "文件大小（字节）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long totalSize;

    /**
     * 分片总数
     */
    @NotNull(message = "分片总数不能为空")
    @Min(value = 1, message = "分片总数必须大于0")
    @Schema(description = "分片总数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer totalChunks;

    /**
     * 所属存储桶ID
     */
    @NotNull(message = "存储桶ID不能为空")
    @Schema(description = "存储桶ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long bucketId;

    /**
     * 分片路径列表
     */
    @NotNull(message = "分片路径列表不能为空")
    @Schema(description = "分片路径列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> chunkPaths;
} 