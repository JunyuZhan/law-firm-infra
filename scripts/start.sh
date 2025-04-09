#!/bin/bash

# 默认环境为开发环境
ENV="dev"

# 解析命令行参数
if [ "$1" != "" ]; then
    ENV="$1"
fi

# 设置通用变量
APP_NAME="律师事务所管理系统"
JAR_PATH="law-firm-api/target/law-firm-api-1.0.0.jar"

# 根据环境设置特定配置
case "$ENV" in
    "dev")
        ENV_NAME="开发环境"
        SPRING_PROFILES_ACTIVE="dev-mysql"
        JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC"
        ;;
    "dev-noredis")
        ENV_NAME="开发环境(无Redis)"
        SPRING_PROFILES_ACTIVE="dev-noredis"
        JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC"
        ;;
    "test")
        ENV_NAME="测试环境"
        SPRING_PROFILES_ACTIVE="test"
        JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC"
        ;;
    "prod")
        ENV_NAME="生产环境"
        SPRING_PROFILES_ACTIVE="prod"
        JAVA_OPTS="-Xms2048m -Xmx4096m -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError"
        ;;
    "docker")
        ENV_NAME="Docker环境"
        SPRING_PROFILES_ACTIVE="docker"
        JAVA_OPTS="-Xms1024m -Xmx2048m -XX:+UseG1GC"
        DOCKER_ENABLED="true"
        ;;
    "local")
        ENV_NAME="本地环境"
        SPRING_PROFILES_ACTIVE="local"
        JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC"
        
        # 检查MySQL和Redis服务
        echo "检查MySQL服务..."
        if ! command -v mysql &> /dev/null || ! pgrep -x "mysqld" > /dev/null; then
            echo "警告: MySQL服务未检测到或未运行，可能影响应用正常启动。"
            read -p "是否继续? [y/N]: " -n 1 -r
            echo
            if [[ ! $REPLY =~ ^[Yy]$ ]]; then
                exit 1
            fi
        fi
        
        echo "检查Redis服务..."
        if ! command -v redis-cli &> /dev/null || ! pgrep -x "redis-server" > /dev/null; then
            echo "警告: Redis服务未检测到或未运行，可能影响应用缓存功能。"
            read -p "是否继续? [y/N]: " -n 1 -r
            echo
            if [[ ! $REPLY =~ ^[Yy]$ ]]; then
                exit 1
            fi
        fi
        ;;
    *)
        echo "错误: 不支持的环境类型 \"$ENV\""
        echo "支持的环境: dev | dev-noredis | test | prod | docker | local"
        exit 1
        ;;
esac

# 切换到项目根目录
cd "$(dirname "$0")/.."

echo "============================================="
echo "正在启动${APP_NAME} - ${ENV_NAME}"
echo "============================================="
echo "环境配置: ${SPRING_PROFILES_ACTIVE}"
echo "JVM参数: ${JAVA_OPTS}"
echo ""

# 检查Java是否已安装
if ! command -v java &> /dev/null; then
    echo "错误: 未检测到Java环境，请先安装或配置Java。"
    exit 1
fi

# 显示启动选项
echo "请选择启动方式:"
echo "1. 使用Maven启动(开发模式)"
echo "2. 使用Java直接运行Jar包"
if [ "$DOCKER_ENABLED" = "true" ]; then
    echo "3. 使用Docker Compose启动"
    MAX_OPTION=3
else
    MAX_OPTION=2
fi
echo ""

read -p "请选择 [1-${MAX_OPTION}]: " CHOICE

if [ "$CHOICE" = "1" ]; then
    echo "使用Maven启动..."
    ./mvnw spring-boot:run -pl law-firm-api -Dspring-boot.run.profiles=$SPRING_PROFILES_ACTIVE
elif [ "$CHOICE" = "2" ]; then
    echo "使用Java直接运行Jar包..."
    if [ ! -f "$JAR_PATH" ]; then
        echo "JAR包不存在，正在构建项目..."
        ./mvnw clean package -DskipTests -pl law-firm-api
        if [ $? -ne 0 ]; then
            echo "构建失败！"
            exit 1
        fi
    fi
    echo "正在启动应用..."
    java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar $JAR_PATH
elif [ "$CHOICE" = "3" ] && [ "$DOCKER_ENABLED" = "true" ]; then
    echo "使用Docker Compose启动..."
    docker-compose up -d
    if [ $? -ne 0 ]; then
        echo "Docker启动失败！请确认Docker服务是否正常运行。"
        exit 1
    fi
    echo "Docker容器已启动。"
else
    echo "无效的选择！"
    exit 1
fi

echo "应用已退出。" 