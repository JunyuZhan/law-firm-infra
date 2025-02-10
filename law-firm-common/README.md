# 公共模块 (Common)

## 模块说明
公共模块是律师事务所管理系统的基础支撑模块，提供了各个业务模块共用的工具类、通用功能和基础设施代码。该模块被所有其他模块依赖，是整个系统的基石。

## 项目结构
```
law-firm-common
├── common-core                # 核心功能模块
│   ├── src/main/java
│   │   ├── api              # 通用API接口
│   │   ├── base            # 基础类
│   │   ├── config          # 配置类
│   │   ├── constant        # 常量定义
│   │   ├── context         # 上下文
│   │   ├── domain          # 领域模型
│   │   ├── entity          # 实体类
│   │   ├── enums           # 枚举类
│   │   ├── exception       # 异常类
│   │   ├── model           # 数据模型
│   │   ├── result          # 响应结果
│   │   ├── status          # 状态定义
│   │   ├── tenant          # 多租户
│   │   └── utils           # 工具类
├── common-message           # 消息服务模块
├── common-util             # 通用工具模块
├── common-data             # 数据访问模块
├── common-cache            # 缓存处理模块
├── common-log              # 日志处理模块
├── common-security         # 安全框架模块
├── common-web              # Web功能模块
└── common-test             # 测试支持模块
```

## 子模块说明

### common-core
核心功能模块，提供最基础的工具和通用功能：
- 基础实体与领域模型
- 通用枚举和常量
- 异常处理机制
- 多租户支持
- 统一响应结果
- 工具类库

### common-message
消息服务模块，提供统一的消息处理能力：
- 短信服务
- 邮件服务
- 微信消息
- WebSocket 支持
- 消息模板管理
- 消息发送监控

### common-util
通用工具模块，提供独立的工具类支持：
- 字符串处理
- 日期时间
- 加密解密
- 文件操作
- 验证工具

### common-data
数据访问模块，提供统一的数据访问支持：
- 数据源配置
- MyBatis增强
- 分页支持
- 数据审计
- 多租户支持

### common-cache
缓存处理模块，提供统一的缓存操作接口：
- Redis配置
- 缓存操作封装
- 分布式锁
- 缓存注解
- 会话管理

### common-log
日志处理模块，提供统一的日志记录功能：
- 操作日志
- 审计日志
- 异常日志
- 性能日志
- 日志查询

### common-security
安全框架模块，提供统一的安全管理功能：
- 认证授权
- 权限控制
- 密码管理
- 安全过滤
- 防护配置

### common-web
Web功能模块，提供统一的Web层支持：
- 统一响应
- 统一异常处理
- 参数校验
- 跨域配置
- 文件处理

### common-test
测试支持模块，提供测试相关的通用功能：
- 测试基类
- 测试工具
- Mock支持
- 测试数据构建
- 断言增强

## 技术架构

### 核心依赖
- Spring Boot 2.7.x
- Spring Cloud 2021.0.x
- MyBatis Plus 3.5.x
- Redis 6.x
- Spring Security 5.7.x
- Swagger 3.0.0
- Hutool 5.8.x
- Lombok 1.18.x

### 设计理念
1. 高内聚，低耦合
2. 可扩展性优先
3. 约定优于配置
4. DRY原则
5. SOLID原则

## 使用说明

### 引入依赖
```xml
<!-- 核心功能 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-core</artifactId>
    <version>${project.version}</version>
</dependency>

<!-- 数据访问 -->
<dependency>
    <groupId>com.lawfirm</groupId>
    <artifactId>common-data</artifactId>
    <version>${project.version}</version>
</dependency>

<!-- 根据需要引入其他模块 -->
```

### 配置示例
```yaml
lawfirm:
  common:
    # 数据源配置
    datasource:
      enabled: true
      url: jdbc:mysql://localhost:3306/lawfirm
      username: root
      password: root
    
    # Redis配置
    redis:
      enabled: true
      host: localhost
      port: 6379
    
    # 安全配置
    security:
      enabled: true
      ignore-urls:
        - /api/auth/**
        - /api/public/**
```

### 使用示例

#### 1. 统一响应处理
```java
@RestController
@RequestMapping("/api/demo")
public class DemoController {
    
    @GetMapping("/test")
    public R<String> test() {
        return R.ok("测试成功");
    }
    
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody DemoDTO dto) {
        // 业务处理
        return R.ok();
    }
}
```

#### 2. Redis缓存使用
```java
@Service
public class UserService {
    
    @Autowired
    private RedisHelper redisHelper;
    
    @CacheAble(key = "user:#{#id}")
    public UserVO getUser(Long id) {
        // 业务处理
        return userVO;
    }
}
```

#### 3. 分页查询
```java
@Service
public class DocumentService {
    
    public IPage<DocumentVO> page(DocumentQuery query) {
        return baseMapper.selectPage(new Page<>(query.getPage(), query.getSize()),
            new LambdaQueryWrapper<Document>()
                .eq(Document::getStatus, CommonStatus.VALID));
    }
}
```

## 开发规范

### 命名规范
1. 包名：com.lawfirm.common.{module}
2. 类名：驼峰命名，见名知意
3. 方法名：动词开头，驼峰命名
4. 常量：全大写，下划线分隔

### 代码规范
1. 遵循阿里巴巴Java开发手册
2. 必须添加完整注释
3. 统一异常处理
4. 统一日志记录
5. 统一返回格式

## 扩展开发

### 新增工具类
1. 在appropriate模块下创建工具类
2. 添加完整注释和单元测试
3. 在README中更新文档
4. 提交代码评审

### 新增功能模块
1. 创建新的Maven模块
2. 配置正确的依赖关系
3. 实现基础功能
4. 编写单元测试
5. 更新文档

## 版本说明

### 当前版本
- 版本号：1.0.0
- 发布日期：2024-02-09
- 主要特性：
  - 基础功能完善
  - 性能优化
  - 安全加固

### 版本规划
- 1.1.0：性能优化
- 1.2.0：新增特性
- 2.0.0：架构升级

## 常见问题

### 问题排查
1. 依赖冲突
2. 配置无效
3. 性能问题
4. 安全漏洞
5. 兼容性问题

### 解决方案
1. 检查依赖版本
2. 查看启动日志
3. 使用监控工具
4. 升级补丁版本
5. 参考文档说明

## 注意事项
1. 保持向后兼容
2. 避免重复造轮子
3. 关注性能影响
4. 注意代码质量
5. 及时更新文档

## 贡献指南

### 开发流程
1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/xxx`)
3. 提交代码 (`git commit -am 'Add some feature'`)
4. 推送到分支 (`git push origin feature/xxx`)
5. 创建 Pull Request

### 提交规范
- feat: 新功能
- fix: 修复问题
- docs: 文档修改
- style: 代码格式修改
- refactor: 代码重构
- test: 测试用例修改
- chore: 其他修改

### 代码审查
1. 代码风格符合项目规范
2. 必须包含单元测试
3. 所有测试用例必须通过
4. 文档及时更新 