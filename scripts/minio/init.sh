#!/bin/bash

# 等待MinIO服务启动
echo "等待MinIO服务启动..."
sleep 10

# 设置MinIO客户端配置
mc alias set local http://localhost:9000 ${MINIO_ACCESS_KEY} ${MINIO_SECRET_KEY}

# 创建存储桶
echo "创建存储桶: ${MINIO_BUCKET_NAME}"
mc mb local/${MINIO_BUCKET_NAME}

# 设置存储桶策略
echo "设置存储桶策略"
mc policy set public local/${MINIO_BUCKET_NAME}

echo "MinIO初始化完成"