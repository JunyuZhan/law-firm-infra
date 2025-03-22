#!/bin/bash

# 律所管理系统Docker构建脚本
echo "=========================================="
echo "     律所管理系统 Docker 构建脚本"
echo "=========================================="
echo ""

# 确保scripts目录存在
mkdir -p scripts

# 创建目录结构
echo "正在创建必要的目录..."
mkdir -p data/mysql
mkdir -p data/redis
mkdir -p data/elasticsearch
mkdir -p data/minio
mkdir -p data/rocketmq/namesrv/logs
mkdir -p data/rocketmq/broker/logs
mkdir -p data/rocketmq/broker/store
mkdir -p logs

# 检查docker是否已经安装
if ! command -v docker &> /dev/null; then
    echo "错误：Docker未安装，请先安装Docker后再运行此脚本。"
    exit 1
fi

# 检查docker-compose是否已经安装
if ! command -v docker-compose &> /dev/null; then
    echo "错误：Docker Compose未安装，请先安装Docker Compose后再运行此脚本。"
    exit 1
fi

# 构建和启动容器
echo "正在构建Docker镜像..."
docker-compose build

# 启动服务
echo "正在启动容器..."
docker-compose up -d

# 检查服务是否正常启动
echo "正在检查服务状态..."
sleep 10
docker-compose ps

echo ""
echo "=========================================="
echo "     服务启动完成！"
echo "=========================================="
echo "以下是访问地址："
echo "- 律所管理系统: http://localhost:8080"
echo "- SwaggerUI接口文档: http://localhost:8080/swagger-ui.html"
echo "- MinIO控制台: http://localhost:9001"
echo ""
echo "用户名：admin"
echo "密码：admin123"
echo "==========================================" 