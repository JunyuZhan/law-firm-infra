@echo off
chcp 65001 >nul
echo ==============================================
echo         Law Firm Management System
echo ==============================================
echo.

rem Set JVM parameters
set JAVA_OPTS=-Xms512m -Xmx1024m -Dfile.encoding=UTF-8

rem Database configuration
echo Database Configuration:
set /p MYSQL_HOST=MySQL Host [default localhost]: 
if "%MYSQL_HOST%"=="" set MYSQL_HOST=localhost

set /p MYSQL_PORT=MySQL Port [default 3306]: 
if "%MYSQL_PORT%"=="" set MYSQL_PORT=3306

set /p MYSQL_DATABASE=MySQL Database [default law_firm]: 
if "%MYSQL_DATABASE%"=="" set MYSQL_DATABASE=law_firm

set /p MYSQL_USERNAME=MySQL Username [default root]: 
if "%MYSQL_USERNAME%"=="" set MYSQL_USERNAME=root

set /p MYSQL_PASSWORD=MySQL Password: 
echo.

rem Select environment
echo Select environment:
echo [1] Development (dev)
echo [2] Testing (test)
echo [3] Production (prod)
set /p ENV_CHOICE=Choose option (1-3) [default 1]: 

if "%ENV_CHOICE%"=="2" (
    set SPRING_PROFILES_ACTIVE=test
    echo Selected: Test environment
) else if "%ENV_CHOICE%"=="3" (
    set SPRING_PROFILES_ACTIVE=prod
    echo Selected: Production environment
) else (
    set SPRING_PROFILES_ACTIVE=dev
    echo Selected: Development environment
)
echo.

rem Check Java environment
echo Checking Java environment...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java not detected. Please install JDK 17 or higher.
    pause
    exit /b 1
)
echo Java environment OK

rem Check Redis service
echo Checking Redis service...
redis-cli ping >nul 2>&1
if %errorlevel% neq 0 (
    echo WARNING: Redis service not running or not in PATH
    echo You may need to start Redis manually.
    echo Continue anyway? (Y/N)
    set /p CONTINUE_CHOICE=
    if /i not "%CONTINUE_CHOICE%"=="Y" (
        echo Startup cancelled.
        exit /b 1
    )
)

rem Ask whether to rebuild the project
echo.
echo Rebuild project?
echo [1] Yes - Clean and rebuild (recommended after code changes)
echo [2] No - Use existing build (faster, but may not reflect recent changes)
set /p BUILD_CHOICE=Choose option (1-2) [default 1]: 

if "%BUILD_CHOICE%"=="2" (
    echo Skipping build process...
) else (
    rem Build project
    echo Building project...
    call mvn clean package -DskipTests
    if %errorlevel% neq 0 (
        echo Build failed. Please check the error messages.
        pause
        exit /b 1
    )
    echo Build successful!
)

echo.
rem Start application
echo Starting application...
echo Environment: %SPRING_PROFILES_ACTIVE%
echo Database: %MYSQL_USERNAME%@%MYSQL_HOST%:%MYSQL_PORT%/%MYSQL_DATABASE%
echo Application will be available at: http://localhost:8080
echo Press Ctrl+C to stop the application
echo.

cd law-firm-api\target
if not exist law-firm-api-1.0.0.jar (
    echo ERROR: JAR file not found! Please rebuild the project.
    cd ..
    pause
    exit /b 1
)

rem 设置JDBC URL并处理特殊字符
set "JDBC_URL=jdbc:mysql://%MYSQL_HOST%:%MYSQL_PORT%/%MYSQL_DATABASE%?useUnicode=true^&characterEncoding=utf8^&useSSL=false^&serverTimezone=Asia/Shanghai^&allowPublicKeyRetrieval=true"

rem 启动应用程序
echo 启动应用程序...
java %JAVA_OPTS% -jar law-firm-api-1.0.0.jar ^
  --spring.profiles.active=%SPRING_PROFILES_ACTIVE% ^
  --spring.datasource.url="%JDBC_URL%" ^
  --spring.datasource.username=%MYSQL_USERNAME% ^
  --spring.datasource.password=%MYSQL_PASSWORD% ^
  --law-firm.core.audit.enabled=false ^
  --law-firm.common.log.enabled=false ^
  --spring.task.execution.enabled=true ^
  --spring.task.execution.pool.core-size=10 ^
  --spring.task.execution.pool.max-size=20 ^
  --spring.task.execution.pool.queue-capacity=100

rem If application exits abnormally
echo.
echo Application has stopped
pause