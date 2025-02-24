# 客户管理模块 (Client Module)

## 模块说明
客户管理模块是律师事务所管理系统的客户关系管理模块，负责管理律所的所有客户信息、客户关系维护、客户服务跟踪等功能。该模块为律所提供了完整的客户生命周期管理，支持个人客户和企业客户的差异化管理。

## 核心功能

### 1. 客户信息管理
- 客户基本信息
- 客户类型管理
- 客户状态管理
- 客户标签管理
- 客户画像分析

### 2. 客户关系管理
- 客户来源管理
- 客户跟进记录
- 客户联系人管理
- 客户回访管理
- 客户满意度调查

### 3. 客户服务管理
- 服务需求管理
- 服务记录跟踪
- 服务质量评估
- 服务投诉处理
- 服务改进建议

### 4. 客户分析统计
- 客户统计分析
- 客户价值评估
- 客户行为分析
- 客户转化分析
- 客户流失预警

## 核心组件

### 1. 客户服务
- ClientService：客户服务接口
- ClientManager：客户管理器
- ClientConverter：客户转换器
- ClientValidator：客户验证器
- ClientSearcher：客户搜索器

### 2. 关系服务
- RelationshipService：关系服务
- ContactService：联系人服务
- FollowUpService：跟进服务
- VisitService：回访服务
- SurveyService：调查服务

### 3. 服务管理
- ServiceRequestService：需求服务
- ServiceRecordService：记录服务
- QualityEvaluationService：质量评估
- ComplaintService：投诉服务
- FeedbackService：反馈服务

### 4. 分析服务
- ClientAnalysisService：分析服务
- ValueEvaluationService：价值评估
- BehaviorAnalysisService：行为分析
- ConversionService：转化服务
- ChurnPredictionService：流失预警

## 使用示例

### 1. 创建客户
```java
@Autowired
private ClientService clientService;

public ClientDTO createClient(ClientCreateRequest request) {
    // 创建客户
    Client client = new Client()
        .setName(request.getName())
        .setType(ClientTypeEnum.ENTERPRISE)
        .setIndustry(request.getIndustry())
        .setSource(request.getSource())
        .setContactInfo(request.getContactInfo());
    
    // 保存客户
    return clientService.createClient(client);
}
```

### 2. 添加跟进记录
```java
@Autowired
private FollowUpService followUpService;

public void addFollowUp(Long clientId, FollowUpRequest request) {
    // 创建跟进记录
    FollowUpRecord record = new FollowUpRecord()
        .setClientId(clientId)
        .setType(request.getType())
        .setContent(request.getContent())
        .setNextPlan(request.getNextPlan())
        .setFollowUpTime(LocalDateTime.now());
    
    // 保存记录
    followUpService.addRecord(record);
}
```

### 3. 客户分析
```java
@Autowired
private ClientAnalysisService analysisService;

public ClientAnalysisResult analyzeClient(Long clientId) {
    // 获取分析维度
    AnalysisDimension dimension = new AnalysisDimension()
        .setValueAnalysis(true)
        .setBehaviorAnalysis(true)
        .setServiceAnalysis(true);
    
    // 执行分析
    return analysisService.analyze(clientId, dimension);
}
```

## 配置说明

### 1. 客户配置
```yaml
client:
  # 客户编号规则
  number:
    prefix: CLT
    date-format: yyyyMMdd
    sequence-length: 4
    
  # 客户分类
  category:
    enable-auto-category: true
    value-threshold: 1000000
    
  # 客户画像
  portrait:
    enable-auto-update: true
    update-interval: 7d
```

### 2. 服务配置
```yaml
service:
  # 跟进配置
  follow-up:
    min-interval: 7d
    alert-threshold: 30d
    
  # 满意度调查
  survey:
    auto-send: true
    after-service-days: 7
    reminder-times: 2
```

## 注意事项
1. 客户信息安全
2. 及时跟进维护
3. 服务质量保证
4. 客户价值评估
5. 数据分析应用 