@echo off
echo 正在修复数据库迁移问题...

:: 切换到项目根目录
cd /d %~dp0..

:: 连接到MySQL并执行修复操作
echo 正在连接MySQL执行修复操作...

:: 创建临时SQL文件
echo -- 临时创建employee表以解决约束问题 > temp_fix.sql
echo CREATE TABLE IF NOT EXISTS employee ( >> temp_fix.sql
echo   id BIGINT(20) NOT NULL AUTO_INCREMENT, >> temp_fix.sql
echo   user_id BIGINT(20), >> temp_fix.sql
echo   department_id BIGINT(20), >> temp_fix.sql
echo   position VARCHAR(100), >> temp_fix.sql
echo   PRIMARY KEY (id) >> temp_fix.sql
echo ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='临时员工表'; >> temp_fix.sql

:: 执行MySQL命令
echo 请输入MySQL root密码执行修复:
mysql -u root -p law_firm < temp_fix.sql

:: 删除临时文件
del temp_fix.sql

echo.
echo 修复完成！请重新运行应用。

pause 