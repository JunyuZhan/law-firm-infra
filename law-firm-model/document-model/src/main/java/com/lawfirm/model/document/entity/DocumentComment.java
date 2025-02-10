package com.lawfirm.model.document.entity;

import com.lawfirm.model.document.entity.base.AuditableDocumentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 文档评注实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document_comment")
public class DocumentComment extends AuditableDocumentEntity<DocumentComment> {

    @NotNull(message = "文档ID不能为空")
    @Column(nullable = false)
    private Long documentId;      // 文档ID

    @NotNull(message = "评注内容不能为空")
    @Size(max = 2000, message = "评注内容长度不能超过2000个字符")
    @Column(nullable = false, length = 2000)
    private String content;       // 评注内容

    @Size(max = 100, message = "评注位置长度不能超过100个字符")
    @Column(length = 100)
    private String location;      // 评注位置（如页码、坐标等）

    @Size(max = 50, message = "评注类型长度不能超过50个字符")
    @Column(length = 50)
    private String type;          // 评注类型（文本/图形等）

    @Column
    private Integer pageNumber;   // 页码

    @Column(length = 100)
    private String coordinates;   // 坐标信息（JSON格式）

    @Size(max = 500, message = "引用文本长度不能超过500个字符")
    @Column(length = 500)
    private String quotedText;    // 引用的文本内容

    @Column(length = 100)
    private String replyTo;       // 回复的评注ID

    @Column
    private Boolean resolved = false;  // 是否已解决

    @Column(length = 100)
    private String resolvedBy;    // 解决人

    @Column
    private LocalDateTime resolvedTime;  // 解决时间

    @Column(length = 500)
    private String resolution;    // 解决方案
} 