package com.lawfirm.document.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文档实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("document")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文档编号
     */
    private String docNo;

    /**
     * 文档名称
     */
    private String docName;

    /**
     * 文档类型（1:合同、2:协议、3:报告、4:其他）
     */
    private Integer docType;

    /**
     * 文件类型（doc/docx/pdf等）
     */
    private String fileType;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 存储路径
     */
    private String storagePath;

    /**
     * 当前版本号
     */
    private Integer version;

    /**
     * 状态（1:正常、2:已归档、3:已删除）
     */
    private Integer status;

    /**
     * 关键词
     */
    private String keywords;

    /**
     * 文档描述
     */
    private String description;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String updateBy;

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