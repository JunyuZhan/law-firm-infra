@echo off
chcp 65001 >nul
echo =====================================
echo 律师事务所系统 - 启动工具
echo =====================================
echo.

:menu
echo 请选择要启动的环境:
echo 1. 开发环境 (develop)
echo 2. 测试环境 (test)
echo 3. 生产环境 (prod)
echo 4. 自定义环境
echo 5. 退出
echo.
set /p ENV_CHOICE=请输入选择 (1-5): 

if "%ENV_CHOICE%"=="1" (
    set PROFILE=develop
    goto env_vars
)
if "%ENV_CHOICE%"=="2" (
    set PROFILE=test
    goto env_vars
)
if "%ENV_CHOICE%"=="3" (
    set PROFILE=prod
    goto env_vars
)
if "%ENV_CHOICE%"=="4" (
    echo.
    set /p PROFILE=请输入自定义环境名称: 
    goto env_vars
)
if "%ENV_CHOICE%"=="5" (
    goto end
)

echo.
echo 无效的选择，请重新输入。
goto menu

:env_vars
echo.
echo 为 %PROFILE% 环境配置环境变量
echo.

rem 根据不同环境设置对应的环境变量名称
if "%PROFILE%"=="develop" (
    set DB_PASSWORD_VAR=DEV_DB_PASSWORD
    set REDIS_PASSWORD_VAR=DEV_REDIS_PASSWORD
    echo 正在配置开发环境变量...
) else if "%PROFILE%"=="test" (
    set DB_PASSWORD_VAR=TEST_DB_PASSWORD
    set REDIS_PASSWORD_VAR=TEST_REDIS_PASSWORD
    echo 正在配置测试环境变量...
) else if "%PROFILE%"=="prod" (
    set DB_PASSWORD_VAR=SPRING_DATASOURCE_PASSWORD
    set REDIS_PASSWORD_VAR=SPRING_REDIS_PASSWORD
    echo 正在配置生产环境变量...
) else (
    set DB_PASSWORD_VAR=SPRING_DATASOURCE_PASSWORD
    set REDIS_PASSWORD_VAR=SPRING_REDIS_PASSWORD
    echo 正在配置自定义环境变量...
)

echo 是否设置数据库和Redis密码? (Y/N)
set /p SET_PASSWORDS=

if /i "%SET_PASSWORDS%"=="Y" (
    echo 请输入数据库密码: 
    set /p DB_PASSWORD=
    
    echo 请输入Redis密码 (如无可直接回车): 
    set /p REDIS_PASSWORD=
    
    rem 设置环境变量
    set %DB_PASSWORD_VAR%=%DB_PASSWORD%
    set %REDIS_PASSWORD_VAR%=%REDIS_PASSWORD%
    
    echo 已设置 %DB_PASSWORD_VAR% 和 %REDIS_PASSWORD_VAR% 环境变量
)

goto config

:config
echo.
echo 已选择环境: %PROFILE%
echo.

echo 是否修改数据库连接参数? (Y/N)
set /p DB_CONFIG_CHOICE=

if /i "%DB_CONFIG_CHOICE%"=="Y" (
    set /p DB_HOST=请输入数据库主机 (默认localhost): 
    if "%DB_HOST%"=="" set DB_HOST=localhost
    
    set /p DB_PORT=请输入数据库端口 (默认3306): 
    if "%DB_PORT%"=="" set DB_PORT=3306
    
    rem 根据环境设置默认数据库名
    if "%PROFILE%"=="test" (
        set DEFAULT_DB_NAME=law_firm_test
    ) else (
        set DEFAULT_DB_NAME=law_firm
    )
    
    set /p DB_NAME=请输入数据库名称 (默认%DEFAULT_DB_NAME%): 
    if "%DB_NAME%"=="" set DB_NAME=%DEFAULT_DB_NAME%
    
    set /p DB_USER=请输入数据库用户名 (默认root): 
    if "%DB_USER%"=="" set DB_USER=root
    
    rem 使用之前设置的环境变量来构建参数，不再直接在命令行中暴露密码
    set DB_PARAMS=--SPRING_DATASOURCE_URL=jdbc:mysql://%DB_HOST%:%DB_PORT%/%DB_NAME%?useUnicode=true^&characterEncoding=utf8^&useSSL=false^&serverTimezone=Asia/Shanghai^&allowPublicKeyRetrieval=true --SPRING_DATASOURCE_USERNAME=%DB_USER%
) else (
    set DB_PARAMS=
)

echo.
echo 是否修改服务器端口? (Y/N) 
set /p PORT_CONFIG_CHOICE=

if /i "%PORT_CONFIG_CHOICE%"=="Y" (
    set /p SERVER_PORT=请输入服务器端口 (默认8080): 
    if "%SERVER_PORT%"=="" set SERVER_PORT=8080
    set PORT_PARAMS=--server.port=%SERVER_PORT%
) else (
    set PORT_PARAMS=
)

echo.
echo 是否需要启用JVM调优参数? (Y/N)
set /p JVM_CONFIG_CHOICE=

if /i "%JVM_CONFIG_CHOICE%"=="Y" (
    rem 根据环境提供不同的默认内存配置
    if "%PROFILE%"=="prod" (
        set DEFAULT_XMS=1024m
        set DEFAULT_XMX=2g
    ) else if "%PROFILE%"=="test" (
        set DEFAULT_XMS=512m
        set DEFAULT_XMX=1g
    ) else (
        set DEFAULT_XMS=256m
        set DEFAULT_XMX=768m
    )
    
    set /p XMS=请输入JVM初始堆大小 (默认%DEFAULT_XMS%): 
    if "%XMS%"=="" set XMS=%DEFAULT_XMS%
    
    set /p XMX=请输入JVM最大堆大小 (默认%DEFAULT_XMX%): 
    if "%XMX%"=="" set XMX=%DEFAULT_XMX%
    
    set JVM_OPTS=-Xms%XMS% -Xmx%XMX% -XX:+UseG1GC -XX:MaxGCPauseMillis=200
) else (
    set JVM_OPTS=
)

:start
echo.
echo 正在启动律师事务所管理系统，环境: %PROFILE%
echo.

cd law-firm-api/target
set CMD=java %JVM_OPTS% -jar law-firm-api-1.0.0.jar --spring.profiles.active=%PROFILE% %DB_PARAMS% %PORT_PARAMS%

rem 不显示完整命令，避免暴露环境变量内容
echo 启动命令已准备完毕，正在启动...
echo.

%CMD%

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo 应用启动失败，错误代码: %ERRORLEVEL%
    echo.
    pause
) else (
    echo.
    echo 应用已停止运行。
    echo.
    pause
)

goto end

:end
echo.
echo 程序已退出。
echo. 