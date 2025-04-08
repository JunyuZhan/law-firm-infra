@echo off
echo Starting Law Firm Management System...

:: 设置环境变量
set SPRING_PROFILES_ACTIVE=local

:: 检查Java版本
java -version
if %ERRORLEVEL% NEQ 0 (
    echo Java is not installed or configured properly.
    exit /b 1
)

:: 检查MySQL服务
echo Checking MySQL service...
powershell -Command "Get-Service MySQL* | Where-Object {$_.Status -eq 'Running'}"
if %ERRORLEVEL% NEQ 0 (
    echo MySQL service is not running. Please start it first.
    pause
)

:: 检查Redis服务
echo Checking Redis service...
powershell -Command "Get-Service Redis* | Where-Object {$_.Status -eq 'Running'}"
if %ERRORLEVEL% NEQ 0 (
    echo Redis service is not running. Please start it first.
    pause
)

echo All dependencies are ready. Starting the application...

:: 返回上级目录
cd ..

:: 使用完整路径运行指定模块
echo Starting the application using Maven...
call mvn -f law-firm-api/pom.xml org.springframework.boot:spring-boot-maven-plugin:run -Dspring-boot.run.profiles=local

echo Application started. Access at: http://localhost:8080/api 