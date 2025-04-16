#!/bin/bash

# 设置颜色输出
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

# 检查 Docker 是否运行
check_docker() {
    if ! docker info >/dev/null 2>&1; then
        echo -e "${RED}Docker 未运行，请先启动 Docker 服务${NC}"
        exit 1
    fi
}

# 清理旧容器和构建缓存
cleanup() {
    echo "清理旧容器和构建缓存..."
    docker-compose down -v
    docker system prune -f
}

# 启动服务
start() {
    check_docker
    echo "启动服务..."
    docker-compose up -d --build
    echo -e "${GREEN}服务启动完成${NC}"
}

# 停止服务
stop() {
    echo "停止服务..."
    docker-compose down
    echo -e "${GREEN}服务已停止${NC}"
}

# 重启服务
restart() {
    stop
    sleep 5
    start
}

# 查看日志
logs() {
    docker-compose logs -f --tail=100
}

# 检查服务状态
status() {
    docker-compose ps
}

# 显示帮助信息
show_help() {
    echo "使用方法: $0 [命令]"
    echo "命令:"
    echo "  start    - 启动所有服务"
    echo "  stop     - 停止所有服务"
    echo "  restart  - 重启所有服务"
    echo "  cleanup  - 清理旧容器和构建缓存"
    echo "  logs     - 查看服务日志"
    echo "  status   - 查看服务状态"
    echo "  help     - 显示此帮助信息"
}

# 主逻辑
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        restart
        ;;
    cleanup)
        cleanup
        ;;
    logs)
        logs
        ;;
    status)
        status
        ;;
    help|*)
        show_help
        ;;
esac 