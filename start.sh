#!/bin/bash
# Law Firm Management System Startup Script for Linux/Unix environments

echo "================================================"
echo "         Law Firm Management System"
echo "================================================"
echo ""

# Default database configuration (can be overridden by environment variables)
MYSQL_HOST=${MYSQL_HOST:-localhost}
MYSQL_PORT=${MYSQL_PORT:-3306}
MYSQL_DATABASE=${MYSQL_DATABASE:-law_firm}
MYSQL_USERNAME=${MYSQL_USERNAME:-root}
MYSQL_PASSWORD=${MYSQL_PASSWORD:-}

# Environment selection with default to dev
SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-dev}

# Core modules configuration (required)
STORAGE_ENABLED=${STORAGE_ENABLED:-true}
AUDIT_ENABLED=${AUDIT_ENABLED:-true}

# JVM options
JAVA_OPTS=${JAVA_OPTS:-"-Xms512m -Xmx1024m -Dfile.encoding=UTF-8 -Duser.language=zh -Duser.country=CN -Dsun.jnu.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8"}

# Display configuration
echo "Database Configuration:"
echo "Host: $MYSQL_HOST"
echo "Port: $MYSQL_PORT"
echo "Database: $MYSQL_DATABASE"
echo "Username: $MYSQL_USERNAME"
echo ""

echo "Active Profile: $SPRING_PROFILES_ACTIVE"
echo "Storage Module: Enabled (Required Component)"
echo "Audit Module: Enabled (Required Component)"
echo ""

# Check for JAR file
JAR_PATH="law-firm-api/target/law-firm-api-1.0.0.jar"
if [ ! -f "$JAR_PATH" ]; then
    echo "ERROR: JAR file not found at $JAR_PATH! Please build the project first."
    exit 1
fi

# MySQL connection URL
JDBC_URL="jdbc:mysql://$MYSQL_HOST:$MYSQL_PORT/$MYSQL_DATABASE?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true"

# 日志目录自动创建，保证与Windows一致
LOG_DIR="logs"
ARCHIVE_DIR="$LOG_DIR/archive"
DATE_DIR="$LOG_DIR/$(date +%Y%m%d)"
if [ ! -d "$LOG_DIR" ]; then
  mkdir -p "$LOG_DIR"
  echo "Created logs directory."
fi
if [ ! -d "$ARCHIVE_DIR" ]; then
  mkdir -p "$ARCHIVE_DIR"
  echo "Created logs archive directory."
fi
if [ ! -d "$DATE_DIR" ]; then
  mkdir -p "$DATE_DIR"
  echo "Created date-specific logs directory."
fi
LOG_FILE="$LOG_DIR/startup-$(date +%Y%m%d).log"

echo "Starting application..."
java $JAVA_OPTS \
  -Dlogging.file.path=$LOG_DIR \
  -Dlogging.file.name=$LOG_DIR/law-firm-api.log \
  -jar $JAR_PATH \
  --spring.profiles.active=$SPRING_PROFILES_ACTIVE \
  --spring.datasource.url="$JDBC_URL" \
  --spring.datasource.username=$MYSQL_USERNAME \
  --spring.datasource.password=$MYSQL_PASSWORD \
  --mybatis-plus.mapper-locations=classpath*:/mapper/**/*.xml \
  --mybatis-plus.type-aliases-package=com.lawfirm.model \
  --mybatis-plus.mapper-package=com.lawfirm.model.**.mapper \
  --mybatis-plus.configuration.map-underscore-to-camel-case=true \
  --mybatis-plus.configuration.cache-enabled=false \
  --law-firm.core.storage.enabled=$STORAGE_ENABLED \
  --law-firm.core.audit.enabled=$AUDIT_ENABLED \
  > "$LOG_FILE" 2>&1 