package com.lawfirm.model.cases.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "case_document")
@EqualsAndHashCode(callSuper = true)
public class CaseDocument extends ModelBaseEntity {

    @NotNull(message = "案件ID不能为空")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    private Case caseInfo;

    @NotBlank(message = "文档名称不能为空")
    @Size(max = 200, message = "文档名称长度不能超过200个字符")
    @Column(nullable = false, length = 200)
    private String documentName;

    @Column(length = 500)
    private String documentPath;  // 文档存储路径

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DocumentType documentType = DocumentType.OTHER;

    @Column(length = 500)
    private String description;  // 文档描述

    private Long fileSize;  // 文件大小（字节）

    @Column(length = 100)
    private String fileType;  // 文件类型（MIME类型）

    public enum DocumentType {
        CONTRACT("合同文书"),
        EVIDENCE("证据材料"),
        COURT_DOC("法院文书"),
        LEGAL_OPINION("法律意见"),
        MEETING_RECORD("会议记录"),
        CORRESPONDENCE("往来函件"),
        OTHER("其他文档");

        private final String description;

        DocumentType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
} 