# Law Firm Document Module

文档管理模块，提供律所文档全生命周期管理功能。

## 开发环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+
- LibreOffice 7.0+ (文档转换)
- Tesseract 4.0+ (OCR支持)

## 快速开始

### 1. 环境准备
```bash
# 创建必要目录
mkdir -p /data/documents
mkdir -p /data/documents/temp

# 设置目录权限
chmod 755 /data/documents
chmod 755 /data/documents/temp

# 安装LibreOffice（Ubuntu）
sudo apt-get install -y libreoffice

# 安装Tesseract（Ubuntu）
sudo apt-get install -y tesseract-ocr
sudo apt-get install -y tesseract-ocr-chi-sim  # 中文支持
```

### 2. 数据库配置
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
```

### 3. 数据库初始化
```bash
# 创建数据库
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS law_firm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 执行Flyway迁移脚本
mvn flyway:migrate -Dflyway.configFiles=src/main/resources/application.yml
```

### 4. 构建运行
```bash
# 编译
mvn clean package

# 运行测试
mvn test

# 开发环境运行
java -jar target/law-firm-document.jar --spring.profiles.active=dev

# 生产环境运行
export MYSQL_HOST=prod-db-host
export MYSQL_USERNAME=prod-user
export MYSQL_PASSWORD=prod-password
java -jar target/law-firm-document.jar --spring.profiles.active=prod
```

## 开发指南

### 项目结构
```
src/
├── main/
│   ├── java/
│   │   └── com/lawfirm/document/
│   │       ├── controller/    # REST接口
│   │       ├── service/       # 业务逻辑
│   │       ├── mapper/        # MyBatis映射
│   │       └── config/        # 配置类
│   └── resources/
│       ├── mapper/           # MyBatis XML
│       ├── db/              # 数据库脚本
│       └── application.yml  # 配置文件
└── test/
    └── java/               # 测试代码
```

### 开发规范

1. **代码风格**
   - 遵循阿里巴巴Java开发规范
   - 使用lombok简化代码
   - 控制方法行数，建议不超过80行

2. **异常处理**
   - 业务异常统一使用`DocumentException`
   - 必须记录关键操作日志
   - 敏感操作需要审计日志

3. **测试要求**
   - 单元测试覆盖率>80%
   - 必须包含集成测试
   - 提交前本地测试通过

### 常见问题

1. **文件上传失败**
   - 检查存储目录权限
   - 确认文件大小限制
   - 验证文件类型是否支持

2. **转换服务异常**
   - 检查LibreOffice安装
   - 确认内存配置充足
   - 查看转换服务日志

3. **OCR识别问题**
   - 确认Tesseract安装正确
   - 检查训练数据完整性
   - 调整OCR参数配置

## 部署说明

### 系统要求
- CPU: 4核+
- 内存: 8GB+
- 磁盘: 100GB+
- 操作系统: CentOS 7+/Ubuntu 18.04+

### 依赖服务
- MySQL 8.0+
- Redis 6.0+
- LibreOffice 7.0+
- Tesseract 4.0+

### 部署步骤
1. 安装基础环境
2. 配置数据库
3. 准备存储目录
4. 部署应用服务
5. 配置nginx代理
6. 启动服务验证

### 监控运维
- 使用Prometheus采集指标
- 配置Grafana面板监控
- 设置关键指标告警

## 版本历史

- v1.0.0 (2024-01-20)
  - 基础文档管理功能
  - 文档转换服务
  - OCR识别支持

- v1.1.0 (开发中)
  - 增加全文检索
  - 优化转换性能
  - 支持更多格式

## 贡献指南

1. Fork 项目
2. 创建特性分支
3. 提交代码
4. 创建Pull Request

## 许可证

[MIT License](LICENSE) 