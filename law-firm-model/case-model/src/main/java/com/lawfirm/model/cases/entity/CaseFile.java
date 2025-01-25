package com.lawfirm.model.cases.entity;

import com.lawfirm.model.base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "case_file")
@EqualsAndHashCode(callSuper = true)
public class CaseFile extends BaseEntity {

    @NotNull(message = "案件ID不能为空")
    @Column(nullable = false)
    private Long caseId;

    @NotBlank(message = "文件名不能为空")
    @Size(max = 200, message = "文件名长度不能超过200个字符")
    @Column(nullable = false, length = 200)
    private String fileName;

    @Column(length = 500)
    private String filePath;

    private Long fileSize;

    @Column(length = 100)
    private String contentType;

    @Column(length = 32)
    private String fileMd5;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean isConfidential = false;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;
} 