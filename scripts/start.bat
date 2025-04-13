@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion

:: 默认环境为开发环境
set ENV=dev

:: 解析命令行参数
if "%~1" neq "" (
    set ENV=%~1
)

:: 设置通用变量
set APP_NAME=律师事务所管理系统
set JAR_PATH=law-firm-api\target\law-firm-api-1.0.0.jar

:: 根据环境设置特定配置
if /i "%ENV%"=="dev" (
    set ENV_NAME=开发环境
    set SPRING_PROFILES_ACTIVE=dev-mysql
    set JAVA_OPTS=-Xms512m -Xmx1024m -XX:+UseG1GC
) else if /i "%ENV%"=="dev-noredis" (
    set ENV_NAME=开发环境(无Redis)
    set SPRING_PROFILES_ACTIVE=dev-noredis
    set JAVA_OPTS=-Xms512m -Xmx1024m -XX:+UseG1GC
) else if /i "%ENV%"=="test" (
    set ENV_NAME=测试环境
    set SPRING_PROFILES_ACTIVE=test
    set JAVA_OPTS=-Xms512m -Xmx1024m -XX:+UseG1GC
) else if /i "%ENV%"=="prod" (
    set ENV_NAME=生产环境
    set SPRING_PROFILES_ACTIVE=prod
    set JAVA_OPTS=-Xms2048m -Xmx4096m -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError
) else if /i "%ENV%"=="docker" (
    set ENV_NAME=Docker环境
    set SPRING_PROFILES_ACTIVE=docker
    set JAVA_OPTS=-Xms1024m -Xmx2048m -XX:+UseG1GC
    set DOCKER_ENABLED=true
) else if /i "%ENV%"=="local" (
    set ENV_NAME=本地环境
    set SPRING_PROFILES_ACTIVE=local
    set JAVA_OPTS=-Xms512m -Xmx1024m -XX:+UseG1GC

    :: 检查MySQL和Redis服务
    echo 检查MySQL服务...
    powershell -Command "Get-Service MySQL* | Where-Object {$_.Status -eq 'Running'}" >nul 2>&1
    if !ERRORLEVEL! NEQ 0 (
        echo 警告: MySQL服务未运行，可能影响应用正常启动。
        pause
    )

    echo 检查Redis服务...
    powershell -Command "Get-Service Redis* | Where-Object {$_.Status -eq 'Running'}" >nul 2>&1
    if !ERRORLEVEL! NEQ 0 (
        echo 警告: Redis服务未运行，可能影响应用缓存功能。
        pause
    )
) else (
    echo 错误: 不支持的环境类型 "%ENV%"
    echo 支持的环境: dev ^| dev-noredis ^| test ^| prod ^| docker ^| local
    exit /b 1
)

:: 切换到项目根目录
cd /d %~dp0..

echo =============================================
echo 正在启动%APP_NAME% - %ENV_NAME%
echo =============================================
echo 环境配置: %SPRING_PROFILES_ACTIVE%
echo JVM参数: %JAVA_OPTS%
echo.

:: 检查Java是否已安装
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo 错误: 未检测到Java环境，请先安装或配置Java。
    goto :ERROR
)

:: 显示启动选项
echo 请选择启动方式:
echo 1. 使用Maven启动(开发模式)
echo 2. 使用Java直接运行Jar包
if "%DOCKER_ENABLED%"=="true" echo 3. 使用Docker Compose启动
echo.

if "%DOCKER_ENABLED%"=="true" (
    set /p CHOICE=请选择 [1-3]: 
) else (
    set /p CHOICE=请选择 [1-2]: 
)

if "%CHOICE%"=="1" (
    echo 使用Maven启动...
    call mvnw.cmd spring-boot:run -pl law-firm-api -Dspring-boot.run.profiles=%SPRING_PROFILES_ACTIVE%
    goto :END
) else if "%CHOICE%"=="2" (
    echo 使用Java直接运行Jar包...
    if not exist "%JAR_PATH%" (
        echo JAR包不存在，正在构建项目...
        call mvnw.cmd clean package -DskipTests -pl law-firm-api
        if !ERRORLEVEL! NEQ 0 (
            echo 构建失败！
            goto :ERROR
        )
    )
    echo 正在启动应用...
    java %JAVA_OPTS% "-Dspring.profiles.active=%SPRING_PROFILES_ACTIVE%" -jar %JAR_PATH%
    goto :END
) else if "%CHOICE%"=="3" && "%DOCKER_ENABLED%"=="true" (
    echo 使用Docker Compose启动...
    docker-compose up -d
    if !ERRORLEVEL! NEQ 0 (
        echo Docker启动失败！请确认Docker服务是否正常运行。
        goto :ERROR
    )
    echo Docker容器已启动。
    goto :END
) else (
    echo 无效的选择！
    goto :ERROR
)

:ERROR
echo 启动失败。
exit /b 1

:END
echo 应用已退出。
endlocal 