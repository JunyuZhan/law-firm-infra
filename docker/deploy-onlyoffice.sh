#!/bin/bash
# OnlyOffice集成部署脚本

echo "================================================"
echo "      法律事务所管理系统 - OnlyOffice部署"
echo "================================================"

# 检查环境参数
ENVIRONMENT=${1:-dev}
ENV_FILE="env-${ENVIRONMENT}.example"

echo "🌍 部署环境: $ENVIRONMENT"

# 检查环境配置文件
if [ ! -f "$ENV_FILE" ]; then
    echo "❌ 环境配置文件 $ENV_FILE 不存在"
    echo "📝 可用环境: dev, prod"
    exit 1
fi

# 检查是否存在实际的.env文件
ACTUAL_ENV_FILE=".env.${ENVIRONMENT}"
if [ ! -f "$ACTUAL_ENV_FILE" ]; then
    echo "⚠️  实际环境配置文件 $ACTUAL_ENV_FILE 不存在"
    echo "📋 正在从示例文件创建: $ENV_FILE -> $ACTUAL_ENV_FILE"
    cp "$ENV_FILE" "$ACTUAL_ENV_FILE"
    echo "✅ 已创建 $ACTUAL_ENV_FILE，请根据实际情况修改配置"
    echo "⚡ 继续使用示例配置进行部署..."
fi

# 设置环境变量来源
export $(cat $ACTUAL_ENV_FILE | grep -v '^#' | xargs)

echo "📋 当前配置:"
echo "  Spring Profile: $SPRING_PROFILES_ACTIVE"
echo "  OnlyOffice启用: $ONLYOFFICE_ENABLED"
echo "  OnlyOffice地址: $ONLYOFFICE_URL"
echo "  数据库: $MYSQL_HOST:$MYSQL_PORT/$MYSQL_DATABASE"
echo "  Redis: $REDIS_HOST:$REDIS_PORT"
echo "  MinIO: $MINIO_ENDPOINT"

# 检查Docker环境
if ! command -v docker &> /dev/null; then
    echo "❌ Docker 未安装，请先安装Docker"
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose 未安装，请先安装Docker Compose"
    exit 1
fi

# 创建必要的目录
echo "📁 创建数据目录..."
mkdir -p ./data/onlyoffice
mkdir -p ./data/onlyoffice-postgres
mkdir -p ./logs/onlyoffice

# 检查端口是否被占用
check_port() {
    local port=$1
    local service=$2
    
    if netstat -tuln | grep -q ":$port "; then
        echo "⚠️  警告: 端口 $port 已被占用 ($service)"
        read -p "是否继续部署？(y/N): " confirm
        if [[ $confirm != [yY] ]]; then
            echo "❌ 部署已取消"
            exit 1
        fi
    fi
}

echo "🔍 检查端口占用情况..."
check_port 8088 "OnlyOffice Document Server"
check_port 8443 "OnlyOffice HTTPS"

# 停止现有容器
echo "🛑 停止现有容器..."
docker-compose -f docker-compose-onlyoffice.yml down

# 拉取最新镜像
echo "📥 拉取Docker镜像..."
docker-compose -f docker-compose-onlyoffice.yml pull

# 启动服务
echo "🚀 启动OnlyOffice集成服务..."
docker-compose --env-file $ACTUAL_ENV_FILE -f docker-compose-onlyoffice.yml up -d

# 等待服务启动
echo "⏳ 等待服务启动..."
sleep 30

# 检查服务状态
echo "🔍 检查服务状态..."
services=(
    "law-firm-app:8080"
    "law-onlyoffice:8088"
    "law-mysql:3306"
    "law-redis:6379"
    "law-minio:9000"
)

all_healthy=true
for service in "${services[@]}"; do
    container_name=$(echo $service | cut -d':' -f1)
    port=$(echo $service | cut -d':' -f2)
    
    if docker ps | grep -q $container_name; then
        echo "✅ $container_name 运行正常"
    else
        echo "❌ $container_name 启动失败"
        all_healthy=false
    fi
done

# 初始化MinIO存储桶
echo "🪣 初始化MinIO存储桶..."
docker exec law-minio sh -c '
    mc alias set myminio http://localhost:9000 minioadmin minioadmin &&
    mc mb myminio/law-firm-docs --ignore-existing &&
    mc policy set public myminio/law-firm-docs
' 2>/dev/null || echo "⚠️ MinIO存储桶配置可能需要手动设置"

# 等待OnlyOffice完全启动
echo "⏳ 等待OnlyOffice Document Server启动完成..."
for i in {1..30}; do
    if curl -s -f http://localhost:8088/healthcheck > /dev/null 2>&1; then
        echo "✅ OnlyOffice Document Server 启动成功"
        break
    fi
    echo "⏳ 等待中... ($i/30)"
    sleep 10
done

# 显示访问信息
if $all_healthy; then
    echo ""
    echo "🎉 OnlyOffice集成部署成功！"
    echo ""
    echo "📋 服务访问信息："
    echo "┌─────────────────────────────────────────────┐"
    echo "│ 法律事务所管理系统                           │"
    echo "│ 地址: http://localhost:8080                 │"
    echo "│ 环境: $ENVIRONMENT                         │"
    echo "│ Profile: $SPRING_PROFILES_ACTIVE           │"
    echo "│                                           │"
    echo "│ OnlyOffice Document Server                 │"
    echo "│ 地址: http://localhost:8088                 │"
    echo "│ 状态: $ONLYOFFICE_ENABLED                  │"
    echo "│                                           │"
    echo "│ MinIO管理控制台                             │"
    echo "│ 地址: http://localhost:9001                 │"
    echo "│ 用户名: $MINIO_ACCESS_KEY                  │"
    echo "│ 密码: [已隐藏]                            │"
    echo "│                                           │"
    echo "│ MySQL数据库                                 │"
    echo "│ 地址: $MYSQL_HOST:$MYSQL_PORT              │"
    echo "│ 数据库: $MYSQL_DATABASE                    │"
    echo "│ 用户名: $MYSQL_USERNAME                    │"
    echo "└─────────────────────────────────────────────┘"
    echo ""
    echo "💡 使用提示："
    echo "• 登录系统后，在文档管理页面可以使用OnlyOffice编辑功能"
    echo "• 支持协同编辑，多人可同时编辑同一文档"
    echo "• 支持Word、Excel、PowerPoint等多种格式"
    echo "• 文档自动保存到MinIO存储中"
    echo ""
    echo "📝 配置信息："
    echo "• 部署环境: $ENVIRONMENT"
    echo "• OnlyOffice已启用: $ONLYOFFICE_ENABLED"
    echo "• 编辑器类型: $DOCUMENT_EDITOR_TYPE"
    echo "• JWT密钥已配置用于安全认证"
    echo ""
    echo "🔧 管理命令："
    echo "• 查看日志: docker-compose --env-file $ACTUAL_ENV_FILE -f docker-compose-onlyoffice.yml logs -f"
    echo "• 停止服务: docker-compose --env-file $ACTUAL_ENV_FILE -f docker-compose-onlyoffice.yml down"
    echo "• 重启服务: docker-compose --env-file $ACTUAL_ENV_FILE -f docker-compose-onlyoffice.yml restart"
    echo "• 重新部署: ./deploy-onlyoffice.sh $ENVIRONMENT"
else
    echo ""
    echo "❌ 部分服务启动失败，请检查日志："
    echo "docker-compose --env-file $ACTUAL_ENV_FILE -f docker-compose-onlyoffice.yml logs"
fi

echo ""
echo "================================================" 