#!/bin/bash

# 检查环境变量
if [ -z "$DB_HOST" ] || [ -z "$DB_PORT" ] || [ -z "$DB_NAME" ] || [ -z "$DB_USERNAME" ] || [ -z "$DB_PASSWORD" ]; then
    echo "错误：数据库环境变量未设置"
    exit 1
fi

if [ -z "$REDIS_HOST" ] || [ -z "$REDIS_PORT" ] || [ -z "$REDIS_PASSWORD" ]; then
    echo "错误：Redis环境变量未设置"
    exit 1
fi

# 设置环境变量
export SPRING_DATASOURCE_URL="jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true"
export SPRING_DATASOURCE_USERNAME="${DB_USERNAME}"
export SPRING_DATASOURCE_PASSWORD="${DB_PASSWORD}"
export SPRING_REDIS_HOST="${REDIS_HOST}"
export SPRING_REDIS_PORT="${REDIS_PORT}"
export SPRING_REDIS_PASSWORD="${REDIS_PASSWORD}"

# 检查初始化脚本
if [ ! -f "init.sh" ]; then
    echo "错误：init.sh脚本不存在"
    exit 1
fi

# 添加执行权限
chmod +x init.sh

# 运行初始化检查
if ! ./init.sh; then
    echo "错误：环境初始化失败"
    exit 1
fi

# JVM参数配置
JAVA_OPTS="-server \
  -Xms${JVM_XMS:2g} \
  -Xmx${JVM_XMX:4g} \
  -Xmn${JVM_XMN:1g} \
  -XX:MetaspaceSize=${JVM_METASPACE_SIZE:256m} \
  -XX:MaxMetaspaceSize=${JVM_MAX_METASPACE_SIZE:512m} \
  -XX:+HeapDumpOnOutOfMemoryError \
  -XX:HeapDumpPath=${JVM_HEAP_DUMP_PATH:./logs/heapdump.hprof} \
  -XX:+PrintGCDetails \
  -XX:+PrintGCDateStamps \
  -XX:+PrintHeapAtGC \
  -Xloggc:${JVM_GC_LOG_PATH:./logs/gc.log} \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=${JVM_MAX_GC_PAUSE_MILLIS:200} \
  -XX:InitiatingHeapOccupancyPercent=${JVM_INITIATING_HEAP_OCCUPANCY_PERCENT:45} \
  -XX:+ParallelRefProcEnabled \
  -XX:MaxTenuringThreshold=${JVM_MAX_TENURING_THRESHOLD:15} \
  -XX:G1HeapRegionSize=${JVM_G1_HEAP_REGION_SIZE:8m} \
  -XX:+DisableExplicitGC \
  -XX:+UseCompressedOops \
  -XX:+UseCompressedClassPointers \
  -XX:+AlwaysPreTouch \
  -XX:+UseStringDeduplication \
  -XX:StringDeduplicationAgeThreshold=${JVM_STRING_DEDUPLICATION_AGE_THRESHOLD:3}"

# 启动应用
echo "正在启动应用..."
java $JAVA_OPTS -jar law-firm-api.jar 