@echo off
chcp 65001 > nul
echo ==========================================================
echo                 律所管理系统 Docker 构建脚本
echo ==========================================================
echo.

REM 创建目录结构
echo 正在创建必要的目录...
if not exist "scripts" mkdir scripts
if not exist "data\mysql" mkdir data\mysql
if not exist "data\redis" mkdir data\redis
if not exist "data\elasticsearch" mkdir data\elasticsearch
if not exist "data\minio" mkdir data\minio
if not exist "data\rocketmq\namesrv\logs" mkdir data\rocketmq\namesrv\logs
if not exist "data\rocketmq\broker\logs" mkdir data\rocketmq\broker\logs
if not exist "data\rocketmq\broker\store" mkdir data\rocketmq\broker\store
if not exist "logs" mkdir logs

REM 检查docker是否已经安装
docker --version > nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo 错误：Docker未安装，请先安装Docker后再运行此脚本。
    goto :EOF
)

REM 检查docker-compose是否已经安装
docker-compose --version > nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo 错误：Docker Compose未安装，请先安装Docker Compose后再运行此脚本。
    goto :EOF
)

REM 构建和启动容器
echo 正在构建Docker镜像...
docker-compose build

REM 启动服务
echo 正在启动容器...
docker-compose up -d

REM 检查服务是否正常启动
echo 正在检查服务状态...
timeout /t 10 /nobreak > nul
docker-compose ps

echo.
echo ==========================================================
echo                     服务启动完成！
echo ==========================================================
echo 以下是访问地址：
echo - 律所管理系统: http://localhost:8080
echo - SwaggerUI接口文档: http://localhost:8080/swagger-ui.html
echo - MinIO控制台: http://localhost:9001
echo.
echo 用户名：admin
echo 密码：admin123
echo ==========================================================

pause 