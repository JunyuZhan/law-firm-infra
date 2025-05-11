@echo off
chcp 65001 >nul
echo =====================================
echo 律师事务所系统 - 启动工具
echo =====================================
echo.

:menu
echo 请选择要启动的环境:
echo 1. 开发环境 (dev)
echo 2. 测试环境 (test)
echo 3. 生产环境 (prod)
echo 4. 自定义环境
echo 5. 退出
echo.
set /p ENV_CHOICE=请输入选择 (1-5): 

if "%ENV_CHOICE%"=="1" set PROFILE=dev
if "%ENV_CHOICE%"=="2" set PROFILE=test
if "%ENV_CHOICE%"=="3" set PROFILE=prod
if "%ENV_CHOICE%"=="4" (
    echo.
    set /p PROFILE=请输入自定义环境名称: 
)
if "%ENV_CHOICE%"=="5" goto end

if not defined PROFILE (
    echo.
    echo 无效的选择，请重新输入。
    goto menu
)

echo.
echo 为 %PROFILE% 环境配置环境变量
echo.

set /p DB_PASSWORD=请输入数据库密码: 
set DATABASE_PASSWORD=%DB_PASSWORD%

echo.
echo 正在启动律师事务所管理系统，环境: %PROFILE%
echo.

cd law-firm-api\target || (
    echo 找不到目标目录
    goto end
)

java -Dspring.main.allow-bean-definition-overriding=true -Dspring.factories.ignore-errors=true -Dspring.main.allow-circular-references=true -Dspring.boot.factorybean.type-inferencing.enabled=false -jar law-firm-api-1.0.0.jar --spring.profiles.active=%PROFILE%

:end
echo.
echo 程序已退出。
pause 