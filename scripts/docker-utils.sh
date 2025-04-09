#!/bin/bash

# 默认选项
OPTION=""

# 解析命令行参数
if [ "$1" != "" ]; then
    OPTION="$1"
fi

# 切换到项目根目录
cd "$(dirname "$0")/.."

echo "============================================="
echo "     律师事务所管理系统 - Docker工具"
echo "============================================="
echo ""

# 定义函数 - 构建Docker镜像
docker_build() {
    echo "律所管理系统Docker构建脚本"
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
        return 1
    fi
    
    # 检查docker-compose是否已经安装
    if ! command -v docker-compose &> /dev/null; then
        echo "错误：Docker Compose未安装，请先安装Docker Compose后再运行此脚本。"
        return 1
    fi
    
    # 构建镜像
    echo "正在构建Docker镜像..."
    docker-compose build
    
    echo ""
    echo "Docker镜像构建完成！"
}

# 定义函数 - 启动Docker容器
docker_start() {
    echo "正在启动Docker容器..."
    
    # 启动服务
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
}

# 定义函数 - 停止Docker容器
docker_stop() {
    echo "正在停止Docker容器..."
    
    # 停止服务
    docker-compose down
    
    echo ""
    echo "所有容器已停止。"
}

# 定义函数 - 重启Docker容器
docker_restart() {
    echo "正在重启Docker容器..."
    
    # 重启服务
    docker-compose restart
    
    # 检查服务是否正常启动
    echo "正在检查服务状态..."
    sleep 10
    docker-compose ps
    
    echo ""
    echo "所有容器已重启。"
}

# 定义函数 - 查看Docker容器状态
docker_status() {
    echo "正在查询Docker容器状态..."
    
    # 查看服务状态
    docker-compose ps
}

# 处理选项
case "$OPTION" in
    "build")
        docker_build
        ;;
    "start")
        docker_start
        ;;
    "stop")
        docker_stop
        ;;
    "restart")
        docker_restart
        ;;
    "status")
        docker_status
        ;;
    *)
        # 显示菜单
        echo "请选择操作:"
        echo "1. 构建镜像"
        echo "2. 启动容器"
        echo "3. 停止容器"
        echo "4. 重启容器"
        echo "5. 查看容器状态"
        echo "6. 退出"
        echo ""
        
        read -p "请选择 [1-6]: " CHOICE
        
        case "$CHOICE" in
            "1")
                docker_build
                ;;
            "2")
                docker_start
                ;;
            "3")
                docker_stop
                ;;
            "4")
                docker_restart
                ;;
            "5")
                docker_status
                ;;
            "6"|*)
                echo "操作已取消"
                ;;
        esac
        ;;
esac

echo "操作完成。" 