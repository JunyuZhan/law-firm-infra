# Core Search Module

## 1. 模块介绍

`core-search` 是律所管理系统的核心搜索模块，基于Elasticsearch实现，提供统一的全文检索服务。该模块支持多字段搜索、高亮显示、分词搜索等功能。

## 2. 核心功能

### 2.1 搜索功能
- 全文检索
- 多字段搜索
- 高亮显示
- 分词搜索
- 结果排序
- 结果过滤

### 2.2 索引管理
- 索引创建
- 索引删除
- 索引更新
- 索引别名
- 索引同步

### 2.3 搜索历史
- 历史记录
- 热搜统计
- 搜索建议
- 相关搜索

### 2.4 性能优化
- 结果缓存
- 查询优化
- 索引优化
- 聚合分析

## 3. 技术架构

### 3.1 核心组件
```
com.lawfirm.core.search/
├── config/                 # 配置类
│   ├── SearchProperties   # 搜索配置属性
│   └── ElasticsearchConfig # ES配置
├── service/               # 服务层
│   ├── SearchService      # 搜索服务
│   ├── IndexService       # 索引服务
│   └── HistoryService    # 历史服务
├── model/                # 数据模型
│   ├── entity/          # 实体类
│   ├── dto/             # 传输对象
│   └── request/         # 请求对象
└── repository/          # 数据访问层
```

### 3.2 数据库设计
```sql
-- 搜索配置表
search_config
  - id            # 主键
  - index_name    # 索引名称
  - alias_name    # 索引别名
  - mapping       # 索引mapping
  - settings      # 索引settings
  - status        # 状态

-- 搜索历史表
search_history
  - id            # 主键
  - user_id       # 用户ID
  - keyword       # 关键词
  - index_name    # 索引名称
  - filters       # 过滤条件
  - total_hits    # 结果数
  - search_time   # 搜索时间

-- 热搜词表
search_hot_keyword
  - id            # 主键
  - keyword       # 关键词
  - index_name    # 索引名称
  - search_count  # 搜索次数
  - last_search   # 最后搜索时间
```

## 4. 接口设计

### 4.1 搜索服务接口
```java
public interface SearchService {
    SearchResponse<T> search(SearchRequest request, Class<T> clazz);
    List<String> suggest(String keyword, String index);
    List<String> related(String keyword, String index);
}
```

### 4.2 索引服务接口
```java
public interface IndexService {
    void createIndex(String index, String mapping, String settings);
    void deleteIndex(String index);
    void updateIndex(String index, String mapping);
    boolean exists(String index);
    void sync(String index, String type);
}
```

### 4.3 历史服务接口
```java
public interface HistoryService {
    void recordSearch(SearchHistory history);
    List<String> getHotKeywords(String index, int limit);
    List<SearchHistory> getUserHistory(Long userId, String index);
}
```

## 5. 配置说明

### 5.1 Elasticsearch配置
```yaml
spring:
  elasticsearch:
    uris: http://localhost:9200
    username: elastic
    password: changeme

search:
  index:
    shards: 1
    replicas: 1
    refresh-interval: 1s
```

### 5.2 索引配置
```json
{
  "settings": {
    "number_of_shards": 1,
    "number_of_replicas": 1,
    "analysis": {
      "analyzer": {
        "ik_smart_pinyin": {
          "type": "custom",
          "tokenizer": "ik_smart",
          "filter": ["pinyin_filter"]
        }
      }
    }
  }
}
```

## 6. 使用示例

### 6.1 基础搜索
```java
@Autowired
private SearchService searchService;

public void search() {
    SearchRequest request = new SearchRequest()
        .setIndex("documents")
        .setKeyword("合同")
        .setPage(1)
        .setSize(10);
    
    SearchResponse<Document> response = searchService.search(request, Document.class);
}
```

### 6.2 高级搜索
```java
SearchRequest request = new SearchRequest()
    .setIndex("documents")
    .setKeyword("合同")
    .setFields(Map.of("title", 2.0f, "content", 1.0f))
    .setFilters(List.of(new Filter("type", "eq", "contract")))
    .setHighlightFields(List.of("title", "content"));

SearchResponse<Document> response = searchService.search(request, Document.class);
```

### 6.3 索引管理
```java
@Autowired
private IndexService indexService;

public void manageIndex() {
    String mapping = loadMapping();
    String settings = loadSettings();
    indexService.createIndex("documents", mapping, settings);
}
```

## 7. 测试说明

### 7.1 单元测试
```java
@Test
void search_Success() {
    SearchRequest request = new SearchRequest()
        .setIndex("test_index")
        .setKeyword("test");
    
    SearchResponse<TestDocument> response = 
        searchService.search(request, TestDocument.class);
    
    assertNotNull(response);
    assertEquals(1, response.getTotal());
}
```

### 7.2 集成测试
```java
@Testcontainers
class SearchIntegrationTest {
    @Container
    static ElasticsearchContainer elasticsearch = 
        new ElasticsearchContainer("elasticsearch:8.12.0");
    
    @Test
    void testFullSearchFlow() {
        // 完整搜索流程测试
    }
}
```

## 8. 性能优化

### 8.1 查询优化
- 使用Filter Context减少评分计算
- 合理设置字段权重
- 优化分词器配置
- 使用查询缓存

### 8.2 索引优化
- 合理设置分片数
- 优化刷新间隔
- 定期合并分片
- 设置合适的副本数

### 8.3 缓存优化
- 使用查询缓存
- 缓存热点数据
- 定期更新缓存
- 设置过期策略

## 9. 扩展性设计

### 9.1 自定义分析器
```json
{
  "analysis": {
    "analyzer": {
      "custom_analyzer": {
        "type": "custom",
        "tokenizer": "standard",
        "filter": ["lowercase", "custom_filter"]
      }
    }
  }
}
```

### 9.2 自定义评分
```java
public class CustomScoreQuery extends Query {
    @Override
    public Weight createWeight(IndexSearcher searcher, ScoreMode scoreMode, float boost) {
        // 实现自定义评分逻辑
    }
}
```

## 10. 最佳实践

### 10.1 索引设计
- 合理设计mapping
- 选择适当的分词器
- 设置合适的副本数
- 优化刷新策略

### 10.2 查询设计
- 使用查询模板
- 合理设置超时
- 实现查询降级
- 处理大结果集

### 10.3 安全考虑
- 访问权限控制
- 数据隔离
- 敏感数据处理
- 查询限流

## 11. 监控告警

### 11.1 监控指标
- 查询延迟
- 查询成功率
- 索引大小
- 集群状态

### 11.2 告警规则
- 查询超时告警
- 错误率告警
- 磁盘空间告警
- 集群健康告警 