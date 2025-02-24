# 搜索模块 (Core Search)

## 模块说明
搜索模块是律师事务所管理系统的核心搜索引擎，基于 Elasticsearch 构建，提供了高性能的全文检索、聚合分析等功能。该模块支持多种数据源的索引构建、实时搜索、数据同步等特性。

## 核心功能

### 1. 索引管理
- 索引创建更新
- 映射配置管理
- 别名管理
- 模板管理
- 分片管理

### 2. 数据管理
- 数据索引
- 数据更新
- 数据删除
- 数据同步
- 数据清理

### 3. 搜索功能
- 全文检索
- 精确匹配
- 范围查询
- 聚合分析
- 关联搜索

### 4. 高级特性
- 分词器配置
- 同义词处理
- 高亮显示
- 搜索建议
- 相关性优化

## 核心组件

### 1. 搜索服务
- SearchService：搜索服务接口
- IndexService：索引服务
- QueryService：查询服务
- AnalysisService：分析服务
- SuggestionService：建议服务

### 2. 数据处理
- DocumentProcessor：文档处理器
- DataSynchronizer：数据同步器
- IndexBuilder：索引构建器
- DataCleaner：数据清理器
- FieldAnalyzer：字段分析器

### 3. 搜索增强
- SearchEnhancer：搜索增强器
- RelevanceOptimizer：相关性优化器
- SynonymHandler：同义词处理器
- HighlightHandler：高亮处理器
- ScoreCalculator：评分计算器

### 4. 监控管理
- SearchMonitor：搜索监控器
- IndexMonitor：索引监控器
- PerformanceMonitor：性能监控器
- HealthChecker：健康检查器
- MetricsCollector：指标收集器

## 使用示例

### 1. 创建索引
```java
@Autowired
private IndexService indexService;

public void createIndex() {
    // 创建索引配置
    IndexConfig config = new IndexConfig()
        .setIndexName("cases")
        .setShards(3)
        .setReplicas(1);
    
    // 设置映射
    Map<String, Object> properties = new HashMap<>();
    properties.put("title", new TextField()
        .setAnalyzer("ik_max_word")
        .setSearchAnalyzer("ik_smart"));
    
    // 创建索引
    indexService.createIndex(config, properties);
}
```

### 2. 搜索数据
```java
@Autowired
private SearchService searchService;

public SearchResult search(String keyword) {
    // 构建搜索条件
    SearchQuery query = new SearchQuery()
        .setKeyword(keyword)
        .setFields(Arrays.asList("title", "content"))
        .setHighlight(true)
        .setPage(1)
        .setSize(10);
    
    // 执行搜索
    return searchService.search(query);
}
```

### 3. 数据同步
```java
@Autowired
private DataSynchronizer dataSynchronizer;

public void syncData() {
    // 配置同步
    SyncConfig config = new SyncConfig()
        .setTableName("case")
        .setBatchSize(1000)
        .setThreads(3);
    
    // 执行同步
    dataSynchronizer.sync(config);
}
```

## 配置说明

### 1. Elasticsearch配置
```yaml
elasticsearch:
  # 集群配置
  cluster:
    name: law-firm-cluster
    nodes: 
      - localhost:9200
      - localhost:9201
    
  # 客户端配置
  client:
    connect-timeout: 5000
    socket-timeout: 60000
    
  # 索引配置
  index:
    number-of-shards: 3
    number-of-replicas: 1
```

### 2. 搜索配置
```yaml
search:
  # 基础配置
  base:
    max-result-window: 10000
    batch-size: 1000
    
  # 分词器配置
  analyzer:
    default: ik_max_word
    search: ik_smart
```

## 注意事项
1. 索引设计优化
2. 数据同步策略
3. 性能监控优化
4. 资源使用控制
5. 容错机制处理 