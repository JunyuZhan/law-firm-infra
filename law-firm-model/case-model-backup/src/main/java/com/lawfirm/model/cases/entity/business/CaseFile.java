package com.lawfirm.model.cases.entity.business;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.enums.doc.FileSecurityLevelEnum;
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
@Table(name = "case_file")
public class CaseFile extends ModelBaseEntity {

    @NotNull(message = "案件ID不能为空")
    @Column(nullable = false)
    @Comment("案件ID")
    private Long caseId;

    @NotBlank(message = "文件名不能为空")
    @Size(max = 200, message = "文件名长度不能超过200个字符")
    @Column(nullable = false, length = 200)
    @Comment("文件名")
    private String fileName;

    @Column(length = 500)
    @Comment("文件路径")
    private String filePath;

    @NotNull(message = "文件大小不能为空")
    @Comment("文件大小（字节）")
    private Long fileSize;

    @NotBlank(message = "文件类型不能为空")
    @Column(length = 100)
    @Comment("文件类型（MIME类型）")
    private String contentType;

    @Column(length = 32)
    @Comment("文件MD5")
    private String fileMd5;

    @Size(max = 500, message = "描述长度不能超过500个字符")
    @Column(length = 500)
    @Comment("文件描述")
    private String description;

    @NotNull(message = "安全级别不能为空")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("安全级别")
    private FileSecurityLevelEnum securityLevel;

    @Column(nullable = false)
    @Comment("是否加密")
    private Boolean encrypted = false;

    @Column(length = 64)
    @Comment("加密密钥")
    private String encryptKey;

    @Column(nullable = false)
    @Comment("是否允许下载")
    private Boolean allowDownload = true;

    @Column(nullable = false)
    @Comment("是否允许打印")
    private Boolean allowPrint = true;

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

    @Column
    @Comment("文件过期时间")
    private LocalDateTime expiryTime;

    @Column(length = 64)
    @Comment("文件版本")
    private Integer version;

    @Column(length = 64)
    @Comment("上传人")
    private String uploader;

    @Column
    @Comment("上传时间")
    private LocalDateTime uploadTime = LocalDateTime.now();

    @Column(length = 64)
    @Comment("文件分类")
    private String category;

    @Column(length = 64)
    @Comment("文件标签，多个用逗号分隔")
    private String tags;

    @Column(nullable = false)
    @Comment("是否为临时文件")
    private Boolean temporary = false;

    @Column
    @Comment("临时文件过期时间")
    private LocalDateTime temporaryExpireTime;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Comment("文件内容")
    private byte[] content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", insertable = false, updatable = false)
    @Comment("所属案件")
    private Case caseInfo;
} 