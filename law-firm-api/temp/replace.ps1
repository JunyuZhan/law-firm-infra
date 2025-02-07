$files = Get-ChildItem -Path "..\admin-api\src\main\java\com\lawfirm\admin" -Filter "*.java" -Recurse
foreach ($file in $files) {
    $content = Get-Content $file.FullName
    $content = $content -replace 'javax\.validation', 'jakarta.validation'
    $content | Set-Content $file.FullName -Encoding UTF8
} 