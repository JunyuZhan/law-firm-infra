# 部署指南

## 环境准备

### 系统要求
- CentOS 7.x 或更高版本
- 4核8G以上配置
- 50GB以上磁盘空间

### 基础软件安装

#### 1. 安装JDK 17
```bash
# 下载JDK
wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.tar.gz

# 解压
tar -zxvf jdk-17_linux-x64_bin.tar.gz -C /usr/local/

# 配置环境变量
echo "export JAVA_HOME=/usr/local/jdk-17" >> /etc/profile
echo "export PATH=\$JAVA_HOME/bin:\$PATH" >> /etc/profile
source /etc/profile

# 验证安装
java -version
```

#### 2. 安装MySQL 8.0
```bash
# 添加MySQL仓库
wget https://dev.mysql.com/get/mysql80-community-release-el7-3.noarch.rpm
rpm -ivh mysql80-community-release-el7-3.noarch.rpm

# 安装MySQL服务器
yum install -y mysql-community-server

# 启动MySQL
systemctl start mysqld
systemctl enable mysqld

# 获取初始密码
grep 'temporary password' /var/log/mysqld.log

# 安全配置
mysql_secure_installation
```

#### 3. 安装Redis 6.0
```bash
# 安装依赖
yum install -y gcc make

# 下载Redis
wget https://download.redis.io/releases/redis-6.0.16.tar.gz
tar -zxf redis-6.0.16.tar.gz
cd redis-6.0.16

# 编译安装
make
make install

# 配置Redis
mkdir /etc/redis
cp redis.conf /etc/redis/
vi /etc/redis/redis.conf

# 创建服务
vi /etc/systemd/system/redis.service

# 启动Redis
systemctl start redis
systemctl enable redis
```

#### 4. 安装Nacos 2.2.0
```bash
# 下载Nacos
wget https://github.com/alibaba/nacos/releases/download/2.2.0/nacos-server-2.2.0.tar.gz
tar -zxf nacos-server-2.2.0.tar.gz -C /usr/local/

# 配置Nacos
cd /usr/local/nacos/conf
cp application.properties.example application.properties

# 创建服务
vi /etc/systemd/system/nacos.service

# 启动Nacos
systemctl start nacos
systemctl enable nacos
```

## 应用部署

### 1. 数据库配置
```sql
-- 创建数据库
CREATE DATABASE law_firm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 创建用户并授权
CREATE USER 'lawfirm'@'%' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON law_firm.* TO 'lawfirm'@'%';
FLUSH PRIVILEGES;

-- 执行SQL脚本
mysql -ulawfirm -p law_firm < schema.sql
mysql -ulawfirm -p law_firm < data.sql
```

### 2. Redis配置
```bash
# 修改Redis配置
vi /etc/redis/redis.conf

# 主要配置项
bind 0.0.0.0
protected-mode yes
port 6379
requirepass your_password
maxmemory 2gb
maxmemory-policy allkeys-lru
```

### 3. Nacos配置
```bash
# 修改Nacos配置
vi /usr/local/nacos/conf/application.properties

# 主要配置项
spring.datasource.platform=mysql
db.url.0=jdbc:mysql://localhost:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true
db.user=nacos
db.password=your_password
```

### 4. 应用部署

#### 4.1 准备部署目录
```bash
mkdir -p /opt/lawfirm
cd /opt/lawfirm
```

#### 4.2 上传应用文件
```bash
# 上传jar包
scp staff-api.jar root@your_server:/opt/lawfirm/
scp admin-api.jar root@your_server:/opt/lawfirm/

# 上传配置文件
scp application.yml root@your_server:/opt/lawfirm/
```

#### 4.3 创建服务
```bash
# 创建staff-api服务
vi /etc/systemd/system/lawfirm-staff.service

[Unit]
Description=Law Firm Staff API Service
After=network.target

[Service]
Type=simple
User=lawfirm
Environment="JAVA_HOME=/usr/local/jdk-17"
Environment="SPRING_PROFILES_ACTIVE=prod"
WorkingDirectory=/opt/lawfirm
ExecStart=/usr/local/jdk-17/bin/java -jar staff-api.jar
Restart=always

[Install]
WantedBy=multi-user.target
```

#### 4.4 启动服务
```bash
# 重载服务配置
systemctl daemon-reload

# 启动服务
systemctl start lawfirm-staff
systemctl enable lawfirm-staff

# 查看服务状态
systemctl status lawfirm-staff
```

### 5. Nginx配置

#### 5.1 安装Nginx
```bash
yum install -y nginx
```

#### 5.2 配置Nginx
```nginx
# /etc/nginx/conf.d/lawfirm.conf

upstream staff_backend {
    server 127.0.0.1:8081;
}

upstream admin_backend {
    server 127.0.0.1:8082;
}

server {
    listen 80;
    server_name staff.lawfirm.com;

    location / {
        proxy_pass http://staff_backend;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}

server {
    listen 80;
    server_name admin.lawfirm.com;

    location / {
        proxy_pass http://admin_backend;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```

#### 5.3 启动Nginx
```bash
# 测试配置
nginx -t

# 启动Nginx
systemctl start nginx
systemctl enable nginx
```

## 监控和维护

### 1. 日志查看
```bash
# 查看应用日志
tail -f /opt/lawfirm/logs/staff-api.log

# 查看系统日志
journalctl -u lawfirm-staff -f
```

### 2. 性能监控
```bash
# 查看CPU和内存使用
top

# 查看磁盘使用
df -h

# 查看网络连接
netstat -tunlp
```

### 3. 备份策略
```bash
# 数据库备份
mysqldump -ulawfirm -p law_firm > backup_$(date +%Y%m%d).sql

# 配置文件备份
tar -zcf config_backup_$(date +%Y%m%d).tar.gz /opt/lawfirm/config/
```

## 故障处理

### 1. 常见问题
- 服务无法启动
- 数据库连接失败
- Redis连接失败
- Nacos注册失败

### 2. 排查步骤
1. 检查日志文件
2. 检查配置文件
3. 检查网络连接
4. 检查系统资源

### 3. 应急处理
```bash
# 重启服务
systemctl restart lawfirm-staff

# 清理日志
find /opt/lawfirm/logs -name "*.log" -mtime +30 -delete

# 释放内存
echo 3 > /proc/sys/vm/drop_caches
``` 