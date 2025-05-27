package com.lawfirm.model.cases.dto.business;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.doc.DocumentSecurityLevelEnum;
import com.lawfirm.model.cases.enums.doc.CaseDocumentStatusEnum;
import com.lawfirm.model.cases.enums.doc.CaseDocumentTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件文档数据传输对象
 * 
 * 包含文档的基本信息，如文档名称、类型、状态、安全级别等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseDocumentDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 文档名称
     */
    private String documentName;

    /**
     * 文档类型
     */
    private CaseDocumentTypeEnum documentType;

    /**
     * 文档状态
     */
    private CaseDocumentStatusEnum documentStatus;

    /**
     * 文档安全级别
     */
    private DocumentSecurityLevelEnum securityLevel;

    /**
     * 文档路径
     */
    private String documentPath;

    /**
     * 文档格式
     */
    private String documentFormat;

    /**
     * 文档大小（字节）
     */
    private Long documentSize;

    /**
     * 文档描述
     */
    private String documentDescription;

    /**
     * 文档关键词
     */
    private String documentKeywords;

    /**
     * 版本号
     */
    private String versionNumber;

    /**
     * 是否为最新版本
     */
    private Boolean isLatestVersion;

    /**
     * 上一版本ID
     */
    private Long previousVersionId;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 最后修改人ID
     */
    private Long lastModifierId;

    /**
     * 最后修改人姓名
     */
    private String lastModifierName;

    /**
     * 最后修改时间
     */
    private transient LocalDateTime lastModifiedTime;

    /**
     * 是否加密
     */
    private Boolean isEncrypted;

    /**
     * 加密方式
     */
    private String encryptionMethod;

    /**
     * 是否需要审核
     */
    private Boolean needReview;

    /**
     * 审核人ID
     */
    private Long reviewerId;

    /**
     * 审核人姓名
     */
    private String reviewerName;

    /**
     * 审核时间
     */
    private transient LocalDateTime reviewTime;

    /**
     * 审核意见
     */
    private String reviewOpinion;

    /**
     * 是否对外提供
     */
    private Boolean isExternal;

    /**
     * 是否归档
     */
    private Boolean isArchived;

    /**
     * 归档时间
     */
    private transient LocalDateTime archiveTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 文件内容（Base64编码，用于文件上传）
     */
    private String fileContent;

    /**
     * 获取格式化大小
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