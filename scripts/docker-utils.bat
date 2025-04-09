@echo off
setlocal enabledelayedexpansion

:: 默认选项
set OPTION=""

:: 解析命令行参数
if "%~1" neq "" (
    set OPTION=%~1
)

:: 切换到项目根目录
cd /d %~dp0..

echo =============================================
echo     律师事务所管理系统 - Docker工具
echo =============================================
echo.

if /i "%OPTION%"=="build" (
    call :docker_build
    goto :END
) else if /i "%OPTION%"=="start" (
    call :docker_start
    goto :END
) else if /i "%OPTION%"=="stop" (
    call :docker_stop
    goto :END
) else if /i "%OPTION%"=="restart" (
    call :docker_restart
    goto :END
) else (
    :: 显示菜单
    echo 请选择操作:
    echo 1. 构建镜像
    echo 2. 启动容器
    echo 3. 停止容器
    echo 4. 重启容器
    echo 5. 查看容器状态
    echo 6. 退出
    echo.

    set /p CHOICE=请选择 [1-6]: 

    if "!CHOICE!"=="1" (
        call :docker_build
    ) else if "!CHOICE!"=="2" (
        call :docker_start
    ) else if "!CHOICE!"=="3" (
        call :docker_stop
    ) else if "!CHOICE!"=="4" (
        call :docker_restart
    ) else if "!CHOICE!"=="5" (
        call :docker_status
    ) else if "!CHOICE!"=="6" (
        goto :END
    ) else (
        echo 无效的选择!
        goto :END
    )
)

goto :END

:: ============================
:: 构建Docker镜像
:: ============================
:docker_build
echo 律所管理系统Docker构建脚本
echo ==========================================

:: 确保scripts目录存在
if not exist "scripts" mkdir scripts

:: 创建目录结构
echo 正在创建必要的目录...
if not exist "data\mysql" mkdir data\mysql
if not exist "data\redis" mkdir data\redis
if not exist "data\elasticsearch" mkdir data\elasticsearch
if not exist "data\minio" mkdir data\minio
if not exist "data\rocketmq\namesrv\logs" mkdir data\rocketmq\namesrv\logs
if not exist "data\rocketmq\broker\logs" mkdir data\rocketmq\broker\logs
if not exist "data\rocketmq\broker\store" mkdir data\rocketmq\broker\store
if not exist "logs" mkdir logs

:: 检查docker是否已经安装
docker --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo 错误：Docker未安装，请先安装Docker后再运行此脚本。
    exit /b 1
)

:: 检查docker-compose是否已经安装
docker-compose --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo 错误：Docker Compose未安装，请先安装Docker Compose后再运行此脚本。
    exit /b 1
)

:: 构建镜像
echo 正在构建Docker镜像...
docker-compose build

echo.
echo Docker镜像构建完成！

exit /b 0

:: ============================
:: 启动Docker容器
:: ============================
:docker_start
echo 正在启动Docker容器...

:: 启动服务
docker-compose up -d

:: 检查服务是否正常启动
echo 正在检查服务状态...
timeout /t 10 >nul
docker-compose ps

echo.
echo ==========================================
echo     服务启动完成！
echo ==========================================
echo 以下是访问地址：
echo - 律所管理系统: http://localhost:8080
echo - SwaggerUI接口文档: http://localhost:8080/swagger-ui.html
echo - MinIO控制台: http://localhost:9001
echo.
echo 用户名：admin
echo 密码：admin123
echo ==========================================

exit /b 0

:: ============================
:: 停止Docker容器
:: ============================
:docker_stop
echo 正在停止Docker容器...

:: 停止服务
docker-compose down

echo.
echo 所有容器已停止。

exit /b 0

:: ============================
:: 重启Docker容器
:: ============================
:docker_restart
echo 正在重启Docker容器...

:: 重启服务
docker-compose restart

:: 检查服务是否正常启动
echo 正在检查服务状态...
timeout /t 10 >nul
docker-compose ps

echo.
echo 所有容器已重启。

exit /b 0

:: ============================
:: 查看Docker容器状态
:: ============================
:docker_status
echo 正在查询Docker容器状态...

:: 查看服务状态
docker-compose ps

exit /b 0

:END
echo 操作完成。
endlocal 