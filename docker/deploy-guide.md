# 律师事务所管理系统 Docker 部署指南

本文档介绍如何在 Ubuntu 或其他 Linux 环境中使用 Docker 部署律师事务所管理系统。

## 前提条件

- 安装 Docker（20.10.0+）和 Docker Compose（2.0.0+）
- 至少 4GB 可用内存
- 至少 20GB 可用磁盘空间

### Ubuntu 安装 Docker

```bash
# 安装依赖
sudo apt-get update
sudo apt-get install apt-transport-https ca-certificates curl software-properties-common

# 添加 Docker 官方 GPG 密钥
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

# 添加 Docker 仓库
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

# 安装 Docker
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io

# 安装 Docker Compose
sudo curl -L "https://github.com/docker/compose/releases/download/v2.23.3/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# 将当前用户添加到 docker 组（可选，避免每次都用 sudo）
sudo usermod -aG docker $USER
# 需要注销并重新登录使配置生效
```

## 部署方式

### 方式一：一键部署

使用提供的部署脚本一键部署系统：

```bash
# 确保脚本有执行权限
chmod +x docker/deploy.sh

# 执行部署脚本
./docker/deploy.sh
```

### 方式二：手动部署

1. 创建环境变量文件

```bash
# 在项目根目录创建 .env 文件
cat > .env << EOF
MYSQL_ROOT_PASSWORD=lawfirm_default
MYSQL_DATABASE=law_firm
MINIO_ACCESS_KEY=minioadmin
MINIO_SECRET_KEY=minioadmin
EOF
```

2. 构建并启动服务

```bash
# 切换到 docker 目录
cd docker

# 构建并启动所有服务（后台运行）
docker-compose up -d

# 查看服务状态
docker-compose ps

# 查看日志
docker-compose logs -f api
```

## 系统组件

系统由以下Docker容器组成：

1. **api** - 应用服务器 (Spring Boot)
2. **mysql** - 数据库服务器 (MySQL 8.0)
3. **redis** - 缓存服务器 (Redis 7.0)
4. **minio** - 对象存储服务器 (MinIO)

## 端口配置

默认端口映射：

- **8080** - 应用服务 HTTP 端口
- **3306** - MySQL 数据库端口
- **6379** - Redis 端口
- **9000** - MinIO API 端口
- **9001** - MinIO 控制台端口

> 注意：如果服务器上已有程序占用这些端口，请修改 docker-compose.yml 文件中的端口映射。

## 数据持久化

所有数据通过 Docker 命名卷进行持久化：

- **mysql_data** - MySQL 数据库文件
- **redis_data** - Redis 数据库文件
- **minio_data** - MinIO 存储的对象文件
- **app_logs** - 应用日志文件
- **app_storage** - 应用上传的文件
- **app_lucene** - 搜索引擎索引文件

## 常用操作命令

```bash
# 启动所有服务
docker-compose up -d

# 停止所有服务
docker-compose down

# 重启特定服务
docker-compose restart api

# 查看服务日志
docker-compose logs -f api

# 进入容器内部
docker exec -it law-firm-app bash

# 备份数据库
docker exec law-mysql mysqldump -u root -plawfirm_default law_firm > backup.sql

# 恢复数据库
cat backup.sql | docker exec -i law-mysql mysql -u root -plawfirm_default law_firm
```

## 环境配置

系统支持不同环境配置，默认使用 docker 环境。主要配置项可在 docker-compose.yml 文件的 environment 部分找到：

- 数据库连接信息
- Redis 配置
- MinIO 配置
- 功能模块开关
- 系统性能参数

## 故障排除

1. **应用无法连接到数据库**
   - 检查 MySQL 容器是否正常运行：`docker ps | grep mysql`
   - 检查数据库密码是否正确：确认 .env 文件和 docker-compose.yml 中的密码一致

2. **应用启动失败**
   - 检查日志：`docker-compose logs api`
   - 确认所有依赖服务都已启动：`docker-compose ps`

3. **访问MinIO出错**
   - 检查MinIO容器是否正常运行：`docker ps | grep minio`
   - 验证访问密钥：检查 .env 文件中的 MINIO_ACCESS_KEY 和 MINIO_SECRET_KEY

## 性能优化

您可以根据服务器性能调整 docker-compose.yml 中的资源限制：

```yaml
deploy:
  resources:
    limits:
      cpus: '2'   # 调整CPU限制
      memory: 4G  # 调整内存限制
    reservations:
      cpus: '1'   # 调整CPU预留
      memory: 2G  # 调整内存预留
```

## 安全注意事项

- 生产环境部署前，请修改所有默认密码
- 考虑使用外部数据库服务而非容器化数据库用于生产环境
- 配置防火墙仅开放必要端口
- 定期备份数据 