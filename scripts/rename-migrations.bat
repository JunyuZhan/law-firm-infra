@echo off
echo 正在调整数据库迁移脚本顺序...

:: 切换到项目根目录
cd /d %~dp0..

:: 定义路径
set AUTH_MODULE_PATH=law-firm-modules\law-firm-auth\src\main\resources\db\migration
set PERSONNEL_MODULE_PATH=law-firm-modules\law-firm-personnel\src\main\resources\db\migration

:: 检查脚本是否存在
if not exist "%AUTH_MODULE_PATH%\V1003__add_employee_constraint.sql" (
    echo 错误：找不到需要重命名的脚本文件！
    goto END
)

:: 清理之前可能存在的Flyway记录
echo 正在准备清理Flyway记录...
echo -- 清理Flyway记录以重新执行迁移 > clean_flyway.sql
echo DROP TABLE IF EXISTS flyway_schema_history; >> clean_flyway.sql

echo 请输入MySQL root密码执行Flyway清理:
mysql -u root -p law_firm < clean_flyway.sql
del clean_flyway.sql

:: 将V1003脚本重命名为V10003，确保它在表创建后执行
echo 正在重命名迁移脚本...
ren "%AUTH_MODULE_PATH%\V1003__add_employee_constraint.sql" "V10003__add_employee_constraint.sql"

echo.
echo 迁移脚本顺序调整完成！
echo.
echo 已将auth模块的V1003脚本重命名为V10003，确保它在personnel模块创建employee表后执行。
echo.
echo 请重新构建并启动应用程序:
echo mvnw.cmd clean package -DskipTests -pl law-firm-api
echo scripts\start-dev-noredis.bat
echo.

:END
pause 