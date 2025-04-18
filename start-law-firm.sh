#!/bin/bash

echo "====================================="
echo "律师事务所系统 - 启动工具"
echo "====================================="
echo

# 显示菜单函数
show_menu() {
  echo "请选择要启动的环境:"
  echo "1. 开发环境 (develop)"
  echo "2. 测试环境 (test)"
  echo "3. 生产环境 (prod)"
  echo "4. 自定义环境"
  echo "5. 退出"
  echo
  read -p "请输入选择 (1-5): " ENV_CHOICE
  
  case $ENV_CHOICE in
    1) PROFILE="develop" ;;
    2) PROFILE="test" ;;
    3) PROFILE="prod" ;;
    4)
      echo
      read -p "请输入自定义环境名称: " PROFILE
      ;;
    5) exit 0 ;;
    *)
      echo
      echo "无效的选择，请重新输入。"
      show_menu
      ;;
  esac
}

# 配置环境变量函数
configure_env_vars() {
  echo
  echo "为 $PROFILE 环境配置环境变量"
  echo

  # 根据不同环境设置对应的环境变量名称
  case $PROFILE in
    develop)
      DB_PASSWORD_VAR="DEV_DB_PASSWORD"
      REDIS_PASSWORD_VAR="DEV_REDIS_PASSWORD"
      echo "正在配置开发环境变量..."
      ;;
    test)
      DB_PASSWORD_VAR="TEST_DB_PASSWORD"
      REDIS_PASSWORD_VAR="TEST_REDIS_PASSWORD"
      echo "正在配置测试环境变量..."
      ;;
    prod)
      DB_PASSWORD_VAR="SPRING_DATASOURCE_PASSWORD"
      REDIS_PASSWORD_VAR="SPRING_REDIS_PASSWORD"
      echo "正在配置生产环境变量..."
      ;;
    *)
      DB_PASSWORD_VAR="SPRING_DATASOURCE_PASSWORD"
      REDIS_PASSWORD_VAR="SPRING_REDIS_PASSWORD"
      echo "正在配置自定义环境变量..."
      ;;
  esac

  # 询问是否需要设置环境变量
  read -p "是否设置数据库和Redis密码? (Y/N): " SET_PASSWORDS
  
  if [[ "$SET_PASSWORDS" =~ ^[Yy]$ ]]; then
    # 数据库密码
    read -sp "请输入数据库密码: " DB_PASSWORD
    echo
    
    # Redis密码
    read -sp "请输入Redis密码 (如无可直接回车): " REDIS_PASSWORD
    echo

    # 配置环境变量
    export $DB_PASSWORD_VAR="$DB_PASSWORD"
    export $REDIS_PASSWORD_VAR="$REDIS_PASSWORD"
    
    echo "已设置 $DB_PASSWORD_VAR 和 $REDIS_PASSWORD_VAR 环境变量"
  fi
}

# 配置数据库函数
configure_database() {
  echo
  echo "已选择环境: $PROFILE"
  echo
  
  read -p "是否修改数据库连接参数? (Y/N): " DB_CONFIG_CHOICE
  
  if [[ "$DB_CONFIG_CHOICE" =~ ^[Yy]$ ]]; then
    read -p "请输入数据库主机 (默认localhost): " DB_HOST
    DB_HOST=${DB_HOST:-localhost}
    
    read -p "请输入数据库端口 (默认3306): " DB_PORT
    DB_PORT=${DB_PORT:-3306}
    
    # 根据环境设置默认数据库名
    case $PROFILE in
      test)
        DEFAULT_DB_NAME="law_firm_test"
        ;;
      *)
        DEFAULT_DB_NAME="law_firm"
        ;;
    esac
    
    read -p "请输入数据库名称 (默认$DEFAULT_DB_NAME): " DB_NAME
    DB_NAME=${DB_NAME:-$DEFAULT_DB_NAME}
    
    read -p "请输入数据库用户名 (默认root): " DB_USER
    DB_USER=${DB_USER:-root}
    
    # 使用之前设置的环境变量来构建参数，不再直接在命令行中暴露密码
    DB_PARAMS="--SPRING_DATASOURCE_URL=jdbc:mysql://$DB_HOST:$DB_PORT/$DB_NAME?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true --SPRING_DATASOURCE_USERNAME=$DB_USER"
  else
    DB_PARAMS=""
  fi
}

# 配置服务器端口函数
configure_port() {
  echo
  read -p "是否修改服务器端口? (Y/N): " PORT_CONFIG_CHOICE
  
  if [[ "$PORT_CONFIG_CHOICE" =~ ^[Yy]$ ]]; then
    read -p "请输入服务器端口 (默认8080): " SERVER_PORT
    SERVER_PORT=${SERVER_PORT:-8080}
    PORT_PARAMS="--server.port=$SERVER_PORT"
  else
    PORT_PARAMS=""
  fi
}

# 配置JVM参数函数
configure_jvm() {
  echo
  read -p "是否需要启用JVM调优参数? (Y/N): " JVM_CONFIG_CHOICE
  
  if [[ "$JVM_CONFIG_CHOICE" =~ ^[Yy]$ ]]; then
    # 根据环境提供不同的默认内存配置
    case $PROFILE in
      prod)
        DEFAULT_XMS="1024m"
        DEFAULT_XMX="2g"
        ;;
      test)
        DEFAULT_XMS="512m"
        DEFAULT_XMX="1g"
        ;;
      *)
        DEFAULT_XMS="256m"
        DEFAULT_XMX="768m"
        ;;
    esac
    
    read -p "请输入JVM初始堆大小 (默认$DEFAULT_XMS): " XMS
    XMS=${XMS:-$DEFAULT_XMS}
    
    read -p "请输入JVM最大堆大小 (默认$DEFAULT_XMX): " XMX
    XMX=${XMX:-$DEFAULT_XMX}
    
    JVM_OPTS="-Xms$XMS -Xmx$XMX -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
  else
    JVM_OPTS=""
  fi
}

# 启动应用函数
start_application() {
  echo
  echo "正在启动律师事务所管理系统，环境: $PROFILE"
  echo
  
  cd law-firm-api/target || {
    echo "错误: 找不到目标目录 'law-firm-api/target'"
    exit 1
  }
  
  CMD="java $JVM_OPTS -jar law-firm-api-1.0.0.jar --spring.profiles.active=$PROFILE $DB_PARAMS $PORT_PARAMS"
  
  # 不显示完整命令，避免暴露环境变量内容
  echo "启动命令已准备完毕，正在启动..."
  echo
  
  eval $CMD
  
  if [ $? -ne 0 ]; then
    echo
    echo "应用启动失败，错误代码: $?"
    echo
  else
    echo
    echo "应用已停止运行。"
    echo
  fi
}

# 检查Java是否安装
check_java() {
  if ! command -v java &> /dev/null; then
    echo "错误: 未找到Java，请确保已安装JDK 21或更高版本"
    exit 1
  fi
  
  java_version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
  echo "检测到Java版本: $java_version"
  
  # 简单版本检查，只检查主版本号
  major_version=$(echo "$java_version" | cut -d'.' -f1)
  if [ "$major_version" -lt 21 ]; then
    echo "警告: 推荐使用JDK 21或更高版本，当前版本为 $java_version"
    read -p "是否继续使用当前Java版本? (Y/N): " CONTINUE
    if [[ ! "$CONTINUE" =~ ^[Yy]$ ]]; then
      exit 1
    fi
  fi
}

# 检查JAR文件是否存在
check_jar_file() {
  if [ ! -f "law-firm-api/target/law-firm-api-1.0.0.jar" ]; then
    echo "错误: 找不到JAR文件 'law-firm-api/target/law-firm-api-1.0.0.jar'"
    echo "请确保已经构建项目"
    exit 1
  fi
}

# 主函数
main() {
  # 检查环境
  check_java
  check_jar_file
  
  # 显示菜单并获取选择
  show_menu
  
  # 配置参数
  configure_env_vars
  configure_database
  configure_port
  configure_jvm
  
  # 启动应用
  start_application
}

# 执行主函数
main 