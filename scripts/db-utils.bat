@echo off
setlocal enabledelayedexpansion

:: 默认选项
set OPTION=""

:: 解析命令行参数
if "%~1" neq "" (
    set OPTION=%~1
)

:: 切换到项目根目录
cd /d %~dp0..

echo =============================================
echo     律师事务所管理系统 - 数据库工具
echo =============================================
echo.

if /i "%OPTION%"=="debug" (
    call :debug_db
    goto :END
) else if /i "%OPTION%"=="fix" (
    call :fix_flyway
    goto :END
) else if /i "%OPTION%"=="disable" (
    call :disable_flyway
    goto :END
) else if /i "%OPTION%"=="clear" (
    call :clear_flyway
    goto :END
) else (
    :: 显示菜单
    echo 请选择操作:
    echo 1. 数据库调试模式
    echo 2. 修复Flyway迁移问题
    echo 3. 禁用Flyway迁移
    echo 4. 清理Flyway历史记录
    echo 5. 退出
    echo.

    set /p CHOICE=请选择 [1-5]: 

    if "!CHOICE!"=="1" (
        call :debug_db
    ) else if "!CHOICE!"=="2" (
        call :fix_flyway
    ) else if "!CHOICE!"=="3" (
        call :disable_flyway
    ) else if "!CHOICE!"=="4" (
        call :clear_flyway
    ) else if "!CHOICE!"=="5" (
        goto :END
    ) else (
        echo 无效的选择!
        goto :END
    )
)

goto :END

:: ============================
:: 数据库调试模式
:: ============================
:debug_db
echo 正在启动律师事务所管理系统 - 数据库调试模式...

:: 设置环境变量
set SPRING_PROFILES_ACTIVE=dev-mysql

:: 设置Java选项，开启详细日志
set JAVA_OPTS=-Xms512m -Xmx1024m -XX:+UseG1GC -Dlogging.level.org.hibernate.SQL=DEBUG -Dlogging.level.org.hibernate.type.descriptor.sql=TRACE -Dlogging.level.com.zaxxer.hikari=DEBUG -Dlogging.level.org.springframework.jdbc=DEBUG

echo 开始数据库连接测试...

:: 使用Java直接运行Jar包，显示详细的数据库连接日志
if not exist "law-firm-api\target\law-firm-api-1.0.0.jar" (
    echo 正在构建项目...
    call mvnw.cmd clean package -DskipTests -pl law-firm-api
)
cd law-firm-api
java %JAVA_OPTS% "-Dspring.profiles.active=%SPRING_PROFILES_ACTIVE%" -jar target\law-firm-api-1.0.0.jar
cd ..

exit /b 0

:: ============================
:: 修复Flyway迁移问题
:: ============================
:fix_flyway
echo 正在修复数据库迁移问题...

:: 连接到MySQL并执行修复操作
echo 正在连接MySQL执行修复操作...

:: 创建临时SQL文件
echo -- 临时创建employee表以解决约束问题 > temp_fix.sql
echo CREATE TABLE IF NOT EXISTS employee ( >> temp_fix.sql
echo   id BIGINT(20) NOT NULL AUTO_INCREMENT, >> temp_fix.sql
echo   user_id BIGINT(20), >> temp_fix.sql
echo   department_id BIGINT(20), >> temp_fix.sql
echo   position VARCHAR(100), >> temp_fix.sql
echo   PRIMARY KEY (id) >> temp_fix.sql
echo ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='临时员工表'; >> temp_fix.sql

:: 执行MySQL命令
echo 请输入MySQL root密码执行修复:
mysql -u root -p law_firm < temp_fix.sql

:: 删除临时文件
del temp_fix.sql

echo.
echo 修复完成！请重新运行应用。

exit /b 0

:: ============================
:: 禁用Flyway迁移
:: ============================
:disable_flyway
echo 正在禁用Flyway数据库迁移...

:: 设置环境变量
set CONFIG_FILE=law-firm-api\src\main\resources\application.yml
set BACKUP_FILE=law-firm-api\src\main\resources\application.yml.bak

:: 备份配置文件
copy %CONFIG_FILE% %BACKUP_FILE%

:: 替换配置
echo 正在修改配置文件，禁用Flyway...
powershell -Command "(Get-Content %CONFIG_FILE%) -replace 'flyway:\s*enabled:\s*true', 'flyway:\n    enabled: false' | Set-Content %CONFIG_FILE%"

echo.
echo Flyway已禁用！请重新启动应用。
echo 如需恢复，请将%BACKUP_FILE%还原。

exit /b 0

:: ============================
:: 清理Flyway历史记录
:: ============================
:clear_flyway
echo 正在清理Flyway历史记录...

:: 创建临时SQL文件
echo -- 删除Flyway历史表以重置迁移 > clear_flyway.sql
echo DROP TABLE IF EXISTS flyway_schema_history; >> clear_flyway.sql

:: 执行MySQL命令
echo 请输入MySQL root密码执行清理:
mysql -u root -p law_firm < clear_flyway.sql

:: 删除临时文件
del clear_flyway.sql

echo.
echo Flyway历史已清理！现在可以重新运行应用。

exit /b 0

:END
echo 操作完成。
endlocal 