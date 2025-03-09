@echo off
setlocal enabledelayedexpansion

echo 开始移除Java文件中的BOM标记...

set "SOURCE_DIR=law-firm-modules\law-firm-auth\src\main\java\com\lawfirm\auth"

for /r "%SOURCE_DIR%" %%f in (*.java) do (
    echo 处理文件: %%f
    
    rem 使用type命令读取文件内容并重新写入，这样会自动去除BOM标记
    type "%%f" > "%%f.tmp"
    move /y "%%f.tmp" "%%f" > nul
)

echo 所有文件处理完成！
echo 请重新编译项目。 