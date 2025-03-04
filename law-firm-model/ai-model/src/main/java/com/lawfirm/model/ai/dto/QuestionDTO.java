package com.lawfirm.model.ai.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import java.io.Serializable;

/**
 * 法律问题DTO
 * 用于向QAService提交法律问题
 */
public class QuestionDTO extends BaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    private String question;        // 问题内容
    private String userContext;     // 用户上下文信息（可选）
    private String language;        // 语言（默认中文）
    private String category;        // 问题类别（如合同法、刑法等）
    private Integer maxResults;     // 最大返回结果数
    private Boolean needReferences; // 是否需要引用来源
    
    // 构造函数
    public QuestionDTO() {
    }
    
    public QuestionDTO(String question) {
        this.question = question;
        this.language = "zh_CN";
        this.maxResults = 1;
        this.needReferences = false;
    }
    
    // Getter和Setter
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public String getUserContext() {
        return userContext;
    }
    
    public void setUserContext(String userContext) {
        this.userContext = userContext;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public Integer getMaxResults() {
        return maxResults;
    }
    
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }
    
    public Boolean getNeedReferences() {
        return needReferences;
    }
    
    public void setNeedReferences(Boolean needReferences) {
        this.needReferences = needReferences;
    }
    
    @Override
    public String toString() {
        return "QuestionDTO{" +
                "question='" + question + '\'' +
                ", category='" + category + '\'' +
                ", language='" + language + '\'' +
                ", needReferences=" + needReferences +
                ", id=" + getId() +
                '}';
    }
} 