# 律师事务所管理系统使用教程

本文档自动生成，包含了系统所有业务模块的使用说明。

## 目录

- [law-firm - 法律事务管理系统模块](#law-firm)
- [law-firm-common - Web模块，提供Web相关功能](#law-firm-common)
- [law-firm-core - 工作流核心模块](#law-firm-core)
- [law-firm-model - 工作流模型模块](#law-firm-model)
- [law-firm-modules - 任务管理模块](#law-firm-modules)

---

# 律师事务所管理系统 (Law Firm Management System)

## 技术架构
**核心组件**：
- 微服务框架：Spring Cloud 2022.0.1
- 安全认证：Spring Security 6.1.0
- 数据访问：MyBatis-Plus 3.5.3.1

**基础设施**：
- 数据库：MySQL 8.0 + MongoDB 6.0
- 缓存：Redis 7.0 集群
- 消息队列：RocketMQ 5.0

## 开发规范
### 1. 代码规范
- 遵循阿里巴巴Java开发规范
- 统一的代码格式化模板
- 规范的注释和文档
- 完整的单元测试
- 统一的异常处理

### 2. 接口规范
- RESTful API设计规范
- 统一的响应格式
- 完整的接口文档
- 标准的错误码
- 接口版本控制

### 3. 安全规范
- 身份认证
- 权限控制
- 数据加密
- 安全审计
- 漏洞防护

## 模块详细分工

### 基础业务模块
#### law-firm-auth（认证授权模块）
- **核心职责**：负责整个系统的认证、授权与安全管理
- **主要功能**：
  - 用户认证（登录、注销、多因素认证）
  - 权限管理（基于角色/权限的访问控制）
  - 安全审计（操作日志、安全日志）
  - 会话管理（令牌生成、验证、刷新）
  - 单点登录集成
- **技术组件**：Spring Security、JWT、OAuth2.0
- **数据管理**：用户、角色、权限、操作日志
- **对外接口**：身份验证API、权限检查API、用户信息API


### law-firm-system（系统管理模块）
- **核心职责**：系统基础设置与运维管理
- **主要功能**：
  - 组织架构管理（部门、职位、团队）
  - 系统参数配置
  - 字典管理（公共代码表）
  - 菜单管理
  - 系统监控与告警
  - 审计日志管理
- **技术组件**：Spring Admin、Actuator
- **数据管理**：组织、配置、字典、菜单、系统日志
- **对外接口**：组织架构API、系统配置API、字典API

### law-firm-document（文档管理模块）
- **核心职责**：系统全局文档管理与存储服务
- **主要功能**：
  - 文档上传与下载
  - 文档版本控制
  - 文档分类与标签
  - 全文检索
  - 文档转换与预览
  - 文档权限控制
- **技术组件**：MinIO/S3、PDF处理库、OCR
- **数据管理**：文档元数据、文档内容、文档版本、文档分类
- **对外接口**：文档CRUD API、文档搜索API、文档预览API

## 核心业务模块

### law-firm-case（案件管理模块）
- **核心职责**：律所案件全生命周期管理
- **主要功能**：
  - 案件登记与分类
  - 案件团队管理
  - 案件阶段管理
  - 案件文档管理
  - 案件任务分配
  - 案件关联管理（关联客户、合同等）
  - 案件时间记录
  - 案件统计分析
- **技术组件**：core-workflow集成
- **数据管理**：案件基本信息、案件进度、案件团队、案件文档
- **对外接口**：案件CRUD API、案件流程API、案件统计API
- **依赖模块**：client、document、task、personnel

### law-firm-client（客户管理模块）
- **核心职责**：客户信息与客户关系管理
- **主要功能**：
  - 客户信息管理（个人/机构）
  - 客户联系人管理
  - 客户分类与标签
  - 客户关系维护记录
  - 客户满意度评估
  - 客户风险评估
  - 潜在客户管理
- **技术组件**：数据加密、数据质量校验
- **数据管理**：客户基本信息、联系人、客户关系记录
- **对外接口**：客户CRUD API、客户关系API、客户搜索API
- **依赖模块**：system、document

### law-firm-contract（合同管理模块）
- **核心职责**：律所内外部合同管理
- **主要功能**：
  - 合同起草与模板管理
  - 合同审批流程
  - 合同执行跟踪
  - 收付款计划管理
  - 合同变更管理
  - 合同到期提醒
  - 律师函管理
- **技术组件**：core-workflow集成、合同模板引擎
- **数据管理**：合同基本信息、合同条款、合同履行状态
- **对外接口**：合同CRUD API、合同模板API、合同审批API
- **依赖模块**：client、document、finance

### law-firm-finance（财务管理模块）
- **核心职责**：律所财务与费用管理
- **主要功能**：
  - 收费项目管理
  - 计费模式管理（固定费用、计时收费等）
  - 账单生成与发送
  - 收付款管理
  - 成本核算
  - 发票管理
  - 账期管理与催款
  - 财务报表
- **技术组件**：报表生成、财务计算
- **数据管理**：收费项目、账单、收付款记录、发票
- **对外接口**：计费API、账单API、收付款API、报表API
- **依赖模块**：client、case、contract

## 支撑业务模块

### law-firm-personnel（人事管理模块）
- **核心职责**：律所人员、绩效与培训管理
- **主要功能**：
  - 人员档案管理
  - 绩效考核与评估
  - 工时登记与统计
  - 项目贡献度统计
  - 培训与发展
  - 专业技能管理
- **技术组件**：考核评估算法
- **数据管理**：人员信息、绩效数据、工时记录、培训记录
- **对外接口**：人员API、绩效API、工时API
- **依赖模块**：system、document

### law-firm-knowledge（知识管理模块）
- **核心职责**：律所知识资产管理与利用
- **主要功能**：
  - 法律知识库管理
  - 案例库管理
  - 文书模板管理
  - 研究成果管理
  - 法规动态跟踪
  - 知识分享与协作
- **技术组件**：全文检索、分类算法
- **数据管理**：知识条目、案例、模板、法规
- **对外接口**：知识库API、案例库API、法规库API
- **依赖模块**：document、case

### law-firm-analysis（数据分析模块）
- **核心职责**：系统数据分析与决策支持
- **主要功能**：
  - 业务数据分析
  - 绩效分析
  - 财务分析
  - 客户分析
  - 案件类型与成功率分析
  - 趋势分析
  - 预测分析
- **技术组件**：数据仓库、OLAP、可视化
- **数据管理**：分析模型、分析结果、报表
- **对外接口**：数据分析API、报表API
- **依赖模块**：几乎所有其他业务模块

### law-firm-archive（档案管理模块）
- **核心职责**：律所电子与纸质档案管理
- **主要功能**：
  - 档案分类与编码
  - 档案入库与借阅
  - 档案移交与销毁
  - 电子档案与实体档案关联
  - 档案清单与目录
  - 档案完整性检查
- **技术组件**：条码/RFID集成
- **数据管理**：档案元数据、档案位置、档案状态
- **对外接口**：档案CRUD API、档案检索API
- **依赖模块**：document、case、contract

## 辅助业务模块

### law-firm-task（任务管理模块）
- **核心职责**：跨模块的统一任务管理
- **主要功能**：
  - 任务创建与分配
  - 任务优先级与截止日期
  - 任务依赖关系
  - 任务协作与沟通
  - 任务提醒与通知
  - 任务完成与延期处理
- **技术组件**：core-workflow集成、通知服务
- **数据管理**：任务基本信息、任务状态、任务关联
- **对外接口**：任务CRUD API、任务分配API、任务状态API
- **依赖模块**：system、personnel

### law-firm-schedule（日程管理模块）
- **核心职责**：律所人员日程与会议管理
- **主要功能**：
  - 个人日程管理
  - 团队日程协调
  - 会议室预约
  - 日程提醒
  - 外部日程集成
  - 庭审安排
- **技术组件**：日历同步、提醒服务
- **数据管理**：日程信息、会议信息、提醒设置
- **对外接口**：日程API、会议室API、提醒API
- **依赖模块**：personnel、case

### law-firm-conflict（利益冲突模块）
- **核心职责**：律所利益冲突检查与管理
- **主要功能**：
  - 利益冲突规则配置
  - 冲突检查（案件、客户、律师）
  - 潜在冲突预警
  - 冲突审批与处理
  - 回避管理
  - 保密墙设置
- **技术组件**：规则引擎、冲突检测算法
- **数据管理**：冲突规则、检查记录、处理记录
- **对外接口**：冲突检查API、预警API、审批API
- **依赖模块**：client、case、personnel

### law-firm-supplies（办公用品模块）
- **核心职责**：律所办公用品管理
- **主要功能**：
  - 办公用品分类与管理
  - 库存管理
  - 申请与审批
  - 领用与归还
  - 供应商管理
  - 采购管理
- **技术组件**：库存算法、条码集成
- **数据管理**：用品信息、库存记录、申请记录
- **对外接口**：用品API、库存API、申请API
- **依赖模块**：system、finance

### law-firm-asset（资产管理模块）
- **核心职责**：律所固定资产管理
- **主要功能**：
  - 资产登记与编码
  - 资产分类与状态管理
  - 资产领用与归还
  - 资产维护与报修
  - 资产折旧与盘点
  - 资产处置
- **技术组件**：资产标签、折旧算法
- **数据管理**：资产信息、领用记录、维护记录
- **对外接口**：资产API、领用API、维护API
- **依赖模块**：finance、personnel

### law-firm-seal（印章管理模块）
- **核心职责**：律所印章使用与管理
- **主要功能**：
  - 印章登记与分类
  - 印章申请与审批
  - 用印记录与跟踪
  - 印章权限管理
  - 电子印章管理
  - 印章归档与统计
- **技术组件**：core-workflow集成、电子签章
- **数据管理**：印章信息、申请记录、用印记录
- **对外接口**：印章API、申请API、用印API
- **依赖模块**：document、contract

## 模块间依赖关系
### 层级结构
1. **基础层**：
   - 📦 law-firm-auth
   - 📦 law-firm-system
   - 📦 law-firm-document

2. **核心层**：
   - 🏢 law-firm-case
   - 🤝 law-firm-client
   - 📑 law-firm-contract
   - 💰 law-firm-finance

3. **支撑层**：
   - 👥 law-firm-personnel
   - 🧠 law-firm-knowledge
   - 📊 law-firm-analysis
   - 🗃️ law-firm-archive

4. **辅助层**：
   - ✅ law-firm-task
   - 📅 law-firm-schedule
   - ⚖️ law-firm-conflict
   - 🖨️ law-firm-supplies
   - 🏷️ law-firm-asset
   - 🖋️ law-firm-seal

## 核心工作流集成策略
### 流程集成点
| 模块           | 集成场景                 | 业务类型标识       |
|----------------|------------------------|-------------------|
| 案件管理模块    | 案件阶段转换审批        | CASE_PHASE_CHANGE |
| 合同管理模块    | 合同版本变更审批        | CONTRACT_UPDATE   |
| 财务管理模块    | 大额费用报销审批        | FINANCE_REIMBURSE |
| 印章管理模块    | 印章使用申请审批        | SEAL_APPLICATION  |
| 人事管理模块    | 绩效考核结果确认        | PERFORMANCE_REVIEW|

注：各业务模块根据业务需求，使用ProcessCreateDTO中的businessType字段，标识不同业务类型的流程，并在各自模块内实现特定业务逻辑。

## 系统说明
律师事务所管理系统是一套完整的律所业务管理解决方案...



---

# Common Web Module

## 简介
Common Web模块是律所管理系统的Web通用功能模块，提供了Web应用开发所需的基础设施和工具类。该模块依赖于`common-core`模块，为其他业务模块提供Web层面的支持。

## 主要功能

### 1. Web配置 (`WebConfig`)
- CORS跨域配置
  - 支持所有源(`*`)
  - 允许的HTTP方法: GET, POST, PUT, DELETE, OPTIONS
  - 允许所有请求头
  - 允许携带认证信息
  - 预检请求缓存时间: 3600秒
- 消息转换器配置
  - Long类型自动转String (解决前端精度丢失问题)
  - JSON序列化和反序列化支持

### 2. 安全配置 (`SecurityConfig`)
- XSS过滤器
  - 自动过滤请求参数中的XSS攻击代码
  - 支持自定义过滤规则

### 3. 文件上传配置 (`UploadConfig`)
- 单个文件大小限制: 10MB
- 总上传大小限制: 20MB
- 支持文件上传的Multipart配置

### 4. 全局异常处理 (`GlobalExceptionHandler`)
- 框架异常处理
- 参数校验异常处理
- 参数绑定异常处理
- 未知异常统一处理

### 5. 工具类

#### IP地址工具 (`IpUtils`)
- 获取请求的真实IP地址
- 获取本机IP地址
- IP地址合法性校验
- 内网IP判断
- 公网IP判断

### 6. Web支持类

#### 基础Web支持 (`WebSupport`)
- 提供Request、Response、Session的便捷访问
- 支持Web上下文操作

#### 参数验证支持 (`ValidSupport`)
- 必填参数验证
- 整数参数验证
- 长整数参数验证
- 布尔参数验证

#### 分页支持 (`PageSupport`)
- 分页参数获取
- 排序参数处理
- 分页参数验证

### 7. 请求/响应对象
- 分页请求对象 (`PageRequest`)
  - 支持页码、每页记录数
  - 支持排序字段和排序方式

## 依赖关系
```xml
<dependencies>
    <!-- Common Core -->
    <dependency>
        <groupId>com.lawfirm</groupId>
        <artifactId>common-core</artifactId>
    </dependency>

    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <!-- Knife4j -->
    <dependency>
        <groupId>com.github.xiaoymin</groupId>
        <artifactId>knife4j-openapi3-spring-boot-starter</artifactId>
        <version>4.3.0</version>
    </dependency>
</dependencies>
```

## 使用方式

### 1. 引入依赖
在需要使用Web功能的模块的`pom.xml`中添加：
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-web</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 2. 自动配置
模块提供了自动配置类`WebAutoConfiguration`，会自动注入以下配置：
- WebConfig (Web MVC配置)
- UploadConfig (文件上传配置)
- SecurityConfig (安全配置)

### 3. 使用示例

#### 分页请求处理
```java
@RestController
@RequestMapping("/api/example")
public class ExampleController extends PageSupport {
    
    @GetMapping("/list")
    public CommonResult<?> list() {
        Integer pageNum = getPageNum();
        Integer pageSize = getPageSize();
        String orderBy = getOrderBy();
        Boolean isAsc = getIsAsc();
        // 进行分页查询...
    }
}
```

#### IP地址工具使用
```java
@RestController
public class DemoController {
    
    @GetMapping("/ip")
    public String getIp(HttpServletRequest request) {
        // 获取真实IP地址
        String ip = IpUtils.getIpAddr(request);
        // 判断是否是内网IP
        boolean isInternal = IpUtils.isInternalIP(ip);
        return ip;
    }
}
```

## 注意事项
1. 全局异常处理器只处理框架级的基础异常，业务异常应该由具体的业务模块自行处理
2. 文件上传大小限制可以通过配置文件覆盖默认配置
3. XSS过滤器默认对所有请求参数进行过滤，如需排除特定参数，请自定义过滤规则

## 常见问题
1. 跨域请求失败
   - 检查目标域名是否在允许列表中
   - 确认请求方法是否被允许
   - 验证是否允许发送认证信息

2. 文件上传失败
   - 检查文件大小是否超过限制
   - 确认上传目录权限是否正确
   - 验证文件类型是否被允许

3. 响应格式异常
   - 检查响应对象是否正确封装
   - 确认消息转换器配置是否正确
   - 验证序列化/反序列化是否正常

## 相关文档
- [Spring Boot 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring MVC 官方文档](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [Swagger 官方文档](https://swagger.io/docs/) 

---

# 工作流核心模块 (Core Workflow)

## 模块定位

工作流核心模块是律师事务所管理系统的核心流程引擎，基于Flowable工作流引擎进行扩展，负责实现工作流模型模块(`workflow-model`)中定义的服务接口，提供了流程定义、执行、监控和管理的完整实现。本模块处于业务层与数据层之间，为上层业务提供统一的工作流能力支持。

## 架构设计

### 整体架构

```
                    +------------------+
                    |  业务应用模块    |
                    +--------+---------+
                             |
                             v
+------------+     +--------------------+    +--------------+
|            |     |                    |    |              |
| 表单服务   +---->+   工作流核心模块   +<---+ 权限服务     |
|            |     |  (core-workflow)   |    |              |
+------------+     +----------+---------+    +--------------+
                             |
                             v
                  +--------------------+    +--------------+
                  |                    |    |              |
                  |    Flowable引擎    +<-->+ MySQL数据库  |
                  |                    |    | (流程数据)   |
                  +--------------------+    +--------------+
```

### 分层架构

```
+---------------------------------------------------+
|                    Controller层                    |
| - 流程管理API                                      |
| - 任务管理API                                      |
| - 流程模板管理API                                  |
+---------------------------------------------------+
|                    Service层                       |
| - 流程服务(ProcessServiceImpl)                     |
| - 任务服务(TaskServiceImpl)                        |
| - 权限服务(ProcessPermissionServiceImpl)           |
| - 任务分配服务(TaskAssignmentServiceImpl)          |
| - 任务通知服务(TaskNotificationServiceImpl)        |
+---------------------------------------------------+
|                   适配器层                         |
| - Flowable流程适配器(FlowableProcessAdapterImpl)   |
| - Flowable任务适配器(FlowableTaskAdapterImpl)      |
| - 数据转换器(ProcessConverter, TaskConverter)      |
+---------------------------------------------------+
|                    数据访问层                      |
| - MyBatis映射文件                                  |
| - 流程数据仓库                                     |
| - 任务数据仓库                                     |
+---------------------------------------------------+
```

## 核心组件说明

### 1. 服务实现

实现了`workflow-model`模块中定义的服务接口：

- **ProcessServiceImpl**: 实现流程管理的核心业务功能
- **TaskServiceImpl**: 实现任务管理的核心业务功能
- **ProcessPermissionServiceImpl**: 实现流程权限管理
- **TaskNotificationServiceImpl**: 实现任务通知功能
- **TaskAssignmentServiceImpl**: 实现任务分配功能

### 2. 适配器层

封装Flowable引擎API，提供更高层次的抽象：

- **FlowableProcessAdapter/Impl**: 封装流程相关的Flowable API
- **FlowableTaskAdapter/Impl**: 封装任务相关的Flowable API

### 3. 数据转换器

处理不同数据模型之间的转换：

- **ProcessConverter**: 流程实体、DTO和VO之间的转换
- **TaskConverter**: 任务实体、DTO和VO之间的转换

### 4. 配置类

- **FlowableConfig**: 配置Flowable流程引擎
- **CacheConfig**: 配置缓存机制
- **WorkflowConfig**: 工作流模块的全局配置

### 5. 控制器层

提供RESTful API：

- **ProcessController**: 流程管理API
- **TaskController**: 任务管理API
- **ProcessTemplateController**: 流程模板管理API

## 核心功能

### 1. 流程管理

- 流程定义部署
- 流程实例创建
- 流程实例控制（挂起、激活、终止）
- 流程变量管理
- 流程权限控制

### 2. 任务管理

- 任务创建与分配
- 任务查询与过滤
- 任务处理（完成、转交、委派）
- 任务通知

### 3. 流程监控

- 流程实例监控
- 任务执行监控
- 流程性能分析

## 代码目录结构

```
core-workflow/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.lawfirm.core.workflow/
│   │   │       ├── adapter/                     # Flowable适配层
│   │   │       │   ├── converter/              # 数据转换器
│   │   │       │   │   ├── ProcessConverter.java
│   │   │       │   │   └── TaskConverter.java
│   │   │       │   │
│   │   │       │   └── flowable/               # Flowable核心适配器
│   │   │       │       ├── FlowableProcessAdapter.java
│   │   │       │       ├── FlowableProcessAdapterImpl.java
│   │   │       │       ├── FlowableTaskAdapter.java
│   │   │       │       └── FlowableTaskAdapterImpl.java
│   │   │       │
│   │   │       ├── config/                      # 配置类
│   │   │       │   ├── CacheConfig.java
│   │   │       │   ├── FlowableConfig.java
│   │   │       │   └── WorkflowConfig.java
│   │   │       │
│   │   │       ├── controller/                  # API接口实现
│   │   │       │   ├── ProcessController.java
│   │   │       │   ├── ProcessTemplateController.java
│   │   │       │   └── TaskController.java
│   │   │       │
│   │   │       ├── entity/                      # 实体类
│   │   │       │
│   │   │       ├── exception/                   # 异常处理类
│   │   │       │
│   │   │       ├── listener/                    # 事件监听器
│   │   │       │
│   │   │       ├── mapper/                      # MyBatis Mapper接口
│   │   │       │
│   │   │       ├── repository/                  # 数据访问层
│   │   │       │
│   │   │       ├── service/                     # 服务实现
│   │   │       │   │   ├── ProcessServiceImpl.java
│   │   │       │       ├── TaskServiceImpl.java
│   │   │       │
│   │   │       └── vo/                         # 视图对象
│   │   │
│   │   └── resources/
│   │       ├── mapper/                         # MyBatis映射文件
│   │       │   ├── CommonProcessMapper.xml
│   │       │   ├── ProcessMapper.xml
│   │       │   └── TaskMapper.xml
│   │       │
│   │       ├── db/                            # 数据库相关配置
│   │       │
│   │       └── application.yml                 # 应用配置文件
└── pom.xml                                      # 项目依赖
```

## 与workflow-model集成

core-workflow模块作为workflow-model接口的实现层，通过以下方式与workflow-model集成：

1. **引入依赖**：在pom.xml中引入workflow-model依赖
2. **实现接口**：实现workflow-model中定义的服务接口
3. **适配数据模型**：通过转换器转换workflow-model的DTO和VO
4. **遵循接口语义**：严格按照接口定义的语义实现功能

## 开发规范

1. **命名规范**：
   - 接口实现类统一使用"接口名+Impl"形式
   - 适配器类使用"Flowable+功能+Adapter"形式
   - 转换器类使用"实体名+Converter"形式

2. **代码组织**：
   - 流程相关的服务放在service.process包下
   - 任务相关的服务放在service.task包下
   - 适配器相关的类放在adapter包下

3. **异常处理**：
   - 业务异常继承自RuntimeException
   - 所有异常需要有明确的错误码和错误消息
   - 在服务层处理异常，不在适配器层抛出

4. **日志规范**：
   - 使用SLF4J日志框架
   - 每个方法入口记录info级别日志
   - 异常情况记录error级别日志

## 重构与优化计划

### 1. 架构优化

- **进一步完善适配器模式**：隔离Flowable引擎，减少直接依赖
- **增强事件机制**：完善事件监听器，支持更灵活的业务集成
- **统一异常处理**：建立统一的异常处理机制

### 2. 功能增强

- **表单处理**：增强表单渲染和数据处理能力
- **任务分配**：优化任务分配算法，支持更复杂的分配规则
- **流程监控**：增加更多监控指标，提供性能分析功能

### 3. 性能优化

- **缓存机制**：优化缓存策略，减少数据库访问
- **批量处理**：实现批量任务处理，提高性能
- **异步处理**：将非关键操作异步化，提高响应速度

### 4. 安全增强

- **权限控制**：细化权限控制，实现更精细的访问控制
- **数据加密**：敏感数据加密存储
- **操作审计**：记录所有关键操作，便于审计

## 技术栈

- **基础框架**：Spring Boot 3.2.x
- **工作流引擎**：Flowable 6.8.x
- **数据库**：MySQL 8.0+
- **缓存**：Caffeine(本地缓存), Redis(分布式缓存)
- **持久层**：MyBatis Plus 3.5.x
- **Web层**：Spring MVC
- **消息队列**：RocketMQ 5.0.x (用于异步事件处理)
- **API文档**：SpringDoc OpenAPI 2.x
- **安全框架**：Spring Security 6.x
- **测试框架**：JUnit 5.x, Mockito, Testcontainers
- **日志框架**：SLF4J, Logback
- **存储服务**：可选集成Minio/阿里云OSS/腾讯云COS(流程相关文件存储)


---

# 工作流模型模块

## 模块说明
工作流模型模块提供了律师事务所系统中工作流相关的核心数据模型和服务接口定义。该模块主要包含流程管理、任务管理等功能的基础数据结构和服务定义。

## 目录结构
```
workflow-model/
├── entity/
│   ├── base/             # 基础流程
│   │   ├── BaseProcess.java      # 流程基类
│   │   └── ProcessTask.java      # 任务基类
│   └── common/           # 通用流程
│       └── CommonProcess.java    # 通用流程
├── mapper/               # 数据访问层
│   ├── ProcessMapper.java        # 流程Mapper
│   ├── TaskMapper.java           # 任务Mapper
│   └── CommonProcessMapper.java  # 通用流程Mapper
├── enums/
│   ├── ProcessTypeEnum.java   # 流程类型
│   ├── ProcessStatusEnum.java # 流程状态
│   └── TaskTypeEnum.java      # 任务类型
├── dto/
│   ├── process/
│   │   ├── ProcessCreateDTO.java # 流程创建
│   │   ├── ProcessUpdateDTO.java # 流程更新
│   │   └── ProcessQueryDTO.java  # 流程查询
│   └── task/
│       ├── TaskCreateDTO.java    # 任务创建
│       └── TaskQueryDTO.java     # 任务查询
├── vo/
│   ├── ProcessVO.java      # 流程视图
│   └── TaskVO.java         # 任务视图
└── service/
    ├── ProcessService.java  # 流程服务
    └── TaskService.java     # 任务服务
```

## 核心功能

### 1. 流程管理
- 流程定义：支持自定义流程模板和流程节点
- 流程实例：管理流程实例的创建、更新、查询等操作
- 流程状态：跟踪流程实例的状态变更
- 流程类型：支持多种业务类型的流程定义

### 2. 任务管理
- 任务分配：支持任务的创建和分配
- 任务状态：跟踪任务的执行状态
- 任务类型：支持多种类型的任务定义
- 任务查询：提供灵活的任务查询功能

### 3. 数据访问层
- Mapper接口：基于MyBatis-Plus提供高效的数据访问能力
- 通用CRUD：继承BaseMapper获取通用的增删改查方法
- 自定义查询：支持基于业务需求的复杂查询场景
- 批量操作：支持高性能的批量数据处理

## 主要类说明

### 实体类
1. BaseProcess
   - 流程基类，定义流程的基本属性
   - 包含：流程ID、流程名称、流程类型、创建时间等
   - 数据表：workflow_process

2. ProcessTask
   - 任务基类，定义任务的基本属性
   - 包含：任务ID、任务名称、任务类型、执行人等
   - 数据表：workflow_task

3. CommonProcess
   - 通用流程实现，继承自BaseProcess
   - 适用于常规业务流程
   - 数据表：workflow_process

### Mapper接口
1. ProcessMapper
   - 提供BaseProcess实体的数据访问能力
   - 支持基于流程状态、业务类型等条件的复杂查询
   - 实现流程数据的增删改查操作

2. TaskMapper
   - 提供ProcessTask实体的数据访问能力
   - 支持基于任务类型、执行人、截止日期等条件的查询
   - 实现任务数据的增删改查操作

3. CommonProcessMapper
   - 提供CommonProcess实体的数据访问能力
   - 继承自BaseMapper<CommonProcess>
   - 支持通用流程的数据操作

### 枚举类
1. ProcessTypeEnum
   - 定义流程类型：如案件流程、合同流程等

2. ProcessStatusEnum
   - 定义流程状态：如草稿、进行中、已完成等

3. TaskTypeEnum
   - 定义任务类型：如审批任务、处理任务等

### DTO类
1. ProcessCreateDTO
   - 流程创建数据传输对象
   - 包含创建流程所需的必要信息

2. ProcessUpdateDTO
   - 流程更新数据传输对象
   - 包含更新流程所需的信息

3. TaskCreateDTO
   - 任务创建数据传输对象
   - 包含创建任务所需的必要信息

### VO类
1. ProcessVO
   - 流程视图对象
   - 用于展示流程信息

2. TaskVO
   - 任务视图对象
   - 用于展示任务信息

### 服务接口
1. ProcessService
   - 提供流程相关的业务操作接口
   - 包含流程的CRUD操作

2. TaskService
   - 提供任务相关的业务操作接口
   - 包含任务的CRUD操作

## 使用说明
1. 引入依赖
```xml
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>workflow-model</artifactId>
    <version>${project.version}</version>
</dependency>
```

2. 继承基类
```java
public class CustomProcess extends BaseProcess {
    // 自定义流程实现
}
```

3. 使用服务接口
```java
@Autowired
private ProcessService processService;

// 创建流程
ProcessCreateDTO createDTO = new ProcessCreateDTO();
Long processId = processService.createProcess(createDTO);

// 查询流程
ProcessVO process = processService.getProcess(processId);
```

4. 使用Mapper接口
```java
@Autowired
private ProcessMapper processMapper;

// 基本查询
BaseProcess process = processMapper.selectById(1L);

// 条件查询
List<BaseProcess> processList = processMapper.selectList(
    new LambdaQueryWrapper<BaseProcess>()
        .eq(BaseProcess::getBusinessType, "case")
        .eq(BaseProcess::getStatus, ProcessStatusEnum.RUNNING.getCode())
        .orderByDesc(BaseProcess::getCreateTime)
);

// 分页查询
Page<BaseProcess> page = new Page<>(1, 10);
IPage<BaseProcess> pageResult = processMapper.selectPage(page, 
    new LambdaQueryWrapper<BaseProcess>()
        .eq(BaseProcess::getStatus, ProcessStatusEnum.PENDING.getCode())
);
```

## 注意事项
1. 所有实体类都继承自BaseModel，确保基础字段的一致性
2. DTO类继承自BaseDTO，VO类继承自BaseVO
3. 遵循统一的命名规范和代码风格
4. 确保完整的单元测试覆盖
5. Mapper接口继承BaseMapper，遵循MyBatis-Plus规范
6. 复杂查询请使用XML配置，简单查询可使用LambdaQueryWrapper
7. 注意添加适当的索引以提升查询性能

## 迁移记录

### 2024-04-28 JPA到MyBatis Plus迁移
- 添加MyBatis Plus相关注解（@TableName、@TableField）
- 移除JPA相关注解和导入
- 修改pom.xml，移除JPA依赖，添加MyBatis Plus依赖
- 更新实体类继承关系，使用ModelBaseEntity
- 以下实体类已完成迁移：
  - BaseProcess.java
  - ProcessTask.java
  - CommonProcess.java

### 2024-05-01 添加Mapper接口
- 添加ProcessMapper接口，对应BaseProcess实体
- 添加TaskMapper接口，对应ProcessTask实体
- 添加CommonProcessMapper接口，对应CommonProcess实体
- 所有Mapper接口继承自MyBatis-Plus的BaseMapper
- 添加Mapper接口目录README.md文档说明

---

# law-firm-modules



---

