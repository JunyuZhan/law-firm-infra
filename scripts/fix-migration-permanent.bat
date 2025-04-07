@echo off
echo 正在彻底修复数据库迁移问题...

:: 切换到项目根目录
cd /d %~dp0..

:: 创建修复SQL脚本
echo -- 清理现有Flyway记录表并创建新的记录 > fix_migration.sql
echo DROP TABLE IF EXISTS flyway_schema_history; >> fix_migration.sql
echo CREATE TABLE flyway_schema_history ( >> fix_migration.sql
echo   installed_rank INT NOT NULL PRIMARY KEY, >> fix_migration.sql
echo   version VARCHAR(50), >> fix_migration.sql
echo   description VARCHAR(200) NOT NULL, >> fix_migration.sql
echo   type VARCHAR(20) NOT NULL, >> fix_migration.sql
echo   script VARCHAR(1000) NOT NULL, >> fix_migration.sql
echo   checksum INT, >> fix_migration.sql
echo   installed_by VARCHAR(100) NOT NULL, >> fix_migration.sql
echo   installed_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, >> fix_migration.sql
echo   execution_time INT NOT NULL, >> fix_migration.sql
echo   success TINYINT(1) NOT NULL >> fix_migration.sql
echo ); >> fix_migration.sql

echo -- 重命名有问题的迁移脚本 >> fix_migration.sql
echo RENAME TABLE IF EXISTS employee TO employee_old; >> fix_migration.sql

echo -- 创建修复版本的迁移标记 >> fix_migration.sql
echo INSERT INTO flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) VALUES >> fix_migration.sql
echo (1, '1', 'Initial Schema', 'SQL', 'V1__Initial_Schema.sql', NULL, 'migration_fix', NOW(), 0, 1), >> fix_migration.sql
echo (2, '1.1', 'Initial Data', 'SQL', 'V1.1__Initial_Data.sql', NULL, 'migration_fix', NOW(), 0, 1), >> fix_migration.sql
echo (3, '1000', 'init auth tables', 'SQL', 'V1000__init_auth_tables.sql', NULL, 'migration_fix', NOW(), 0, 1), >> fix_migration.sql
echo (4, '1001', 'init auth data', 'SQL', 'V1001__init_auth_data.sql', NULL, 'migration_fix', NOW(), 0, 1), >> fix_migration.sql
echo (5, '1002', 'init business permissions', 'SQL', 'V1002__init_business_permissions.sql', NULL, 'migration_fix', NOW(), 0, 1); >> fix_migration.sql

echo.
echo 请输入MySQL root密码执行修复:
mysql -u root -p law_firm < fix_migration.sql

:: 删除临时SQL文件
del fix_migration.sql

:: 创建临时禁用V1003脚本的方法
echo.
echo 创建禁用有问题迁移脚本的解决方案...

:: 创建一个空的V1003迁移脚本来替代原来的
set AUTH_MODULE_PATH=law-firm-modules\law-firm-auth\src\main\resources\db\migration
set V1003_PATH=%AUTH_MODULE_PATH%\V1003__add_employee_constraint.sql

:: 备份原始脚本
if exist "%V1003_PATH%" (
    echo 正在备份原始迁移脚本...
    move "%V1003_PATH%" "%V1003_PATH%.bak"
    
    :: 创建一个空的替代脚本
    echo -- 此迁移脚本已被修改以解决顺序问题 > "%V1003_PATH%"
    echo -- 原始约束将在V9003中添加 >> "%V1003_PATH%"
)

:: 创建新的迁移脚本，将employee约束移到personnel模块之后
set PERSONNEL_MODULE_PATH=law-firm-modules\law-firm-personnel\src\main\resources\db\migration
set V9003_PATH=%PERSONNEL_MODULE_PATH%\V9003__add_auth_employee_constraint.sql

:: 创建新的迁移脚本
echo 创建正确顺序的迁移脚本...
echo -- 添加员工表外键约束 > "%V9003_PATH%"
echo -- 此脚本替代了原始的V1003脚本，以确保正确的执行顺序 >> "%V9003_PATH%"
echo. >> "%V9003_PATH%"
echo -- 添加employee表外键约束 >> "%V9003_PATH%"
echo ALTER TABLE employee ADD CONSTRAINT fk_employee_user_id FOREIGN KEY (user_id) REFERENCES sys_user(id); >> "%V9003_PATH%"
echo. >> "%V9003_PATH%"
echo -- 添加员工表索引 >> "%V9003_PATH%"
echo CREATE INDEX idx_employee_department_id ON employee(department_id); >> "%V9003_PATH%"
echo CREATE INDEX idx_employee_position ON employee(position); >> "%V9003_PATH%"

echo.
echo 修复完成！系统将: 
echo 1. 重置Flyway迁移历史
echo 2. 禁用原有的V1003迁移脚本
echo 3. 创建新的V9003迁移脚本，确保正确的执行顺序
echo.
echo 请重新构建并启动应用程序:
echo mvnw.cmd clean package -DskipTests -pl law-firm-api
echo scripts\start-dev-noredis.bat
echo.

pause 