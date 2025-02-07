@echo off
setlocal enabledelayedexpansion

cd ..\admin-api\src\main\java\com\lawfirm\admin\model\request

for /r %%f in (*.java) do (
    echo Processing: %%f
    set "tempfile=%%f.tmp"
    
    (for /f "delims=" %%i in ('type "%%f"') do (
        set "line=%%i"
        set "line=!line:javax.validation=jakarta.validation!"
        echo !line!
    )) > "!tempfile!"
    
    move /y "!tempfile!" "%%f"
)

echo Done.
pause 