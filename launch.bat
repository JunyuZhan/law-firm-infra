@echo off

echo ==============================================
echo         Law Firm Management System
echo ==============================================
echo.

set MYSQL_HOST=localhost
set MYSQL_PORT=3306
set MYSQL_DATABASE=law_firm
set MYSQL_USERNAME=root
set /p MYSQL_PASSWORD=MySQL Password: 
echo.

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

echo Rebuild project?
echo [1] Yes - Clean and rebuild
echo [2] No - Use existing build
set /p BUILD_CHOICE=Choose option (1-2) [default 2]: 

if not "%BUILD_CHOICE%"=="2" (
    echo Building project...
    call mvn clean package -DskipTests
    if %errorlevel% neq 0 (
        echo Build failed. Please check the error messages.
        pause
        exit /b 1
    )
    echo Build successful!
) else (
    echo Skipping build process...
)

echo.
echo Starting application...
echo Environment: %SPRING_PROFILES_ACTIVE%
echo Database: %MYSQL_USERNAME%@%MYSQL_HOST%:%MYSQL_PORT%/%MYSQL_DATABASE%
echo.

cd law-firm-api\target
if not exist law-firm-api-1.0.0.jar (
    echo ERROR: JAR file not found! Please rebuild the project.
    cd ..\..
    pause
    exit /b 1
)

set JAVA_OPTS=-Xms512m -Xmx1024m -Dfile.encoding=UTF-8
set JDBC_URL=jdbc:mysql://%MYSQL_HOST%:%MYSQL_PORT%/%MYSQL_DATABASE%?useUnicode=true^&characterEncoding=utf8^&useSSL=false^&serverTimezone=Asia/Shanghai^&allowPublicKeyRetrieval=true

echo Launching application...
java %JAVA_OPTS% -jar law-firm-api-1.0.0.jar ^
  --spring.profiles.active=%SPRING_PROFILES_ACTIVE% ^
  --spring.datasource.url="%JDBC_URL%" ^
  --spring.datasource.username=%MYSQL_USERNAME% ^
  --spring.datasource.password=%MYSQL_PASSWORD%

cd ..\..
pause 