package com.lawfirm.cases.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 案件文件实体类
 */
@Data
@TableName("case_file")
@EqualsAndHashCode(callSuper = true)
public class CaseFile extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小(字节)
     */
    private Long fileSize;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件MD5
     */
    private String fileMd5;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 是否为机密文件
     */
    private Boolean isConfidential;
} 