package com.lawfirm.model.ai.vo;

import com.lawfirm.model.base.vo.BaseVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 法律问答结果VO
 * 用于QAService返回法律问题的答案
 */
public class AnswerVO extends BaseVO {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "问题ID")
    private String questionId;    // 问题ID
    @Schema(description = "原始问题")
    private String question;      // 原始问题
    @Schema(description = "回答内容")
    private String answer;        // 回答内容
    @Schema(description = "置信度（0-1）")
    private Double confidence;    // 置信度（0-1）
    @Schema(description = "回答时间")
    private Date answerTime;      // 回答时间
    @Schema(description = "回答来源（模型名称）")
    private String answerSource;  // 回答来源（模型名称）
    @Schema(description = "引用来源列表")
    private transient List<ReferenceItem> references; // 引用来源列表
    
    public AnswerVO() {
        this.references = new ArrayList<>();
        this.answerTime = new Date();
    }
    
    // 引用来源项
    public static class ReferenceItem implements Serializable {
        private static final long serialVersionUID = 1L;
        
        @Schema(description = "标题")
        private String title;      // 标题
        @Schema(description = "引用内容")
        private String content;    // 引用内容
        @Schema(description = "来源（如法规名称、案例编号等）")
        private String source;     // 来源（如法规名称、案例编号等）
        @Schema(description = "相关性（0-1）")
        private Double relevance;  // 相关性（0-1）
        
        public ReferenceItem() {
        }
        
        public ReferenceItem(String title, String content, String source) {
            this.title = title;
            this.content = content;
            this.source = source;
        }
        
        // Getter和Setter
        public String getTitle() {
            return title;
        }
        
        public void setTitle(String title) {
            this.title = title;
        }
        
        public String getContent() {
            return content;
        }
        
        public void setContent(String content) {
            this.content = content;
        }
        
        public String getSource() {
            return source;
        }
        
        public void setSource(String source) {
            this.source = source;
        }
        
        public Double getRelevance() {
            return relevance;
        }
        
        public void setRelevance(Double relevance) {
            this.relevance = relevance;
        }
    }
    
    // Getter和Setter
    public String getQuestionId() {
        return questionId;
    }
    
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public Double getConfidence() {
        return confidence;
    }
    
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
    
    public Date getAnswerTime() {
        return answerTime;
    }
    
    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }
    
    public String getAnswerSource() {
        return answerSource;
    }
    
    public void setAnswerSource(String answerSource) {
        this.answerSource = answerSource;
    }
    
    public List<ReferenceItem> getReferences() {
        return references;
    }
    
    public void setReferences(List<ReferenceItem> references) {
        this.references = references;
    }
    
    public void addReference(ReferenceItem reference) {
        this.references.add(reference);
    }
} 