package com.lawfirm.document.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文档版本实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("document_version")
public class DocumentVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文档ID
     */
    private Long docId;

    /**
     * 版本号
     */
    private Integer versionNo;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 存储路径
     */
    private String storagePath;

    /**
     * 变更说明
     */
    private String changeDesc;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableLogic
    private Integer deleted;

    /**
     * 备注
     */
    private String remark;
} 