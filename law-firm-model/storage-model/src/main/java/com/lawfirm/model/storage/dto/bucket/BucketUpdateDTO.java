package com.lawfirm.model.storage.dto.bucket;

import com.lawfirm.model.storage.enums.BucketTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 存储桶更新DTO
 * 
 * @author JunyuZhan
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "存储桶更新DTO")
public class BucketUpdateDTO {

    /**
     * 存储桶ID
     */
    @NotNull(message = "存储桶ID不能为空")
    @Schema(description = "存储桶ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long bucketId;

    /**
     * 存储桶名称
     */
    @NotBlank(message = "存储桶名称不能为空")
    @Size(min = 3, max = 63, message = "存储桶名称长度必须在3-63个字符之间")
    @Schema(description = "存储桶名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bucketName;

    /**
     * 存储桶类型
     */
    @NotNull(message = "存储桶类型不能为空")
    @Schema(description = "存储桶类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private BucketTypeEnum bucketType;

    /**
     * 存储桶描述
     */
    @Size(max = 255, message = "存储桶描述长度不能超过255个字符")
    @Schema(description = "存储桶描述")
    private String description;

    /**
     * 访问域名
     */
    @Schema(description = "访问域名")
    private String domain;
    
    /**
     * 设置ID，映射到bucketId
     * 
     * @param id ID
     * @return 当前对象
     */
    public BucketUpdateDTO setId(Long id) {
        this.bucketId = id;
        return this;
    }
    
    /**
     * 获取ID，实际获取bucketId
     * 
     * @return bucketId
     */
    public Long getId() {
        return this.bucketId;
    }
} 
