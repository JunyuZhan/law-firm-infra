@echo off
echo 设置环境变量...
set FLYWAY_ENABLED=true
set FLYWAY_BASELINE_ON_MIGRATE=true
set FLYWAY_VALIDATE_ON_MIGRATE=true
set MINIO_ACCESS_KEY=minioadmin
set MINIO_SECRET_KEY=minioadmin

echo 创建必要的目录...
if not exist .\data\mysql mkdir .\data\mysql
if not exist .\data\redis mkdir .\data\redis
if not exist .\data\minio mkdir .\data\minio

echo 启动服务...
docker-compose up -d

echo 等待MySQL启动...
timeout /t 30

echo 初始化MinIO...
docker-compose exec minio mc alias set myminio http://minio:9000 minioadmin minioadmin
docker-compose exec minio mc mb myminio/law-firm
docker-compose exec minio mc policy set public myminio/law-firm

echo 服务启动完成！
echo API服务：http://localhost:8080
echo MinIO控制台：http://localhost:9000
echo Redis：localhost:6379
echo MySQL：localhost:13306

echo 查看API服务日志...
docker-compose logs -f api 