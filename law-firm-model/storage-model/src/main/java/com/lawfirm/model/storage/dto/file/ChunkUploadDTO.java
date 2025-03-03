package com.lawfirm.model.storage.dto.file;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 分片上传DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ChunkUploadDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 文件唯一标识
     */
    @NotBlank(message = "文件ID不能为空")
    private String fileId;

    /**
     * 文件名
     */
    @NotBlank(message = "文件名不能为空")
    private String filename;

    /**
     * 分片索引，从0开始
     */
    @NotNull(message = "分片索引不能为空")
    @Min(value = 0, message = "分片索引必须大于等于0")
    private Integer chunkIndex;

    /**
     * 总分片数
     */
    @NotNull(message = "总分片数不能为空")
    @Min(value = 1, message = "总分片数必须大于0")
    private Integer totalChunks;

    /**
     * 分片大小（字节）
     */
    @NotNull(message = "分片大小不能为空")
    @Min(value = 1, message = "分片大小必须大于0")
    private Long chunkSize;

    /**
     * 文件总大小（字节）
     */
    @NotNull(message = "文件总大小不能为空")
    @Min(value = 1, message = "文件总大小必须大于0")
    private Long totalSize;
    
    /**
     * 存储桶ID，可为空使用默认桶
     */
    private Long bucketId;
    
    /**
     * 文件MD5校验码
     */
    private String md5;
} 