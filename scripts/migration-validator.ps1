# 数据库迁移脚本验证工具 (PowerShell版本)
# 用于验证Flyway迁移脚本的版本号、执行顺序、冲突和重复问题

param(
    [string]$BasePath = ".",
    [switch]$ShowDetails = $false,
    [switch]$ExportJson = $false
)

# 全局变量
$Script:MigrationFiles = @()
$Script:Errors = @()
$Script:Warnings = @()
$Script:VersionConflicts = @()

function Write-ColorOutput {
    param(
        [string]$Message,
        [string]$Color = "White"
    )
    
    $colorMap = @{
        "Green" = "Green"
        "Red" = "Red" 
        "Yellow" = "Yellow"
        "Blue" = "Blue"
        "Cyan" = "Cyan"
        "Magenta" = "Magenta"
        "White" = "White"
    }
    
    Write-Host $Message -ForegroundColor $colorMap[$Color]
}

function Get-MigrationFiles {
    param([string]$Path)
    
    Write-ColorOutput "📁 扫描迁移脚本文件..." "Blue"
    
    $patterns = @(
        "**/META-INF/db/migration/V*.sql",
        "**/src/main/resources/db/migration/V*.sql"
    )
    
    $files = @()
    foreach ($pattern in $patterns) {
        $foundFiles = Get-ChildItem -Path $Path -Recurse -Filter "V*.sql" | Where-Object {
            $_.FullName -like "*META-INF*db*migration*" -or 
            $_.FullName -like "*src*main*resources*db*migration*"
        }
        $files += $foundFiles
    }
    
    # 过滤掉备份目录
    $validFiles = $files | Where-Object { $_.FullName -notlike "*db-migration-backup*" }
    
    Write-ColorOutput "✅ 找到 $($validFiles.Count) 个迁移脚本文件" "Green"
    
    return $validFiles
}

function Parse-MigrationFile {
    param([System.IO.FileInfo]$File)
    
    # 提取版本号
    if ($File.Name -match '^V(\d+)(__.*)?\.sql$') {
        $version = [int]$Matches[1]
    } else {
        return $null
    }
    
    # 确定模块
    $module = Get-ModuleName -Path $File.FullName
    
    # 读取文件内容
    try {
        $content = Get-Content $File.FullName -Raw -Encoding UTF8
    } catch {
        $Script:Errors += "❌ 无法读取文件 $($File.FullName): $($_.Exception.Message)"
        return $null
    }
    
    return @{
        Path = $File.FullName
        Filename = $File.Name
        Version = $version
        Module = $module
        Content = $content
        Size = $content.Length
    }
}

function Get-ModuleName {
    param([string]$Path)
    
    $pathParts = $Path -split '\\'
    
    foreach ($part in $pathParts) {
        if ($part -like "law-firm-*" -or $part -like "core-*") {
            return $part
        }
    }
    
    if ($Path -like "*law-firm-api*") {
        return "law-firm-api"
    }
    
    return "unknown"
}

function Test-VersionConflicts {
    Write-ColorOutput "🔍 检查版本号冲突..." "Blue"
    
    $versionGroups = $Script:MigrationFiles | Group-Object Version
    $conflicts = @()
    
    foreach ($group in $versionGroups) {
        if ($group.Count -gt 1) {
            $conflict = @{
                Version = $group.Name
                Files = $group.Group | ForEach-Object { $_.Filename }
                Modules = $group.Group | ForEach-Object { $_.Module }
            }
            $conflicts += $conflict
            $Script:Errors += "❌ 版本号冲突 V$($group.Name): $($conflict.Files -join ', ')"
        }
    }
    
    if ($conflicts.Count -eq 0) {
        Write-ColorOutput "✅ 未发现版本号冲突" "Green"
    } else {
        Write-ColorOutput "⚠️  发现 $($conflicts.Count) 个版本号冲突" "Yellow"
    }
    
    $Script:VersionConflicts = $conflicts
    return $conflicts
}

function Get-ExecutionOrder {
    Write-ColorOutput "📋 生成执行顺序..." "Blue"
    
    $sorted = $Script:MigrationFiles | Sort-Object Version
    
    Write-ColorOutput "✅ 执行顺序已生成，共 $($sorted.Count) 个脚本" "Green"
    
    return $sorted
}

function Test-TableDependencies {
    Write-ColorOutput "🔗 检查表依赖关系..." "Blue"
    
    $createdTables = @()
    $dependencyIssues = @()
    $sortedMigrations = Get-ExecutionOrder
    
    foreach ($migration in $sortedMigrations) {
        # 提取创建的表
        $newTables = Get-CreatedTables -Content $migration.Content
        $createdTables += $newTables
        
        # 检查外键依赖
        $foreignKeys = Get-ForeignKeyReferences -Content $migration.Content
        foreach ($fk in $foreignKeys) {
            if ($fk -and $fk -notin $createdTables) {
                $issue = @{
                    Migration = $migration.Filename
                    ReferencedTable = $fk
                    Issue = "引用的表 $fk 在当前脚本执行前未创建"
                }
                $dependencyIssues += $issue
                $Script:Errors += "❌ 依赖问题: $($migration.Filename) 引用了未创建的表 $fk"
            }
        }
    }
    
    if ($dependencyIssues.Count -eq 0) {
        Write-ColorOutput "✅ 未发现表依赖问题" "Green"
    } else {
        Write-ColorOutput "⚠️  发现 $($dependencyIssues.Count) 个表依赖问题" "Yellow"
    }
    
    return @{
        Issues = $dependencyIssues
        CreatedTables = $createdTables | Sort-Object -Unique
        TotalTables = ($createdTables | Sort-Object -Unique).Count
    }
}

function Get-CreatedTables {
    param([string]$Content)
    
    $tables = @()
    
    # 匹配 CREATE TABLE 和 DROP TABLE 语句
    $createPattern = 'CREATE\s+TABLE\s+(?:IF\s+NOT\s+EXISTS\s+)?([a-zA-Z_][a-zA-Z0-9_]*)'
    $dropPattern = 'DROP\s+TABLE\s+IF\s+EXISTS\s+([a-zA-Z_][a-zA-Z0-9_]*)'
    
    $createMatches = [regex]::Matches($Content, $createPattern, [System.Text.RegularExpressions.RegexOptions]::IgnoreCase)
    $dropMatches = [regex]::Matches($Content, $dropPattern, [System.Text.RegularExpressions.RegexOptions]::IgnoreCase)
    
    foreach ($match in $createMatches) {
        $tables += $match.Groups[1].Value
    }
    
    foreach ($match in $dropMatches) {
        $tables += $match.Groups[1].Value
    }
    
    return $tables
}

function Get-ForeignKeyReferences {
    param([string]$Content)
    
    $references = @()
    
    # 匹配 FOREIGN KEY REFERENCES 语句
    $fkPattern = 'CONSTRAINT\s+\w+\s+FOREIGN\s+KEY\s*\([^)]+\)\s+REFERENCES\s+([a-zA-Z_][a-zA-Z0-9_]*)'
    $matches = [regex]::Matches($Content, $fkPattern, [System.Text.RegularExpressions.RegexOptions]::IgnoreCase)
    
    foreach ($match in $matches) {
        $references += $match.Groups[1].Value
    }
    
    return $references
}

function Test-SqlSyntax {
    Write-ColorOutput "🔍 检查SQL语法..." "Blue"
    
    $syntaxErrors = @()
    
    foreach ($migration in $Script:MigrationFiles) {
        $errors = Test-SingleFileSyntax -Content $migration.Content -Filename $migration.Filename
        $syntaxErrors += $errors
    }
    
    if ($syntaxErrors.Count -eq 0) {
        Write-ColorOutput "✅ 未发现SQL语法错误" "Green"
    } else {
        Write-ColorOutput "⚠️  发现 $($syntaxErrors.Count) 个语法问题" "Yellow"
    }
    
    return $syntaxErrors
}

function Test-SingleFileSyntax {
    param(
        [string]$Content,
        [string]$Filename
    )
    
    $errors = @()
    
    # 检查外键检查配对
    $fkDisableCount = ($Content | Select-String -Pattern 'SET\s+FOREIGN_KEY_CHECKS\s*=\s*0' -AllMatches).Matches.Count
    $fkEnableCount = ($Content | Select-String -Pattern 'SET\s+FOREIGN_KEY_CHECKS\s*=\s*1' -AllMatches).Matches.Count
    
    if ($fkDisableCount -gt 0 -and $fkEnableCount -eq 0) {
        $errors += @{
            File = $Filename
            Type = "FOREIGN_KEY_CHECKS"
            Message = "禁用了外键检查但未恢复"
        }
    }
    
    # 检查常见最佳实践
    if ($Content -match 'CREATE TABLE' -and $Content -notmatch 'ENGINE=InnoDB') {
        $Script:Warnings += "⚠️  $Filename`: 建议显式指定存储引擎"
    }
    
    if ($Content -match 'CREATE TABLE' -and $Content -notmatch 'utf8mb4') {
        $Script:Warnings += "⚠️  $Filename`: 建议使用utf8mb4字符集"
    }
    
    return $errors
}

function Get-ModuleSummary {
    $moduleStats = @{}
    
    foreach ($migration in $Script:MigrationFiles) {
        $module = $migration.Module
        if (-not $moduleStats.ContainsKey($module)) {
            $moduleStats[$module] = @{
                Count = 0
                Versions = @()
            }
        }
        $moduleStats[$module].Count++
        $moduleStats[$module].Versions += $migration.Version
    }
    
    # 排序版本号
    foreach ($module in $moduleStats.Keys) {
        $moduleStats[$module].Versions = $moduleStats[$module].Versions | Sort-Object
    }
    
    return $moduleStats
}

function Show-ValidationSummary {
    Write-Host ""
    Write-Host ("=" * 60)
    Write-ColorOutput "📋 数据库迁移脚本验证摘要" "Cyan"
    Write-Host ("=" * 60)
    
    Write-Host "📁 总文件数: $($Script:MigrationFiles.Count)"
    Write-ColorOutput "❌ 错误数: $($Script:Errors.Count)" $(if ($Script:Errors.Count -eq 0) { "Green" } else { "Red" })
    Write-ColorOutput "⚠️  警告数: $($Script:Warnings.Count)" $(if ($Script:Warnings.Count -eq 0) { "Green" } else { "Yellow" })
    Write-ColorOutput "🔄 版本冲突: $($Script:VersionConflicts.Count)" $(if ($Script:VersionConflicts.Count -eq 0) { "Green" } else { "Red" })
    
    if ($Script:Errors.Count -gt 0) {
        Write-Host ""
        Write-ColorOutput "❌ 错误列表:" "Red"
        foreach ($error in $Script:Errors) {
            Write-Host "   $error"
        }
    }
    
    if ($Script:Warnings.Count -gt 0 -and $ShowDetails) {
        Write-Host ""
        Write-ColorOutput "⚠️  警告列表:" "Yellow"
        $displayWarnings = if ($Script:Warnings.Count -gt 5) { $Script:Warnings[0..4] } else { $Script:Warnings }
        foreach ($warning in $displayWarnings) {
            Write-Host "   $warning"
        }
        if ($Script:Warnings.Count -gt 5) {
            Write-Host "   ... 还有 $($Script:Warnings.Count - 5) 个警告"
        }
    }
    
    Write-Host ""
    Write-ColorOutput "📊 模块分布:" "Blue"
    $moduleSummary = Get-ModuleSummary
    foreach ($module in ($moduleSummary.Keys | Sort-Object)) {
        $stats = $moduleSummary[$module]
        $versionRange = if ($stats.Versions.Count -gt 0) { 
            "V$($stats.Versions[0])-V$($stats.Versions[-1])" 
        } else { 
            "无" 
        }
        Write-Host "   $module`: $($stats.Count) 个文件, 版本范围: $versionRange"
    }
    
    Write-Host ""
    if ($Script:Errors.Count -eq 0) {
        Write-ColorOutput "🎉 验证通过！迁移脚本可以安全执行。" "Green"
    } else {
        Write-ColorOutput "⚠️  发现问题，建议修复后再执行迁移。" "Red"
    }
}

function Export-ValidationReport {
    $report = @{
        Summary = @{
            TotalFiles = $Script:MigrationFiles.Count
            VersionConflicts = $Script:VersionConflicts.Count
            Errors = $Script:Errors.Count
            Warnings = $Script:Warnings.Count
        }
        Files = $Script:MigrationFiles
        ExecutionOrder = (Get-ExecutionOrder | ForEach-Object { $_.Filename })
        VersionConflicts = $Script:VersionConflicts
        Errors = $Script:Errors
        Warnings = $Script:Warnings
        Modules = Get-ModuleSummary
    }
    
    $jsonReport = $report | ConvertTo-Json -Depth 10
    $reportPath = "migration-validation-report.json"
    $jsonReport | Out-File -FilePath $reportPath -Encoding UTF8
    
    Write-ColorOutput "📄 详细报告已保存到: $reportPath" "Green"
}

# 主执行流程
function Main {
    Write-ColorOutput "🚀 数据库迁移脚本验证工具启动 (PowerShell版本)" "Cyan"
    Write-ColorOutput "📂 扫描路径: $(Resolve-Path $BasePath)" "Blue"
    
    # 扫描文件
    $files = Get-MigrationFiles -Path $BasePath
    $Script:MigrationFiles = @()
    
    foreach ($file in $files) {
        $parsed = Parse-MigrationFile -File $file
        if ($parsed) {
            $Script:MigrationFiles += $parsed
        }
    }
    
    # 执行验证
    Test-VersionConflicts
    $dependencies = Test-TableDependencies
    Test-SqlSyntax
    
    # 显示结果
    Show-ValidationSummary
    
    # 导出报告
    if ($ExportJson) {
        Export-ValidationReport
    }
    
    # 返回状态
    return $Script:Errors.Count
}

# 执行主函数
$exitCode = Main
exit $exitCode 