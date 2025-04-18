#!/bin/sh

# MinIO初始化脚本 - 简化版
# 此脚本在容器内执行，用于初始化MinIO存储桶

echo "开始初始化MinIO..."

# 确保MinIO客户端已安装
if ! command -v mc &> /dev/null; then
  echo "MinIO客户端未安装，正在安装..."
  wget https://dl.min.io/client/mc/release/linux-amd64/mc -O /usr/local/bin/mc
  chmod +x /usr/local/bin/mc
fi

# 等待MinIO服务启动
echo "等待MinIO服务启动..."
sleep 10

# 设置MinIO客户端
mc config host add myminio http://minio:9000 minioadmin minioadmin

# 创建存储桶
echo "创建存储桶..."
mc mb --ignore-existing myminio/law-firm

# 设置访问权限
echo "设置访问权限..."
mc policy set download myminio/law-firm

echo "MinIO初始化完成！"

# 退出
exit 0 