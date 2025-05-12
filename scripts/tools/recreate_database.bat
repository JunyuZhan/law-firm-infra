@echo off
REM Try to set UTF-8 encoding, continue if command not found
chcp 65001 >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo Warning: chcp command not found. Continuing without UTF-8 encoding.
)

echo Recreating database...

REM MySQL connection information - please modify according to your setup
set MYSQL_USER=root
set MYSQL_PASSWORD=password
set MYSQL_DATABASE=law_firm
set MYSQL_HOST=localhost

echo Dropping and recreating database %MYSQL_DATABASE%...

REM Drop and create database (no need to clean tables)
echo DROP DATABASE IF EXISTS %MYSQL_DATABASE%; > recreate_db.sql
echo CREATE DATABASE %MYSQL_DATABASE% CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; >> recreate_db.sql

echo Executing SQL...

REM Try with password first
mysql -u%MYSQL_USER% -h%MYSQL_HOST% -p%MYSQL_PASSWORD% < recreate_db.sql
if %ERRORLEVEL% NEQ 0 (
    echo First attempt failed. Trying without password...
    mysql -u%MYSQL_USER% -h%MYSQL_HOST% < recreate_db.sql
    if %ERRORLEVEL% NEQ 0 (
        echo Second attempt failed. Trying interactive password prompt...
        mysql -u%MYSQL_USER% -h%MYSQL_HOST% -p < recreate_db.sql
        if %ERRORLEVEL% NEQ 0 (
            echo Database recreation failed. Please check your MySQL connection settings.
            goto :cleanup
        )
    )
)

echo Database %MYSQL_DATABASE% has been successfully recreated.

:cleanup
REM Clean up temporary files
del recreate_db.sql

pause 