package com.lawfirm.model.ai.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 案例分析请求DTO
 * 用于DecisionSupportService处理案例分析
 */
public class CaseAnalysisRequestDTO extends BaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    private String caseId;           // 案件ID（可选，针对已有案件）
    private String caseTitle;        // 案件标题
    private String caseDescription;  // 案件描述
    private String caseType;         // 案件类型
    private String clientType;       // 客户类型：plaintiff原告/defendant被告/other其他
    private transient List<String> relatedDocuments; // 相关文档ID列表
    private String analysisType;     // 分析类型：risk风险评估/outcome结果预测/precedent判例推荐/laws法规建议
    private transient Map<String, Object> caseDetails; // 案件详情
    private transient Map<String, Object> parameters;  // 额外参数
    
    public CaseAnalysisRequestDTO() {
        this.relatedDocuments = new ArrayList<>();
        this.caseDetails = new HashMap<>();
        this.parameters = new HashMap<>();
    }
    
    // Getter和Setter
    public String getCaseId() {
        return caseId;
    }
    
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
    
    public String getCaseTitle() {
        return caseTitle;
    }
    
    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }
    
    public String getCaseDescription() {
        return caseDescription;
    }
    
    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }
    
    public String getCaseType() {
        return caseType;
    }
    
    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }
    
    public String getClientType() {
        return clientType;
    }
    
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
    
    public List<String> getRelatedDocuments() {
        return relatedDocuments;
    }
    
    public void setRelatedDocuments(List<String> relatedDocuments) {
        this.relatedDocuments = relatedDocuments;
    }
    
    public void addRelatedDocument(String documentId) {
        this.relatedDocuments.add(documentId);
    }
    
    public String getAnalysisType() {
        return analysisType;
    }
    
    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }
    
    public Map<String, Object> getCaseDetails() {
        return caseDetails;
    }
    
    public void setCaseDetails(Map<String, Object> caseDetails) {
        this.caseDetails = caseDetails;
    }
    
    public void addCaseDetail(String key, Object value) {
        this.caseDetails.put(key, value);
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
        return "CaseAnalysisRequestDTO{" +
                "caseId='" + caseId + '\'' +
                ", caseTitle='" + caseTitle + '\'' +
                ", caseType='" + caseType + '\'' +
                ", clientType='" + clientType + '\'' +
                ", analysisType='" + analysisType + '\'' +
                ", relatedDocuments=" + relatedDocuments.size() +
                ", id=" + getId() +
                '}';
    }
} 