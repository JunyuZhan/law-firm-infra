# 律所管理系统AI核心服务模块 (Core AI)

## 模块说明
AI核心服务模块是律所管理系统的基础功能模块，提供了通用的AI能力支持，包括文本分析、智能问答、文档处理、内容生成等功能。该模块作为系统的横切关注点，为各业务模块提供AI服务能力支持。

## 功能特性

### 1. 文本智能处理
- 法律文本分析与关键信息提取
- 文档摘要生成
- 文本相似度比较与查重
- 专业术语识别与解释

### 2. 智能问答与咨询
- 法律知识问答
- 案例检索与智能匹配
- 对话式法律咨询
- 智能客服接口

### 3. 文档智能处理
- 合同条款自动分析
- 法律文档智能分类
- 文档关键点提取
- 智能文书生成辅助

### 4. 智能决策支持
- 案件风险评估
- 诉讼结果预测
- 判例分析与推荐
- 法规适用建议

### 5. AI模型管理
- 多模型统一调用接口
- 模型性能监控
- 用户反馈收集与分析
- 自定义模型训练支持

## 技术架构

```
┌─────────────────────────────┐
│      业务层 (Business)      │
└───────────────┬─────────────┘
                │
┌───────────────▼─────────────┐
│       核心层 (Core)         │
├─────────────────────────────┤
│         core-ai             │──┐
└───────────────┬─────────────┘  │
                │                │
┌───────────────▼─────────────┐  │
│      模型层 (Model)         │  │
├─────────────────────────────┤  │
│         ai-model            │◄─┘
└───────────────┬─────────────┘
                │
┌───────────────▼─────────────┐
│      基础层 (Common)        │
└─────────────────────────────┘
```

## 目录结构

```
core-ai/
├── config/           # 配置类
│   ├── AIConfig.java            # AI核心配置
│   └── ModelConfig.java         # 模型配置
├── service/          # 服务实现
│   ├── impl/                    # 服务实现类
│   │   ├── TextAnalysisServiceImpl.java   # 文本分析服务实现
│   │   ├── QAServiceImpl.java             # 问答服务实现
│   │   ├── DocProcessServiceImpl.java     # 文档处理服务实现
│   │   ├── DecisionSupportServiceImpl.java # 决策支持服务实现
│   │   └── AIModelServiceImpl.java        # AI模型服务实现
├── provider/         # 提供者
│   ├── OpenAIProvider.java      # OpenAI服务提供者
│   ├── BaiduAIProvider.java     # 百度AI服务提供者
│   ├── LocalAIProvider.java     # 本地AI服务提供者
│   └── AIProviderFactory.java   # AI服务提供者工厂
├── handler/          # 处理器
│   ├── TextHandler.java         # 文本处理器
│   ├── DocHandler.java          # 文档处理器
│   └── QAHandler.java           # 问答处理器
├── utils/            # 工具类
│   ├── AIUtils.java             # AI工具类
│   └── ModelUtils.java          # 模型工具类
├── exception/        # 异常处理
│   └── AIException.java         # AI异常类
├── aop/              # 切面
│   └── AILogAspect.java         # AI日志切面
└── event/            # 事件
    ├── AIEvent.java             # AI事件
    └── AIEventListener.java     # AI事件监听器
```

## 核心接口

### 1. TextAnalysisService (文本分析服务)
- 提供文本分析、关键信息提取、文本分类等功能
- 支持法律文本的专业分析
- 提供文本相似度比较与查重功能

### 2. QAService (问答服务)
- 提供智能问答能力
- 支持专业知识库检索
- 提供对话式交互接口

### 3. DocProcessService (文档处理服务)
- 提供文档解析与分类
- 支持关键信息提取
- 提供文档摘要生成
- 支持智能文书辅助生成

### 4. DecisionSupportService (决策支持服务)
- 提供案件分析与风险评估
- 支持判例检索与推荐
- 提供法规适用建议

### 5. AIModelService (AI模型服务)
- 管理AI模型资源
- 提供模型调用统一接口
- 支持多种AI提供商服务
- 收集用户反馈与模型评价

## 集成方式

### 1. 依赖引入
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>core-ai</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 配置文件
在application.yml中配置AI服务参数：
```yaml
lawfirm:
  ai:
    default-provider: openai  # 默认服务提供商
    timeout: 30000           # 超时时间(毫秒)
    max-token: 4000          # 最大Token数
    providers:
      openai:
        api-key: ${OPENAI_API_KEY}
        api-url: https://api.openai.com/v1
        model: gpt-4
      baidu:
        app-id: ${BAIDU_APP_ID}
        api-key: ${BAIDU_API_KEY}
        secret-key: ${BAIDU_SECRET_KEY}
        model: ernie-bot-4
```

### 3. 服务调用示例
```java
// 文本分析示例
@Autowired
private TextAnalysisService textAnalysisService;

public void analyzeCase() {
    String caseText = "原告张三诉称，2022年1月5日，其与被告李四签订了一份买卖合同...";
    TextAnalysisResult result = textAnalysisService.extractKeyInfo(caseText);
    System.out.println("案件当事人: " + result.getParties());
    System.out.println("案件类型: " + result.getCaseType());
    System.out.println("关键日期: " + result.getKeyDates());
}

// 智能问答示例
@Autowired
private QAService qaService;

public void legalConsultation() {
    String question = "劳动合同到期未续签但继续工作，法律效力如何？";
    String answer = qaService.getLegalAnswer(question);
    System.out.println("法律咨询回答: " + answer);
}
```

## 扩展指南

### 1. 添加新的AI提供商
1. 实现`AIProvider`接口
2. 在`AIProviderFactory`中注册新提供商
3. 在配置文件中添加相应配置

### 2. 扩展模型能力
1. 在ai-model模块中添加新的实体定义
2. 实现对应的服务接口
3. 注册到Spring容器中

## 最佳实践
1. 推荐使用依赖注入方式获取AI服务
2. 处理好异常情况，设置合理的超时时间
3. 对敏感数据进行脱敏处理
4. 缓存频繁使用的AI结果
5. 收集用户反馈，不断优化AI服务质量 