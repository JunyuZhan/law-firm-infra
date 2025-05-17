# 系统管理模块 (Law Firm System)

## 一、模块说明
系统管理模块是律师事务所管理系统的基础支撑模块，负责全局系统配置管理、字典管理、系统监控和系统升级等基础功能。本模块仅供系统管理员使用，需要最高级别的系统权限。作为整个系统的基础设施，系统管理模块为其他业务模块提供统一的配置管理、字典服务和系统维护能力。

### 1.1 系统设置分级
#### 个人级设置（由auth模块或personnel模块负责）
- 位置：认证授权模块或人事模块
- 权限：普通用户
- 功能：
  - 个人偏好设置
  - 消息通知设置
  - 隐私设置
  - 界面布局设置
  - 快捷操作设置

#### 系统级设置（本模块职责）
- 位置：系统管理模块
- 权限：仅系统管理员
- 功能：
  - 全局系统参数配置
  - 系统字典管理
  - 系统监控和维护
  - 系统版本升级和补丁管理
  - 系统日志查询和审计记录管理

### 1.2 权限要求
- 访问角色：仅系统管理员（ROLE_ADMIN）
- 权限级别：系统最高权限
- 访问限制：需要进行IP白名单控制
- 操作审计：所有操作都需要记录审计日志（使用core-audit）

### 1.3 核心功能
- 系统配置管理：全局系统运行参数的配置
- 字典管理：系统通用字典维护
- 系统监控：系统运行状态监控
- 系统升级：系统版本升级和补丁管理
- 日志管理：系统日志查询和审计记录查看

### 1.4 与其他模块的关系

#### 底层基础设施（注解方式调用）
- common-core：核心功能支持
- common-data：数据访问能力（@DataScope等注解）
- common-cache：缓存处理（@Cacheable等注解）
- common-security：安全控制（@PreAuthorize等注解）
- common-web：Web功能支持（BaseController等基类）
- common-log：日志处理（@Log等注解）

#### 核心功能模块（注解/工具类方式调用）
1. core-audit：
   - @AuditLog：审计日志记录
   - AuditLogUtils：审计工具类
   - SecurityAuditAspect：安全审计切面

2. core-message：
   - @MessageListener：消息监听
   - MessageUtils：消息发送工具
   - NotificationTemplate：通知模板

3. core-storage：
   - @Storage：存储注解
   - StorageUtils：存储工具类

4. core-search：
   - @Searchable：搜索注解
   - SearchUtils：搜索工具类

#### 模型层依赖
1. system-model：系统管理模块的模型层
   ```xml
   <dependency>
       <groupId>com.lawfirm</groupId>
       <artifactId>system-model</artifactId>
   </dependency>
   ```
   - 实体类定义：
     - SysConfig：系统配置实体
     - SysDict：系统字典实体
     - SysDictItem：系统字典项实体
     - SysUpgrade：系统升级实体
     - SysUpgradePatch：系统升级补丁实体
     - SysLog：系统日志实体
     - SysAuditLog：系统审计日志实体
   - 服务接口定义：
     - ConfigService：配置服务接口，定义配置的CRUD操作
     - DictService：字典服务接口，定义字典的CRUD操作
     - UpgradeService：升级服务接口，定义系统升级和补丁管理操作
     - LogService：日志服务接口，定义日志查询和审计记录查看操作
   - Mapper接口定义：
     - SysConfigMapper：配置Mapper接口
     - SysDictMapper：字典Mapper接口
     - SysDictItemMapper：字典项Mapper接口
     - SysUpgradeMapper：系统升级Mapper接口
     - SysUpgradePatchMapper：系统升级补丁Mapper接口
     - SysLogMapper：系统日志Mapper接口
     - SysAuditLogMapper：系统审计日志Mapper接口
   - 数据传输对象：
     - config包：ConfigCreateDTO、ConfigUpdateDTO等
     - dict包：DictCreateDTO、DictUpdateDTO、DictItemCreateDTO等
     - upgrade包：UpgradeCreateDTO、UpgradeUpdateDTO、PatchCreateDTO等
     - log包：LogQueryDTO、AuditLogQueryDTO等
   - 视图对象：
     - ConfigVO：配置视图对象
     - DictVO：字典视图对象
     - DictItemVO：字典项视图对象
     - UpgradeVO：升级视图对象
     - UpgradePatchVO：升级补丁视图对象
     - LogVO：日志视图对象
     - AuditLogVO：审计日志视图对象
   - 枚举定义：
     - ConfigTypeEnum：配置类型枚举
     - DictTypeEnum：字典类型枚举
     - UpgradeStatusEnum：升级状态枚举
     - PatchTypeEnum：补丁类型枚举
     - LogLevelEnum：日志级别枚举
     - LogTypeEnum：日志类型枚举
     - AuditOperationEnum：审计操作类型枚举

   > **注意**：law-firm-system模块直接使用system-model中定义的DTO、VO和Mapper接口，不需要扩展。这样可以保持模型的一致性，避免重复定义。

#### 业务模块关系（直接依赖调用）
1. auth模块（law-firm-auth）：
   ```java
   // 通过依赖注入方式调用
   @Autowired
   private AuthService authService;
   
   // 权限校验
   boolean hasPermission = authService.checkPermission(permission);
   
   // 获取用户角色
   List<String> roles = authService.getUserRoles(userId);
   ```

## 二、技术架构

### 2.1 目录结构
```
law-firm-system
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.lawfirm.system
│   │   │       ├── controller         # 接口层
│   │   │       │   ├── config        # 配置控制器
│   │   │       │   ├── dict         # 字典控制器
│   │   │       │   ├── monitor      # 监控控制器
│   │   │       │   ├── upgrade      # 升级控制器
│   │   │       │   └── log          # 日志控制器
│   │   │       ├── service           # 服务层
│   │   │       │   └── impl          # 服务实现类
│   │   │       │       ├── config    # 配置服务实现
│   │   │       │       ├── dict     # 字典服务实现
│   │   │       │       ├── monitor  # 监控服务实现
│   │   │       │       ├── upgrade  # 升级服务实现
│   │   │       │       └── log      # 日志服务实现
│   │   │       └── config            # 模块配置类
│   │   └── resources                 # 资源文件
│   │       ├── application.yml       # 模块配置文件
│   │       └── db
│   │           └── migration         # 数据库迁移脚本
│   │                   V1.0.0__init_system_tables.sql
│   │                   V1.0.1__add_system_config_data.sql
│   └── test                        # 测试代码
│       └── java
│           └── com.lawfirm.system
│               ├── controller
│               └── service
└── pom.xml                         # 项目依赖
```

### 2.2 开发规范

1. **基础设施使用规范**
   - 审计日志：使用core-audit模块的AuditLogService
   - 消息通知：使用core-message模块的MessageService
   - 缓存处理：使用common-cache的注解和工具类
   - 数据访问：使用common-data的BaseMapper和MyBatis
   - 安全控制：使用common-security的注解和工具类
   - Web处理：使用common-web的统一响应和异常处理

2. **通用功能使用**
   - Controller层继承BaseController，并添加@RequiresRoles("ROLE_ADMIN")注解
   - 服务实现类放在service.impl包下，实现system-model中定义的接口
   - 直接使用system-model中定义的Mapper接口，不需要重新定义
   - 直接使用system-model中定义的DTO和VO，不需要扩展
   - 使用@Log注解记录操作日志（来自common-log）
   - 使用@Cache相关注解处理缓存（来自common-cache）
   - 使用@PreAuthorize等注解控制权限（来自common-security）

3. **服务实现规范**
   - 实现类需要添加@Service注解
   - 实现类放在service.impl包下
   - 实现system-model中定义的服务接口
   - 配置服务实现ConfigServiceImpl：实现ConfigService接口
   - 字典服务实现DictServiceImpl：实现DictService接口
   - 监控服务实现MonitorServiceImpl：实现自定义监控功能
   - 升级服务实现UpgradeServiceImpl：实现UpgradeService接口，管理系统版本和补丁
   - 日志服务实现LogServiceImpl：实现LogService接口，提供日志查询和审计记录查看
   - 使用@Transactional注解管理事务

4. **MyBatis使用规范**
   - 使用system-model中定义的Mapper接口
   - 不需要在law-firm-system模块中重新定义Mapper接口
   - 在service.impl包下的服务实现类中注入Mapper接口
   - 使用MyBatis-Plus提供的CRUD方法
   - 复杂查询可以使用LambdaQueryWrapper
   - 分页查询使用Page对象

5. **安全规范**
   - 所有接口必须进行权限校验
   - 敏感操作需要二次确认
   - 重要操作需要留存审计日志
   - 配置修改需要进行备份

## 三、数据库设计

### 3.1 表结构设计

#### 系统配置表 (sys_config)
| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|-------|------|-----|-------|-------|------|
| id | bigint | 20 | 否 | | 主键ID |
| config_name | varchar | 100 | 否 | | 配置名称 |
| config_key | varchar | 100 | 否 | | 配置键 |
| config_value | varchar | 500 | 否 | | 配置值 |
| config_type | varchar | 20 | 否 | 'SYSTEM' | 配置类型（SYSTEM系统配置/BUSINESS业务配置） |
| is_system | tinyint | 1 | 否 | 0 | 是否系统内置（0否/1是） |
| remark | varchar | 500 | 是 | NULL | 备注说明 |
| create_by | varchar | 64 | 否 | | 创建人 |
| create_time | datetime | | 否 | CURRENT_TIMESTAMP | 创建时间 |
| update_by | varchar | 64 | 是 | NULL | 更新人 |
| update_time | datetime | | 是 | NULL | 更新时间 |
| deleted | tinyint | 1 | 否 | 0 | 是否删除（0否/1是） |

#### 字典表 (sys_dict)
| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|-------|------|-----|-------|-------|------|
| id | bigint | 20 | 否 | | 主键ID |
| dict_name | varchar | 100 | 否 | | 字典名称 |
| dict_type | varchar | 100 | 否 | | 字典类型 |
| status | tinyint | 1 | 否 | 0 | 状态（0正常/1停用） |
| remark | varchar | 500 | 是 | NULL | 备注说明 |
| create_by | varchar | 64 | 否 | | 创建人 |
| create_time | datetime | | 否 | CURRENT_TIMESTAMP | 创建时间 |
| update_by | varchar | 64 | 是 | NULL | 更新人 |
| update_time | datetime | | 是 | NULL | 更新时间 |
| deleted | tinyint | 1 | 否 | 0 | 是否删除（0否/1是） |

#### 字典项表 (sys_dict_item)
| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|-------|------|-----|-------|-------|------|
| id | bigint | 20 | 否 | | 主键ID |
| dict_id | bigint | 20 | 否 | | 字典ID |
| item_label | varchar | 100 | 否 | | 字典标签 |
| item_value | varchar | 100 | 否 | | 字典键值 |
| item_sort | int | 10 | 否 | 0 | 排序号 |
| status | tinyint | 1 | 否 | 0 | 状态（0正常/1停用） |
| remark | varchar | 500 | 是 | NULL | 备注说明 |
| create_by | varchar | 64 | 否 | | 创建人 |
| create_time | datetime | | 否 | CURRENT_TIMESTAMP | 创建时间 |
| update_by | varchar | 64 | 是 | NULL | 更新人 |
| update_time | datetime | | 是 | NULL | 更新时间 |
| deleted | tinyint | 1 | 否 | 0 | 是否删除（0否/1是） |

#### 系统升级表 (sys_upgrade)
| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|-------|------|-----|-------|-------|------|
| id | bigint | 20 | 否 | | 主键ID |
| version | varchar | 50 | 否 | | 版本号 |
| version_name | varchar | 100 | 否 | | 版本名称 |
| description | text | | 是 | NULL | 版本描述 |
| upgrade_time | datetime | | 否 | | 升级时间 |
| status | varchar | 20 | 否 | 'PENDING' | 状态（PENDING待升级/UPGRADING升级中/SUCCESS成功/FAILED失败） |
| backup_path | varchar | 255 | 是 | NULL | 备份路径 |
| create_by | varchar | 64 | 否 | | 创建人 |
| create_time | datetime | | 否 | CURRENT_TIMESTAMP | 创建时间 |
| update_by | varchar | 64 | 是 | NULL | 更新人 |
| update_time | datetime | | 是 | NULL | 更新时间 |
| deleted | tinyint | 1 | 否 | 0 | 是否删除（0否/1是） |

#### 系统升级补丁表 (sys_upgrade_patch)
| 字段名 | 类型 | 长度 | 允许空 | 默认值 | 说明 |
|-------|------|-----|-------|-------|------|
| id | bigint | 20 | 否 | | 主键ID |
| upgrade_id | bigint | 20 | 否 | | 升级ID |
| patch_name | varchar | 100 | 否 | | 补丁名称 |
| patch_type | varchar | 20 | 否 | 'SQL' | 补丁类型（SQL/JAR/CONFIG） |
| patch_path | varchar | 255 | 否 | | 补丁路径 |
| execute_order | int | 10 | 否 | 0 | 执行顺序 |
| status | varchar | 20 | 否 | 'PENDING' | 状态（PENDING待执行/EXECUTING执行中/SUCCESS成功/FAILED失败） |
| error_message | text | | 是 | NULL | 错误信息 |
| create_by | varchar | 64 | 否 | | 创建人 |
| create_time | datetime | | 否 | CURRENT_TIMESTAMP | 创建时间 |
| update_by | varchar | 64 | 是 | NULL | 更新人 |
| update_time | datetime | | 是 | NULL | 更新时间 |
| deleted | tinyint | 1 | 否 | 0 | 是否删除（0否/1是） |

### 3.2 数据库迁移脚本

系统管理模块的数据库迁移脚本位于 `src/main/resources/db/migration` 目录下，采用Flyway命名规范：

```
V1.0.0__init_system_tables.sql       # 初始化系统表结构
V1.0.1__add_system_config_data.sql   # 添加系统配置初始数据
```

#### 初始化脚本示例 (V1.0.0__init_system_tables.sql)

```sql
-- 系统配置表
CREATE TABLE IF NOT EXISTS sys_config (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  config_name varchar(100) NOT NULL COMMENT '配置名称',
  config_key varchar(100) NOT NULL COMMENT '配置键',
  config_value varchar(500) NOT NULL COMMENT '配置值',
  config_type varchar(20) NOT NULL DEFAULT 'SYSTEM' COMMENT '配置类型（SYSTEM系统配置/BUSINESS业务配置）',
  is_system tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否系统内置（0否/1是）',
  remark varchar(500) DEFAULT NULL COMMENT '备注说明',
  create_by varchar(64) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0否/1是）',
  PRIMARY KEY (id),
  UNIQUE KEY uk_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 字典表
CREATE TABLE IF NOT EXISTS sys_dict (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  dict_name varchar(100) NOT NULL COMMENT '字典名称',
  dict_type varchar(100) NOT NULL COMMENT '字典类型',
  status tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态（0正常/1停用）',
  remark varchar(500) DEFAULT NULL COMMENT '备注说明',
  create_by varchar(64) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0否/1是）',
  PRIMARY KEY (id),
  UNIQUE KEY uk_dict_type (dict_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- 字典项表
CREATE TABLE IF NOT EXISTS sys_dict_item (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  dict_id bigint(20) NOT NULL COMMENT '字典ID',
  item_label varchar(100) NOT NULL COMMENT '字典标签',
  item_value varchar(100) NOT NULL COMMENT '字典键值',
  item_sort int(10) NOT NULL DEFAULT 0 COMMENT '排序号',
  status tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态（0正常/1停用）',
  remark varchar(500) DEFAULT NULL COMMENT '备注说明',
  create_by varchar(64) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0否/1是）',
  PRIMARY KEY (id),
  KEY idx_dict_id (dict_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典项表';

-- 系统升级表
CREATE TABLE IF NOT EXISTS sys_upgrade (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  version varchar(50) NOT NULL COMMENT '版本号',
  version_name varchar(100) NOT NULL COMMENT '版本名称',
  description text DEFAULT NULL COMMENT '版本描述',
  upgrade_time datetime NOT NULL COMMENT '升级时间',
  status varchar(20) NOT NULL DEFAULT 'PENDING' COMMENT '状态（PENDING待升级/UPGRADING升级中/SUCCESS成功/FAILED失败）',
  backup_path varchar(255) DEFAULT NULL COMMENT '备份路径',
  create_by varchar(64) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0否/1是）',
  PRIMARY KEY (id),
  UNIQUE KEY uk_version (version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统升级表';

-- 系统升级补丁表
CREATE TABLE IF NOT EXISTS sys_upgrade_patch (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  upgrade_id bigint(20) NOT NULL COMMENT '升级ID',
  patch_name varchar(100) NOT NULL COMMENT '补丁名称',
  patch_type varchar(20) NOT NULL DEFAULT 'SQL' COMMENT '补丁类型（SQL/JAR/CONFIG）',
  patch_path varchar(255) NOT NULL COMMENT '补丁路径',
  execute_order int(10) NOT NULL DEFAULT 0 COMMENT '执行顺序',
  status varchar(20) NOT NULL DEFAULT 'PENDING' COMMENT '状态（PENDING待执行/EXECUTING执行中/SUCCESS成功/FAILED失败）',
  error_message text DEFAULT NULL COMMENT '错误信息',
  create_by varchar(64) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by varchar(64) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0否/1是）',
  PRIMARY KEY (id),
  KEY idx_upgrade_id (upgrade_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统升级补丁表';
```

#### 数据初始化脚本示例 (V1.0.1__add_system_config_data.sql)

```sql
-- 初始化系统配置数据
INSERT INTO sys_config (config_name, config_key, config_value, config_type, is_system, remark, create_by) VALUES 
('系统名称', 'sys.name', '律师事务所管理系统', 'SYSTEM', 1, '系统名称配置', 'admin'),
('系统版本', 'sys.version', '1.0.0', 'SYSTEM', 1, '系统版本配置', 'admin'),
('系统Logo', 'sys.logo', '/static/logo.png', 'SYSTEM', 1, '系统Logo配置', 'admin'),
('系统首页', 'sys.index', '/dashboard', 'SYSTEM', 1, '系统首页配置', 'admin'),
('系统底部版权', 'sys.copyright', 'Copyright © 2023 Law Firm Management System', 'SYSTEM', 1, '系统底部版权配置', 'admin'),
('系统默认主题', 'sys.default.theme', 'light', 'SYSTEM', 1, '系统默认主题配置', 'admin'),
('系统默认语言', 'sys.default.language', 'zh_CN', 'SYSTEM', 1, '系统默认语言配置', 'admin'),
('系统默认分页大小', 'sys.default.page.size', '10', 'SYSTEM', 1, '系统默认分页大小配置', 'admin'),
('系统会话超时时间', 'sys.session.timeout', '1800', 'SYSTEM', 1, '系统会话超时时间配置（秒）', 'admin'),
('系统最大上传文件大小', 'sys.upload.max.size', '10', 'SYSTEM', 1, '系统最大上传文件大小配置（MB）', 'admin');

-- 初始化字典数据
INSERT INTO sys_dict (dict_name, dict_type, status, remark, create_by) VALUES 
('系统状态', 'sys_status', 0, '系统状态字典', 'admin'),
('配置类型', 'config_type', 0, '配置类型字典', 'admin'),
('是否标识', 'yes_no', 0, '是否标识字典', 'admin'),
('补丁类型', 'patch_type', 0, '补丁类型字典', 'admin'),
('升级状态', 'upgrade_status', 0, '升级状态字典', 'admin');

-- 初始化字典项数据
-- 系统状态字典项
INSERT INTO sys_dict_item (dict_id, item_label, item_value, item_sort, status, remark, create_by) VALUES 
((SELECT id FROM sys_dict WHERE dict_type = 'sys_status'), '正常', '0', 1, 0, '正常状态', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'sys_status'), '停用', '1', 2, 0, '停用状态', 'admin');

-- 配置类型字典项
INSERT INTO sys_dict_item (dict_id, item_label, item_value, item_sort, status, remark, create_by) VALUES 
((SELECT id FROM sys_dict WHERE dict_type = 'config_type'), '系统配置', 'SYSTEM', 1, 0, '系统配置', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'config_type'), '业务配置', 'BUSINESS', 2, 0, '业务配置', 'admin');

-- 是否标识字典项
INSERT INTO sys_dict_item (dict_id, item_label, item_value, item_sort, status, remark, create_by) VALUES 
((SELECT id FROM sys_dict WHERE dict_type = 'yes_no'), '是', '1', 1, 0, '是', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'yes_no'), '否', '0', 2, 0, '否', 'admin');

-- 补丁类型字典项
INSERT INTO sys_dict_item (dict_id, item_label, item_value, item_sort, status, remark, create_by) VALUES 
((SELECT id FROM sys_dict WHERE dict_type = 'patch_type'), 'SQL脚本', 'SQL', 1, 0, 'SQL脚本补丁', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'patch_type'), 'JAR包', 'JAR', 2, 0, 'JAR包补丁', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'patch_type'), '配置文件', 'CONFIG', 3, 0, '配置文件补丁', 'admin');

-- 升级状态字典项
INSERT INTO sys_dict_item (dict_id, item_label, item_value, item_sort, status, remark, create_by) VALUES 
((SELECT id FROM sys_dict WHERE dict_type = 'upgrade_status'), '待升级', 'PENDING', 1, 0, '待升级状态', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'upgrade_status'), '升级中', 'UPGRADING', 2, 0, '升级中状态', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'upgrade_status'), '成功', 'SUCCESS', 3, 0, '升级成功状态', 'admin'),
((SELECT id FROM sys_dict WHERE dict_type = 'upgrade_status'), '失败', 'FAILED', 4, 0, '升级失败状态', 'admin');
```

### 3.3 数据库版本管理

系统管理模块使用Flyway进行数据库版本管理，在pom.xml中添加以下依赖：

```xml
<!-- Flyway数据库版本管理 -->
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-mysql</artifactId>
</dependency>
```

Flyway配置示例：

```yaml
spring:
  flyway:
    enabled: true
    locations: classpath:META-INF/db/migration
    baseline-on-migrate: true
    baseline-version: 0
    validate-on-migrate: true
    out-of-order: false
    table: flyway_schema_history
```

## 四、配置说明

### 4.1 模块配置
系统管理模块的配置通过以下方式提供：

1. **通过父项目配置**：基础配置由父项目提供，如数据库连接、缓存等

2. **通过配置类**：模块特定的配置通过Java配置类提供
   ```java
   @Configuration
   public class SystemModuleConfig {
       // 系统管理模块特定配置
   }
   ```

3. **通过属性文件**：在resources目录下可以提供特定的属性文件
   ```
   system.security.admin-role=ROLE_ADMIN
   system.security.ip-whitelist=127.0.0.1,192.168.1.1
   ```

### 4.2 安全配置
安全相关配置主要包括：

```java
@Configuration
@EnableWebSecurity
public class SystemSecurityConfig {
    // 安全相关配置
    // 忽略认证的URL配置
    // 管理员操作相关配置
}
```

## 五、常见问题

### 5.1 缓存相关
1. 问题：缓存数据不一致
   解决：使用Cache Aside模式，先更新数据库，再删除缓存

2. 问题：缓存穿透
   解决：使用BloomFilter

### 5.2 访问监控相关
1. 问题：敏感信息泄露
   解决：统一脱敏处理，配置脱敏规则

2. 问题：统计分析性能
   解决：采用离线计算，结果缓存

### 5.3 系统升级相关
1. 问题：升级过程中断
   解决：使用事务管理确保升级原子性，支持断点续传

2. 问题：升级后系统不稳定
   解决：提供回滚机制，自动备份升级前的系统状态

3. 问题：补丁冲突
   解决：实现补丁依赖检查，确保按正确顺序应用补丁

## 六、业务流程

### 6.1 系统配置管理流程
1. 管理员登录系统管理模块
2. 查看当前系统配置列表
3. 新增/修改/删除系统配置
4. 系统自动记录操作日志
5. 配置变更后自动刷新缓存

### 6.2 字典管理流程
1. 管理员登录系统管理模块
2. 查看字典分类和字典项
3. 新增/修改/删除字典和字典项
4. 系统自动记录操作日志
5. 字典变更后自动刷新缓存

### 6.3 系统监控流程
1. 管理员登录系统管理模块
2. 查看系统运行状态和性能指标
3. 设置监控阈值和告警规则
4. 系统自动记录异常情况
5. 触发告警时自动通知管理员

### 6.4 系统升级流程
1. 管理员登录系统管理模块
2. 上传新版本或补丁包
3. 系统自动验证包的完整性和兼容性
4. 系统自动备份当前版本
5. 执行升级操作
6. 升级完成后自动验证系统状态
7. 如有问题，可执行回滚操作

### 6.5 日志管理流程
1. 管理员登录系统管理模块
2. 进入日志管理页面
3. 选择日志类型（系统日志/审计日志）
4. 设置查询条件（时间范围、操作类型、操作人等）
5. 系统管理模块调用日志模块的服务接口查询日志数据
6. 系统管理模块展示日志列表和详情
7. 管理员可以导出日志或对特定日志添加备注
8. 日志的存储和清理由日志模块负责

## 七、模块交互场景

### 7.1 与认证授权模块交互
- 场景：用户登录系统管理模块
- 交互流程：
  1. 用户提交登录请求
  2. 系统管理模块调用认证授权模块验证用户身份
  3. 认证授权模块返回用户角色和权限信息
  4. 系统管理模块根据权限控制功能访问

### 7.2 与日志模块交互
- 场景：记录系统配置变更
- 交互流程：
  1. 管理员修改系统配置
  2. 系统管理模块调用日志模块记录操作
  3. 日志模块保存操作记录，包含操作人、操作时间、操作内容等信息
  4. 系统管理模块提供日志查询界面，但底层数据由日志模块提供

### 7.3 与消息模块交互
- 场景：系统升级通知
- 交互流程：
  1. 管理员发起系统升级
  2. 系统管理模块调用消息模块发送升级通知
  3. 消息模块向所有在线用户推送通知
  4. 用户收到升级提醒，系统提示保存工作并退出

### 7.4 与其他业务模块交互
- 场景：提供字典数据服务
- 交互流程：
  1. 业务模块需要展示字典数据（如案件类型、文档类型等）
  2. 业务模块调用系统管理模块的字典服务
  3. 系统管理模块返回字典数据
  4. 业务模块展示字典数据 