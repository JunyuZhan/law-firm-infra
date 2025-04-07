@echo off
echo 正在创建禁用Flyway的启动脚本...

:: 切换到项目根目录
cd /d %~dp0..

echo @echo off > scripts\start-no-migration.bat
echo echo 正在启动律师事务所管理系统 - 禁用数据库迁移... >> scripts\start-no-migration.bat
echo. >> scripts\start-no-migration.bat
echo :: 设置环境变量 >> scripts\start-no-migration.bat
echo set SPRING_PROFILES_ACTIVE=dev-mysql >> scripts\start-no-migration.bat
echo. >> scripts\start-no-migration.bat
echo :: 设置Java选项 >> scripts\start-no-migration.bat
echo set JAVA_OPTS=-Xms512m -Xmx1024m -XX:+UseG1GC -Dflyway.enabled=false >> scripts\start-no-migration.bat
echo. >> scripts\start-no-migration.bat
echo :: 切换到项目根目录 >> scripts\start-no-migration.bat
echo cd /d %%~dp0.. >> scripts\start-no-migration.bat
echo. >> scripts\start-no-migration.bat
echo cd law-firm-api >> scripts\start-no-migration.bat
echo java %%JAVA_OPTS%% "-Dspring.profiles.active=%%SPRING_PROFILES_ACTIVE%%" -jar target\law-firm-api-1.0.0.jar >> scripts\start-no-migration.bat
echo. >> scripts\start-no-migration.bat
echo pause >> scripts\start-no-migration.bat

echo.
echo 已创建启动脚本: scripts\start-no-migration.bat
echo 此脚本将禁用Flyway数据库迁移，避免迁移错误。

pause 