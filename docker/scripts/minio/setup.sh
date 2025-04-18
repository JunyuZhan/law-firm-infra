#!/bin/bash

# MinIO初始化脚本
# 在MinIO容器启动后运行此脚本以创建必要的存储桶和用户权限

# 设置MinIO客户端
mc alias set myminio http://localhost:9000 ${MINIO_ROOT_USER:-minioadmin} ${MINIO_ROOT_PASSWORD:-minioadmin}

# 等待MinIO服务可用
echo "等待MinIO服务启动..."
until mc admin info myminio > /dev/null 2>&1; do
  echo "MinIO服务未就绪，等待..."
  sleep 1
done

echo "MinIO服务已就绪，开始配置..."

# 创建所需存储桶
echo "创建存储桶..."

# 创建主存储桶
mc mb --ignore-existing myminio/law-firm

# 设置桶策略为下载权限（允许匿名下载但不能上传）
mc policy set download myminio/law-firm/public

# 创建应用使用的子文件夹
echo "创建文件夹结构..."
mc mb --ignore-existing myminio/law-firm/documents
mc mb --ignore-existing myminio/law-firm/contracts
mc mb --ignore-existing myminio/law-firm/cases
mc mb --ignore-existing myminio/law-firm/avatars
mc mb --ignore-existing myminio/law-firm/temp

# 设置特定权限
echo "设置权限..."
mc policy set download myminio/law-firm/avatars

# 创建系统使用的访问密钥（如果使用环境变量配置则可以跳过）
# mc admin user add myminio lawfirm-app lawfirm-app-secret

echo "MinIO配置完成！" 