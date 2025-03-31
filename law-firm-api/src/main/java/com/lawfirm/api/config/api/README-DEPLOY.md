# 律师事务所管理系统 - 生产环境部署指南

本文档提供律师事务所管理系统在生产环境下的部署和安全配置指南。

## 1. 部署前准备

### 1.1 系统要求
- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- 至少4GB内存
- 至少20GB磁盘空间

### 1.2 安全检查清单
- [ ] 服务器已安装最新安全补丁
- [ ] 数据库使用专用账户（非root）
- [ ] 所有密码符合复杂度要求
- [ ] 防火墙已配置（只开放必要端口）
- [ ] 已准备HTTPS证书
- [ ] 已配置独立的日志目录（可写权限）

## 2. 部署步骤

### 2.1 编译打包
```bash
# 切换到项目根目录
cd /path/to/law-firm-infra

# 使用Maven打包
mvn clean package -DskipTests -Pprod
```

### 2.2 配置环境变量
创建环境变量配置文件 `.env.prod`:

```
# 数据库配置
DB_HOST=192.168.1.100
DB_PORT=3306
DB_NAME=law_firm_prod
DB_USERNAME=law_firm_app
DB_PASSWORD=complex-password-here

# Redis配置
REDIS_HOST=192.168.1.101
REDIS_PORT=6379
REDIS_PASSWORD=redis-password-here
REDIS_DB=0

# 安全配置
JWT_SECRET=your-complex-jwt-secret-at-least-32-chars-long
JASYPT_PASSWORD=your-jasypt-encryption-password

# 服务器配置
SERVER_PORT=8443
SERVER_CONTEXT_PATH=/api

# CORS配置
CORS_ALLOWED_ORIGINS=https://admin.lawfirm.com,https://client.lawfirm.com

# 日志配置
LOG_PATH=/var/log/law-firm

# API文档配置（生产环境推荐禁用）
API_DOCS_ENABLED=false
```

### 2.3 配置应用
**使用环境变量启动应用**:

```bash
java -jar \
  -Dspring.profiles.active=prod \
  -Dspring.config.additional-location=file:/path/to/.env.prod \
  law-firm-api/target/law-firm-api.jar
```

**使用systemd管理应用**:

创建服务文件 `/etc/systemd/system/law-firm-api.service`:

```
[Unit]
Description=Law Firm Management API
After=network.target mysql.service redis.service

[Service]
Type=simple
User=lawfirm
EnvironmentFile=/path/to/.env.prod
WorkingDirectory=/opt/law-firm
ExecStart=/usr/bin/java -Xms1g -Xmx2g -Dspring.profiles.active=prod -jar /opt/law-firm/law-firm-api.jar
Restart=on-failure
RestartSec=5s

[Install]
WantedBy=multi-user.target
```

启动服务:
```bash
sudo systemctl daemon-reload
sudo systemctl enable law-firm-api
sudo systemctl start law-firm-api
```

### 2.4 配置HTTPS

使用Nginx作为反向代理配置HTTPS:

```nginx
server {
    listen 443 ssl http2;
    server_name api.lawfirm.com;
    
    ssl_certificate     /path/to/ssl/cert.pem;
    ssl_certificate_key /path/to/ssl/key.pem;
    ssl_protocols       TLSv1.2 TLSv1.3;
    ssl_ciphers         HIGH:!aNULL:!MD5;
    
    access_log /var/log/nginx/lawfirm-api-access.log;
    error_log  /var/log/nginx/lawfirm-api-error.log;
    
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}

# HTTP重定向到HTTPS
server {
    listen 80;
    server_name api.lawfirm.com;
    return 301 https://$host$request_uri;
}
```

## 3. 安全加固

### 3.1 文件权限

```bash
# 设置应用目录权限
sudo chown -R lawfirm:lawfirm /opt/law-firm
sudo chmod -R 750 /opt/law-firm

# 设置配置文件权限
sudo chmod 640 /path/to/.env.prod

# 设置日志目录权限
sudo mkdir -p /var/log/law-firm
sudo chown -R lawfirm:lawfirm /var/log/law-firm
sudo chmod -R 755 /var/log/law-firm
```

### 3.2 数据库安全

```sql
-- 创建专用应用数据库用户
CREATE USER 'law_firm_app'@'%' IDENTIFIED BY 'complex-password-here';
GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON law_firm_prod.* TO 'law_firm_app'@'%';
FLUSH PRIVILEGES;

-- 移除不必要的权限
REVOKE DROP, ALTER, CREATE ON law_firm_prod.* FROM 'law_firm_app'@'%'; 
```

### 3.3 防火墙配置

```bash
# 使用UFW (Ubuntu)
sudo ufw allow ssh
sudo ufw allow 80/tcp
sudo ufw allow 443/tcp
sudo ufw enable

# 或使用firewalld (CentOS/RHEL)
sudo firewall-cmd --permanent --add-service=ssh
sudo firewall-cmd --permanent --add-service=http
sudo firewall-cmd --permanent --add-service=https
sudo firewall-cmd --reload
```

## 4. 监控与维护

### 4.1 日志监控

配置logrotate确保日志轮转:

```
/var/log/law-firm/*.log {
    daily
    missingok
    rotate 14
    compress
    delaycompress
    notifempty
    create 0644 lawfirm lawfirm
    postrotate
        systemctl restart law-firm-api
    endscript
}
```

### 4.2 健康监控

配置健康检查接口:

```bash
# 监控应用状态
curl -X GET https://api.lawfirm.com/api/actuator/health

# 监控指标
curl -X GET https://api.lawfirm.com/api/actuator/metrics
```

### 4.3 备份策略

```bash
# MySQL备份脚本示例
#!/bin/bash
BACKUP_DIR="/backup/mysql"
DATE=$(date +"%Y%m%d")
MYSQL_USER="backup_user"
MYSQL_PASS="backup_password"
MYSQL_DB="law_firm_prod"

mkdir -p $BACKUP_DIR
mysqldump -u$MYSQL_USER -p$MYSQL_PASS $MYSQL_DB | gzip > $BACKUP_DIR/$MYSQL_DB-$DATE.sql.gz
find $BACKUP_DIR -type f -name "*.sql.gz" -mtime +30 -delete
```

## 5. 故障排除

### 5.1 应用启动失败

检查以下内容:

1. 日志文件 `/var/log/law-firm/api.log`
2. 数据库连接是否可用
3. Redis连接是否可用
4. 环境变量是否正确设置
5. 磁盘空间是否充足

### 5.2 常见错误处理

**数据库连接错误**:
- 检查网络连通性 `telnet DB_HOST DB_PORT`
- 验证数据库凭据 `mysql -u DB_USERNAME -p -h DB_HOST`

**内存不足错误**:
- 调整JVM内存设置 `-Xms1g -Xmx2g`
- 检查系统内存使用 `free -h`

**权限错误**:
- 检查日志目录权限 `ls -la /var/log/law-firm`
- 检查应用目录权限 `ls -la /opt/law-firm`

## 6. 联系支持

如遇到无法解决的问题，请联系技术支持团队:

- 邮箱: support@lawfirm.com
- 电话: 400-123-4567
- 工作时间: 周一至周五 9:00-18:00 