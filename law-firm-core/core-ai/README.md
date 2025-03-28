# AI模块使用指南

## 模块说明
AI模块(`core-ai`)是核心功能层的重要组成部分，提供智能问答、文档处理、文本分析和决策支持等AI能力服务。本模块**仅作为功能封装层**提供AI能力支持，不直接对外暴露接口，不负责数据持久化和配置管理。

## 功能特性

### 1. 智能问答服务 (QAService)
- 法律问题智能应答
- 多轮对话支持
- 相关案例检索
- 带法律依据的回答

### 2. 文档处理服务 (DocProcessService) 
- 法律文书分类
- 关键信息提取
- 文档摘要生成
- 合同条款分析
- 文档对比分析

### 3. 文本分析服务 (TextAnalysisService)
- 文本相似度分析
- 关键词提取
- 情感分析
- 命名实体识别
- 法律文本标准化

### 4. 决策支持服务 (DecisionSupportService)
- 案件风险评估
- 法律建议生成
- 判决结果预测
- 法律策略推荐

## 使用方式

### 1. 添加依赖
在业务模块的pom.xml中添加:
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>law-firm-core-ai</artifactId>
</dependency>
```

### 2. 服务注入
使用`@Autowired`和`@Qualifier`注入所需服务:
```java
@Autowired
@Qualifier("aiQAServiceImpl")
private QAService qaService;

@Autowired
@Qualifier("aiDocProcessServiceImpl")
private DocProcessService docProcessService;

@Autowired
@Qualifier("aiTextAnalysisServiceImpl")
private TextAnalysisService textAnalysisService;

@Autowired
@Qualifier("aiDecisionSupportServiceImpl")
private DecisionSupportService decisionSupportService;
```

### 3. 服务调用示例
```java
// 获取法律问题答案
String answer = qaService.getLegalAnswer("什么是知识产权保护期限?");

// 获取带引用的法律回答
Map<String, Object> answerWithReferences = qaService.getLegalAnswerWithReferences(
    "商标侵权的法律责任有哪些?"
);

// 文档分类
Map<String, Double> classifications = docProcessService.classifyDocument(documentContent);

// 提取文档信息
Map<String, Object> docInfo = docProcessService.extractDocumentInfo(
    documentContent, 
    "合同"
);

// 文本相似度分析
double similarity = textAnalysisService.calculateSimilarity(text1, text2);

// 案件风险评估
Map<String, Object> riskAssessment = decisionSupportService.assessCaseRisk(caseDetails);
```

### 4. 业务层封装建议
建议在业务层对AI服务进行封装，增加业务逻辑:
```java
@Service
public class ContractAIService {
    @Autowired
    private DocProcessService docProcessService;
    @Autowired
    private DecisionSupportService decisionSupportService;
    
    public ContractReviewResult reviewContract(String contractContent) {
        // 提取合同信息
        Map<String, Object> contractInfo = docProcessService.extractDocumentInfo(
            contractContent, 
            "合同"
        );
        
        // 分析合同条款
        List<Map<String, Object>> clauses = docProcessService.analyzeContractClauses(
            contractContent
        );
        
        // 风险评估
        Map<String, Object> riskAssessment = decisionSupportService.assessRisks(
            contractContent,
            "合同"
        );
        
        return new ContractReviewResult(contractInfo, clauses, riskAssessment);
    }
}
```

## 配置说明

本模块作为功能封装层，**不直接管理配置**。业务模块负责提供以下配置：

1. AI服务提供商类型
2. API密钥和服务端点
3. 模型参数配置
4. 安全和性能配置

业务模块提供配置示例：
```yaml
# 在业务模块的application.yml中配置
ai:
  default-provider: openai  # 默认AI提供商
  providers:
    openai:
      api-key: ${OPENAI_API_KEY}
      model: gpt-4
    baidu:
      api-key: ${BAIDU_API_KEY}
      secret-key: ${BAIDU_SECRET_KEY}
      model: ernie-bot-4
```

## 安全与脱敏处理

为保障API密钥等敏感信息的安全，业务模块应采取以下措施：

1. **数据库加密存储**：
   - API密钥在数据库中应以加密形式存储
   - 使用加密算法，密钥由系统安全模块管理

2. **内存安全处理**：
   - 敏感配置信息不应序列化到JSON响应中
   - 配置对象不应在应用缓存中明文存储

3. **日志脱敏处理**：
   - 使用脱敏服务对日志中的敏感信息进行脱敏
   - API密钥在日志中应隐藏大部分字符，仅保留少量前缀和后缀
   - 脱敏处理应贯穿整个系统，确保敏感信息不会在任何日志中泄露

4. **访问控制**：
   - 敏感配置项应仅对管理员角色可见
   - 配置变更应记录完整的操作日志

## 注意事项

1. 本模块仅提供功能封装，不负责数据持久化
2. AI相关的配置应由业务模块提供
3. 业务模块应妥善管理API密钥等敏感信息
4. 调用AI服务时注意异常处理
5. 建议在业务层做好结果缓存
6. 关注API调用频率和成本控制
7. 重要决策建议人工复核 