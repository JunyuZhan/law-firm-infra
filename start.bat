@echo off
rem 设置控制台代码页为UTF-8
chcp 65001
echo ================================================
echo         Law Firm Management System
echo ================================================
echo.

set MYSQL_HOST=localhost
set MYSQL_PORT=3306
set MYSQL_DATABASE=law_firm
set MYSQL_USERNAME=root
set /p MYSQL_PASSWORD=MySQL Password: 
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

REM 存储服务和审计服务都设置为强制启用，这些是系统必需的核心服务
set STORAGE_ENABLED=true
set AUDIT_ENABLED=true
echo Storage Module: Enabled (必需组件)
echo Audit Module: Enabled (必需组件)
echo.

cd law-firm-api\target
if not exist law-firm-api-1.0.0.jar (
    echo ERROR: JAR file not found! Please build the project first.
    cd ..\..
    pause
    exit /b 1
)

set JAVA_OPTS=-Xms512m -Xmx1024m -Dfile.encoding=UTF-8 -Duser.language=zh -Duser.country=CN -Dsun.jnu.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8
set JDBC_URL=jdbc:mysql://%MYSQL_HOST%:%MYSQL_PORT%/%MYSQL_DATABASE%?useUnicode=true^&characterEncoding=utf8^&useSSL=false^&serverTimezone=Asia/Shanghai^&allowPublicKeyRetrieval=true

echo Starting application...
java %JAVA_OPTS% -jar law-firm-api-1.0.0.jar ^
  --spring.profiles.active=%SPRING_PROFILES_ACTIVE% ^
  --spring.datasource.url="%JDBC_URL%" ^
  --spring.datasource.username=%MYSQL_USERNAME% ^
  --spring.datasource.password=%MYSQL_PASSWORD% ^
  --mybatis-plus.mapper-locations=classpath*:/mapper/**/*.xml ^
  --mybatis-plus.type-aliases-package=com.lawfirm.model ^
  --mybatis-plus.mapper-package=com.lawfirm.model.**.mapper ^
  --mybatis-plus.configuration.map-underscore-to-camel-case=true ^
  --mybatis-plus.configuration.cache-enabled=false ^
  --law-firm.core.storage.enabled=%STORAGE_ENABLED% ^
  --law-firm.core.audit.enabled=%AUDIT_ENABLED%

cd ..\..
pause 