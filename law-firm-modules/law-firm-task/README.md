# 任务管理模块 (law-firm-task)

## 模块概述
任务管理模块是律所管理系统中的任务管理子系统，用于管理律所内部的各种任务，包括案件任务、合同任务、文档任务等。该模块与排期模块紧密集成，共同实现任务的时间管理和提醒功能。同时，该模块还与案件、客户、文档、人员等模块建立了关联关系，实现跨模块的任务管理。

## 功能特性
1. **任务基础管理**
   - 任务创建、分配、更新、删除
   - 任务状态跟踪（待办、进行中、已完成、已取消）
   - 任务优先级管理
   - 任务分类和标签

2. **任务时间管理**
   - 任务开始时间和截止时间设置
   - 任务时间冲突检查
   - 任务时间调整
   - 任务提醒设置

3. **任务协作**
   - 多人协作任务
   - 任务评论和讨论
   - 任务附件管理
   - 任务历史记录

4. **任务统计与分析**
   - 个人任务统计
   - 团队任务统计
   - 任务完成率分析
   - 任务耗时分析

5. **任务模板**
   - 常用任务模板
   - 可复用的任务流程
   - 标准化任务操作

6. **跨模块任务管理**
   - 案件关联任务：与案件模块关联，管理案件相关任务
   - 客户关联任务：与客户模块关联，管理客户服务任务
   - 文档关联任务：与文档模块关联，管理文档处理任务
   - 部门任务管理：按部门组织和管理任务
   - 法律专业任务：区分普通任务和法律专业任务

## 技术架构
1. **基础框架**
   - Spring Boot
   - MyBatis-Plus
   - Spring Security
   - Redis

2. **模块依赖**
   - common-core：基础框架
   - law-firm-schedule：排期模块
   - law-firm-document：文档模块
   - law-firm-personnel：人员模块
   - law-firm-case：案件模块
   - law-firm-client：客户模块

3. **数据库设计**
   ```sql
   -- 任务表
   CREATE TABLE `task` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
     `title` varchar(100) NOT NULL COMMENT '任务标题',
     `content` text COMMENT '任务内容',
     `priority` tinyint NOT NULL DEFAULT '0' COMMENT '优先级(0:低 1:中 2:高)',
     `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态(0:待办 1:进行中 2:已完成 3:已取消)',
     `start_time` datetime COMMENT '开始时间',
     `end_time` datetime COMMENT '截止时间',
     `assignee_id` bigint COMMENT '负责人ID',
     `creator_id` bigint NOT NULL COMMENT '创建人ID',
     `parent_id` bigint COMMENT '父任务ID',
     `case_id` bigint DEFAULT NULL COMMENT '案例ID',
     `client_id` bigint DEFAULT NULL COMMENT '客户ID',
     `schedule_id` bigint DEFAULT NULL COMMENT '日程ID',
     `is_legal_task` tinyint(1) DEFAULT '0' COMMENT '是否法律专业任务',
     `document_ids` varchar(1000) DEFAULT NULL COMMENT '关联文档ID列表（JSON数组）',
     `department_id` bigint DEFAULT NULL COMMENT '所属部门ID',
     `category_id` bigint COMMENT '分类ID',
     `tenant_id` bigint NOT NULL COMMENT '租户ID',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
     PRIMARY KEY (`id`),
     KEY `idx_assignee` (`assignee_id`),
     KEY `idx_creator` (`creator_id`),
     KEY `idx_parent` (`parent_id`),
     KEY `idx_case` (`case_id`),
     KEY `idx_client` (`client_id`),
     KEY `idx_department` (`department_id`),
     KEY `idx_category` (`category_id`),
     KEY `idx_tenant` (`tenant_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';

   -- 任务标签表
   CREATE TABLE `task_tag` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
     `name` varchar(50) NOT NULL COMMENT '标签名称',
     `color` varchar(20) COMMENT '标签颜色',
     `sort` int DEFAULT '0' COMMENT '排序',
     `tenant_id` bigint NOT NULL COMMENT '租户ID',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`),
     KEY `idx_tenant` (`tenant_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务标签表';

   -- 任务标签关联表
   CREATE TABLE `task_tag_relation` (
     `task_id` bigint NOT NULL COMMENT '任务ID',
     `tag_id` bigint NOT NULL COMMENT '标签ID',
     `tenant_id` bigint NOT NULL COMMENT '租户ID',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     PRIMARY KEY (`task_id`,`tag_id`),
     KEY `idx_tenant` (`tenant_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务标签关联表';

   -- 任务评论表
   CREATE TABLE `task_comment` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
     `task_id` bigint NOT NULL COMMENT '任务ID',
     `content` text NOT NULL COMMENT '评论内容',
     `user_id` bigint NOT NULL COMMENT '评论人ID',
     `parent_id` bigint COMMENT '父评论ID',
     `tenant_id` bigint NOT NULL COMMENT '租户ID',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`),
     KEY `idx_task` (`task_id`),
     KEY `idx_user` (`user_id`),
     KEY `idx_parent` (`parent_id`),
     KEY `idx_tenant` (`tenant_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务评论表';

   -- 任务附件表
   CREATE TABLE `task_attachment` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '附件ID',
     `task_id` bigint NOT NULL COMMENT '任务ID',
     `file_name` varchar(255) NOT NULL COMMENT '文件名',
     `file_path` varchar(500) NOT NULL COMMENT '文件路径',
     `file_size` bigint NOT NULL COMMENT '文件大小',
     `file_type` varchar(50) COMMENT '文件类型',
     `file_suffix` varchar(20) DEFAULT NULL COMMENT '文件后缀',
     `preview_url` varchar(255) DEFAULT NULL COMMENT '预览地址',
     `download_url` varchar(255) DEFAULT NULL COMMENT '下载地址',
     `download_count` int(11) DEFAULT '0' COMMENT '下载次数',
     `previewable` tinyint(1) DEFAULT '0' COMMENT '是否可预览',
     `file_icon` varchar(50) DEFAULT NULL COMMENT '文件图标',
     `sort` int DEFAULT '0' COMMENT '排序',
     `storage_id` varchar(100) COMMENT '存储ID',
     `tenant_id` bigint NOT NULL COMMENT '租户ID',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`),
     KEY `idx_task` (`task_id`),
     KEY `idx_tenant` (`tenant_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务附件表';
   ```

## 模块集成
1. **与排期模块集成**
   - 任务时间与日程同步
   - 任务提醒使用排期提醒机制
   - 时间冲突检查复用排期模块功能

2. **与文档模块集成**
   - 任务附件使用文档模块的存储服务
   - 文档处理任务与文档模块关联
   - 支持文档预览和下载功能

3. **与人员模块集成**
   - 任务负责人和参与人使用人员模块数据
   - 任务权限与人员权限关联
   - 支持按部门组织任务

4. **与案件模块集成**
   - 任务与案件关联
   - 支持案件相关任务查询
   - 案件进度与任务进度同步

5. **与客户模块集成**
   - 任务与客户关联
   - 支持客户服务任务管理
   - 客户服务进度跟踪

## 配置说明
```yaml
# 任务模块配置
task:
  # 任务提醒配置
  remind:
    # 默认提醒时间（分钟）
    default-before-minutes: 30
    # 提醒方式
    types: [EMAIL, SMS, SYSTEM]
  
  # 任务附件配置
  attachment:
    # 最大附件大小
    max-size: 20MB
    # 允许的文件类型
    allowed-types: [doc, docx, pdf, xls, xlsx, jpg, png]
  
  # 任务统计配置
  statistic:
    # 统计任务执行时间
    cron: "0 0 1 * * ?"
    
  # 模块关联配置
  relation:
    # 是否启用案件关联
    enable-case: true
    # 是否启用客户关联
    enable-client: true
    # 是否启用文档关联
    enable-document: true
    # 是否启用部门关联
    enable-department: true
```

## 开发规范
1. **命名规范**
   - 实体类：Task, TaskTag, TaskComment
   - 服务接口：TaskService, TaskTagService
   - 服务实现：TaskServiceImpl, TaskTagServiceImpl
   - 控制器：TaskController, TaskTagController, CaseTaskController, ClientTaskController
   - 数据访问：TaskMapper, TaskTagMapper

2. **代码规范**
   - 遵循阿里巴巴Java开发手册
   - 使用Lombok简化代码
   - 统一使用MyBatis-Plus进行数据库操作
   - 统一异常处理和返回结果

3. **接口规范**
   - RESTful API设计
   - 统一使用JSON格式
   - 接口版本控制
   - 接口文档使用Swagger

## 部署说明
1. **环境要求**
   - JDK 1.8+
   - MySQL 5.7+
   - Redis 5.0+
   - Maven 3.6+

2. **部署步骤**
   ```bash
   # 1. 克隆代码
   git clone [repository-url]
   
   # 2. 编译打包
   mvn clean package
   
   # 3. 运行服务
   java -jar law-firm-task.jar
   ```

3. **配置说明**
   - 数据库配置：application.yml
   - Redis配置：application.yml
   - 任务配置：application-task.yml

## 维护说明
1. **日常维护**
   - 定期检查任务状态
   - 清理过期任务
   - 备份任务数据

2. **问题处理**
   - 任务状态异常处理
   - 任务时间冲突处理
   - 任务提醒失败处理
   - 模块关联异常处理

3. **性能优化**
   - 任务查询优化
   - 任务统计优化
   - 任务提醒优化
   - 跨模块查询优化
