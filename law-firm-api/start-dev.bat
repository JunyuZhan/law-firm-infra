@echo off
echo 启动律师事务所管理系统(开发环境)...

set JAVA_OPTS=-Xms256m -Xmx512m -XX:+HeapDumpOnOutOfMemoryError
set JAR_FILE=target\law-firm-api-1.0.0.jar

rem 设置开发环境变量
set SPRING_PROFILES_ACTIVE=develop

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

rem 启动应用
echo 正在启动应用...
java %JAVA_OPTS% -jar %JAR_FILE% --spring.profiles.active=%SPRING_PROFILES_ACTIVE%

pause 