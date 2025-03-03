package com.lawfirm.model.storage.dto.bucket;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.storage.enums.BucketTypeEnum;
import com.lawfirm.model.storage.enums.StorageTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 存储桶创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BucketCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 桶名称
     */
    @NotBlank(message = "桶名称不能为空")
    private String bucketName;

    /**
     * 存储类型
     */
    @NotNull(message = "存储类型不能为空")
    private StorageTypeEnum storageType;

    /**
     * 桶类型
     */
    @NotNull(message = "桶类型不能为空")
    private BucketTypeEnum bucketType;

    /**
     * 访问域名
     */
    private String domain;

    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 密钥密文
     */
    private String secretKey;

    /**
     * 配置信息（JSON格式）
     */
    private String config;

    /**
     * 最大存储容量（字节）
     */
    private Long maxSize;

    /**
     * 备注
     */
    private String remark;
} 