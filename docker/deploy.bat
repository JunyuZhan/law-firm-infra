@echo off
chcp 65001 >nul
echo ===== 律师事务所管理系统部署脚本 =====
echo.

rem 获取当前脚本所在目录和根目录
set "SCRIPT_DIR=%~dp0"
set "ROOT_DIR=%SCRIPT_DIR%.."

rem 确保MySQL初始化目录存在
if not exist "%SCRIPT_DIR%scripts\mysql" (
    echo 创建MySQL初始化脚本目录...
    mkdir "%SCRIPT_DIR%scripts\mysql"
)

rem 确保MinIO初始化目录存在
if not exist "%SCRIPT_DIR%scripts\minio" (
    echo 创建MinIO初始化脚本目录...
    mkdir "%SCRIPT_DIR%scripts\minio"
)

rem 确保初始化SQL文件存在
if not exist "%SCRIPT_DIR%scripts\mysql\init.sql" (
    echo 警告: MySQL初始化脚本不存在，请确保已创建docker\scripts\mysql\init.sql
    echo 可以复制项目中提供的示例SQL文件
)

rem 确保卷目录存在
if not exist "%ROOT_DIR%\data\mysql" mkdir "%ROOT_DIR%\data\mysql"
if not exist "%ROOT_DIR%\data\redis" mkdir "%ROOT_DIR%\data\redis"
if not exist "%ROOT_DIR%\logs" mkdir "%ROOT_DIR%\logs"
if not exist "%ROOT_DIR%\storage" mkdir "%ROOT_DIR%\storage"

rem 检查Docker是否已安装
docker --version > nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo 错误: Docker未安装，请先安装Docker
    exit /b 1
)

rem 检查Docker Compose是否已安装
docker-compose --version > nul 2>&1
if %ERRORLEVEL% EQU 0 (
    set "DOCKER_COMPOSE=docker-compose"
) else (
    docker compose version > nul 2>&1
    if %ERRORLEVEL% EQU 0 (
        set "DOCKER_COMPOSE=docker compose"
    ) else (
        echo 错误: Docker Compose未安装，请先安装Docker Compose
        exit /b 1
    )
)

rem 设置变量
set "APP_NAME=law-firm-api"
set "IMAGE_NAME=law-firm-infra-api"
set "CONTAINER_NAME=law-firm-app"

rem 保存当前目录
set "CURRENT_DIR=%CD%"

rem 切换到docker目录
cd /d "%SCRIPT_DIR%"

rem 停止并删除旧容器
echo 停止并删除旧容器...
%DOCKER_COMPOSE% down

rem 删除旧镜像
echo 删除旧镜像...
docker rmi %IMAGE_NAME% 2>nul || echo 镜像不存在或无法删除，继续执行...

rem 构建新镜像
echo 构建新镜像...
%DOCKER_COMPOSE% build

rem 启动服务
echo 启动服务...
%DOCKER_COMPOSE% up -d

rem 检查容器状态
echo 检查容器状态...
timeout /t 5 /nobreak > nul
%DOCKER_COMPOSE% ps

rem 返回原始目录
cd /d "%CURRENT_DIR%"

echo.
echo ===== 部署完成 =====
echo 系统访问地址: http://localhost:8080/api
echo 查看日志命令: cd docker ^&^& %DOCKER_COMPOSE% logs -f api
echo 停止系统命令: cd docker ^&^& %DOCKER_COMPOSE% down
echo.

pause 