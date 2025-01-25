package com.lawfirm.model.document.entity;

import java.time.LocalDateTime;

import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.base.status.StatusAware;
import com.lawfirm.model.document.enums.DocumentSecurityLevelEnum;
import com.lawfirm.model.document.enums.DocumentStatusEnum;
import com.lawfirm.model.document.enums.DocumentTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "document_info")
@EqualsAndHashCode(callSuper = true)
public class Document extends BaseEntity implements StatusAware {

    @NotBlank(message = "文档编号不能为空")
    @Size(max = 50, message = "文档编号长度不能超过50个字符")
    @Column(nullable = false, length = 50, unique = true)
    private String documentNumber;

    @NotBlank(message = "文档名称不能为空")
    @Size(max = 200, message = "文档名称长度不能超过200个字符")
    @Column(nullable = false, length = 200)
    private String documentName;

    @NotNull(message = "文档类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DocumentTypeEnum documentType;

    @NotNull(message = "文档密级不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DocumentSecurityLevelEnum securityLevel = DocumentSecurityLevelEnum.INTERNAL;

    @NotNull(message = "文档状态不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DocumentStatusEnum status = DocumentStatusEnum.DRAFT;

    // 关联信息
    private Long lawFirmId;  // 律所ID
    private Long caseId;     // 案件ID

    // 文件信息
    @Size(max = 500, message = "文件路径长度不能超过500个字符")
    @Column(length = 500)
    private String filePath;  // 文件存储路径

    private Long fileSize;    // 文件大小（字节）

    @Size(max = 100, message = "文件类型长度不能超过100个字符")
    @Column(length = 100)
    private String fileType;  // 文件类型（MIME类型）

    @Size(max = 100, message = "文件Hash长度不能超过100个字符")
    @Column(length = 100)
    private String fileHash;  // 文件Hash值

    // 内容信息
    @Size(max = 500, message = "关键词长度不能超过500个字符")
    @Column(length = 500)
    private String keywords;  // 关键词，多个关键词用逗号分隔

    @Column(length = 1000)
    private String summary;   // 文档摘要

    // 版本信息
    @Column(nullable = false)
    private Integer version = 1;  // 当前版本号

    private LocalDateTime lastModifiedTime;  // 最后修改时间

    @Size(max = 50, message = "最后修改人长度不能超过50个字符")
    @Column(length = 50)
    private String lastModifiedBy;  // 最后修改人

    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Column(length = 500)
    private String remark;  // 备注信息

    @Override
    public StatusEnum getStatus() {
        if (status == null) {
            return StatusEnum.DISABLED;
        }
        switch (status) {
            case DRAFT:
            case REVIEWING:
                return StatusEnum.ENABLED;
            case APPROVED:
            case PUBLISHED:
                return StatusEnum.ENABLED;
            case REJECTED:
                return StatusEnum.DISABLED;
            case ARCHIVED:
                return StatusEnum.LOCKED;
            case DELETED:
                return StatusEnum.DELETED;
            default:
                return StatusEnum.DISABLED;
        }
    }

    @Override
    public void setStatus(StatusEnum status) {
        if (status == null) {
            this.status = DocumentStatusEnum.DRAFT;
            return;
        }
        switch (status) {
            case ENABLED:
                if (this.status == null || this.status == DocumentStatusEnum.REJECTED) {
                    this.status = DocumentStatusEnum.DRAFT;
                }
                break;
            case DISABLED:
                this.status = DocumentStatusEnum.REJECTED;
                break;
            case DELETED:
                this.status = DocumentStatusEnum.DELETED;
                break;
            case LOCKED:
                this.status = DocumentStatusEnum.ARCHIVED;
                break;
            case EXPIRED:
                this.status = DocumentStatusEnum.ARCHIVED;
                break;
        }
    }
} 