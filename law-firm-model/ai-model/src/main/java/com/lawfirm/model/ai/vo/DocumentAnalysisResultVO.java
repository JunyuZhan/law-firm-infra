package com.lawfirm.model.ai.vo;

import com.lawfirm.model.base.vo.BaseVO;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 文档分析结果VO
 * 用于DocProcessService返回文档分析结果
 */
public class DocumentAnalysisResultVO extends BaseVO {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "请求ID")
    private String requestId;        // 请求ID
    @Schema(description = "文档ID")
    private String documentId;       // 文档ID
    @Schema(description = "文档类型")
    private String documentType;     // 文档类型
    @Schema(description = "处理操作")
    private String operation;        // 处理操作
    @Schema(description = "分析时间")
    private Date analysisTime;       // 分析时间
    @Schema(description = "处理状态：success成功/failed失败")
    private String processStatus;    // 处理状态：success成功/failed失败
    @Schema(description = "错误信息（若有）")
    private String errorMessage;     // 错误信息（若有）
    @Schema(description = "分类结果及置信度")
    private transient Map<String, Double> classification; // 分类结果及置信度
    @Schema(description = "提取的关键信息")
    private transient Map<String, Object> extractedInfo; // 提取的关键信息
    @Schema(description = "摘要内容")
    private String summary;          // 摘要内容
    @Schema(description = "条款分析结果")
    private transient List<ClauseAnalysisVO> clauseAnalysis; // 条款分析结果
    @Schema(description = "元数据")
    private transient Map<String, Object> metadata; // 元数据
    
    /**
     * 默认构造函数，仅初始化自身字段，不调用父类方法
     */
    public DocumentAnalysisResultVO() {
        this.analysisTime = new Date();
        this.processStatus = "success";
        this.classification = new HashMap<>();
        this.extractedInfo = new HashMap<>();
        this.clauseAnalysis = new ArrayList<>();
        this.metadata = new HashMap<>();
    }
    
    /**
     * 创建一个完全初始化的DocumentAnalysisResultVO实例
     * @return 初始化的DocumentAnalysisResultVO实例
     */
    public static DocumentAnalysisResultVO createDefault() {
        DocumentAnalysisResultVO vo = new DocumentAnalysisResultVO();
        vo.setStatus(0); // 正常状态
        vo.setCreateTime(LocalDateTime.now());
        return vo;
    }
    
    // 条款分析VO
    public static class ClauseAnalysisVO implements Serializable {
        private static final long serialVersionUID = 1L;
        
        @Schema(description = "条款ID")
        private String clauseId;      // 条款ID
        @Schema(description = "条款标题")
        private String clauseTitle;   // 条款标题
        @Schema(description = "条款内容")
        private String clauseContent; // 条款内容
        @Schema(description = "条款类型")
        private String clauseType;    // 条款类型
        @Schema(description = "重要性：high高/medium中/low低")
        private String importance;    // 重要性：high高/medium中/low低
        @Schema(description = "风险评级：high高/medium中/low低/none无")
        private String risk;          // 风险评级：high高/medium中/low低/none无
        @Schema(description = "分析说明")
        private String analysis;      // 分析说明
        @Schema(description = "相关法规")
        private transient List<String> relatedLaws; // 相关法规
        
        public ClauseAnalysisVO() {
            this.relatedLaws = new ArrayList<>();
        }
        
        // Getter和Setter
        public String getClauseId() {
            return clauseId;
        }
        
        public void setClauseId(String clauseId) {
            this.clauseId = clauseId;
        }
        
        public String getClauseTitle() {
            return clauseTitle;
        }
        
        public void setClauseTitle(String clauseTitle) {
            this.clauseTitle = clauseTitle;
        }
        
        public String getClauseContent() {
            return clauseContent;
        }
        
        public void setClauseContent(String clauseContent) {
            this.clauseContent = clauseContent;
        }
        
        public String getClauseType() {
            return clauseType;
        }
        
        public void setClauseType(String clauseType) {
            this.clauseType = clauseType;
        }
        
        public String getImportance() {
            return importance;
        }
        
        public void setImportance(String importance) {
            this.importance = importance;
        }
        
        public String getRisk() {
            return risk;
        }
        
        public void setRisk(String risk) {
            this.risk = risk;
        }
        
        public String getAnalysis() {
            return analysis;
        }
        
        public void setAnalysis(String analysis) {
            this.analysis = analysis;
        }
        
        public List<String> getRelatedLaws() {
            return relatedLaws;
        }
        
        public void setRelatedLaws(List<String> relatedLaws) {
            this.relatedLaws = relatedLaws;
        }
    }
    
    // Getter和Setter
    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    public String getDocumentId() {
        return documentId;
    }
    
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    
    public String getDocumentType() {
        return documentType;
    }
    
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    public Date getAnalysisTime() {
        return analysisTime;
    }
    
    public void setAnalysisTime(Date analysisTime) {
        this.analysisTime = analysisTime;
    }
    
    public String getProcessStatus() {
        return processStatus;
    }
    
    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        if (errorMessage != null && !errorMessage.isEmpty()) {
            this.processStatus = "failed";
        }
    }
    
    public Map<String, Double> getClassification() {
        return classification;
    }
    
    public void setClassification(Map<String, Double> classification) {
        this.classification = classification;
    }
    
    public void addClassification(String type, Double confidence) {
        this.classification.put(type, confidence);
    }
    
    public Map<String, Object> getExtractedInfo() {
        return extractedInfo;
    }
    
    public void setExtractedInfo(Map<String, Object> extractedInfo) {
        this.extractedInfo = extractedInfo;
    }
    
    public void addExtractedInfo(String key, Object value) {
        this.extractedInfo.put(key, value);
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public List<ClauseAnalysisVO> getClauseAnalysis() {
        return clauseAnalysis;
    }
    
    public void setClauseAnalysis(List<ClauseAnalysisVO> clauseAnalysis) {
        this.clauseAnalysis = clauseAnalysis;
    }
    
    public void addClauseAnalysis(ClauseAnalysisVO clause) {
        this.clauseAnalysis.add(clause);
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
    
    public void addMetadata(String key, Object value) {
        this.metadata.put(key, value);
    }
} 