# 搜索核心模块 (Core Search)

## 模块说明
搜索核心模块是律师事务所管理系统的搜索引擎实现层，基于Elasticsearch构建。该模块主要负责实现`search-model`中定义的接口，提供高性能的全文检索、聚合分析等功能的具体实现。

## 技术栈
- Spring Boot 3.2.x
- Elasticsearch Java API Client
- Elasticsearch 8.0
- Lombok
- JUnit 5 + Testcontainers (测试)

## 项目结构
```
core-search
├── src/main/java/com/lawfirm/core/search
│   ├── config                    // ES配置
│   │   ├── ElasticsearchConfig.java        // ES客户端配置
│   │   └── ElasticsearchProperties.java    // ES配置属性
│   ├── service                   // 服务实现
│   │   └── impl
│   │       ├── SearchServiceImpl.java      // 搜索服务实现
│   │       └── IndexServiceImpl.java       // 索引服务实现
│   ├── handler                   // 处理器
│   │   ├── DocumentHandler.java            // 文档处理
│   │   ├── IndexHandler.java              // 索引处理
│   │   └── SearchHandler.java             // 搜索处理
│   └── utils                     // 工具类
│       ├── ElasticsearchUtils.java        // ES工具类
│       └── QueryBuilderUtils.java         // 查询构建工具
└── src/main/resources
    └── application.yml                    // 配置文件
```

## 配置说明

### Elasticsearch配置
```yaml
elasticsearch:
  cluster:
    name: law-firm-cluster
    nodes: 
      - localhost:9200
  client:
    connect-timeout: 5000
    socket-timeout: 60000
    max-retries: 3
  index:
    number-of-shards: 3
    number-of-replicas: 1
```

## 业务模块集成说明

### 1. 依赖引入
在需要使用搜索功能的业务模块的`pom.xml`中添加：
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>core-search</artifactId>
</dependency>
```

### 2. 配置导入
业务模块需要在配置类上添加注解以启用搜索功能：
```java
@Configuration
@EnableSearchCore  // 启用搜索核心功能
public class BusinessConfig {
    // 业务配置
}
```

### 3. 使用方式
1. 直接注入接口
```java
@Service
public class BusinessService {
    @Autowired
    private SearchService searchService;
    
    @Autowired
    private IndexService indexService;
}
```

2. 配置覆盖
业务模块可在自己的`application.yml`中覆盖默认配置：
```yaml
elasticsearch:
  cluster:
    name: ${CLUSTER_NAME:law-firm-cluster}
    nodes: ${ES_NODES:localhost:9200}
```

### 4. 注意事项
- 业务模块不要直接依赖`elasticsearch-java`等ES相关包，统一通过core-search模块调用
- 建议使用search-model中定义的DTO、VO等对象进行数据传输
- 索引命名规范：`{业务模块}-{业务类型}`，如：`case-document`
- 建议实现SearchCallback接口来处理搜索结果的业务逻辑

## 开发规范

### 1. 代码规范
- 遵循阿里巴巴Java开发手册
- 使用Lombok简化代码
- 保持与search-model定义的接口一致
- 添加完整的Java文档注释

### 2. 异常处理
- 统一使用SearchException处理业务异常
- 详细记录异常堆栈和上下文信息
- 提供友好的错误提示

### 3. 日志规范
- 使用SLF4J + Logback
- 记录关键操作和异常信息
- 添加MDC支持，便于追踪

### 4. 测试规范
- 单元测试覆盖率 > 80%
- 使用Testcontainers进行集成测试
- 编写完整的测试用例文档

## 开发计划

### 1. 基础设施搭建 (Phase 1)
- [x] 添加search-model依赖
- [ ] 配置Elasticsearch客户端
  - [ ] ElasticsearchProperties配置类
  - [ ] ElasticsearchConfig配置类
  - [ ] application.yml配置文件
- [ ] 创建基础工具类
  - [ ] ElasticsearchUtils
  - [ ] QueryBuilderUtils

### 2. 索引管理实现 (Phase 2)
- [ ] 实现IndexService接口
  - [ ] 索引CRUD操作
  - [ ] 索引配置管理
  - [ ] 索引映射管理
  - [ ] 索引别名管理
- [ ] 开发IndexHandler
  - [ ] 索引模板管理
  - [ ] 分片管理
  - [ ] 索引监控

### 3. 搜索功能实现 (Phase 3)
- [ ] 实现SearchService接口
  - [ ] 文档CRUD操作
  - [ ] 批量操作支持
  - [ ] 搜索功能实现
  - [ ] 建议功能实现
- [ ] 开发SearchHandler
  - [ ] 查询构建
  - [ ] 高亮处理
  - [ ] 排序支持
  - [ ] 聚合分析

### 4. 高级特性实现 (Phase 4)
- [ ] 分词器配置
- [ ] 同义词处理
- [ ] 相关性优化
- [ ] 性能优化
- [ ] 错误处理

### 5. 测试与文档 (Phase 5)
- [ ] 单元测试
  - [ ] 服务层测试
  - [ ] 处理器测试
  - [ ] 工具类测试
- [ ] 集成测试
  - [ ] Testcontainers支持
  - [ ] ES集群测试
- [ ] 性能测试
- [ ] 文档完善

## 注意事项
1. 所有版本号统一在law-firm-dependencies中管理
2. 确保与search-model模块的接口定义保持一致
3. 关注ES操作的性能优化
4. 实现优雅的错误处理机制
5. 保持代码的可测试性 