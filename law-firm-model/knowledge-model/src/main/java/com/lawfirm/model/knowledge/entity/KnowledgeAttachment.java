package com.lawfirm.model.knowledge.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 知识附件实体
 */
@Data
@TableName("knowledge_attachment")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KnowledgeAttachment extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 知识ID
     */
    @TableField("knowledge_id")
    private Long knowledgeId;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 文件大小（字节）
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 文件类型
     */
    @TableField("file_type")
    private String fileType;

    /**
     * 排序号
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 存储ID（关联存储系统）
     */
    @TableField("storage_id")
    private Long storageId;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;
} 