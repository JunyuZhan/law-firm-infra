package com.lawfirm.model.ai.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 文档处理请求DTO
 * 用于DocProcessService处理文档
 */
public class DocumentProcessRequestDTO extends BaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    private String documentId;      // 文档ID（可选，如果是已存在的文档）
    private String documentContent; // 文档内容
    private String documentType;    // 文档类型（可选，如合同、起诉书、判决书等）
    private String documentFormat;  // 文档格式（如txt、docx、pdf等）
    private String language;        // 语言（默认中文）
    private String operation;       // 操作类型（classify分类/extract提取/summarize摘要等）
    private transient Map<String, Object> parameters; // 额外参数
    
    public DocumentProcessRequestDTO() {
        this.language = "zh_CN";
        this.parameters = new HashMap<>();
    }
    
    public DocumentProcessRequestDTO(String documentContent, String operation) {
        this();
        this.documentContent = documentContent;
        this.operation = operation;
    }
    
    // Getter和Setter
    public String getDocumentId() {
        return documentId;
    }
    
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    
    public String getDocumentContent() {
        return documentContent;
    }
    
    public void setDocumentContent(String documentContent) {
        this.documentContent = documentContent;
    }
    
    public String getDocumentType() {
        return documentType;
    }
    
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    
    public String getDocumentFormat() {
        return documentFormat;
    }
    
    public void setDocumentFormat(String documentFormat) {
        this.documentFormat = documentFormat;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    public Map<String, Object> getParameters() {
        return parameters;
    }
    
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
    
    public void addParameter(String key, Object value) {
        this.parameters.put(key, value);
    }
    
    @Override
    public String toString() {
        return "DocumentProcessRequestDTO{" +
                "documentId='" + documentId + '\'' +
                ", documentType='" + documentType + '\'' +
                ", operation='" + operation + '\'' +
                ", contentLength=" + (documentContent != null ? documentContent.length() : 0) +
                ", id=" + getId() +
                '}';
    }
} 