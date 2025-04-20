@echo off
chcp 65001 >nul
setlocal EnableDelayedExpansion

echo 启动律师事务所管理系统(开发环境)...

set JAVA_OPTS=-Xms256m -Xmx512m -XX:+HeapDumpOnOutOfMemoryError
set JAR_FILE=target\law-firm-api-1.0.0.jar

rem 设置开发环境变量
set SPRING_PROFILES_ACTIVE=develop

rem 请求数据库密码
echo 请输入数据库密码:
set /p DB_PASSWORD=
set SPRING_DATASOURCE_PASSWORD=%DB_PASSWORD%

rem 检查JAR文件是否存在
if not exist %JAR_FILE% (
  echo 错误: 找不到JAR文件：%JAR_FILE%
  echo 请先运行 mvn clean package 来构建项目
  exit /b 1
)

echo 使用以下环境变量:
echo SPRING_PROFILES_ACTIVE=%SPRING_PROFILES_ACTIVE%
echo JAVA_OPTS=%JAVA_OPTS%
echo JAR_FILE=%JAR_FILE%
echo 数据库密码已设置

rem 直接在命令行传递密码参数
java %JAVA_OPTS% -jar %JAR_FILE% --spring.profiles.active=%SPRING_PROFILES_ACTIVE% --spring.datasource.password=%DB_PASSWORD% --spring.flyway.enabled=true --spring.flyway.baseline-on-migrate=true --spring.flyway.out-of-order=true --spring.flyway.locations=classpath:db/migration --spring.datasource.url=jdbc:mysql://localhost:3306/law_firm?useUnicode=true^&characterEncoding=utf8^&useSSL=false^&serverTimezone=Asia/Shanghai^&allowPublicKeyRetrieval=true^&createDatabaseIfNotExist=true

pause 