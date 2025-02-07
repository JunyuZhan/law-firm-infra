package com.lawfirm.cases.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.cases.enums.DocumentTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 案件文档实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("case_document")
public class CaseDocument extends BaseEntity {

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 文档名称
     */
    private String name;

    /**
     * 文档类型
     */
    private DocumentTypeEnum type;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小(字节)
     */
    private Long fileSize;

    /**
     * 上传人ID
     */
    private Long uploaderId;

    /**
     * 上传人姓名
     */
    private String uploaderName;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;
} 