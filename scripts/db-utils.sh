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
echo "     律师事务所管理系统 - 数据库工具"
echo "============================================="
echo ""

# 定义函数 - 数据库调试模式
debug_db() {
    echo "正在启动律师事务所管理系统 - 数据库调试模式..."
    
    # 设置环境变量
    export SPRING_PROFILES_ACTIVE=dev-mysql
    
    # 设置Java选项，开启详细日志
    export JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -Dlogging.level.org.hibernate.SQL=DEBUG -Dlogging.level.org.hibernate.type.descriptor.sql=TRACE -Dlogging.level.com.zaxxer.hikari=DEBUG -Dlogging.level.org.springframework.jdbc=DEBUG"
    
    echo "开始数据库连接测试..."
    
    # 使用Java直接运行Jar包，显示详细的数据库连接日志
    if [ ! -f "law-firm-api/target/law-firm-api-1.0.0.jar" ]; then
        echo "正在构建项目..."
        ./mvnw clean package -DskipTests -pl law-firm-api
    fi
    cd law-firm-api
    java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar target/law-firm-api-1.0.0.jar
    cd ..
}

# 定义函数 - 修复Flyway迁移问题
fix_flyway() {
    echo "正在修复数据库迁移问题..."
    
    # 连接到MySQL并执行修复操作
    echo "正在连接MySQL执行修复操作..."
    
    # 创建临时SQL文件
    cat > temp_fix.sql << EOF
-- 临时创建employee表以解决约束问题
CREATE TABLE IF NOT EXISTS employee (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  user_id BIGINT(20),
  department_id BIGINT(20),
  position VARCHAR(100),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='临时员工表';
EOF
    
    # 执行MySQL命令
    echo "请输入MySQL root密码执行修复:"
    mysql -u root -p law_firm < temp_fix.sql
    
    # 删除临时文件
    rm temp_fix.sql
    
    echo ""
    echo "修复完成！请重新运行应用。"
}

# 定义函数 - 禁用Flyway迁移
disable_flyway() {
    echo "正在禁用Flyway数据库迁移..."
    
    # 设置环境变量
    CONFIG_FILE="law-firm-api/src/main/resources/application.yml"
    BACKUP_FILE="law-firm-api/src/main/resources/application.yml.bak"
    
    # 备份配置文件
    cp $CONFIG_FILE $BACKUP_FILE
    
    # 替换配置
    echo "正在修改配置文件，禁用Flyway..."
    sed -i 's/flyway:\s*enabled:\s*true/flyway:\n    enabled: false/g' $CONFIG_FILE
    
    echo ""
    echo "Flyway已禁用！请重新启动应用。"
    echo "如需恢复，请将$BACKUP_FILE还原。"
}

# 定义函数 - 清理Flyway历史记录
clear_flyway() {
    echo "正在清理Flyway历史记录..."
    
    # 创建临时SQL文件
    cat > clear_flyway.sql << EOF
-- 删除Flyway历史表以重置迁移
DROP TABLE IF EXISTS flyway_schema_history;
EOF
    
    # 执行MySQL命令
    echo "请输入MySQL root密码执行清理:"
    mysql -u root -p law_firm < clear_flyway.sql
    
    # 删除临时文件
    rm clear_flyway.sql
    
    echo ""
    echo "Flyway历史已清理！现在可以重新运行应用。"
}

# 处理选项
case "$OPTION" in
    "debug")
        debug_db
        ;;
    "fix")
        fix_flyway
        ;;
    "disable")
        disable_flyway
        ;;
    "clear")
        clear_flyway
        ;;
    *)
        # 显示菜单
        echo "请选择操作:"
        echo "1. 数据库调试模式"
        echo "2. 修复Flyway迁移问题"
        echo "3. 禁用Flyway迁移"
        echo "4. 清理Flyway历史记录"
        echo "5. 退出"
        echo ""
        
        read -p "请选择 [1-5]: " CHOICE
        
        case "$CHOICE" in
            "1")
                debug_db
                ;;
            "2")
                fix_flyway
                ;;
            "3")
                disable_flyway
                ;;
            "4")
                clear_flyway
                ;;
            "5"|*)
                echo "操作已取消"
                ;;
        esac
        ;;
esac

echo "操作完成。" 