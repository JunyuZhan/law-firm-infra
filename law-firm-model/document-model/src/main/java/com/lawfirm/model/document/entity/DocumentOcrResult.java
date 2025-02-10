package com.lawfirm.model.document.entity;

import com.lawfirm.model.document.entity.base.AuditableDocumentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 文档OCR结果实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document_ocr_result")
public class DocumentOcrResult extends AuditableDocumentEntity<DocumentOcrResult> {

    @NotNull(message = "文档ID不能为空")
    @Column(nullable = false)
    private Long documentId;              // 文档ID

    @Column(nullable = false)
    private Integer pageNo;               // 页码

    @Column(columnDefinition = "TEXT")
    private String content;               // OCR识别内容

    @Column(precision = 10, scale = 2)
    private Double confidence;            // 识别置信度

    @Column(columnDefinition = "TEXT")
    private String structuredData;        // 结构化数据（JSON格式）

    @Column(length = 100)
    private String language;              // 识别语言

    @Column
    private Integer wordCount;            // 字数统计

    @Column(columnDefinition = "TEXT")
    private String positions;             // 文字位置信息（JSON格式）

    @Column(length = 500)
    private String remark;                // 备注
} 