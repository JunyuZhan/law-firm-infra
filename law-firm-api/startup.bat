@echo off
rem 设置Java字符编码
set JAVA_OPTS=%JAVA_OPTS% -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8

rem 设置控制台编码为UTF-8
chcp 65001

rem 启动应用
java %JAVA_OPTS% -jar law-firm-api.jar 