# Law Firm Core 核心层

## 1. 模块说明

Law Firm Core 是律所管理系统的核心层，提供了系统的核心功能实现。该层主要包含以下模块：

### 1.1 core-storage 存储模块
文件存储核心实现，提供统一的文件存储服务。

#### 主要功能：
- 文件上传下载
- 分片上传
- 文件预览
- 异步处理
- 存储策略（当前支持MinIO）

#### 关键特性：
- 支持大文件分片上传
- 支持文件预览（PDF、Office、图片等）
- 支持异步处理和任务管理
- 支持存储策略的灵活扩展
- 完整的数据库表结构设计

### 1.2 core-search 搜索模块
统一的搜索服务实现，基于Elasticsearch。

#### 主要功能：
- 统一搜索接口
- 高亮显示
- 分词搜索
- 搜索历史记录
- 热搜统计

#### 关键特性：
- 支持多字段搜索
- 支持权重配置
- 支持结果高亮
- 支持搜索历史记录
- 支持热搜词统计
- 支持索引管理

### 1.3 core-message 消息模块
统一的消息处理服务。

#### 主要功能：
- 消息发送
- 消息接收
- 消息模板
- 消息历史

#### 关键特性：
- 支持多种消息类型
- 支持消息模板
- 支持消息状态追踪
- 支持消息历史查询

### 1.4 core-workflow 工作流模块
工作流引擎核心实现。

#### 主要功能：
- 工作流定义
- 流程执行
- 任务管理
- 流程监控

#### 关键特性：
- 支持自定义工作流
- 支持流程变量
- 支持任务分配
- 支持流程监控
- 支持流程历史

## 2. 技术栈

- Spring Boot 2.7.x
- Spring Data JPA
- MySQL 8.0
- Elasticsearch 8.x
- MinIO
- RabbitMQ
- Redis
- Flyway

## 3. 项目结构

```
law-firm-core/
├── core-storage/            # 存储模块
│   ├── src/main/java/      # 源代码
│   ├── src/main/resources/ # 配置文件
│   └── src/test/           # 测试代码
├── core-search/            # 搜索模块
│   ├── src/main/java/      # 源代码
│   ├── src/main/resources/ # 配置文件
│   └── src/test/           # 测试代码
├── core-message/           # 消息模块
│   ├── src/main/java/      # 源代码
│   ├── src/main/resources/ # 配置文件
│   └── src/test/           # 测试代码
└── core-workflow/          # 工作流模块
    ├── src/main/java/      # 源代码
    ├── src/main/resources/ # 配置文件
    └── src/test/           # 测试代码
```

## 4. 开发指南

### 4.1 环境要求
- JDK 21
- Maven 3.8+
- Docker (用于集成测试)
- IDE推荐：IntelliJ IDEA

### 4.2 本地开发
1. 克隆项目
```bash
git clone [repository-url]
cd law-firm-core
```

2. 安装依赖
```bash
mvn clean install
```

3. 运行测试
```bash
mvn test
```

### 4.3 配置说明

各模块的配置文件位于 `src/main/resources/` 目录下：
- `application.yml`: 基础配置
- `application-*.yml`: 环境特定配置
- `db/migration/`: 数据库迁移脚本

### 4.4 测试说明

- 单元测试：使用JUnit 5
- 集成测试：使用TestContainers
- 测试覆盖率要求：>80%

## 5. 部署指南

### 5.1 打包
```bash
mvn clean package
```

### 5.2 运行
```bash
java -jar [module-name]/target/[module-name]-[version].jar
```

### 5.3 Docker部署
```bash
docker build -t law-firm-[module-name] .
docker run -d -p [port]:[port] law-firm-[module-name]
```

## 6. 注意事项

1. 代码规范
   - 遵循阿里巴巴Java开发规范
   - 使用统一的代码格式化配置
   - 提交前进行代码审查

2. 数据库
   - 使用Flyway进行版本控制
   - 遵循数据库命名规范
   - 重要操作添加审计字段

3. 测试
   - 新功能必须包含测试
   - 修复bug必须包含测试用例
   - 保持测试覆盖率

4. 文档
   - 及时更新API文档
   - 保持注释的完整性
   - 记录重要的设计决策

## 7. 常见问题

1. 如何添加新的存储策略？
   - 实现 `StorageStrategy` 接口
   - 在配置中注册新策略
   - 添加相应的单元测试

2. 如何扩展搜索功能？
   - 在 `SearchRequest` 中添加新的参数
   - 在 `ElasticsearchServiceImpl` 中实现新功能
   - 更新测试用例

3. 如何添加新的消息类型？
   - 在消息模型中添加新类型
   - 实现对应的处理器
   - 添加消息模板

## 8. 维护者

- 开发团队：[team-name]
- 技术支持：[support-email]

## 9. 贡献指南

1. Fork 项目
2. 创建特性分支
3. 提交变更
4. 推送到分支
5. 创建Pull Request

## 10. 版本历史

- v1.0.0 (2024-02-10)
  - 初始版本
  - 实现基础功能
  - 完成核心模块开发 