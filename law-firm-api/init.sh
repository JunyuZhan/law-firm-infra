#!/bin/bash

# 环境检查脚本

echo "开始环境检查..."

# 检查Java环境
if ! command -v java &> /dev/null; then
    echo "错误：未找到Java环境"
    exit 1
fi

# 检查系统配置
if [ ! -d "${SYSTEM_TEMP_DIR}" ]; then
    echo "错误：临时目录不存在"
    exit 1
fi

if [ ! -d "${SYSTEM_LOG_DIR}" ]; then
    mkdir -p "${SYSTEM_LOG_DIR}"
fi

# 检查系统限制
CURRENT_MAX_OPEN_FILES=$(ulimit -n)
if [ "${CURRENT_MAX_OPEN_FILES}" -lt "${SYSTEM_MAX_OPEN_FILES}" ]; then
    echo "警告：当前最大打开文件数(${CURRENT_MAX_OPEN_FILES})小于推荐值(${SYSTEM_MAX_OPEN_FILES})"
fi

# 检查系统内存
TOTAL_MEMORY=$(free -g | awk '/^Mem:/{print $2}')
if [ "${TOTAL_MEMORY}" -lt 4 ]; then
    echo "警告：系统内存(${TOTAL_MEMORY}G)小于推荐值(4G)"
fi

# 检查系统交换空间
TOTAL_SWAP=$(free -g | awk '/^Swap:/{print $2}')
if [ "${TOTAL_SWAP}" -lt 2 ]; then
    echo "警告：系统交换空间(${TOTAL_SWAP}G)小于推荐值(2G)"
fi

# 检查系统CPU
CPU_CORES=$(nproc)
if [ "${CPU_CORES}" -lt 2 ]; then
    echo "警告：CPU核心数(${CPU_CORES})小于推荐值(2)"
fi

# 检查磁盘空间
DISK_USAGE=$(df -h ${SYSTEM_LOG_DIR} | awk 'NR==2 {print $5}' | sed 's/%//')
if [ "${DISK_USAGE}" -gt "${ALERT_DISK_USAGE_THRESHOLD}" ]; then
    echo "警告：磁盘使用率(${DISK_USAGE}%)超过阈值(${ALERT_DISK_USAGE_THRESHOLD}%)"
fi

# 检查SSL证书
if [ "${SSL_ENABLED}" = "true" ]; then
    if [ ! -f "${SSL_KEY_STORE}" ]; then
        echo "错误：SSL证书文件不存在"
        exit 1
    fi
    
    # 检查证书有效期
    CERT_EXPIRY=$(keytool -list -v -keystore "${SSL_KEY_STORE}" -storepass "${SSL_KEY_STORE_PASSWORD}" | grep "Valid from" | head -1)
    if [ -z "${CERT_EXPIRY}" ]; then
        echo "错误：无法读取SSL证书信息"
        exit 1
    fi
fi

# 检查审计日志目录
if [ ! -d "${SECURITY_AUDIT_LOG_PATH%/*}" ]; then
    echo "创建审计日志目录: ${SECURITY_AUDIT_LOG_PATH%/*}"
    mkdir -p "${SECURITY_AUDIT_LOG_PATH%/*}"
    chmod 700 "${SECURITY_AUDIT_LOG_PATH%/*}"
fi

# 检查环境变量
required_vars=(
    "DB_HOST"
    "DB_PORT"
    "DB_NAME"
    "DB_USERNAME"
    "DB_PASSWORD"
    "REDIS_HOST"
    "REDIS_PORT"
    "REDIS_PASSWORD"
    "MINIO_ENDPOINT"
    "MINIO_ACCESS_KEY"
    "MINIO_SECRET_KEY"
    "LAWFIRM_SEARCH_ENABLED"
    "LAWFIRM_SEARCH_TYPE"
    "LAWFIRM_LUCENE_INDEX_PATH"
)

for var in "${required_vars[@]}"; do
    if [ -z "${!var}" ]; then
        echo "错误: 环境变量 $var 未设置"
        exit 1
    fi
done

# 检查搜索引擎类型
if [ "$LAWFIRM_SEARCH_ENABLED" = "true" ] && [ "$LAWFIRM_SEARCH_TYPE" = "lucene" ]; then
    echo "检查 Lucene 配置..."
    # 检查索引目录权限
    if [ ! -d "$LAWFIRM_LUCENE_INDEX_PATH" ]; then
        echo "创建 Lucene 索引目录: $LAWFIRM_LUCENE_INDEX_PATH"
        mkdir -p "$LAWFIRM_LUCENE_INDEX_PATH"
        chmod 755 "$LAWFIRM_LUCENE_INDEX_PATH"
    fi
    
    # 验证目录写入权限
    if ! touch "$LAWFIRM_LUCENE_INDEX_PATH/.write_test" 2>/dev/null; then
        echo "错误: 无法写入 Lucene 索引目录"
        exit 1
    fi
    rm -f "$LAWFIRM_LUCENE_INDEX_PATH/.write_test"
fi

# 检查数据库连接
echo "检查数据库连接..."
if ! mysql -h"${DB_HOST}" -P"${DB_PORT}" -u"${DB_USERNAME}" -p"${DB_PASSWORD}" -e "SELECT 1" &> /dev/null; then
    echo "错误：无法连接到数据库"
    exit 1
fi

# 检查Redis连接
echo "检查Redis连接..."
if ! redis-cli -h "${REDIS_HOST}" -p "${REDIS_PORT}" -a "${REDIS_PASSWORD}" ping &> /dev/null; then
    echo "错误：无法连接到Redis"
    exit 1
fi

# 检查Redis内存使用
REDIS_MEMORY=$(redis-cli -h "${REDIS_HOST}" -p "${REDIS_PORT}" -a "${REDIS_PASSWORD}" info memory | grep "used_memory_human" | cut -d: -f2 | tr -d '\r')
if [ "${REDIS_MEMORY%G}" -gt "${ALERT_REDIS_MEMORY_USAGE_THRESHOLD}" ]; then
    echo "警告：Redis内存使用(${REDIS_MEMORY})超过阈值(${ALERT_REDIS_MEMORY_USAGE_THRESHOLD}%)"
fi

# 检查MinIO连接
echo "检查MinIO连接..."
if ! curl -s "${MINIO_ENDPOINT}/minio/health/live" &> /dev/null; then
    echo "错误：无法连接到MinIO服务"
    exit 1
fi

# 检查必要的目录
required_dirs=(
    "logs"
    "storage"
    "lucene"
)

for dir in "${required_dirs[@]}"; do
    if [ ! -d "$dir" ]; then
        echo "创建目录: $dir"
        mkdir -p "$dir"
        # 设置目录权限
        chmod 755 "$dir"
    fi
done

# 创建必要的目录
mkdir -p "${SYSTEM_LOG_DIR}"
mkdir -p "${JVM_HEAP_DUMP_PATH%/*}"
mkdir -p "${JVM_GC_LOG_PATH%/*}"

echo "环境检查通过"
exit 0 