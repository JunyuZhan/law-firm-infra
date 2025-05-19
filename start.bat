@echo off
rem Fix encoding issues
chcp 65001 > nul 2>&1

rem Force console code page to UTF-8
reg add "HKCU\Console" /v CodePage /t REG_DWORD /d 65001 /f >nul 2>&1
rem Set appropriate font for Chinese display
reg add "HKCU\Console" /v FaceName /t REG_SZ /d "NSimSun" /f >nul 2>&1

rem Set Java parameters to handle UTF-8 correctly
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8
set JAVA_HOME_OPTIONS=-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8

rem Keep console open
title Law Firm Management System Console
color 0A
echo ================================================
echo         Law Firm Management System
echo ================================================
echo.

rem Set system environment variables
set LANG=zh_CN.UTF-8
set LC_ALL=zh_CN.UTF-8

rem Default database configuration
set DEFAULT_MYSQL_HOST=localhost
set DEFAULT_MYSQL_PORT=3306
set DEFAULT_MYSQL_DATABASE=law_firm
set DEFAULT_MYSQL_USERNAME=root

echo Database Configuration (Press Enter to use default values):
set /p MYSQL_HOST=Database Host [%DEFAULT_MYSQL_HOST%]: 
if "%MYSQL_HOST%"=="" set MYSQL_HOST=%DEFAULT_MYSQL_HOST%

set /p MYSQL_PORT=Database Port [%DEFAULT_MYSQL_PORT%]: 
if "%MYSQL_PORT%"=="" set MYSQL_PORT=%DEFAULT_MYSQL_PORT%

set /p MYSQL_DATABASE=Database Name [%DEFAULT_MYSQL_DATABASE%]: 
if "%MYSQL_DATABASE%"=="" set MYSQL_DATABASE=%DEFAULT_MYSQL_DATABASE%

set /p MYSQL_USERNAME=Database Username [%DEFAULT_MYSQL_USERNAME%]: 
if "%MYSQL_USERNAME%"=="" set MYSQL_USERNAME=%DEFAULT_MYSQL_USERNAME%

set /p MYSQL_PASSWORD=Database Password: 
echo.

echo Database Connection Information:
echo Host: %MYSQL_HOST%
echo Port: %MYSQL_PORT%
echo Database: %MYSQL_DATABASE%
echo Username: %MYSQL_USERNAME%
echo.

echo Select running environment:
echo [1] Development Environment (dev)
echo [2] Testing Environment (test)
echo [3] Production Environment (prod)
set /p ENV_CHOICE=Please select (1-3) [default 1]: 

if "%ENV_CHOICE%"=="2" (
    set SPRING_PROFILES_ACTIVE=test
    echo Selected: Testing Environment
) else if "%ENV_CHOICE%"=="3" (
    set SPRING_PROFILES_ACTIVE=prod
    echo Selected: Production Environment
) else (
    set SPRING_PROFILES_ACTIVE=dev
    echo Selected: Development Environment
)
echo.

REM These are required core services
set STORAGE_ENABLED=true
set AUDIT_ENABLED=true
echo Storage Module: Enabled (Required Component)
echo Audit Module: Enabled (Required Component)
echo.

rem Check if JAR file exists
if not exist "law-firm-api\target" (
    echo ERROR: Target directory not found. Please compile the project first:
    echo mvn clean install
    pause
    exit /b 1
)

cd law-firm-api\target
for %%f in (law-firm-api-*.jar) do (
    set JAR_FILE=%%f
)

if not defined JAR_FILE (
    echo ERROR: JAR file not found. Please compile the project first:
    echo mvn clean install
    cd ..\..
    pause
    exit /b 1
)

echo Using JAR file: %JAR_FILE%

rem Ensure log directories exist
if not exist "..\..\logs" (
    mkdir "..\..\logs"
    echo Created logs directory.
)

if not exist "..\..\logs\archive" (
    mkdir "..\..\logs\archive"
    echo Created logs archive directory.
)

rem Create date-formatted log directory
set LOG_DATE=%date:~0,4%%date:~5,2%%date:~8,2%
if not exist "..\..\logs\%LOG_DATE%" (
    mkdir "..\..\logs\%LOG_DATE%"
    echo Created date-specific logs directory.
)

rem Set log filename
set "LOG_FILE=..\..\logs\startup-%LOG_DATE%.log"

rem JVM parameters for UTF-8
set JAVA_OPTS=-Xms512m -Xmx1024m -Dfile.encoding=UTF-8 -Duser.language=zh -Duser.country=CN -Duser.region=CN -Dsun.jnu.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -Dlog4j.defaultInitOverride=true -Dlog4j2.formatMsgNoLookups=true
rem Specify JVM default charset
set JAVA_OPTS=%JAVA_OPTS% -Djava.nio.charset=UTF-8 -Dfile.encoding.pkg=sun.io -Dspring.mandatory-file-encoding=UTF-8
rem Fix Windows console log issues
set JAVA_OPTS=%JAVA_OPTS% -Djansi.passthrough=true -Djansi.windows=true -Djansi.force=true
rem API文档调试参数
set JAVA_OPTS=%JAVA_OPTS% -Dspringdoc.api-docs.enabled=true -Dknife4j.enable=true -Dspringdoc.api-docs.json-base64-encoded=false -Dspring.mvc.pathmatch.matching-strategy=ANT_PATH_MATCHER

rem Set database URL
set JDBC_URL=jdbc:mysql://%MYSQL_HOST%:%MYSQL_PORT%/%MYSQL_DATABASE%?useUnicode=true^&characterEncoding=utf8^&useSSL=false^&serverTimezone=Asia/Shanghai^&allowPublicKeyRetrieval=true^&rewriteBatchedStatements=true^&useConfigs=maxPerformance

echo Starting application...
echo Log file: %LOG_FILE%
echo.
echo 按 Ctrl+C 可以停止应用程序
echo.

rem Ask if user wants to run as windows service
set /p INSTALL_AS_SERVICE=是否安装为Windows服务? (y/n) [default: n]: 
if /i "%INSTALL_AS_SERVICE%"=="y" (
    echo 此功能正在开发中，将在下一版本提供...
    echo 暂时以控制台模式启动应用程序
    echo.
)

rem Explicitly specify log file path and name
java %JAVA_OPTS% -Dlogging.file.path=..\..\logs -Dlogging.file.name=..\..\logs\law-firm-api.log -Dspring.database.name=%MYSQL_DATABASE% -Dspring.datasource.url="%JDBC_URL%" -Dspring.datasource.username=%MYSQL_USERNAME% -Dspring.datasource.password=%MYSQL_PASSWORD% -jar %JAR_FILE% --spring.profiles.active=%SPRING_PROFILES_ACTIVE% --logging.charset.console=UTF-8 --logging.charset.file=UTF-8 --logging.config=classpath:logback-spring.xml

cd ..\..
pause 