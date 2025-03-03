package com.lawfirm.model.storage.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.storage.enums.StorageTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 存储基类，定义存储的基本属性
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public abstract class BaseStorage extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 存储类型
     */
    @TableField("storage_type")
    private StorageTypeEnum storageType;

    /**
     * 存储路径
     */
    @TableField("storage_path")
    private String storagePath;

    /**
     * 存储大小（字节）
     */
    @TableField("storage_size")
    private Long storageSize;

    /**
     * 存储状态（0：无效，1：有效）
     */
    @TableField("status")
    private Integer status = 1;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
} 