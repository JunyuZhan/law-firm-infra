# 律师事务所管理系统 - Docker部署文档

本目录包含律师事务所管理系统的Docker相关配置文件和部署脚本。

## 目录结构

```
docker/
├── Dockerfile                # 应用容器构建文件
├── docker-compose.yml        # 容器编排配置文件
├── deploy.sh                 # Linux/MacOS部署脚本
├── deploy.bat                # Windows部署脚本
├── .dockerignore             # Docker构建忽略文件
├── scripts/                  # 初始化脚本目录
│   ├── mysql/                # MySQL初始化脚本
│   │   └── init.sql          # 数据库初始化SQL (创建测试用户)
│   └── minio/                # MinIO初始化脚本
│       ├── init.sh           # MinIO初始化脚本 (创建存储桶)
│       └── setup.sh          # MinIO高级设置脚本 (可选)
└── README.md                 # 本文档
```

## 环境要求

- Docker 20.10+
- Docker Compose V1/V2
- 至少4GB内存
- 至少10GB可用磁盘空间

## 初始化脚本说明

### MySQL初始化脚本

`scripts/mysql/init.sql` 在MySQL容器首次启动时自动执行，用于：

- 创建必要的系统表（如果不使用Flyway）
- 添加初始管理员账户 (用户名: admin, 密码: admin123)
- 添加测试用户账户

> 注意：生产环境部署前请修改默认密码

### MinIO初始化脚本

`scripts/minio/init.sh` 用于初始化MinIO存储系统：

- 创建law-firm存储桶
- 设置适当的访问权限

可以手动执行此脚本，或将其集成到容器启动过程中：

```bash
docker exec -it law-minio /scripts/minio/init.sh
```

## 快速部署

### Windows环境

1. 确保已安装Docker Desktop并启动
2. 双击执行 `deploy.bat`
3. 等待部署完成

### Linux/MacOS环境

1. 确保已安装Docker和Docker Compose
2. 添加执行权限：`chmod +x deploy.sh`
3. 执行部署脚本：`./deploy.sh`
4. 等待部署完成

## 服务组件

部署后将启动以下服务：

- **API服务**：律师事务所管理系统主应用
  - 端口：8080
  - 访问地址：http://localhost:8080/api
  
- **MySQL数据库**：数据存储服务
  - 端口：13306（映射到内部3306）
  - 默认用户：root
  - 默认密码：lawfirm_default
  - 默认数据库：law_firm

- **Redis**：缓存服务
  - 端口：6379
  - 无密码保护（仅开发环境）

- **MinIO**：对象存储服务
  - API端口：9000
  - 控制台端口：9001
  - 控制台访问地址：http://localhost:9001
  - 默认用户：minioadmin
  - 默认密码：minioadmin

## 常用操作

### 查看容器状态

```bash
cd docker
docker-compose ps
```

### 查看应用日志

```bash
cd docker
docker-compose logs -f api
```

### 停止所有服务

```bash
cd docker
docker-compose down
```

### 重新构建并启动

```bash
cd docker
docker-compose down
docker-compose build
docker-compose up -d
```

### 单独重启某个服务

```bash
cd docker
docker-compose restart api   # 重启API服务
docker-compose restart mysql # 重启MySQL服务
```

## 数据持久化

所有服务数据通过Docker卷进行持久化：

- `mysql_data`：MySQL数据
- `redis_data`：Redis数据
- `minio_data`：MinIO对象存储数据
- `app_logs`：应用日志
- `app_storage`：文件存储
- `app_lucene`：全文检索索引

## 自定义配置

### 修改环境变量

可以在项目根目录创建`.env`文件设置环境变量：

```
# 数据库配置
SPRING_DATASOURCE_PASSWORD=自定义密码

# MinIO配置
MINIO_ACCESS_KEY=自定义用户名
MINIO_SECRET_KEY=自定义密码
```

### 调整资源限制

如需调整容器资源限制，编辑`docker-compose.yml`文件中的`deploy.resources`部分。

## 故障排除

1. **容器启动失败**：
   - 检查端口是否被占用：`netstat -an | grep 8080`
   - 检查日志：`docker-compose logs api`

2. **数据库连接失败**：
   - 检查数据库健康状态：`docker-compose ps mysql`
   - 查看数据库日志：`docker-compose logs mysql`

3. **内存不足错误**：
   - 增加Docker可用内存
   - 调整JVM参数（编辑Dockerfile中的JAVA_OPTS） 