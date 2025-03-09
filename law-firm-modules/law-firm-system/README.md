# 系统管理模块 (Law Firm System)

## 一、模块说明
系统管理模块是律师事务所管理系统的基础支撑模块，负责全局系统配置管理、字典管理、日志管理、消息通知、系统升级、访问监控等功能。本模块仅供系统管理员使用，需要最高级别的系统权限。

### 1.1 系统设置分级
#### 个人级设置（非本模块职责）
- 位置：用户中心模块
- 权限：普通用户
- 功能：
  - 个人偏好设置（主题、语言等）
  - 消息通知设置（订阅、提醒等）
  - 隐私设置（可见性等）
  - 界面布局设置
  - 快捷操作设置

#### 系统级设置（本模块职责）
- 位置：系统管理模块
- 权限：仅系统管理员
- 功能：
  - 全局系统参数配置
  - 系统字典管理
  - 系统监控和维护
  - 系统升级管理
  - 日志和审计管理

### 1.2 权限要求
- 访问角色：仅系统管理员（ROLE_ADMIN）
- 权限级别：系统最高权限
- 访问限制：需要进行IP白名单控制
- 操作审计：所有操作都需要记录审计日志

### 1.3 核心功能
- 系统配置管理：全局系统运行参数的配置
- 字典管理：系统通用字典维护
- 日志管理：系统日志记录和查询
- 消息通知：系统级消息推送和管理
- 系统升级：版本管理、升级包管理、升级历史
- 访问监控：访问日志、操作审计、行为分析

### 1.4 与其他模块的关系
#### 与Auth模块
- Auth负责：认证授权、权限管理
- System负责：系统管理、监控审计

#### 与用户中心模块
- 用户中心负责：个人设置、偏好管理
- System负责：全局设置、系统管理

## 二、技术架构

### 2.1 目录结构
```
law-firm-system
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.lawfirm.system
│   │   │       ├── controller              # 接口层
│   │   │       │   ├── config             # 配置控制器
│   │   │       │   ├── dict              # 字典控制器
│   │   │       │   ├── log               # 日志控制器
│   │   │       │   ├── message           # 消息控制器
│   │   │       │   ├── upgrade           # 升级控制器
│   │   │       │   └── monitor           # 监控控制器
│   │   │       ├── service                # 服务层
│   │   │       │   ├── config             # 配置服务
│   │   │       │   │   ├── impl          # 实现类
│   │   │       │   │   └── ConfigService.java
│   │   │       │   ├── dict              # 字典服务
│   │   │       │   ├── log               # 日志服务
│   │   │       │   ├── message           # 消息服务
│   │   │       │   ├── upgrade           # 升级服务
│   │   │       │   └── monitor           # 监控服务
│   │   │       │       ├── impl
│   │   │       │       ├── AccessLogService.java     # 访问日志服务
│   │   │       │       ├── OperationLogService.java  # 操作日志服务
│   │   │       │       └── BehaviorAnalysisService.java # 行为分析服务
│   │   │       ├── convert                # 对象转换
│   │   │       │   ├── ConfigConvert.java
│   │   │       │   └── DictConvert.java
│   │   │       └── LawFirmSystemApplication.java
│   │   └── resources
│   │       ├── mapper                     # MyBatis映射文件
│   │       │   ├── config
│   │       │   ├── dict
│   │       │   ├── upgrade               # 升级相关SQL映射
│   │       │   └── monitor               # 监控相关SQL映射
│   │       ├── scripts                    # 升级脚本
│   │       │   └── upgrade               # 版本升级SQL脚本
│   │       ├── application.yml            # 基础配置
│   │       ├── application-dev.yml        # 开发环境
│   │       └── application-prod.yml       # 生产环境
│   └── test                              # 测试代码
│       └── java
│           └── com.lawfirm.system
│               ├── controller
│               └── service
├── pom.xml                               # 项目依赖
└── README.md                             # 项目文档
```

### 2.2 开发规范

1. **基类继承**
   - Controller层继承BaseController，并添加@RequiresRoles("ROLE_ADMIN")注解
   - Service层继承BaseService
   - Mapper层继承BaseMapper

2. **通用功能使用**
   - 缓存：使用@Cacheable等注解
   - 日志：使用@Log注解（必须记录操作人）
   - 消息：使用MessageService
   - 安全：使用SecurityUtils工具类
   - 版本：使用VersionUtils工具类

3. **安全规范**
   - 所有接口必须进行权限校验
   - 敏感操作需要二次确认
   - 重要操作需要留存操作记录
   - 配置修改需要进行备份

## 三、配置说明

### 3.1 应用配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/law_firm
    username: ${MYSQL_USERNAME}
    password: ${MYSQL_PASSWORD}

system:
  security:
    admin-roles: ROLE_ADMIN  # 管理员角色
    ip-whitelist: ${ADMIN_IP_LIST}  # 管理员IP白名单
  upgrade:
    package-path: /data/upgrade    
    backup-path: /data/backup      
    auto-backup: true             
  monitor:
    log-retention-days: 180      
    enable-behavior-analysis: true 
    sensitive-data-mask: true    
```

### 3.2 安全配置
```yaml
security:
  ignore:
    urls:
      - /system/login
      - /system/logout
      - /system/upgrade/version
  admin:
    # 管理员操作相关配置
    operation-confirm: true      # 是否需要二次确认
    session-timeout: 30         # 会话超时时间(分钟)
    max-sessions: 1            # 同一管理员最大会话数
```

## 四、常见问题

### 4.1 缓存相关
1. 问题：缓存数据不一致
   解决：使用Cache Aside模式，先更新数据库，再删除缓存

2. 问题：缓存穿透
   解决：使用BloomFilter

### 4.2 消息相关
1. 问题：消息重复消费
   解决：使用幂等性检查

2. 问题：消息堆积
   解决：配置消费者线程池

### 4.3 升级相关
1. 问题：升级过程中断
   解决：使用事务控制，支持回滚机制

2. 问题：升级后数据不一致
   解决：升级前自动备份，提供回滚功能

### 4.4 访问监控相关
1. 问题：日志数据量大
   解决：按时间分表，定期归档

2. 问题：敏感信息泄露
   解决：统一脱敏处理，配置脱敏规则

3. 问题：统计分析性能
   解决：采用离线计算，结果缓存

## 五、版本历史

### v1.0.0 (2024-03-08)
- 基础功能实现
- 配置管理
- 字典管理
- 日志管理
- 系统升级
- 访问监控 