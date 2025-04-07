#!/bin/bash
echo "正在启动律师事务所管理系统 - Docker环境..."

# 设置环境变量
export SPRING_PROFILES_ACTIVE=docker

# 设置Java选项 - Docker环境通常需要更多内存
export JAVA_OPTS="-Xms1024m -Xmx2048m -XX:+UseG1GC"

# 切换到项目根目录
cd "$(dirname "$0")/.."

echo "选择启动方式:"
echo "1. 使用Maven启动(Docker模式)"
echo "2. 使用Java直接运行Jar包"
echo "3. 使用Docker Compose启动"
echo ""

read -p "请选择 [1-3]: " choice

if [ "$choice" = "1" ]; then
    echo "使用Maven启动..."
    ./mvnw spring-boot:run -pl law-firm-api
elif [ "$choice" = "2" ]; then
    echo "使用Java运行Jar包..."
    if [ ! -f "law-firm-api/target/law-firm-api-1.0.0.jar" ]; then
        echo "正在构建项目..."
        ./mvnw clean package -DskipTests -pl law-firm-api
    fi
    cd law-firm-api
    java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar target/law-firm-api-1.0.0.jar
elif [ "$choice" = "3" ]; then
    echo "使用Docker Compose启动..."
    docker-compose up -d
else
    echo "无效的选择!"
fi 