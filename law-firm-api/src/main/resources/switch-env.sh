#!/bin/bash

# 环境切换脚本
# 用法: ./switch-env.sh [local|docker|test|prod]

ENV=$1

if [ -z "$ENV" ]; then
    echo "请指定环境: local, docker, test 或 prod"
    exit 1
fi

case $ENV in
    "local")
        echo "切换到本地开发环境..."
        export SPRING_PROFILES_ACTIVE=local
        ;;
    "docker")
        echo "切换到Docker环境..."
        export SPRING_PROFILES_ACTIVE=docker
        ;;
    "test")
        echo "切换到测试环境..."
        export SPRING_PROFILES_ACTIVE=test
        ;;
    "prod")
        echo "切换到生产环境..."
        export SPRING_PROFILES_ACTIVE=prod
        ;;
    *)
        echo "无效的环境: $ENV"
        echo "可用环境: local, docker, test, prod"
        exit 1
        ;;
esac

echo "环境已切换到: $ENV"
echo "当前激活的配置文件: application-$ENV.yml" 