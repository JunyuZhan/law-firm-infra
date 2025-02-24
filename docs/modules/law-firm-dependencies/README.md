# law-firm-dependencies 依赖管理模块

## 模块说明
本模块作为项目的依赖管理中心，统一管理所有模块的依赖版本，确保项目依赖的一致性和可维护性。

## 目录结构
```
law-firm-dependencies/
├── pom.xml                // 依赖管理配置文件
└── docs/                  // 文档目录
    ├── dependencies.md    // 依赖版本说明
    ├── plugins.md        // 插件配置说明
    └── build.md          // 构建配置说明
```

## 主要功能

### 1. 依赖版本管理
- Spring Boot 及相关依赖版本管理
- 数据库相关依赖版本管理
- 工具类库依赖版本管理
- 第三方组件依赖版本管理

### 2. 插件管理
- Maven 插件版本管理
- 构建插件配置管理
- 代码质量插件管理
- 部署插件管理

### 3. 构建配置
- 项目构建规则配置
- 资源过滤规则配置
- 编译规则配置
- 打包规则配置

## 核心依赖版本

### 1. 基础框架
- Spring Boot: 2.7.x
- Spring Cloud: 2021.x
- Spring Cloud Alibaba: 2021.x

### 2. 数据存储
- MySQL Connector: 8.0.x
- MyBatis Plus: 3.5.x
- Redis: 6.x

### 3. 工具组件
- Hutool: 5.8.x
- Apache Commons
- Jackson
- Swagger/Knife4j

## 使用指南

### 1. 依赖引入
```xml
<parent>
    <groupId>com.lawfirm</groupId>
    <artifactId>law-firm-dependencies</artifactId>
    <version>${revision}</version>
</parent>
```

### 2. 版本管理
- [依赖版本说明](dependencies.md)
- [版本升级指南](upgrade-guide.md)
- [版本兼容性说明](compatibility.md)

### 3. 构建配置
- [构建配置说明](build.md)
- [环境配置说明](environment.md)
- [部署配置说明](deployment.md)

## 规范要求

### 1. 版本规范
- 统一使用项目定义的版本号
- 禁止在子模块中单独定义版本号
- 版本变更需要经过完整的测试验证
- 遵循语义化版本规范

### 2. 依赖规范
- 禁止引入未经验证的依赖
- 避免依赖冲突
- 定期检查依赖更新
- 及时处理安全漏洞

### 3. 构建规范
- 统一使用项目定义的构建配置
- 遵循多环境构建规范
- 确保构建过程可重现
- 保持构建脚本的简洁性

## 常见问题

1. Q: 如何升级依赖版本？
   A: 参考[版本升级指南](upgrade-guide.md)，按步骤进行升级和测试。

2. Q: 如何解决依赖冲突？
   A: 使用 Maven 的依赖排除机制，或在父 POM 中统一声明版本。

3. Q: 如何添加新的依赖？
   A: 在父 POM 的 dependencyManagement 中声明，并在相关模块中引用。

## 注意事项

1. 版本变更需要充分测试
2. 定期检查依赖更新
3. 关注安全漏洞公告
4. 保持依赖的最小化
5. 做好版本更新记录

## 相关文档

- [项目构建指南](../../development/build-guide.md)
- [版本管理规范](../../development/version-spec.md)
- [依赖管理规范](../../development/dependency-spec.md) 