# 律所管理系统Docker构建脚本
Write-Host "=========================================="
Write-Host "     律所管理系统 Docker 构建脚本"
Write-Host "=========================================="
Write-Host ""

# 确保scripts目录存在
if (-Not (Test-Path "scripts")) {
    New-Item -ItemType Directory -Path "scripts"
}

# 创建目录结构
Write-Host "正在创建必要的目录..."
@("data/mysql", "data/redis", "data/elasticsearch", "data/minio", "data/rocketmq/namesrv/logs", "data/rocketmq/broker/logs", "data/rocketmq/broker/store", "logs") | ForEach-Object {
    if (-Not (Test-Path $_)) {
        New-Item -ItemType Directory -Path $_
    }
}

# 检查docker是否已经安装
if (-Not (Get-Command docker -ErrorAction SilentlyContinue)) {
    Write-Host "错误：Docker未安装，请先安装Docker后再运行此脚本。"
    exit 1
}

# 检查docker-compose是否已经安装
if (-Not (Get-Command docker-compose -ErrorAction SilentlyContinue)) {
    Write-Host "错误：Docker Compose未安装，请先安装Docker Compose后再运行此脚本。"
    exit 1
}

# 构建和启动容器
Write-Host "正在构建Docker镜像..."
docker-compose build

# 启动服务
Write-Host "正在启动容器..."
docker-compose up -d

# 检查服务是否正常启动
Write-Host "正在检查服务状态..."
Start-Sleep -Seconds 10
docker-compose ps

Write-Host ""
Write-Host "=========================================="
Write-Host "     服务启动完成！"
Write-Host "=========================================="
Write-Host "以下是访问地址："
Write-Host "- 律所管理系统: http://localhost:8080"
Write-Host "- SwaggerUI接口文档: http://localhost:8080/swagger-ui.html"
Write-Host "- MinIO控制台: http://localhost:9001"
Write-Host ""
Write-Host "用户名：admin"
Write-Host "密码：admin123"
Write-Host "=========================================="