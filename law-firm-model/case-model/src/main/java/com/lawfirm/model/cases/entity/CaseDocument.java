package com.lawfirm.model.cases.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.enums.DocumentSecurityLevelEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "case_document")
public class CaseDocument extends ModelBaseEntity<CaseDocument> {

    @NotNull(message = "案件ID不能为空")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    @Comment("所属案件")
    private Case caseInfo;

    @NotBlank(message = "文档名称不能为空")
    @Size(max = 200, message = "文档名称长度不能超过200个字符")
    @Column(nullable = false, length = 200)
    @Comment("文档名称")
    private String documentName;

    @Column(length = 500)
    @Comment("文档存储路径")
    private String documentPath;

    @NotNull(message = "文档类型不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    @Comment("文档类型")
    private DocumentType documentType = DocumentType.OTHER;

    @Column(length = 500)
    @Comment("文档描述")
    private String description;

    @NotNull(message = "文件大小不能为空")
    @Comment("文件大小（字节）")
    private Long fileSize;

    @NotBlank(message = "文件类型不能为空")
    @Column(length = 100)
    @Comment("文件类型（MIME类型）")
    private String fileType;

    @NotNull(message = "安全级别不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("文档安全级别")
    private DocumentSecurityLevelEnum securityLevel;

    @Column(nullable = false)
    @Comment("是否加密")
    private Boolean encrypted = false;

    @Column(length = 64)
    @Comment("文档版本号")
    private Integer version;

    @Column
    @Comment("文档过期时间")
    private LocalDateTime expiryTime;

    @Column(nullable = false)
    @Comment("是否需要审批")
    private Boolean needApproval = false;

    @Column(nullable = false)
    @Comment("是否已审批")
    private Boolean approved = false;

    @Column(length = 64)
    @Comment("审批人")
    private String approver;

    @Column
    @Comment("审批时间")
    private LocalDateTime approvalTime;

    @Column(length = 500)
    @Comment("审批意见")
    private String approvalComment;

    @Column(nullable = false)
    @Comment("是否允许下载")
    private Boolean allowDownload = true;

    @Column(nullable = false)
    @Comment("是否允许打印")
    private Boolean allowPrint = true;

    @Column(length = 32)
    @Comment("文件MD5")
    private String md5;

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