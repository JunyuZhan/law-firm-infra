#!/bin/bash

# 律师事务所管理系统部署脚本

echo "===== 律师事务所管理系统部署脚本 ====="

# 确保MySQL初始化目录存在
if [ ! -d "scripts/mysql" ]; then
    echo "创建MySQL初始化脚本目录..."
    mkdir -p scripts/mysql
fi

# 确保初始化SQL文件存在
if [ ! -f "scripts/mysql/init.sql" ]; then
    echo "警告: MySQL初始化脚本不存在，请确保已创建scripts/mysql/init.sql"
    echo "可以复制项目中提供的示例SQL文件"
fi

# 确保卷目录存在
mkdir -p data/mysql
mkdir -p data/redis
mkdir -p logs
mkdir -p storage

# 检查Docker和Docker Compose是否已安装
if ! command -v docker &> /dev/null; then
    echo "错误: Docker未安装，请先安装Docker"
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo "错误: Docker Compose未安装，请先安装Docker Compose"
    exit 1
fi

# 设置变量
APP_NAME="law-firm-api"
IMAGE_NAME="law-firm-infra-law-firm-api"
CONTAINER_NAME="law-firm-infra"

# 停止并删除旧容器
echo "停止并删除旧容器..."
docker-compose down

# 删除旧镜像
echo "删除旧镜像..."
docker rmi $IMAGE_NAME

# 构建新镜像
echo "构建新镜像..."
docker-compose build

# 启动服务
echo "启动服务..."
docker-compose up -d

# 检查容器状态
echo "检查容器状态..."
sleep 5
docker-compose ps

echo "===== 部署完成 ====="
echo "系统访问地址: http://服务器IP:8080/api"
echo "查看日志命令: docker-compose logs -f law-firm-api"
echo "停止系统命令: docker-compose down" 