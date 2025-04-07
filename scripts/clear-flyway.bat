@echo off
echo 正在清理Flyway迁移历史记录...

:: 切换到项目根目录
cd /d %~dp0..

:: 创建SQL脚本
echo -- 清理Flyway迁移历史记录 > clear_flyway.sql
echo DROP TABLE IF EXISTS flyway_schema_history; >> clear_flyway.sql

echo 请输入MySQL root密码执行清理:
mysql -u root -p law_firm < clear_flyway.sql

:: 删除临时文件
del clear_flyway.sql

echo.
echo Flyway迁移历史记录已清理！
echo 现在可以重新运行应用，将重新执行所有迁移脚本。
echo.
echo 请重新构建并启动应用:
echo mvnw.cmd clean package -DskipTests -pl law-firm-api
echo scripts\start-dev-noredis.bat
echo.

pause 