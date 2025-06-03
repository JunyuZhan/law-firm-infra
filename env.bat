@echo off
rem Law Firm Management System Environment Configuration
rem 律师事务所管理系统环境配置

rem ===== 数据库配置 Database Configuration =====
set MYSQL_HOST=localhost
set MYSQL_PORT=3306
set MYSQL_DATABASE=law_firm
set MYSQL_USERNAME=root
set MYSQL_PASSWORD=Zjy-1314

rem ===== 应用配置 Application Configuration =====
rem 运行环境 (dev/test/prod)
set SPRING_PROFILES_ACTIVE=dev

rem ===== 服务配置 Service Configuration =====
set STORAGE_ENABLED=true
set AUDIT_ENABLED=true

rem ===== JVM配置 JVM Configuration =====
set JVM_XMS=512m
set JVM_XMX=1024m

rem ===== 日志配置 Logging Configuration =====
set LOG_LEVEL_ROOT=INFO
set LOG_LEVEL_LAWFIRM=DEBUG

rem ===== 功能开关 Feature Switches =====
set DATABASE_MONITORING_ENABLED=true
set DATABASE_SLOW_QUERY_ENABLED=true
set DATABASE_BACKUP_ENABLED=false

echo Environment variables loaded successfully!
echo Database: %MYSQL_HOST%:%MYSQL_PORT%/%MYSQL_DATABASE%
echo User: %MYSQL_USERNAME%
echo Profile: %SPRING_PROFILES_ACTIVE% 