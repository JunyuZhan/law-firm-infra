@echo off
REM Try to set UTF-8 encoding, continue if command not found
chcp 65001 >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Warning: chcp command not found. Continuing without UTF-8 encoding.
)

echo Cleaning database...

REM MySQL connection information - please modify according to your setup
set MYSQL_USER=root
set MYSQL_PASSWORD=password
set MYSQL_DATABASE=law_firm_db
set MYSQL_HOST=localhost
set MYSQL_PARAMS=-u%MYSQL_USER% -h%MYSQL_HOST% %MYSQL_DATABASE%

REM Create temporary directory
md tmp 2>nul

REM Delete all tables and Flyway history
echo SET FOREIGN_KEY_CHECKS = 0; > tmp\drop_all_tables.sql

REM Get all tables and generate drop statements
echo Connecting to database...

REM Choose connection method based on whether password is set
if "%MYSQL_PASSWORD%"=="" (
    echo Warning: No password set, trying without password...
    echo SHOW TABLES; | mysql %MYSQL_PARAMS% -N > tmp\table_list.txt
) else (
    echo SHOW TABLES; | mysql %MYSQL_PARAMS% -p%MYSQL_PASSWORD% -N > tmp\table_list.txt
)

if %ERRORLEVEL% NEQ 0 (
    echo Database connection failed. Trying interactive password prompt...
    echo SHOW TABLES; | mysql -u%MYSQL_USER% -h%MYSQL_HOST% -p %MYSQL_DATABASE% -N > tmp\table_list.txt
    
    if %ERRORLEVEL% NEQ 0 (
        echo Database connection failed, please check username and password!
        goto cleanup
    )
)

echo Processing tables...
for /F %%i in (tmp\table_list.txt) do (
    if not "%%i"=="" echo DROP TABLE IF EXISTS `%%i`; >> tmp\drop_all_tables.sql
)

echo DROP TABLE IF EXISTS flyway_schema_history; >> tmp\drop_all_tables.sql
echo SET FOREIGN_KEY_CHECKS = 1; >> tmp\drop_all_tables.sql

REM Execute SQL file
echo Deleting database tables...

REM Choose connection method based on whether password is set
if "%MYSQL_PASSWORD%"=="" (
    mysql %MYSQL_PARAMS% < tmp\drop_all_tables.sql
) else (
    mysql %MYSQL_PARAMS% -p%MYSQL_PASSWORD% < tmp\drop_all_tables.sql
)

if %ERRORLEVEL% NEQ 0 (
    echo Trying with interactive password prompt...
    mysql -u%MYSQL_USER% -h%MYSQL_HOST% -p %MYSQL_DATABASE% < tmp\drop_all_tables.sql
)

:cleanup
REM Clean up temporary files
rd /s /q tmp 2>nul

if %ERRORLEVEL% EQU 0 (
    echo Database has been reset. You can now run the application again to test migrations.
) else (
    echo Operation incomplete, please check error messages.
)
pause 