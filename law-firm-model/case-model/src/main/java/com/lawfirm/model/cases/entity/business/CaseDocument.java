package com.lawfirm.model.cases.entity.business;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.enums.doc.DocumentSecurityLevelEnum;
import com.lawfirm.model.cases.enums.doc.CaseDocumentStatusEnum;
import com.lawfirm.model.cases.enums.doc.CaseDocumentTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * 案件文档实体类
 * 
 * 案件文档记录了与案件相关的各类文档信息，包括文档基本信息、
 * 存储信息、安全级别、版本控制、文档状态等。
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("case_document")
@Schema(description = "案件文档实体类")
public class CaseDocument extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    @Schema(description = "案件ID")
    @TableField("case_id")
    private Long caseId;

    /**
     * 案件编号
     */
    @Schema(description = "案件编号")
    @TableField("case_number")
    private String caseNumber;

    /**
     * 文档名称
     */
    @Schema(description = "文档名称")
    @TableField("document_name")
    private String documentName;

    /**
     * 文档类型
     */
    @Schema(description = "文档类型")
    @TableField("document_type")
    private Integer documentType;

    /**
     * 文档状态
     */
    @Schema(description = "文档状态")
    @TableField("document_status")
    private Integer documentStatus;

    /**
     * 文档安全级别
     */
    @Schema(description = "文档安全级别")
    @TableField("security_level")
    private Integer securityLevel;

    /**
     * 文档路径
     */
    @Schema(description = "文档路径")
    @TableField("document_path")
    private String documentPath;

    /**
     * 文档格式
     */
    @Schema(description = "文档格式")
    @TableField("document_format")
    private String documentFormat;

    /**
     * 文档大小（字节）
     */
    @Schema(description = "文档大小（字节）")
    @TableField("document_size")
    private Long documentSize;

    /**
     * 文档描述
     */
    @Schema(description = "文档描述")
    @TableField("document_description")
    private String documentDescription;

    /**
     * 文档关键词
     */
    @Schema(description = "文档关键词")
    @TableField("document_keywords")
    private String documentKeywords;

    /**
     * 版本号
     */
    @Schema(description = "版本号")
    @TableField("version_number")
    private String versionNumber;

    /**
     * 是否为最新版本
     */
    @Schema(description = "是否为最新版本")
    @TableField("is_latest_version")
    private Boolean isLatestVersion;

    /**
     * 上一版本ID
     */
    @Schema(description = "上一版本ID")
    @TableField("previous_version_id")
    private Long previousVersionId;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    @TableField("creator_id")
    private Long creatorId;

    /**
     * 创建人姓名
     */
    @Schema(description = "创建人姓名")
    @TableField("creator_name")
    private String creatorName;

    /**
     * 最后修改人ID
     */
    @Schema(description = "最后修改人ID")
    @TableField("last_modifier_id")
    private Long lastModifierId;

    /**
     * 最后修改人姓名
     */
    @Schema(description = "最后修改人姓名")
    @TableField("last_modifier_name")
    private String lastModifierName;

    /**
     * 最后修改时间
     */
    @Schema(description = "最后修改时间")
    @TableField("last_modified_time")
    private transient LocalDateTime lastModifiedTime;

    /**
     * 是否加密
     */
    @Schema(description = "是否加密")
    @TableField("is_encrypted")
    private Boolean isEncrypted;

    /**
     * 加密方式
     */
    @Schema(description = "加密方式")
    @TableField("encryption_method")
    private String encryptionMethod;

    /**
     * 是否需要审核
     */
    @Schema(description = "是否需要审核")
    @TableField("need_review")
    private Boolean needReview;

    /**
     * 审核人ID
     */
    @Schema(description = "审核人ID")
    @TableField("reviewer_id")
    private Long reviewerId;

    /**
     * 审核人姓名
     */
    @Schema(description = "审核人姓名")
    @TableField("reviewer_name")
    private String reviewerName;

    /**
     * 审核时间
     */
    @Schema(description = "审核时间")
    @TableField("review_time")
    private transient LocalDateTime reviewTime;

    /**
     * 审核意见
     */
    @Schema(description = "审核意见")
    @TableField("review_opinion")
    private String reviewOpinion;

    /**
     * 是否对外提供
     */
    @Schema(description = "是否对外提供")
    @TableField("is_external")
    private Boolean isExternal;

    /**
     * 是否归档
     */
    @Schema(description = "是否归档")
    @TableField("is_archived")
    private Boolean isArchived;

    /**
     * 归档时间
     */
    @Schema(description = "归档时间")
    @TableField("archive_time")
    private transient LocalDateTime archiveTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @TableField("remarks")
    private String remarks;

    /**
     * 获取文档类型枚举
     */
    public CaseDocumentTypeEnum getDocumentTypeEnum() {
        return CaseDocumentTypeEnum.valueOf(this.documentType);
    }

    /**
     * 设置文档类型
     */
    public CaseDocument setDocumentTypeEnum(CaseDocumentTypeEnum documentTypeEnum) {
        this.documentType = documentTypeEnum != null ? documentTypeEnum.getValue() : null;
        return this;
    }

    /**
     * 获取文档状态枚举
     */
    public CaseDocumentStatusEnum getDocumentStatusEnum() {
        return CaseDocumentStatusEnum.valueOf(this.documentStatus);
    }

    /**
     * 设置文档状态
     */
    public CaseDocument setDocumentStatusEnum(CaseDocumentStatusEnum documentStatusEnum) {
        this.documentStatus = documentStatusEnum != null ? documentStatusEnum.getValue() : null;
        return this;
    }

    /**
     * 获取文档安全级别枚举
     */
    public DocumentSecurityLevelEnum getSecurityLevelEnum() {
        return DocumentSecurityLevelEnum.valueOf(this.securityLevel);
    }

    /**
     * 设置文档安全级别
     */
    public CaseDocument setSecurityLevelEnum(DocumentSecurityLevelEnum securityLevelEnum) {
        this.securityLevel = securityLevelEnum != null ? securityLevelEnum.getValue() : null;
        return this;
    }

    /**
     * 判断文档是否为草稿
     */
    public boolean isDraft() {
        return this.documentStatus != null && 
               this.getDocumentStatusEnum() == CaseDocumentStatusEnum.DRAFT;
    }

    /**
     * 判断文档是否已审核
     */
    public boolean isReviewed() {
        return this.documentStatus != null && 
               this.getDocumentStatusEnum() == CaseDocumentStatusEnum.REVIEWED;
    }

    /**
     * 判断文档是否已发布
     */
    public boolean isPublished() {
        return this.documentStatus != null && 
               this.getDocumentStatusEnum() == CaseDocumentStatusEnum.SIGNED;
    }

    /**
     * 判断文档是否已废弃
     */
    public boolean isDiscarded() {
        return this.documentStatus != null && 
               this.getDocumentStatusEnum() == CaseDocumentStatusEnum.VOID;
    }

    /**
     * 判断文档是否为高安全级别
     */
    public boolean isHighSecurityLevel() {
        return this.securityLevel != null && 
               (this.getSecurityLevelEnum() == DocumentSecurityLevelEnum.SECRET || 
                this.getSecurityLevelEnum() == DocumentSecurityLevelEnum.TOP_SECRET);
    }

    /**
     * 获取文档格式化大小
     */
    public String getFormattedSize() {
        if (this.documentSize == null) {
            return "0 B";
        }
        
        long size = this.documentSize;
        if (size < 1024) {
            return size + " B";
        } else if (size < 1024 * 1024) {
            return String.format("%.2f KB", size / 1024.0);
        } else if (size < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", size / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", size / (1024.0 * 1024 * 1024));
        }
    }

    /**
     * 获取文档扩展名
     */
    public String getExtension() {
        if (this.documentFormat != null && !this.documentFormat.isEmpty()) {
            return this.documentFormat;
        }
        
        if (this.documentPath != null && this.documentPath.contains(".")) {
            return this.documentPath.substring(this.documentPath.lastIndexOf(".") + 1);
        }
        
        return "";
    }

    /**
     * 判断是否为文本文档
     */
    public boolean isTextDocument() {
        String ext = getExtension().toLowerCase();
        return ext.equals("txt") || ext.equals("doc") || ext.equals("docx") || 
               ext.equals("pdf") || ext.equals("rtf") || ext.equals("odt");
    }

    /**
     * 判断是否为图片
     */
    public boolean isImage() {
        String ext = getExtension().toLowerCase();
        return ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png") || 
               ext.equals("gif") || ext.equals("bmp") || ext.equals("tiff");
    }

    /**
     * 判断是否为视频
     */
    public boolean isVideo() {
        String ext = getExtension().toLowerCase();
        return ext.equals("mp4") || ext.equals("avi") || ext.equals("mov") || 
               ext.equals("wmv") || ext.equals("flv") || ext.equals("mkv");
    }

    /**
     * 判断是否为音频
     */
    public boolean isAudio() {
        String ext = getExtension().toLowerCase();
        return ext.equals("mp3") || ext.equals("wav") || ext.equals("ogg") || 
               ext.equals("wma") || ext.equals("aac") || ext.equals("flac");
    }
} 