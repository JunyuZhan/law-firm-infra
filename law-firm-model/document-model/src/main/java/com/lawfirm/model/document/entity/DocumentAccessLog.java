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
 * 文档访问日志实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "doc_document_access_log")
public class DocumentAccessLog extends AuditableDocumentEntity<DocumentAccessLog> {

    @NotNull(message = "文档ID不能为空")
    @Column(nullable = false)
    private Long documentId;          // 文档ID

    @Size(max = 50, message = "访问类型长度不能超过50个字符")
    @Column(length = 50)
    private String accessType;        // 访问类型（查看、下载、预览等）

    @Size(max = 50, message = "访问用户长度不能超过50个字符")
    @Column(length = 50)
    private String accessUser;        // 访问用户

    @Column
    private LocalDateTime accessTime; // 访问时间

    @Size(max = 100, message = "客户端信息长度不能超过100个字符")
    @Column(length = 100)
    private String clientInfo;        // 客户端信息

    @Column
    private String ipAddress;         // IP地址

    @Column
    private String sessionId;         // 会话ID

    @Column
    private Long responseTime;        // 响应时间（毫秒）

    @Column
    private Boolean isSuccess;        // 是否成功

    @Size(max = 500, message = "错误信息长度不能超过500个字符")
    @Column(length = 500)
    private String errorMessage;      // 错误信息

    @Column
    private Long dataSize;           // 数据大小（字节）

    @Size(max = 200, message = "请求URL长度不能超过200个字符")
    @Column(length = 200)
    private String requestUrl;        // 请求URL

    @Size(max = 50, message = "请求方法长度不能超过50个字符")
    @Column(length = 50)
    private String requestMethod;     // 请求方法
} 