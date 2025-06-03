@echo off
rem Fix encoding issues
chcp 65001 > nul 2>&1

rem Force console code page to UTF-8
reg add "HKCU\Console" /v CodePage /t REG_DWORD /d 65001 /f >nul 2>&1
reg add "HKCU\Console" /v FaceName /t REG_SZ /d "NSimSun" /f >nul 2>&1

rem Set Java parameters to handle UTF-8 correctly
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8
set JAVA_HOME_OPTIONS=-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8

rem Keep console open
title Law Firm Management System Console (Environment Mode)
color 0A
echo ================================================
echo    Law Firm Management System (Environment Mode)
echo ================================================
echo.

rem Set system environment variables
set LANG=zh_CN.UTF-8
set LC_ALL=zh_CN.UTF-8

rem Load environment configuration
if exist env.bat (
    echo Loading environment configuration from env.bat...
    call env.bat
    echo.
) else (
    echo Environment file env.bat not found!
    echo Please create env.bat file or use start.bat for interactive mode.
    echo.
    echo Example env.bat content:
    echo set MYSQL_HOST=localhost
    echo set MYSQL_PORT=3306
    echo set MYSQL_DATABASE=law_firm
    echo set MYSQL_USERNAME=root
    echo set MYSQL_PASSWORD=your_password
    echo set SPRING_PROFILES_ACTIVE=dev
    echo.
    pause
    exit /b 1
)

rem Validate required environment variables
if not defined MYSQL_HOST (
    echo ERROR: MYSQL_HOST not defined in environment
    pause
    exit /b 1
)
if not defined MYSQL_PORT (
    echo ERROR: MYSQL_PORT not defined in environment
    pause
    exit /b 1
)
if not defined MYSQL_DATABASE (
    echo ERROR: MYSQL_DATABASE not defined in environment
    pause
    exit /b 1
)
if not defined MYSQL_USERNAME (
    echo ERROR: MYSQL_USERNAME not defined in environment
    pause
    exit /b 1
)
if not defined MYSQL_PASSWORD (
    echo ERROR: MYSQL_PASSWORD not defined in environment
    pause
    exit /b 1
)

echo Database Connection Information:
echo Host: %MYSQL_HOST%
echo Port: %MYSQL_PORT%
echo Database: %MYSQL_DATABASE%
echo Username: %MYSQL_USERNAME%
echo Profile: %SPRING_PROFILES_ACTIVE%
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

rem Use environment variables for JVM settings
if not defined JVM_XMS set JVM_XMS=512m
if not defined JVM_XMX set JVM_XMX=1024m

rem JVM parameters for UTF-8
set JAVA_OPTS=-Xms%JVM_XMS% -Xmx%JVM_XMX% -Dfile.encoding=UTF-8 -Duser.language=zh -Duser.country=CN -Duser.region=CN -Dsun.jnu.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -Dlog4j.defaultInitOverride=true -Dlog4j2.formatMsgNoLookups=true
set JAVA_OPTS=%JAVA_OPTS% -Djava.nio.charset=UTF-8 -Dfile.encoding.pkg=sun.io -Dspring.mandatory-file-encoding=UTF-8
set JAVA_OPTS=%JAVA_OPTS% -Djansi.passthrough=true -Djansi.windows=true -Djansi.force=true
set JAVA_OPTS=%JAVA_OPTS% -Dspringdoc.api-docs.enabled=true -Dknife4j.enable=true -Dspringdoc.api-docs.json-base64-encoded=false -Dspring.mvc.pathmatch.matching-strategy=ANT_PATH_MATCHER

rem Set database URL
set JDBC_URL=jdbc:mysql://%MYSQL_HOST%:%MYSQL_PORT%/%MYSQL_DATABASE%?useUnicode=true^&characterEncoding=utf8^&useSSL=false^&serverTimezone=Asia/Shanghai^&allowPublicKeyRetrieval=true^&rewriteBatchedStatements=true^&useConfigs=maxPerformance

echo Starting application with environment configuration...
echo Log file: %LOG_FILE%
echo.
echo 按 Ctrl+C 可以停止应用程序
echo.

rem Start application with environment variables
java %JAVA_OPTS% -Dlogging.file.path=..\..\logs -Dlogging.file.name=..\..\logs\law-firm-api.log -Dspring.database.name=%MYSQL_DATABASE% -Dspring.datasource.url="%JDBC_URL%" -Dspring.datasource.username=%MYSQL_USERNAME% -Dspring.datasource.password=%MYSQL_PASSWORD% -jar %JAR_FILE% --spring.profiles.active=%SPRING_PROFILES_ACTIVE% --logging.charset.console=UTF-8 --logging.charset.file=UTF-8 --logging.config=classpath:logback-spring.xml

cd ..\..
pause 