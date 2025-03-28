package com.lawfirm.model.knowledge.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 知识附件DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KnowledgeAttachmentDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 知识ID
     */
    private Long knowledgeId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 排序号
     */
    private Integer sort;
} 