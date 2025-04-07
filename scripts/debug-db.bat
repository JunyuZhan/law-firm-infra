@echo off
echo 正在启动律师事务所管理系统 - 数据库调试模式...

:: 设置环境变量
set SPRING_PROFILES_ACTIVE=dev-mysql

:: 设置Java选项，开启详细日志
set JAVA_OPTS=-Xms512m -Xmx1024m -XX:+UseG1GC -Dlogging.level.org.hibernate.SQL=DEBUG -Dlogging.level.org.hibernate.type.descriptor.sql=TRACE -Dlogging.level.com.zaxxer.hikari=DEBUG -Dlogging.level.org.springframework.jdbc=DEBUG

:: 切换到项目根目录
cd /d %~dp0..

echo 开始数据库连接测试...

:: 使用Java直接运行Jar包，显示详细的数据库连接日志
if not exist "law-firm-api\target\law-firm-api-1.0.0.jar" (
    echo 正在构建项目...
    call mvnw.cmd clean package -DskipTests -pl law-firm-api
)
cd law-firm-api
java %JAVA_OPTS% "-Dspring.profiles.active=%SPRING_PROFILES_ACTIVE%" -jar target\law-firm-api-1.0.0.jar

pause 