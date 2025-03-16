# law-firm-personnel 律师事务所人事模块

## 1. 模块介绍

人事模块是律师事务所管理系统的核心基础模块，负责管理律师事务所的所有人员信息、职位信息、业务角色和相关数据。该模块为系统其他模块提供人员基础数据支持，是整个系统的基石。

## 2. 目录结构
law-firm-personnel/
├── service/ # 服务实现层
│ └── impl/ # 服务实现类
│ ├── EmployeeServiceImpl.java # 实现personnel-model的EmployeeService接口
│ ├── EmployeeOrganizationServiceImpl.java # 实现personnel-model的EmployeeOrganizationService接口
│ ├── OrganizationPersonnelRelationServiceImpl.java # 实现organization-model的OrganizationPersonnelRelationService接口
│ ├── PositionServiceImpl.java # 实现organization-model的PositionService接口
│ └── EmployeeAuthBridgeImpl.java # 实现personnel-model的EmployeeAuthBridge接口
│
├── converter/ # 对象转换器
│ ├── EmployeeConverter.java # 员工对象转换器（DTO/VO/Entity转换）
│ ├── OrganizationConverter.java # 组织对象转换器
│ └── PositionConverter.java # 职位对象转换器
│
├── controller/ # 控制器层
│ ├── EmployeeController.java # 员工管理控制器（整合原律师、行政人员控制器）
│ ├── OrganizationController.java # 组织人员关系控制器
│ └── PositionController.java # 职位管理控制器
│
├── util/ # 工具类
│ └── PersonnelCodeGenerator.java # 人员编码生成器
│
├── config/ # 配置类
│ ├── PersonnelConfig.java # 人事模块配置（已实现）
│ ├── AuditConfig.java # 审计配置（已实现）
│ └── MessageConfig.java # 消息配置（已实现）
│
├── event/ # 事件相关（已实现）
│ ├── publisher/ # 事件发布器
│ │ └── EmployeeEventPublisher.java # 员工相关事件发布（已实现）
│ └── listener/ # 事件监听器
│ ├── EmployeeEventListener.java # 员工相关事件监听（已实现）
│ └── NotificationEventListener.java # 消息通知事件监听（已实现）
│
└── resources/ # 资源文件
├── application.yml # 应用配置（已实现）
├── db/ # 数据库脚本
│ ├── migration/ # 数据库迁移脚本
│ │ ├── V1.0.0create_personnel_tables.sql # 创建基础表结构
│ │ └── V1.0.1add_employee_event_log.sql # 添加事件相关表结构
│ └── init/ # 数据初始化脚本
│ └── position_init_data.sql # 职位初始数据
└── templates/ # 模板文件
└── mail/ # 邮件模板
├── employee_welcome.html # 员工欢迎邮件模板（已实现）
└── employee_resign.html # 员工离职邮件模板（已实现）

## 3. 功能概述

人事模块提供以下核心功能：

1. **人员管理**
   - 员工信息管理（执业律师、行政人员、实习生等）
   - 人员基本信息维护
   - 员工生命周期管理（入职、转正、异动、离职）

2. **职位与职级管理**
   - 律师职级体系管理
   - 行政职位体系管理
   - 职级晋升管理

3. **组织关系管理**
   - 员工与组织的关联管理
   - 组织人员查询
   - 员工组织变更记录

4. **业务角色管理**
   - 人员角色定义
   - 人员与角色分配

5. **事件驱动功能**
   - 员工创建事件处理
   - 员工状态变更事件处理
   - 基于事件的通知功能

## 4. 系统架构

遵循服务层次架构，主要分为以下几层：

### 4.1 服务实现层 (Service Impl)
- 实现数据模型层定义的服务接口(personnel-model中的service接口)
- 实现业务逻辑
- 事务管理
- 调用数据访问层(personnel-model中的mapper接口)

### 4.2 控制器层 (Controller)
- 提供RESTful API接口
- 处理请求参数校验
- 响应数据组装
- 异常处理

### 4.3 通用组件
- 数据转换器(负责DTO/VO/Entity之间的转换)
- 事件处理机制(基于Spring Event)
- 审计日志(集成core-audit)
- 消息通知(集成common-message)

## 5. 核心基础设施集成

### 5.1 审计功能集成 (core-audit)
人事模块的关键操作需要进行审计记录，集成core-audit模块实现：
- 员工创建、更新、删除等操作审计
- 组织关系变更审计
- 职位变更审计
- 字段级变更记录

集成方式：
```java
@AuditLog(
    module = "人事管理",
    operateType = "CREATE",
    businessType = "EMPLOYEE",
    description = "创建员工"
)
public Long createEmployee(CreateEmployeeDTO createDTO) {
    // 业务逻辑
}
```

### 5.2 消息功能集成 (common-message)
人事变动需要及时通知相关人员，集成common-message模块实现：
- 员工入职通知
- 员工离职通知
- 合同到期提醒
- 职位变更通知

集成方式：
```java
@Autowired
private EmailService emailService;

public void sendEmployeeNotification(Employee employee, String subject, String content) {
    emailService.send(employee.getEmail(), subject, content, true);
}
```

### 5.3 事件驱动设计
基于Spring Event实现事件驱动设计，降低模块间耦合：
- 员工状态变更事件
- 职位变更事件
- 组织关系变更事件

实现方式：
```java
// 事件发布
@Autowired
private EmployeeEventPublisher eventPublisher;

public void createEmployee(Employee employee) {
    // 业务逻辑
    // ...
    
    // 发布员工创建事件
    eventPublisher.publishEmployeeCreatedEvent(employee);
}

// 事件监听
@Component
public class EmployeeEventListener {
    @EventListener
    public void handleEmployeeCreatedEvent(EmployeeCreatedEvent event) {
        // 处理员工创建事件
        log.info("员工创建事件：员工ID={}, 姓名={}", event.getEmployeeId(), event.getEmployeeName());
        // 处理业务逻辑
    }
}
```

### 5.4 日志记录集成 (common-log)
集成common-log模块实现统一的日志记录：
- 请求日志记录
- 操作日志记录
- 异常日志记录
- 链路追踪

配置示例：
```yaml
lawfirm:
  log:
    enable-method-log: true
    enable-request-log: true
    enable-async-log: true
    log-request-params: true
```

## 6. 已实现的功能

### 6.1 基础配置
- **PersonnelConfig**: 人事模块配置类，包含员工编号、文件上传和邮件通知配置
- **AuditConfig**: 审计配置类，为人事模块提供审计功能
- **MessageConfig**: 消息配置类，定义了邮件通知主题和初始化消息模板

### 6.2 事件系统
- **事件模型**: 在personnel-model模块中定义了`EmployeeEvent`基类、`EmployeeCreatedEvent`和`EmployeeStatusChangedEvent`事件类
- **事件发布**: 实现了`EmployeeEventPublisher`类，负责发布员工相关事件
- **事件监听**: 实现了`EmployeeEventListener`和`NotificationEventListener`类，处理员工相关事件

### 6.3 数据库迁移脚本
- **基础表结构**: `V1.0.0__create_personnel_tables.sql`创建人员表、员工表、律师表和行政人员表
- **事件相关表**: `V1.0.1__add_employee_event_log.sql`创建员工事件日志表和员工通知记录表
- **初始化数据**: `position_init_data.sql`提供职位初始数据

### 6.4 邮件模板
- **员工欢迎邮件**: `employee_welcome.html`定义了员工入职欢迎邮件的HTML模板
- **员工离职邮件**: `employee_resign.html`定义了员工离职通知邮件的HTML模板

## 7. 核心实现设计

### 7.1 Employee实体重构实现

#### 7.1.1 重构背景与目标

我们将Lawyer和Staff实体合并到Employee实体中，主要目标是：

- 简化实体继承结构，降低代码复杂度
- 消除重复代码和冗余字段
- 提高查询效率，减少表连接操作
- 保持与auth-model的兼容性
- 便于扩展支持新的员工类型

#### 7.1.2 核心设计思路

**扁平化设计**：将所有员工类型的属性合并到一个Employee实体中，通过`employeeType`字段区分不同类型员工。

**类型区分机制**：使用`EmployeeTypeEnum`枚举定义不同类型的员工。

**事件驱动设计**：员工状态变更触发相应事件，由事件监听器处理后续业务逻辑。

#### 7.1.3 服务层实现

统一的`EmployeeService`接口整合原有的`LawyerService`和`StaffService`功能，在实现类中根据员工类型区分处理逻辑。

```java
@Override
public Long createEmployee(CreateEmployeeDTO createDTO) {
    // 1. 参数校验
    validateCreateEmployeeDTO(createDTO);
    
    // 2. 构建实体对象
    Employee employee = employeeConverter.toEntity(createDTO);
    
    // 3. 执行业务逻辑
    employeeMapper.insert(employee);
    
    // 4. 发布事件
    eventPublisher.publishEmployeeCreatedEvent(employee);
    
    // 5. 返回结果
    return employee.getId();
}
```

### 7.2 事件系统实现

#### 7.2.1 事件模型设计

在personnel-model模块中定义事件模型：
- `EmployeeEvent`: 员工事件基类，包含通用属性
- `EmployeeCreatedEvent`: 员工创建事件，包含创建来源和创建人信息
- `EmployeeStatusChangedEvent`: 员工状态变更事件，包含原状态和新状态信息

#### 7.2.2 事件发布实现

`EmployeeEventPublisher`类负责发布员工相关事件：
- 使用Spring的`ApplicationEventPublisher`发布事件
- 提供多个发布方法，满足不同业务场景的需求
- 记录事件发布的日志，便于问题追踪

```java
@Component
@Slf4j
public class EmployeeEventPublisher {

    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    public void publishEmployeeCreatedEvent(Employee employee, String source, Long creatorId) {
        EmployeeCreatedEvent event = new EmployeeCreatedEvent(employee, source, creatorId);
        log.debug("发布员工创建事件: employeeId={}, name={}", employee.getId(), employee.getName());
        eventPublisher.publishEvent(event);
    }
    
    public void publishEmployeeStatusChangedEvent(Employee employee, 
                                                EmployeeStatusEnum oldStatus, 
                                                EmployeeStatusEnum newStatus) {
        EmployeeStatusChangedEvent event = new EmployeeStatusChangedEvent(
            employee, oldStatus, newStatus);
        log.debug("发布员工状态变更事件: employeeId={}, name={}, oldStatus={}, newStatus={}", 
                employee.getId(), employee.getName(), oldStatus, newStatus);
        eventPublisher.publishEvent(event);
    }
}
```

#### 7.2.3 事件监听实现

实现两个主要的事件监听器：

1. `EmployeeEventListener`: 处理员工业务事件
   - 处理员工创建事件，执行员工入职后的业务逻辑
   - 处理员工状态变更事件，执行离职、转正等状态变更的业务逻辑

2. `NotificationEventListener`: 处理通知相关事件
   - 监听员工创建事件，发送欢迎通知
   - 监听员工状态变更事件，发送离职、转正等通知

```java
@Component
@Slf4j
public class EmployeeEventListener {

    @EventListener
    public void handleEmployeeCreatedEvent(EmployeeCreatedEvent event) {
        log.info("处理员工创建事件: employeeId={}, name={}", 
                event.getEmployeeId(), event.getEmployeeName());
        // 处理员工入职后的业务逻辑
    }
    
    @EventListener
    public void handleEmployeeStatusChangedEvent(EmployeeStatusChangedEvent event) {
        log.info("处理员工状态变更事件: employeeId={}, name={}, oldStatus={}, newStatus={}", 
                event.getEmployeeId(), event.getEmployeeName(), 
                event.getOldStatus(), event.getNewStatus());
        
        // 处理员工状态变更业务逻辑
        if (event.getNewStatus() == EmployeeStatusEnum.RESIGNED) {
            handleEmployeeResignation(event.getEmployee());
        }
    }
    
    private void handleEmployeeResignation(Employee employee) {
        // 处理员工离职逻辑
        log.info("处理员工离职: employeeId={}, name={}", employee.getId(), employee.getName());
        // 执行离职处理逻辑
    }
}
```

### 7.3 数据库设计

#### 7.3.1 基础表结构
- `person`: 人员基本信息表
- `employee`: 员工信息表
- `lawyer`: 律师特有信息表
- `staff`: 行政人员特有信息表

#### 7.3.2 事件相关表结构
- `employee_event_log`: 员工事件日志表，记录所有员工相关事件
- `employee_notification`: 员工通知记录表，记录所有发送给员工的通知

#### 7.3.3 数据持久化策略
- 使用Flyway进行数据库版本管理
- 通过迁移脚本创建和更新表结构
- 使用初始化脚本填充基础数据

## 8. 接口规范

### 8.1 员工管理API

- `GET /api/personnel/employees` - 获取员工列表
- `GET /api/personnel/employees/{id}` - 获取单个员工详情
- `POST /api/personnel/employees` - 创建员工
- `PUT /api/personnel/employees/{id}` - 更新员工信息
- `DELETE /api/personnel/employees/{id}` - 删除员工

### 8.2 员工组织管理API

- `POST /api/personnel/employees/{id}/organizations` - 分配员工到组织
- `PUT /api/personnel/employees/{id}/organizations/{orgId}` - 更新员工组织关系
- `DELETE /api/personnel/employees/{id}/organizations/{orgId}` - 移除员工组织关系
- `GET /api/personnel/employees/{id}/organizations` - 获取员工所属组织

### 8.3 组织人员管理API

- `GET /api/personnel/organizations/{id}/employees` - 获取组织下的员工
- `POST /api/personnel/organizations/{id}/employees` - 批量分配员工到组织
- `DELETE /api/personnel/organizations/{id}/employees/{employeeId}` - 从组织移除员工

## 9. 配置说明

核心配置项示例：

```yaml
# 人事模块配置
personnel:
  # 员工编号配置
  employee-code:
    prefix: "EMP"
    length: 8
    include-date: true
  
  # 文件上传配置
  upload:
    photo-path: "/data/upload/personnel/photos" 
    doc-path: "/data/upload/personnel/docs"
    allowed-types: "jpg,jpeg,png,pdf,doc,docx,xls,xlsx"
    max-size: 10
  
  # 邮件通知配置
  notification:
    enabled: true
    sender: "personnel@lawfirm.com"
    cc: "hr@lawfirm.com"
    contract-exp
birthday-reminder-days: 7
```

## 10. 依赖管理

**重要说明**：本模块不应在自身的pom.xml中定义第三方依赖的版本号。所有依赖版本必须由`law-firm-dependencies/pom.xml`统一管理，以确保整个项目中各个模块使用相同版本的依赖。

示例：
```xml
<!-- 正确方式 - 不指定版本号 -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
</dependency>

<!-- 错误方式 - 在子模块中指定版本号 -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.5</version> <!-- 不应在此处定义版本 -->
</dependency>
```

## 11. 待开发功能

1. **缓存配置实现**
   - 实现`PersonnelCacheConfig`类，配置缓存策略
   - 为频繁访问的数据添加缓存支持

2. **单元测试**
   - 编写服务层单元测试
   - 编写事件处理单元测试
   - 编写控制器层测试

3. **API文档**
   - 使用Swagger或SpringDoc生成API文档
   - 完善API注释和说明

4. **性能优化**
   - 查询优化
   - 批量操作优化
   - 缓存策略优化

## 12. 责任人

- 模块负责人：[待定]
- 开发团队：[待定]
- 技术支持：[待定]    