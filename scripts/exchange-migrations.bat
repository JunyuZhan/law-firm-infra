@echo off
echo 正在交换auth和personnel模块的迁移脚本编号...

:: 切换到项目根目录
cd /d %~dp0..

:: 定义模块路径
set AUTH_PATH=law-firm-modules\law-firm-auth\src\main\resources\db\migration
set PERSONNEL_PATH=law-firm-modules\law-firm-personnel\src\main\resources\db\migration

:: 创建临时目录
mkdir temp 2>nul

:: 备份auth模块V100x系列脚本，重命名为V200x
for %%f in ("%AUTH_PATH%\V100*.sql") do (
    set "file=%%~nxf"
    set "newfile=!file:100=200!"
    echo 备份: %%f -^> temp\!newfile!
    copy "%%f" "temp\!newfile!" >nul
)

:: 备份personnel模块V900x系列脚本，重命名为V100x
for %%f in ("%PERSONNEL_PATH%\V900*.sql") do (
    set "file=%%~nxf"
    set "newfile=!file:900=100!"
    echo 备份: %%f -^> temp\!newfile!
    copy "%%f" "temp\!newfile!" >nul
)

:: 将auth模块临时备份(V200x)重命名为V900x
for %%f in ("temp\V200*.sql") do (
    set "file=%%~nxf"
    set "newfile=!file:200=900!"
    echo 重命名: temp\%%f -^> temp\!newfile!
    rename "temp\%%f" "!newfile!"
)

:: 将V100x系列复制到auth模块
for %%f in ("temp\V100*.sql") do (
    echo 复制: temp\%%f -^> %AUTH_PATH%\%%f
    copy "temp\%%f" "%AUTH_PATH%\" >nul
)

:: 将V900x系列复制到personnel模块
for %%f in ("temp\V900*.sql") do (
    echo 复制: temp\%%f -^> %PERSONNEL_PATH%\%%f
    copy "temp\%%f" "%PERSONNEL_PATH%\" >nul
)

:: 删除原始文件(可选，如果确认备份和复制成功)
echo 是否删除原始迁移脚本文件？(y/n)
set /p confirm=
if /i "%confirm%"=="y" (
    del "%AUTH_PATH%\V100*.sql" 2>nul
    del "%PERSONNEL_PATH%\V900*.sql" 2>nul
    echo 原始文件已删除。
) else (
    echo 原始文件未删除，请手动确认后删除。
)

:: 清理临时目录
rmdir /s /q temp

:: 清理Flyway历史记录
echo 是否清理Flyway迁移历史记录？(y/n)
set /p clean_flyway=
if /i "%clean_flyway%"=="y" (
    echo -- 清理Flyway迁移历史记录 > clear_flyway.sql
    echo DROP TABLE IF EXISTS flyway_schema_history; >> clear_flyway.sql
    
    echo 请输入MySQL root密码执行清理:
    mysql -u root -p law_firm < clear_flyway.sql
    del clear_flyway.sql
    echo Flyway历史记录已清理。
) else (
    echo Flyway历史记录未清理，请注意可能需要手动清理。
)

echo.
echo 迁移脚本交换完成！
echo 1. personnel模块的V900x系列已更改为V100x系列
echo 2. auth模块的V100x系列已更改为V900x系列
echo.
echo 请重新构建并启动应用:
echo mvnw.cmd clean package -DskipTests -pl law-firm-api
echo scripts\start-dev-noredis.bat
echo.

pause 