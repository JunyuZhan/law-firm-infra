#!/bin/bash

# 律师事务所管理系统部署脚本

echo "===== 律师事务所管理系统部署脚本 ====="

# 当前工作目录
CURRENT_DIR=$(pwd)
SCRIPT_DIR=$(dirname "$0")
ROOT_DIR="$SCRIPT_DIR/.."

# 确保MySQL初始化目录存在
if [ ! -d "$SCRIPT_DIR/scripts/mysql" ]; then
    echo "创建MySQL初始化脚本目录..."
    mkdir -p "$SCRIPT_DIR/scripts/mysql"
fi

# 确保MinIO初始化目录存在
if [ ! -d "$SCRIPT_DIR/scripts/minio" ]; then
    echo "创建MinIO初始化脚本目录..."
    mkdir -p "$SCRIPT_DIR/scripts/minio"
fi

# 确保初始化SQL文件存在
if [ ! -f "$SCRIPT_DIR/scripts/mysql/init.sql" ]; then
    echo "警告: MySQL初始化脚本不存在，请确保已创建docker/scripts/mysql/init.sql"
    echo "可以复制项目中提供的示例SQL文件"
fi

# 确保卷目录存在
mkdir -p "$ROOT_DIR/data/mysql"
mkdir -p "$ROOT_DIR/data/redis"
mkdir -p "$ROOT_DIR/logs"
mkdir -p "$ROOT_DIR/storage"

# 检查Docker和Docker Compose是否已安装
if ! command -v docker &> /dev/null; then
    echo "错误: Docker未安装，请先安装Docker"
    exit 1
fi

# 检查docker-compose命令，支持Docker Compose V1和V2
if command -v docker-compose &> /dev/null; then
    DOCKER_COMPOSE="docker-compose"
elif docker compose version &> /dev/null; then
    DOCKER_COMPOSE="docker compose"
else
    echo "错误: Docker Compose未安装，请先安装Docker Compose"
    exit 1
fi

# 设置变量
APP_NAME="law-firm-api"
IMAGE_NAME="law-firm-infra-api"
CONTAINER_NAME="law-firm-app"

# 切换到docker目录
cd "$SCRIPT_DIR"

# 停止并删除旧容器
echo "停止并删除旧容器..."
$DOCKER_COMPOSE down

# 删除旧镜像
echo "删除旧镜像..."
docker rmi $IMAGE_NAME 2>/dev/null || true

# 构建新镜像
echo "构建新镜像..."
$DOCKER_COMPOSE build

# 启动服务
echo "启动服务..."
$DOCKER_COMPOSE up -d

# 检查容器状态
echo "检查容器状态..."
sleep 5
$DOCKER_COMPOSE ps

# 返回原始目录
cd "$CURRENT_DIR"

echo "===== 部署完成 ====="
echo "系统访问地址: http://服务器IP:8080/api"
echo "查看日志命令: cd docker && $DOCKER_COMPOSE logs -f api"
echo "停止系统命令: cd docker && $DOCKER_COMPOSE down" 