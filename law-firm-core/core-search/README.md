# 法律事务管理系统 - 搜索服务模块

## 模块说明

搜索服务模块基于Elasticsearch实现，提供了全文检索、多条件组合查询、结果排序等核心搜索功能。本模块采用策略模式设计，支持多种搜索策略，保证系统搜索功能的可靠性和灵活性。本模块仅作为服务接口层提供给业务模块使用，不直接暴露REST接口。

## 核心功能

### 1. 搜索能力
- 全文检索
- 多字段组合搜索
- 结果分页
- 相关度排序
- 基础的聚合分析

### 2. 索引管理
- 文档索引
- 索引更新
- 索引删除
- 索引优化
- 数据同步

### 3. 搜索策略
- ElasticsearchSearchStrategy: ES搜索策略
- DatabaseSearchStrategy: 数据库搜索策略（降级方案）
- 动态策略切换

### 4. 搜索优化
- 搜索结果高亮
- 同义词扩展
- 拼音搜索
- 专业词汇分词

## 技术架构

### 1. 核心服务接口
- SearchService: 搜索服务接口
- IndexService: 索引服务接口
- SearchStrategy: 搜索策略接口

### 2. 搜索策略实现
```java
public interface SearchStrategy {
    // 获取策略名称
    String getStrategyName();
    
    // 按关键词搜索
    List<SearchDoc> searchByKeyword(String keyword, Pageable pageable);
    
    // 按条件搜索
    List<SearchDoc> searchByCondition(Map<String, Object> condition, Pageable pageable);
    
    // 统计结果数量
    long count(Map<String, Object> condition);
}
```

### 3. 配置管理
```yaml
lawfirm:
  search:
    enabled: true
    default-strategy: elasticsearch
    # Elasticsearch配置
    elasticsearch:
      enabled: true
      nodes: localhost:9200
      username: ${ES_USERNAME}
      password: ${ES_PASSWORD}
      connect-timeout: 5000
      socket-timeout: 30000
    # 索引配置
    index:
      shards: 3
      replicas: 1
      refresh-interval: 1s
    # 搜索配置
    search:
      max-result-window: 10000
      highlight-enabled: true
      synonym-enabled: true
      pinyin-enabled: true
    # 分页配置
    page:
      default-size: 10
      max-size: 100
```

## 使用说明

### 1. 服务接口调用示例
```java
// 注入服务
@Autowired
@Qualifier("searchServiceImpl")
private SearchService searchService;

@Autowired
@Qualifier("searchIndexServiceImpl")
private IndexService indexService;

// 基础搜索
SearchResult<DocVO> result = searchService.search(
    "合同违约",     // 关键词
    "case,contract",  // 搜索范围
    PageRequest.of(0, 10)  // 分页
);

// 高级搜索
Map<String, Object> conditions = new HashMap<>();
conditions.put("type", "合同");
conditions.put("status", "active");
conditions.put("createTime", new Range<>(startDate, endDate));

SearchResult<DocVO> advancedResult = searchService.searchByCondition(
    conditions,
    "contract",
    PageRequest.of(0, 20, Sort.by("createTime").descending())
);

// 索引文档
indexService.indexDocument(document);

// 删除索引
indexService.deleteDocument("contract", "1001");
```

### 2. 自定义搜索处理
```java
@Component
public class ContractSearchHandler extends SearchHandler<ContractIndex> {
    
    @Override
    public boolean supports(String indexName) {
        return "contract".equals(indexName);
    }
    
    @Override
    protected void preprocessQuery(QueryBuilder queryBuilder, Map<String, Object> params) {
        // 为合同搜索添加额外的查询条件
        if (params.containsKey("partyA")) {
            queryBuilder.must(QueryBuilders.matchQuery("partyA", params.get("partyA")));
        }
    }
    
    @Override
    protected ContractIndex convertToDocument(Map<String, Object> source) {
        // 将ES结果转换为合同索引对象
        ContractIndex contract = new ContractIndex();
        // 设置属性
        return contract;
    }
}
```

## 安全说明

1. 数据安全
   - 搜索内容权限控制
   - 敏感信息过滤
   - 索引数据加密

2. 服务安全
   - 访问认证授权
   - 操作审计日志
   - 资源限制保护

3. 性能保障
   - 查询超时控制
   - 结果集大小限制
   - 缓存策略优化 