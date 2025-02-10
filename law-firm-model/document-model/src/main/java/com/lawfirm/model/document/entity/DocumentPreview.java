package com.lawfirm.model.document.entity;

import com.lawfirm.model.document.entity.base.AuditableDocumentEntity;
import com.lawfirm.model.document.enums.DocumentPreviewStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档预览实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document_preview")
public class DocumentPreview extends AuditableDocumentEntity<DocumentPreview> {

    @NotNull(message = "文档ID不能为空")
    @Column(nullable = false)
    private Long documentId;              // 文档ID

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private DocumentPreviewStatusEnum previewStatus = DocumentPreviewStatusEnum.NOT_STARTED;  // 预览状态

    @Size(max = 500, message = "预览URL长度不能超过500个字符")
    @Column(length = 500)
    private String previewUrl;            // 预览文件URL

    @Column
    private LocalDateTime previewGenerateTime;  // 预览生成时间

    @Column
    private Long previewFileSize;         // 预览文件大小

    @Size(max = 500, message = "错误信息长度不能超过500个字符")
    @Column(length = 500)
    private String errorMessage;          // 错误信息（预览失败时）

    @Column
    private Integer retryCount = 0;       // 重试次数

    @Size(max = 100, message = "预览格式长度不能超过100个字符")
    @Column(length = 100)
    private String previewFormat;         // 预览格式（PDF/HTML等）

    @Column
    private Integer previewQuality;       // 预览质量（1-100）

    @Size(max = 200, message = "水印内容长度不能超过200个字符")
    @Column(length = 200)
    private String watermark;             // 水印内容

    @Column
    private Boolean enableDownload = false;  // 允许下载

    @Column
    private Integer pageCount;            // 总页数

    @Size(max = 100, message = "源文件类型长度不能超过100个字符")
    @Column(length = 100)
    private String sourceType;            // 源文件类型

    @Size(max = 500, message = "回调地址长度不能超过500个字符")
    @Column(length = 500)
    private String callback;              // 回调地址
} 