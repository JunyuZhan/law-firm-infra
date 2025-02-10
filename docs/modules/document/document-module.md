# 文档管理模块

## 模块说明
文档管理模块(`law-firm-document`)是律所管理系统的核心业务模块之一，主要负责处理律所各类文档的全生命周期管理，包括文档的创建、存储、版本控制、转换、OCR识别等功能。

## 功能特性

### 1. 文档基础管理
- 文档创建与上传
- 文档分类管理
- 文档元数据管理
- 文档权限控制
- 文档状态管理
- 文档标签管理

### 2. 文档存储
- 分布式文件存储
- 文件去重（MD5校验）
- 文件加密存储
- 临时文件管理
- 存储空间管理

### 3. 版本控制
- 文档版本管理
- 版本差异比较
- 版本回滚
- 版本历史记录

### 4. 文档转换
- Word转PDF
- PDF转HTML
- 图片格式转换
- Office文档预览
- 在线文档查看

### 5. OCR功能
- 文档文字识别
- OCR结果管理
- 全文检索支持
- 批量OCR处理

### 6. 文档检索
- 全文检索
- 元数据检索
- 标签检索
- 高级组合检索

### 7. 文档安全
- 文档加密
- 访问控制
- 操作审计
- 敏感信息保护

## 技术栈

### 核心框架
- Spring Boot
- MyBatis Plus
- JPA

### 文档处理
- Apache POI
- iText 7
- PDFBox
- XDocReport

### 存储组件
- 分布式文件系统
- 缓存（Caffeine）

### 其他
- Lombok
- Commons IO
- Batik

## 模块结构
```
law-firm-document/
├── controller/    # 控制器层
├── service/       # 服务层
├── task/         # 定时任务
├── config/       # 配置类
├── constant/     # 常量定义
├── mapper/       # MyBatis映射
├── dto/          # 数据传输对象
└── exception/    # 异常处理
```

## 配置说明

### 数据库配置
#### 默认配置 (application.yml)
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/law_firm?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8
    username: ${MYSQL_USERNAME:root}
    password: ${MYSQL_PASSWORD:root}
    hikari:
      minimum-idle: 5
      maximum-pool-size: 20
      idle-timeout: 30000
      pool-name: DocumentHikariCP
      max-lifetime: 1800000
      connection-timeout: 30000
  
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true
    baseline-version: 1.0.0
    validate-on-migrate: true
```

#### 开发环境 (application-dev.yml)
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/law_firm_dev
    username: root
    password: root
  jpa:
    show-sql: true

logging:
  level:
    com.lawfirm.document: debug
    org.hibernate.SQL: debug
```

#### 生产环境 (application-prod.yml)
```yaml
spring:
  datasource:
    url: jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:law_firm}
    username: ${MYSQL_USERNAME}
    password: ${MYSQL_PASSWORD}
  jpa:
    show-sql: false

security:
  file:
    encryption:
      enabled: true
      algorithm: AES
      key-size: 256

monitoring:
  metrics:
    enabled: true
```

### MyBatis-Plus配置
```yaml
mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  type-aliases-package: com.lawfirm.document.entity
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false
  global-config:
    db-config:
      id-type: auto
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0
```

### 文档存储配置
```yaml
document:
  storage:
    base-path: /data/documents    # 文档存储基础路径
    temp-path: /data/temp        # 临时文件路径
```

### 文档转换配置
```yaml
document:
  conversion:
    enabled: true                # 启用文档转换
    async: true                  # 异步转换
    thread-pool:
      core-size: 4              # 核心线程数
      max-size: 8               # 最大线程数
      queue-capacity: 100       # 队列容量
```

### OCR配置
```yaml
ocr:
  enabled: true                 # 启用OCR
  async: true                   # 异步处理
  engine: tesseract            # OCR引擎
  thread-pool:
    core-size: 2
    max-size: 4
    queue-capacity: 50
```

## 环境说明

### 开发环境
- 使用本地MySQL数据库
- 开启SQL日志打印
- 本地文件存储路径
- 调试级别日志

### 生产环境
- 使用环境变量配置数据库连接
- 启用文件加密
- 开启监控指标
- 关闭SQL日志
- 信息级别日志

## 部署注意事项
1. 生产环境必须配置以下环境变量：
   - MYSQL_HOST: 数据库主机
   - MYSQL_PORT: 数据库端口
   - MYSQL_DATABASE: 数据库名
   - MYSQL_USERNAME: 数据库用户名
   - MYSQL_PASSWORD: 数据库密码

2. 数据库初始化：
   - 首次部署需执行Flyway迁移脚本
   - 迁移脚本位于`db/migration`目录
   - 版本号从1.0.0开始

3. 存储目录：
   - 确保应用对存储目录有读写权限
   - 定期清理临时目录
   - 建议使用独立存储卷

4. 安全配置：
   - 生产环境启用文件加密
   - 使用HTTPS协议
   - 配置跨域策略
   - 设置访问控制

## API示例

### 文档上传
```http
POST /api/v1/documents
Content-Type: multipart/form-data

file: [文件内容]
metadata: {
  "name": "合同文档",
  "type": "CONTRACT",
  "securityLevel": "CONFIDENTIAL"
}
```

### 文档转换
```http
POST /api/v1/documents/{documentId}/convert
Content-Type: application/json

{
  "targetFormat": "PDF",
  "async": true
}
```

### 版本管理
```http
POST /api/v1/documents/{documentId}/versions
Content-Type: multipart/form-data

file: [文件内容]
metadata: {
  "version": "1.1",
  "description": "更新合同条款"
}
```

## 注意事项
1. 文件上传大小限制：单个文件不超过100MB
2. 支持的文件格式：doc, docx, pdf, jpg, png等
3. OCR处理时间较长，建议使用异步处理
4. 敏感文档必须设置适当的安全级别
5. 定期清理临时文件目录

## 未来规划
1. 支持更多文档格式的转换
2. 优化OCR识别准确率
3. 增加区块链存证功能
4. 支持在线协同编辑
5. 增加智能文档分析功能 