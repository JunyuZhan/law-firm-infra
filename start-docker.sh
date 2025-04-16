#!/bin/bash

# 创建必要的目录
mkdir -p ./data/mysql
mkdir -p ./data/redis
mkdir -p ./data/minio

# 设置权限
chmod -R 777 ./data

# 启动服务
docker-compose up -d

# 等待MySQL启动
echo "等待MySQL启动..."
sleep 30

# 初始化MinIO
echo "初始化MinIO..."
docker-compose exec minio mc alias set myminio http://minio:9000 minioadmin minioadmin
docker-compose exec minio mc mb myminio/law-firm
docker-compose exec minio mc policy set public myminio/law-firm

# 显示服务访问地址
echo "服务启动完成！"
echo "API服务：http://localhost:8080"
echo "MinIO控制台：http://localhost:9000"
echo "Redis：localhost:6379"
echo "MySQL：localhost:3306" 