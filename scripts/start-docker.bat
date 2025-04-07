@echo off
echo 正在启动律师事务所管理系统 - Docker环境...

:: 设置环境变量
set SPRING_PROFILES_ACTIVE=docker

:: 设置Java选项 - Docker环境通常需要更多内存
set JAVA_OPTS=-Xms1024m -Xmx2048m -XX:+UseG1GC

:: 切换到项目根目录
cd /d %~dp0..

echo 选择启动方式:
echo 1. 使用Maven启动(Docker模式)
echo 2. 使用Java直接运行Jar包
echo 3. 使用Docker Compose启动
echo.

set /p choice=请选择 [1-3]: 

if "%choice%"=="1" (
    echo 使用Maven启动...
    call mvnw.cmd spring-boot:run -pl law-firm-api
) else if "%choice%"=="2" (
    echo 使用Java运行Jar包...
    if not exist "law-firm-api\target\law-firm-api-1.0.0.jar" (
        echo 正在构建项目...
        call mvnw.cmd clean package -DskipTests -pl law-firm-api
    )
    cd law-firm-api
    java %JAVA_OPTS% "-Dspring.profiles.active=%SPRING_PROFILES_ACTIVE%" -jar target\law-firm-api-1.0.0.jar
) else if "%choice%"=="3" (
    echo 使用Docker Compose启动...
    docker-compose up -d
) else (
    echo 无效的选择!
)

pause