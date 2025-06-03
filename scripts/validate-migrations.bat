@echo off
chcp 65001 > nul
echo 🚀 数据库迁移脚本验证工具
echo.

:: 检查Python是否安装
python --version > nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ 错误：未安装Python或Python不在PATH中
    echo 请安装Python 3.6+ 或确保Python在系统PATH中
    pause
    exit /b 1
)

:: 检查验证脚本是否存在
if not exist "scripts\migration-validator.py" (
    echo ❌ 错误：找不到验证脚本 scripts\migration-validator.py
    echo 请确保在项目根目录下运行此脚本
    pause
    exit /b 1
)

:: 运行验证
echo 📋 正在验证数据库迁移脚本...
echo.
python scripts\migration-validator.py

:: 检查验证结果
if %errorlevel% equ 0 (
    echo.
    echo ✅ 验证通过！所有迁移脚本可以安全执行。
    echo 📄 详细报告已保存到 migration-validation-report.json
) else (
    echo.
    echo ⚠️  验证发现问题，请查看上方错误信息。
    echo 📄 详细报告已保存到 migration-validation-report.json
    echo.
    echo 💡 常见问题及解决方案：
    echo    1. 版本号冲突：检查是否有重复的版本号
    echo    2. 表依赖问题：确保被引用的表在引用前已创建
    echo    3. 外键检查未恢复：确保SET FOREIGN_KEY_CHECKS=0后有对应的=1
    echo    4. 字符集问题：建议使用utf8mb4字符集
)

echo.
echo 按任意键退出...
pause > nul 