# 搜索模块

## 模块说明
搜索模块提供全文检索功能，支持对系统内各类数据进行索引和搜索。基于 Elasticsearch 实现，提供统一的搜索服务接口。

## 功能特性
1. 索引管理
   - 索引创建与删除
   - 索引配置管理
   - 字段映射管理
   - 索引别名管理
   - 索引状态控制
   
2. 文档管理
   - 文档索引
   - 文档更新
   - 文档删除
   - 批量操作
   
3. 搜索功能
   - 全文检索
   - 条件过滤
   - 聚合分析
   - 高亮显示
   - 智能建议
   
4. 数据同步
   - 数据变更监听
   - 实时同步
   - 批量同步

## 目录结构
```
search-model/
├── entity/       # 实体类
│   ├── SearchIndex.java   # 索引实体
│   ├── SearchDoc.java     # 文档实体
│   └── SearchField.java   # 字段实体
├── enums/        # 枚举类
│   ├── IndexTypeEnum.java  # 索引类型
│   ├── FieldTypeEnum.java  # 字段类型
│   └── SearchTypeEnum.java # 搜索类型
├── dto/          # 数据传输对象
│   ├── index/    # 索引相关
│   │   ├── IndexCreateDTO.java # 索引创建
│   │   └── IndexQueryDTO.java  # 索引查询
│   └── search/   # 搜索相关
│       ├── SearchRequestDTO.java # 搜索请求
│       └── SearchResultDTO.java  # 搜索结果
├── vo/           # 视图对象
│   ├── IndexVO.java       # 索引视图
│   ├── FieldVO.java      # 字段视图
│   └── SearchVO.java      # 搜索视图
└── service/      # 服务接口
    ├── IndexService.java  # 索引服务
    └── SearchService.java # 搜索服务
```

## 核心接口
1. IndexService（继承BaseService<SearchIndex>）
   - 基础的CRUD操作（继承自BaseService）
   - 创建/删除索引
   - 更新索引配置
   - 更新字段映射
   - 管理索引状态
   - 管理索引别名
   
2. SearchService（继承BaseService<SearchDoc>）
   - 基础的CRUD操作（继承自BaseService）
   - 文档搜索
   - 文档索引
   - 批量操作
   - 文本分析
   - 智能建议

## 视图对象说明
1. IndexVO - 索引视图对象
   - 索引基本信息（名称、类型）
   - 配置信息（分片数、副本数）
   - 字段映射（FieldVO列表）
   - 统计信息（文档数、存储大小）

2. FieldVO - 字段视图对象
   - 字段名称和类型
   - 分词配置
   - 存储和索引设置
   - 字段属性（权重、复制等）

3. SearchVO - 搜索视图对象
   - 搜索结果统计
   - 命中文档列表
   - 高亮内容
   - 聚合结果
   - 建议结果

## 使用示例
```java
// 创建索引
IndexCreateDTO createDTO = new IndexCreateDTO()
    .setName("users")
    .setType(IndexTypeEnum.DOCUMENT)
    .addField("name", FieldTypeEnum.TEXT)
    .addField("email", FieldTypeEnum.KEYWORD);
indexService.createIndex(createDTO);

// 搜索文档
SearchRequestDTO requestDTO = new SearchRequestDTO()
    .setKeyword("张三")
    .setIndexName("users")
    .setHighlight("name")
    .setPage(1)
    .setSize(10);
SearchVO result = searchService.search(requestDTO);
```

## 配置说明
1. Elasticsearch配置
```yaml
spring:
  elasticsearch:
    uris: http://localhost:9200
    username: elastic
    password: elastic
```

2. 索引配置
```yaml
search:
  index:
    shards: 1
    replicas: 1
    refresh-interval: 1s
```

## 依赖说明
- Spring Boot 3.2.2
- Spring Data Elasticsearch
- Spring Data JPA
- Lombok
- MyBatis-Plus（通过base-model）

## 扩展功能
1. 分词器支持
   - IK分词器
   - 拼音分词器
   - 同义词分词器

2. 数据同步
   - Canal同步
   - MQ消息同步
   - 定时任务同步

3. 搜索优化
   - 搜索建议
   - 相关性优化
   - 结果排序
   - 聚合分析 