# 律师事务所管理系统工具脚本

本目录包含了律师事务所管理系统的启动脚本和各种工具脚本。

## 统一启动脚本

我们提供了统一的启动脚本，可通过参数来指定不同的运行环境：

- `start.bat` / `start.sh` - 统一启动脚本

### 使用方法

```bash
# Windows 环境
scripts\start.bat [环境类型]

# Linux/Mac 环境
./scripts/start.sh [环境类型]
```

### 支持的环境类型

- `dev` - 开发环境(MySQL)，默认环境
- `dev-noredis` - 开发环境(无Redis)
- `test` - 测试环境
- `prod` - 生产环境
- `docker` - Docker环境
- `local` - 本地环境(会检查MySQL和Redis服务)

## 数据库工具

我们提供了数据库工具脚本，用于管理数据库相关操作：

- `db-utils.bat` / `db-utils.sh` - 数据库工具脚本

### 使用方法

```bash
# Windows 环境
scripts\db-utils.bat [操作]

# Linux/Mac 环境
./scripts/db-utils.sh [操作]
```

### 支持的操作

- `debug` - 数据库调试模式
- `fix` - 修复Flyway迁移问题
- `disable` - 禁用Flyway迁移
- `clear` - 清理Flyway历史记录

## Docker工具

我们提供了Docker工具脚本，用于管理Docker容器：

- `docker-utils.bat` / `docker-utils.sh` - Docker工具脚本

### 使用方法

```bash
# Windows 环境
scripts\docker-utils.bat [操作]

# Linux/Mac 环境
./scripts/docker-utils.sh [操作]
```

### 支持的操作

- `build` - 构建Docker镜像
- `start` - 启动Docker容器
- `stop` - 停止Docker容器
- `restart` - 重启Docker容器
- `status` - 查看Docker容器状态

## 数据库迁移工具

我们提供了Python脚本用于管理数据库迁移版本：

- `migration-utils.py` - 数据库迁移工具脚本

### 使用方法

```bash
# 重命名迁移版本
python scripts/migration-utils.py rename <module> <old_prefix> <new_prefix>

# 交换两个模块的迁移版本
python scripts/migration-utils.py swap <module1> <module2>

# 重置所有模块的迁移版本
python scripts/migration-utils.py reset
```

### 参数说明

- `module`: 模块名称 (auth, personnel, system, client)
- `old_prefix`: 原版本前缀 (e.g. V1000)
- `new_prefix`: 新版本前缀 (e.g. V2000)

## 其他文件

- `init-mysql.sql` - MySQL数据库初始化脚本 