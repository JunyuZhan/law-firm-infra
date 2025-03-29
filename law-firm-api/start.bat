@echo off
rem 设置控制台为UTF-8编码
chcp 65001

rem 设置Java环境变量
set JAVA_OPTS=-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=GBK -Duser.language=zh -Duser.country=CN

rem 设置日志编码参数
set LOGGING_OPTS=-Dlogging.charset.console=UTF-8 -Dlogging.charset.file=UTF-8 -Dspring.output.ansi.enabled=always

rem 处理用户名编码
set USERNAME_OPTS=-Duser.name=lawfirm-user

rem 组合所有参数
set ALL_OPTS=%JAVA_OPTS% %LOGGING_OPTS% %USERNAME_OPTS%

echo 使用以下参数启动应用:
echo %ALL_OPTS%

rem 启动应用
java -jar target/law-firm-api-1.0.0.jar

pause 