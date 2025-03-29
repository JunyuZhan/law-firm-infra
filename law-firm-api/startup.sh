#!/bin/bash

# 设置JVM参数
JAVA_OPTS=${JAVA_OPTS:-"-Xms1024m -Xmx2048m -XX:MaxMetaspaceSize=512m"}
CONFIG_OPTS=${CONFIG_OPTS:-"-Dspring.main.lazy-initialization=true -Dspring.main.allow-bean-definition-overriding=true"}
EXCLUDE_OPTS=${EXCLUDE_OPTS:-"-Dspring.autoconfigure.exclude=org.flowable.spring.boot.FlowableAutoConfiguration,com.lawfirm.core.workflow.config.FlowableConfig,org.flowable.spring.boot.ProcessEngineServicesAutoConfiguration"}
FEATURE_OPTS=${FEATURE_OPTS:-"-Dflowable.enabled=false -Dlawfirm.workflow.enabled=false"}
SCAN_OPTS=${SCAN_OPTS:-"-Dspring.main.sources=com.lawfirm.api,com.lawfirm.common,com.lawfirm.model,com.lawfirm.core.system,com.lawfirm.core.storage"}

# 设置应用环境变量
export SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-prod}
export SERVER_PORT=${SERVER_PORT:-8080}
export SERVER_CONTEXT_PATH=${SERVER_CONTEXT_PATH:-/api}

# 设置数据库连接信息 - 生产环境应该通过环境变量传入
export MYSQL_USERNAME=${MYSQL_USERNAME:-lawfirm}
export MYSQL_PASSWORD=${MYSQL_PASSWORD:-password}

# 日志设置
LOG_OPTS=${LOG_OPTS:-"-Dlogging.file.path=/app/logs -Dlogging.file.name=law-firm-api.log"}

# 判断是否在Docker环境中运行
if [ -f /.dockerenv ]; then
    echo "检测到Docker环境"
    # Docker环境下使用前台运行
    EXEC_MODE="foreground"
else
    echo "检测到普通Linux环境"
    # 普通环境使用后台运行
    EXEC_MODE="background"
fi

echo "正在启动Law Firm API服务..."
echo "环境: $SPRING_PROFILES_ACTIVE"
echo "服务地址: http://localhost:$SERVER_PORT$SERVER_CONTEXT_PATH"

# 根据执行模式选择运行方式
if [ "$EXEC_MODE" = "background" ]; then
    # 后台运行
    nohup java $JAVA_OPTS $CONFIG_OPTS $EXCLUDE_OPTS $FEATURE_OPTS $SCAN_OPTS $LOG_OPTS \
    -Dspring.main.banner-mode=off \
    -jar law-firm-api.jar > /dev/null 2>&1 &
    
    # 输出进程ID
    echo $! > application.pid
    echo "应用已在后台启动，进程ID: $(cat application.pid)"
else
    # 前台运行
    java $JAVA_OPTS $CONFIG_OPTS $EXCLUDE_OPTS $FEATURE_OPTS $SCAN_OPTS $LOG_OPTS \
    -Dspring.main.banner-mode=off \
    -jar law-firm-api.jar 