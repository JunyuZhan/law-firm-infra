@echo off
echo 正在交换模块迁移脚本顺序...

:: 切换到项目根目录
cd /d %~dp0..

:: 定义路径
set AUTH_MODULE_PATH=law-firm-modules\law-firm-auth\src\main\resources\db\migration
set PERSONNEL_MODULE_PATH=law-firm-modules\law-firm-personnel\src\main\resources\db\migration

:: 创建临时目录用于存放重命名的文件
if not exist temp\migration mkdir temp\migration

:: 清理之前可能存在的Flyway记录
echo 正在准备清理Flyway记录...
echo -- 清理Flyway记录以重新执行迁移 > clean_flyway.sql
echo DROP TABLE IF EXISTS flyway_schema_history; >> clean_flyway.sql

echo 请输入MySQL root密码执行Flyway清理:
mysql -u root -p law_firm < clean_flyway.sql
del clean_flyway.sql

echo.
echo 正在交换迁移脚本命名...

:: 交换auth模块的V1000系列到V9000系列
for %%F in ("%AUTH_MODULE_PATH%\V1*.sql") do (
    set "filename=%%~nxF"
    set "newname=!filename:~0,1!9!filename:~2!"
    echo 重命名: %%~nxF 到 !newname!
    copy "%%F" "temp\migration\!newname!" >nul
)

:: 交换personnel模块的V9000系列到V1000系列
for %%F in ("%PERSONNEL_MODULE_PATH%\V9*.sql") do (
    set "filename=%%~nxF"
    set "newname=!filename:~0,1!1!filename:~2!"
    echo 重命名: %%~nxF 到 !newname!
    copy "%%F" "temp\migration\!newname!" >nul
)

:: 启用延迟变量扩展
setlocal EnableDelayedExpansion

:: 特别处理V1003脚本，将其重命名为非冲突版本
if exist "%AUTH_MODULE_PATH%\V1003__add_employee_constraint.sql" (
    echo 重命名问题脚本: V1003__add_employee_constraint.sql 到 V9999__add_employee_constraint.sql
    copy "%AUTH_MODULE_PATH%\V1003__add_employee_constraint.sql" "temp\migration\V9999__add_employee_constraint.sql" >nul
)

:: 删除原文件并复制重命名后的文件
echo 正在更新迁移脚本...
del "%AUTH_MODULE_PATH%\V1*.sql" 2>nul
del "%PERSONNEL_MODULE_PATH%\V9*.sql" 2>nul

:: 复制重命名后的文件到目标目录
for %%F in ("temp\migration\V9*.sql") do (
    copy "%%F" "%AUTH_MODULE_PATH%\" >nul
)

for %%F in ("temp\migration\V1*.sql") do (
    copy "%%F" "%PERSONNEL_MODULE_PATH%\" >nul
)

:: 清理临时目录
rd /s /q temp\migration
rd /s /q temp

endlocal

echo.
echo 迁移脚本顺序交换完成！
echo.
echo 已将personnel模块脚本改为V1000系列，auth模块脚本改为V9000系列。
echo 问题脚本V1003已重命名为V9999，确保它在personnel表创建后执行。
echo.
echo 请重新构建并启动应用程序:
echo mvnw.cmd clean package -DskipTests -pl law-firm-api
echo scripts\start-dev-noredis.bat
echo.

pause