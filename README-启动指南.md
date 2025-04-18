# 律师事务所管理系统启动指南

## 一、环境要求

### 基础环境

1. **JDK要求**
   - JDK 21 或更高版本
   - 推荐使用 Oracle JDK 或 OpenJDK

2. **数据库要求**
   - MySQL 8.0 或更高版本
   - 默认数据库名: `law_firm`
   - 默认用户名: `root`

3. **其他依赖**
   - Redis（如需使用缓存功能）
   - RocketMQ（如需使用消息队列功能）

### 硬件推荐配置

- **开发环境**：4核8G内存
- **测试环境**：8核16G内存
- **生产环境**：16核32G内存以上

## 二、快速启动（小白用户）

### Windows系统

1. 双击项目根目录中的 `start-law-firm.bat` 文件
2. 根据菜单提示选择要启动的环境（推荐选择开发环境）
3. 根据需要配置数据库连接参数
4. 等待系统启动完成

### Linux/Mac系统

1. 打开终端，进入项目根目录
2. 执行以下命令赋予启动脚本执行权限：
   ```bash
   chmod +x start-law-firm.sh
   ```
3. 执行启动脚本：
   ```bash
   ./start-law-firm.sh
   ```
4. 按照菜单提示操作

## 三、数据库初始化

首次启动系统前，需要初始化数据库。

### Windows系统

1. 双击项目根目录中的 `reset-dev-db.bat` 文件
2. 输入数据库密码
3. 确认是否要重置数据库（此操作会删除并重建数据库）
4. 等待数据库初始化完成

### Linux/Mac系统

1. 打开终端，进入项目根目录
2. 执行以下命令：
   ```bash
   chmod +x reset-dev-db.sh && ./reset-dev-db.sh
   ```
3. 按照提示操作

## 四、高级启动选项

### 命令行直接启动

如果您熟悉命令行操作，可以直接使用以下命令启动系统：

```bash
java -jar law-firm-api/target/law-firm-api-1.0.0.jar --spring.profiles.active=develop
```

### 常用启动参数

以下是一些常用的启动参数：

1. **指定环境**
   ```
   --spring.profiles.active=环境名称
   ```
   支持的环境：develop（开发）、test（测试）、prod（生产）

2. **指定端口**
   ```
   --server.port=端口号
   ```
   默认端口：8080

3. **指定数据库连接**
   ```
   --spring.datasource.url=jdbc:mysql://主机:端口/数据库名?参数
   --spring.datasource.username=用户名
   --spring.datasource.password=密码
   ```

4. **JVM参数**
   ```
   -Xms512m -Xmx1g
   ```
   建议根据实际内存情况调整

### 使用环境变量启动

您也可以使用环境变量来配置系统：

**Windows PowerShell**:
```powershell
$env:SPRING_PROFILES_ACTIVE="develop"
$env:SPRING_DATASOURCE_USERNAME="root"
$env:SPRING_DATASOURCE_PASSWORD="your_password"
java -jar law-firm-api/target/law-firm-api-1.0.0.jar
```

**Linux/Mac**:
```bash
export SPRING_PROFILES_ACTIVE=develop
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=your_password
java -jar law-firm-api/target/law-firm-api-1.0.0.jar
```

## 五、问题排查

### 常见问题及解决方案

1. **数据库连接失败**
   - 确认MySQL服务已启动
   - 验证用户名和密码是否正确
   - 检查数据库名称是否存在
   - 检查MySQL端口是否被占用或被防火墙阻止

2. **端口占用**
   - 默认端口8080被占用时，可以使用 `--server.port=新端口号` 指定新端口
   - 或者找到占用端口的进程并关闭

3. **内存不足**
   - 调整JVM参数，减小内存分配
   - 关闭不必要的应用程序释放内存

4. **无法访问系统**
   - 确认系统已成功启动，查看控制台输出
   - 检查访问地址是否正确，默认为 `http://localhost:8080/api`
   - 检查防火墙设置

### 查看日志

系统日志默认保存在以下位置：

- **Windows**: `%TEMP%/logs/law-firm/`
- **Linux/Mac**: `/tmp/logs/law-firm/`

### 获取帮助

如遇到无法解决的问题，请联系系统管理员或开发团队。

## 六、多环境配置说明

系统支持多环境配置，主要通过不同的配置文件区分：

### 开发环境 (develop)

- 配置文件：`application-develop.yml`
- 特点：开启详细日志、自动创建数据库、禁用安全特性

### 测试环境 (test)

- 配置文件：`application-test.yml`（需自行创建）
- 建议配置：使用测试数据库、模拟第三方服务

### 生产环境 (prod)

- 配置文件：`application-prod.yml`（需自行创建）
- 建议配置：关闭详细日志、优化性能参数、启用安全特性

### 自定义新环境

1. 在 `law-firm-api/src/main/resources` 目录下创建新的配置文件，命名为 `application-环境名.yml`
2. 复制 `application-develop.yml` 的内容作为基础
3. 根据需要修改配置
4. 使用 `--spring.profiles.active=环境名` 启动系统 