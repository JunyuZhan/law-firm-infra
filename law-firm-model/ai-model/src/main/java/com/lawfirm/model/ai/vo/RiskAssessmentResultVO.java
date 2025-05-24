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
 * 风险评估结果VO
 * 用于DecisionSupportService返回风险评估结果
 */
public class RiskAssessmentResultVO extends BaseVO {
    
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "请求ID")
    private String requestId;       // 请求ID
    @Schema(description = "案件ID")
    private String caseId;          // 案件ID
    @Schema(description = "案件标题")
    private String caseTitle;       // 案件标题
    @Schema(description = "评估时间")
    private Date assessmentTime;    // 评估时间
    @Schema(description = "总体风险评分（0-100）")
    private Double overallRiskScore; // 总体风险评分（0-100）
    @Schema(description = "风险等级：high高/medium中/low低")
    private String riskLevel;       // 风险等级：high高/medium中/low低
    @Schema(description = "风险因素列表")
    private transient List<RiskFactorVO> riskFactors; // 风险因素列表
    @Schema(description = "优势点")
    private transient List<String> strengths; // 优势点
    @Schema(description = "弱点")
    private transient List<String> weaknesses; // 弱点
    @Schema(description = "详细分析")
    private transient Map<String, Object> detailedAnalysis; // 详细分析
    @Schema(description = "建议行动")
    private transient List<String> recommendationActions; // 建议行动
    
    /**
     * 默认构造函数，仅初始化自身字段，不调用父类方法
     */
    public RiskAssessmentResultVO() {
        this.assessmentTime = new Date();
        this.riskFactors = new ArrayList<>();
        this.strengths = new ArrayList<>();
        this.weaknesses = new ArrayList<>();
        this.detailedAnalysis = new HashMap<>();
        this.recommendationActions = new ArrayList<>();
    }
    
    /**
     * 创建一个完全初始化的RiskAssessmentResultVO实例
     * @return 初始化的RiskAssessmentResultVO实例
     */
    public static RiskAssessmentResultVO createDefault() {
        RiskAssessmentResultVO vo = new RiskAssessmentResultVO();
        vo.setStatus(0); // 正常状态
        vo.setCreateTime(LocalDateTime.now());
        return vo;
    }
    
    // 风险因素VO
    public static class RiskFactorVO implements Serializable {
        private static final long serialVersionUID = 1L;
        
        @Schema(description = "因素ID")
        private String factorId;     // 因素ID
        @Schema(description = "因素名称")
        private String factorName;   // 因素名称
        @Schema(description = "描述")
        private String description;  // 描述
        @Schema(description = "分值（0-100）")
        private Double score;        // 分值（0-100）
        @Schema(description = "级别：high高/medium中/low低")
        private String level;        // 级别：high高/medium中/low低
        @Schema(description = "类别（如法律风险、证据风险等）")
        private String category;     // 类别（如法律风险、证据风险等）
        @Schema(description = "解释说明")
        private String explanation;  // 解释说明
        @Schema(description = "相关项（如相关文档、法规等）")
        private transient List<String> relatedItems; // 相关项
        
        public RiskFactorVO() {
            this.relatedItems = new ArrayList<>();
        }
        
        // Getter和Setter
        public String getFactorId() {
            return factorId;
        }
        
        public void setFactorId(String factorId) {
            this.factorId = factorId;
        }
        
        public String getFactorName() {
            return factorName;
        }
        
        public void setFactorName(String factorName) {
            this.factorName = factorName;
        }
        
        public String getDescription() {
            return description;
        }
        
        public void setDescription(String description) {
            this.description = description;
        }
        
        public Double getScore() {
            return score;
        }
        
        public void setScore(Double score) {
            this.score = score;
        }
        
        public String getLevel() {
            return level;
        }
        
        public void setLevel(String level) {
            this.level = level;
        }
        
        public String getCategory() {
            return category;
        }
        
        public void setCategory(String category) {
            this.category = category;
        }
        
        public String getExplanation() {
            return explanation;
        }
        
        public void setExplanation(String explanation) {
            this.explanation = explanation;
        }
        
        public List<String> getRelatedItems() {
            return relatedItems;
        }
        
        public void setRelatedItems(List<String> relatedItems) {
            this.relatedItems = relatedItems;
        }
        
        public void addRelatedItem(String item) {
            this.relatedItems.add(item);
        }
    }
    
    // Getter和Setter
    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
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
    
    public Date getAssessmentTime() {
        return assessmentTime;
    }
    
    public void setAssessmentTime(Date assessmentTime) {
        this.assessmentTime = assessmentTime;
    }
    
    public Double getOverallRiskScore() {
        return overallRiskScore;
    }
    
    public void setOverallRiskScore(Double overallRiskScore) {
        this.overallRiskScore = overallRiskScore;
        
        // 根据分数自动设置风险等级
        if (overallRiskScore != null) {
            if (overallRiskScore >= 70) {
                this.riskLevel = "high";
            } else if (overallRiskScore >= 40) {
                this.riskLevel = "medium";
            } else {
                this.riskLevel = "low";
            }
        }
    }
    
    public String getRiskLevel() {
        return riskLevel;
    }
    
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
    
    public List<RiskFactorVO> getRiskFactors() {
        return riskFactors;
    }
    
    public void setRiskFactors(List<RiskFactorVO> riskFactors) {
        this.riskFactors = riskFactors;
    }
    
    public void addRiskFactor(RiskFactorVO riskFactor) {
        this.riskFactors.add(riskFactor);
    }
    
    public List<String> getStrengths() {
        return strengths;
    }
    
    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }
    
    public void addStrength(String strength) {
        this.strengths.add(strength);
    }
    
    public List<String> getWeaknesses() {
        return weaknesses;
    }
    
    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }
    
    public void addWeakness(String weakness) {
        this.weaknesses.add(weakness);
    }
    
    public Map<String, Object> getDetailedAnalysis() {
        return detailedAnalysis;
    }
    
    public void setDetailedAnalysis(Map<String, Object> detailedAnalysis) {
        this.detailedAnalysis = detailedAnalysis;
    }
    
    public void addAnalysisDetail(String key, Object value) {
        this.detailedAnalysis.put(key, value);
    }
    
    public List<String> getRecommendationActions() {
        return recommendationActions;
    }
    
    public void setRecommendationActions(List<String> recommendationActions) {
        this.recommendationActions = recommendationActions;
    }
    
    public void addRecommendationAction(String action) {
        this.recommendationActions.add(action);
    }
} 