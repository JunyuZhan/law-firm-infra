# 搜索模块设计文档

## 1. 概述

本模块提供基于Lucene的搜索功能，作为替代Elasticsearch的轻量级嵌入式搜索解决方案。相比Elasticsearch，Lucene无需独立部署，可直接嵌入到应用中，资源占用更少，适合中小型系统。

## 2. 架构设计

### 2.1 模块分层

```
+------------------------+
|    业务模块层           |
| (law-firm-document等)  |
+------------------------+
           ↓
+------------------------+
|    搜索核心层           |
|   (core-search)        |
+------------------------+
           ↓
+------------------------+
|    搜索模型层           |
|   (search-model)       |
+------------------------+
```

- **search-model**: 定义搜索相关的数据模型、DTO、VO和接口
- **core-search**: 提供搜索的核心实现，包括Lucene和数据库两种搜索方式
- **业务模块**: 注入搜索服务，使用搜索功能

### 2.2 核心接口设计

```
+-------------------------+
|     SearchService       |
| (search-model模块定义)   |
+-------------------------+
           ↑
           |
+-------------------------+
| AbstractSearchService   |
| (core-search模块)       |
+-------------------------+
      ↑           ↑
      |           |
+------------+  +--------------+
|LuceneSearch|  |DatabaseSearch|
|Service     |  |Service       |
+------------+  +--------------+
```

通过策略模式实现不同的搜索引擎，业务层通过SearchService接口调用而不感知具体实现。

## 3. 技术实现

### 3.1 Lucene索引设计

#### 3.1.1 索引存储位置

```yaml
search:
  engine: lucene # 可选值: lucene, database
  lucene:
    index-dir: ${user.home}/.law-firm/lucene-index # 索引存储路径
    ram-buffer-size-mb: 32 # 缓冲区大小
```

#### 3.1.2 索引结构

每种文档类型创建独立索引：
- 案件索引：law_firm_case
- 文档索引：law_firm_document
- 客户索引：law_firm_client
- 合同索引：law_firm_contract

### 3.2 核心组件

#### 3.2.1 LuceneManager

负责Lucene索引的底层操作：
- 索引的创建与管理
- 文档的CRUD操作
- 索引优化与维护

#### 3.2.2 LuceneSearchService

实现SearchService接口，提供基于Lucene的搜索服务：
- 文档索引
- 全文搜索
- 高亮显示
- 过滤和排序

#### 3.2.3 DatabaseSearchService

实现SearchService接口，提供基于数据库的搜索服务：
- 使用SQL的LIKE语句实现简单搜索
- 可选使用全文索引进行优化

### 3.3 搜索引擎工厂

通过配置选择搜索引擎类型：

```java
@Component
public class SearchEngineFactory {
    @Autowired private LuceneSearchService luceneSearchService;
    @Autowired private DatabaseSearchService databaseSearchService;
    @Autowired private SearchProperties searchProperties;
    
    public SearchService getSearchService() {
        if ("lucene".equals(searchProperties.getEngine())) {
            return luceneSearchService;
        } else {
            return databaseSearchService;
        }
    }
}
```

## 4. 索引映射设计

### 4.1 文档字段映射

使用注解方式定义实体类与Lucene索引的映射关系：

```java
public @interface LuceneField {
    String name() default ""; // 字段名称
    boolean index() default true; // 是否索引
    boolean store() default true; // 是否存储
    FieldType type() default FieldType.TEXT; // 字段类型
    float boost() default 1.0f; // 权重提升
}

public enum FieldType {
    TEXT, // 全文检索
    KEYWORD, // 关键词匹配
    LONG, // 长整型
    INTEGER, // 整型
    DOUBLE, // 双精度浮点型
    DATE, // 日期类型
    BOOLEAN // 布尔类型
}
```

### 4.2 文档转换器

将Java对象与Lucene文档互相转换：

```java
public interface DocumentConverter<T> {
    Document toDocument(T entity);
    T toEntity(Document document);
}
```

## 5. 配置与扩展

### 5.1 配置选项

```yaml
search:
  # 搜索引擎类型: lucene 或 database
  engine: lucene
  
  # 是否启用搜索功能
  enabled: true
  
  # Lucene配置
  lucene:
    # 索引存储目录
    index-dir: ${user.home}/.law-firm/lucene-index
    # 内存缓冲区大小(MB)
    ram-buffer-size-mb: 32
    # 最大搜索结果数
    max-results: 1000
    # 是否启用高亮
    highlight-enabled: true
    # 高亮标签
    highlight-pre-tag: <em>
    highlight-post-tag: </em>
    # 分析器类型: standard, chinese, smart
    analyzer: chinese
    
  # 索引配置
  index:
    # 案件索引
    case:
      name: law_firm_case
    # 文档索引
    document:
      name: law_firm_document
    # 客户索引
    client:
      name: law_firm_client
    # 合同索引
    contract:
      name: law_firm_contract
```

### 5.2 扩展点

1. **自定义分析器**: 通过实现AnalyzerProvider接口扩展
2. **自定义相似度算法**: 通过实现SimilarityProvider接口扩展
3. **自定义过滤器**: 通过实现TokenFilterProvider接口扩展

## 6. 性能优化

1. **索引优化**:
   - 定期合并段(merge segments)
   - 合理设置缓冲区大小
   - 使用异步索引更新

2. **查询优化**:
   - 使用缓存减少磁盘IO
   - 预热常用查询
   - 使用过滤器缩小搜索范围

3. **存储优化**:
   - 合理配置字段存储策略
   - 使用压缩减少索引体积

## 7. 迁移计划

从Elasticsearch迁移到Lucene的计划：

1. **阶段一**: 实现Lucene搜索引擎，与现有Elasticsearch并行
2. **阶段二**: 测试验证Lucene搜索功能
3. **阶段三**: 将配置切换为Lucene作为主要搜索引擎
4. **阶段四**: 完全移除Elasticsearch依赖 