#!/bin/bash
echo "正在启动律师事务所管理系统 - 测试环境..."

# 设置环境变量
export SPRING_PROFILES_ACTIVE=test

# 设置Java选项
export JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC"

# 切换到项目根目录
cd "$(dirname "$0")/.."

echo "选择启动方式:"
echo "1. 使用Maven启动(测试模式)"
echo "2. 使用Java直接运行Jar包"
echo ""

read -p "请选择 [1-2]: " choice

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
else
    echo "无效的选择!"
fi 