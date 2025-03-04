package com.lawfirm.core.ai.handler;

import com.lawfirm.core.ai.provider.AIProvider;
import com.lawfirm.core.ai.provider.AIProviderFactory;
import com.lawfirm.core.ai.exception.AIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 文档处理器
 * 负责处理法律文档的AI分析和处理
 */
@Component
public class DocHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(DocHandler.class);
    
    @Autowired
    private AIProviderFactory providerFactory;
    
    @Autowired
    private TextHandler textHandler;
    
    /**
     * 文档关键信息提取
     * 
     * @param documentContent 文档内容
     * @return 提取的关键信息
     */
    public Map<String, Object> extractDocumentInfo(String documentContent) {
        logger.info("提取文档关键信息");
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            String prompt = "请从以下法律文档中提取关键信息，包括但不限于：当事人信息、案件类型、争议焦点、法律依据、关键日期等。以JSON格式返回结果。\n\n" +
                           "文档内容：\n" + documentContent;
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.1);
            
            String result = provider.sendTextRequest(prompt, options);
            
            // 简化示例，返回模拟数据
            Map<String, Object> docInfo = new HashMap<>();
            
            // 当事人信息
            Map<String, Object> parties = new HashMap<>();
            parties.put("plaintiff", "张三");
            parties.put("defendant", "李四");
            parties.put("representative", "王律师");
            docInfo.put("parties", parties);
            
            // 案件基本信息
            docInfo.put("caseType", "合同纠纷");
            docInfo.put("caseNumber", "（2023）京01民初12345号");
            docInfo.put("court", "北京市第一中级人民法院");
            
            // 关键日期
            Map<String, String> dates = new HashMap<>();
            dates.put("filingDate", "2023-01-15");
            dates.put("hearingDate", "2023-03-20");
            dates.put("judgmentDate", "2023-04-10");
            docInfo.put("dates", dates);
            
            // 争议焦点
            docInfo.put("disputePoints", Arrays.asList(
                "合同是否有效成立",
                "被告是否构成违约",
                "违约金计算标准"
            ));
            
            // 法律依据
            docInfo.put("legalBasis", Arrays.asList(
                "《中华人民共和国民法典》第四百六十七条",
                "《中华人民共和国民法典》第五百八十五条",
                "最高人民法院关于适用《中华人民共和国合同法》若干问题的解释（二）第二十九条"
            ));
            
            return docInfo;
        } catch (Exception e) {
            logger.error("文档关键信息提取失败", e);
            throw new AIException("文档关键信息提取失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 文档分类
     * 
     * @param documentContent 文档内容
     * @return 文档分类结果
     */
    public Map<String, Object> classifyDocument(String documentContent) {
        logger.info("进行文档分类");
        
        try {
            // 定义法律文档类别
            String[] categories = {
                "起诉状", "答辩状", "上诉状", "判决书", "裁定书", "调解书", 
                "合同文本", "法律意见书", "律师函", "证据清单", "其他"
            };
            
            // 调用文本处理器的分类方法
            return textHandler.classifyText(documentContent, categories);
        } catch (Exception e) {
            logger.error("文档分类失败", e);
            throw new AIException("文档分类失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 文档摘要生成
     * 
     * @param documentContent 文档内容
     * @param maxLength 摘要最大长度
     * @return 文档摘要
     */
    public String generateDocumentSummary(String documentContent, int maxLength) {
        logger.info("生成文档摘要，最大长度: {}", maxLength);
        
        try {
            // 调用文本处理器的摘要方法
            return textHandler.generateSummary(documentContent, maxLength);
        } catch (Exception e) {
            logger.error("文档摘要生成失败", e);
            throw new AIException("文档摘要生成失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 文档相似度比较
     * 
     * @param document1 第一个文档内容
     * @param document2 第二个文档内容
     * @return 相似度比较结果
     */
    public Map<String, Object> compareDocuments(String document1, String document2) {
        logger.info("比较两份文档的相似度");
        
        try {
            // 调用文本处理器的相似度比较方法
            return textHandler.compareTexts(document1, document2);
        } catch (Exception e) {
            logger.error("文档相似度比较失败", e);
            throw new AIException("文档相似度比较失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 文档法律风险分析
     * 
     * @param documentContent 文档内容
     * @return 风险分析结果
     */
    public Map<String, Object> analyzeLegalRisks(String documentContent) {
        logger.info("进行文档法律风险分析");
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            String prompt = "请对以下法律文档进行风险分析，识别潜在的法律风险点，并给出风险等级（高、中、低）和应对建议。以JSON格式返回结果。\n\n" +
                           "文档内容：\n" + documentContent;
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.3);
            
            String result = provider.sendTextRequest(prompt, options);
            
            // 简化示例，返回模拟数据
            Map<String, Object> riskAnalysis = new HashMap<>();
            
            // 总体风险评估
            riskAnalysis.put("overallRiskLevel", "中");
            riskAnalysis.put("overallRiskScore", 65);
            
            // 具体风险点
            List<Map<String, Object>> riskPoints = new ArrayList<>();
            
            Map<String, Object> risk1 = new HashMap<>();
            risk1.put("riskPoint", "合同条款模糊");
            risk1.put("riskLevel", "高");
            risk1.put("description", "第三条关于交付时间的表述不够明确，可能导致履行争议");
            risk1.put("suggestion", "明确约定具体交付日期或时间节点，增加违约责任条款");
            riskPoints.add(risk1);
            
            Map<String, Object> risk2 = new HashMap<>();
            risk2.put("riskPoint", "违约金过高");
            risk2.put("riskLevel", "中");
            risk2.put("description", "约定的违约金为合同总额的30%，可能被法院认定为过高而调整");
            risk2.put("suggestion", "将违约金比例调整至合理范围，一般不超过合同总额的20%");
            riskPoints.add(risk2);
            
            Map<String, Object> risk3 = new HashMap<>();
            risk3.put("riskPoint", "管辖条款不明");
            risk3.put("riskLevel", "低");
            risk3.put("description", "未明确约定争议解决方式和管辖法院");
            risk3.put("suggestion", "增加明确的争议解决条款，约定仲裁机构或管辖法院");
            riskPoints.add(risk3);
            
            riskAnalysis.put("riskPoints", riskPoints);
            
            return riskAnalysis;
        } catch (Exception e) {
            logger.error("文档法律风险分析失败", e);
            throw new AIException("文档法律风险分析失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 文档合规性检查
     * 
     * @param documentContent 文档内容
     * @param lawsAndRegulations 需要检查的法律法规列表
     * @return 合规性检查结果
     */
    public Map<String, Object> checkCompliance(String documentContent, List<String> lawsAndRegulations) {
        logger.info("进行文档合规性检查，法规数量: {}", lawsAndRegulations.size());
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            StringBuilder promptBuilder = new StringBuilder();
            promptBuilder.append("请检查以下法律文档是否符合相关法律法规要求，指出不合规之处并给出修改建议。以JSON格式返回结果。\n\n");
            promptBuilder.append("需要检查的法律法规：\n");
            
            for (String law : lawsAndRegulations) {
                promptBuilder.append("- ").append(law).append("\n");
            }
            
            promptBuilder.append("\n文档内容：\n").append(documentContent);
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.2);
            
            String result = provider.sendTextRequest(promptBuilder.toString(), options);
            
            // 简化示例，返回模拟数据
            Map<String, Object> complianceCheck = new HashMap<>();
            
            // 总体合规评估
            complianceCheck.put("isCompliant", false);
            complianceCheck.put("complianceScore", 75);
            complianceCheck.put("overallAssessment", "文档整体合规性较好，但存在几处需要修改的地方");
            
            // 不合规项
            List<Map<String, Object>> nonComplianceItems = new ArrayList<>();
            
            Map<String, Object> item1 = new HashMap<>();
            item1.put("clause", "第五条第二款");
            item1.put("issue", "违反《中华人民共和国民法典》第五百零六条关于格式条款的规定");
            item1.put("description", "免除己方责任、加重对方责任的格式条款无效");
            item1.put("suggestion", "修改为双方平等承担责任的条款");
            nonComplianceItems.add(item1);
            
            Map<String, Object> item2 = new HashMap<>();
            item2.put("clause", "第八条");
            item2.put("issue", "违反《中华人民共和国消费者权益保护法》第二十六条");
            item2.put("description", "不得以格式条款、通知、声明等方式作出排除或者限制消费者权利的规定");
            item2.put("suggestion", "删除限制消费者权利的条款，增加合理的售后服务条款");
            nonComplianceItems.add(item2);
            
            complianceCheck.put("nonComplianceItems", nonComplianceItems);
            
            return complianceCheck;
        } catch (Exception e) {
            logger.error("文档合规性检查失败", e);
            throw new AIException("文档合规性检查失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 文档条款优化建议
     * 
     * @param documentContent 文档内容
     * @return 条款优化建议
     */
    public List<Map<String, Object>> suggestClauseImprovements(String documentContent) {
        logger.info("生成文档条款优化建议");
        
        try {
            AIProvider provider = providerFactory.getDefaultProvider();
            
            // 构建提示词
            String prompt = "请分析以下法律文档的条款，提出优化建议，使其更加清晰、严谨和有利于保护委托方权益。以JSON格式返回结果。\n\n" +
                           "文档内容：\n" + documentContent;
            
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", 0.3);
            
            String result = provider.sendTextRequest(prompt, options);
            
            // 简化示例，返回模拟数据
            List<Map<String, Object>> improvements = new ArrayList<>();
            
            Map<String, Object> improvement1 = new HashMap<>();
            improvement1.put("clause", "第二条");
            improvement1.put("currentText", "乙方应在合理时间内完成工作。");
            improvement1.put("issue", "时间表述模糊，缺乏可执行性");
            improvement1.put("suggestion", "乙方应在合同签订后30日内完成工作，并提交成果。如需延期，应提前5个工作日书面通知甲方，并经甲方书面同意。");
            improvement1.put("reason", "明确时间节点，增强条款可执行性，便于判断是否构成违约");
            improvements.add(improvement1);
            
            Map<String, Object> improvement2 = new HashMap<>();
            improvement2.put("clause", "第四条");
            improvement2.put("currentText", "甲方应支付乙方服务费用。");
            improvement2.put("issue", "费用金额、支付方式和时间未明确");
            improvement2.put("suggestion", "甲方应向乙方支付服务费用共计人民币XX万元整。支付方式为：合同签订后3个工作日内支付30%，乙方提交初步成果并经甲方确认后支付40%，全部工作完成并验收合格后支付剩余30%。");
            improvement2.put("reason", "明确费用金额和分期支付方式，平衡双方权益，降低履约风险");
            improvements.add(improvement2);
            
            Map<String, Object> improvement3 = new HashMap<>();
            improvement3.put("clause", "第七条");
            improvement3.put("currentText", "如发生争议，双方应友好协商解决。");
            improvement3.put("issue", "争议解决机制不完善");
            improvement3.put("suggestion", "如发生争议，双方应友好协商解决；协商不成的，任何一方均有权向合同签订地有管辖权的人民法院提起诉讼。");
            improvement3.put("reason", "完善争议解决机制，明确管辖法院，便于纠纷解决");
            improvements.add(improvement3);
            
            return improvements;
        } catch (Exception e) {
            logger.error("生成文档条款优化建议失败", e);
            throw new AIException("生成文档条款优化建议失败: " + e.getMessage(), e);
        }
    }
} 