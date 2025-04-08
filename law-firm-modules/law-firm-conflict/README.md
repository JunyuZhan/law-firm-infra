# 冲突检查模块 (law-firm-conflict)

## 模块概述
冲突检查模块是律所管理系统中的冲突管理子系统，用于检查和记录律所内部的利益冲突。主要处理律师与客户之间的利益冲突、案件之间的冲突等情况，帮助律所规避潜在的法律风险。

## 功能特性
1. **冲突检查**
   - 新客户冲突检查
   - 新案件冲突检查
   - 新律师入职冲突检查
   - 冲突检查报告生成

2. **冲突记录**
   - 冲突信息记录
   - 冲突处理记录
   - 历史冲突查询

3. **冲突处理**
   - 冲突处理流程
   - 处理结果记录
   - 冲突豁免管理

## 技术架构
1. **基础框架**
   - Spring Boot
   - MyBatis-Plus
   - Spring Security
   - Redis

2. **模块依赖**
   - common-core：基础框架
   - law-firm-client：客户模块
   - law-firm-case：案件模块
   - law-firm-personnel：人员模块

3. **数据库设计**
   ```sql
   -- 冲突检查记录表
   CREATE TABLE `conflict_check` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '检查ID',
     `check_type` tinyint NOT NULL COMMENT '检查类型(1:新客户 2:新案件 3:新律师)',
     `target_id` bigint NOT NULL COMMENT '检查目标ID',
     `target_type` tinyint NOT NULL COMMENT '目标类型(1:客户 2:案件 3:律师)',
     `check_result` tinyint NOT NULL COMMENT '检查结果(0:无冲突 1:有冲突)',
     `check_time` datetime NOT NULL COMMENT '检查时间',
     `checker_id` bigint NOT NULL COMMENT '检查人ID',
     `tenant_id` bigint NOT NULL COMMENT '租户ID',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`),
     KEY `idx_target` (`target_id`,`target_type`),
     KEY `idx_checker` (`checker_id`),
     KEY `idx_tenant` (`tenant_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冲突检查记录表';

   -- 冲突记录表
   CREATE TABLE `conflict_record` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '冲突ID',
     `check_id` bigint NOT NULL COMMENT '检查记录ID',
     `conflict_type` tinyint NOT NULL COMMENT '冲突类型(1:客户冲突 2:案件冲突)',
     `source_id` bigint NOT NULL COMMENT '冲突源ID',
     `source_type` tinyint NOT NULL COMMENT '冲突源类型(1:客户 2:案件)',
     `target_id` bigint NOT NULL COMMENT '冲突目标ID',
     `target_type` tinyint NOT NULL COMMENT '冲突目标类型(1:客户 2:案件)',
     `conflict_desc` text COMMENT '冲突描述',
     `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态(0:未处理 1:处理中 2:已解决 3:已豁免)',
     `tenant_id` bigint NOT NULL COMMENT '租户ID',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`),
     KEY `idx_check` (`check_id`),
     KEY `idx_source` (`source_id`,`source_type`),
     KEY `idx_target` (`target_id`,`target_type`),
     KEY `idx_tenant` (`tenant_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冲突记录表';

   -- 冲突处理记录表
   CREATE TABLE `conflict_resolution` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '处理ID',
     `conflict_id` bigint NOT NULL COMMENT '冲突ID',
     `resolution_type` tinyint NOT NULL COMMENT '处理类型(1:终止关系 2:建立防火墙 3:获取豁免)',
     `resolution_desc` text NOT NULL COMMENT '处理说明',
     `handler_id` bigint NOT NULL COMMENT '处理人ID',
     `handle_time` datetime NOT NULL COMMENT '处理时间',
     `tenant_id` bigint NOT NULL COMMENT '租户ID',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`),
     KEY `idx_conflict` (`conflict_id`),
     KEY `idx_handler` (`handler_id`),
     KEY `idx_tenant` (`tenant_id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='冲突处理记录表';
   ```

## 模块集成
1. **与客户模块集成**
   - 客户冲突检查
   - 客户历史记录查询

2. **与案件模块集成**
   - 案件冲突检查
   - 案件历史记录查询

3. **与人员模块集成**
   - 律师冲突检查
   - 律师历史记录查询

## 配置说明
```yaml
# 冲突检查模块配置
conflict:
  # 冲突检查配置
  check:
    # 是否启用实时检查
    enable-realtime: true
    
  # 模块关联配置
  relation:
    # 是否启用客户关联
    enable-client: true
    # 是否启用案件关联
    enable-case: true
    # 是否启用人员关联
    enable-personnel: true
```

## 开发规范
1. **命名规范**
   - 实体类：ConflictCheck, ConflictRecord
   - 服务接口：ConflictCheckService
   - 服务实现：ConflictCheckServiceImpl
   - 控制器：ConflictCheckController
   - 数据访问：ConflictCheckMapper

2. **代码规范**
   - 遵循阿里巴巴Java开发手册
   - 使用Lombok简化代码
   - 统一使用MyBatis-Plus进行数据库操作
   - 统一异常处理和返回结果

3. **接口规范**
   - RESTful API设计
   - 统一使用JSON格式
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
   java -jar law-firm-conflict.jar
   ```

3. **配置说明**
   - 数据库配置：application.yml
   - Redis配置：application.yml
   - 冲突检查配置：application-conflict.yml

## 维护说明
1. **日常维护**
   - 检查冲突检查记录
   - 备份冲突数据

2. **问题处理**
   - 冲突检查异常处理
   - 冲突处理流程异常

3. **性能优化**
   - 冲突检查性能优化
   - 查询性能优化
