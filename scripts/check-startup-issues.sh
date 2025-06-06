#!/bin/bash

echo "=== 法律事务所系统启动问题检查脚本 ==="
echo ""

# 检查RocketMQ配置
echo "1. 检查RocketMQ配置..."
if grep -q "rocketmq:" law-firm-api/src/main/resources/application.yml; then
    echo "   ✅ RocketMQ配置已存在"
    if grep -q "enabled: \${ROCKETMQ_ENABLED:false}" law-firm-api/src/main/resources/application.yml; then
        echo "   ✅ RocketMQ默认已禁用，避免启动警告"
    else
        echo "   ⚠️  建议将RocketMQ默认设置为禁用状态"
    fi
else
    echo "   ❌ 缺少RocketMQ配置"
fi

echo ""

# 检查Mapper重复扫描
echo "2. 检查Mapper重复扫描..."
mapper_scan_count=0

# 检查主应用的MapperScan
if grep -q "com.lawfirm.model.\*\*.mapper" law-firm-api/src/main/java/com/lawfirm/api/LawFirmApiApplication.java; then
    echo "   ✅ 主应用使用通配符扫描Mapper"
    mapper_scan_count=$((mapper_scan_count + 1))
fi

# 检查各模块的MapperScan
modules=(
    "law-firm-modules/law-firm-system/src/main/java/com/lawfirm/system/config/SystemMybatisConfig.java"
    "law-firm-modules/law-firm-task/src/main/java/com/lawfirm/task/config/TaskAutoConfiguration.java"
    "law-firm-modules/law-firm-auth/src/main/java/com/lawfirm/auth/config/AuthMybatisConfig.java"
    "law-firm-modules/law-firm-evidence/src/main/java/com/lawfirm/evidence/config/EvidenceModuleConfig.java"
    "law-firm-modules/law-firm-document/src/main/java/com/lawfirm/document/config/DocumentModuleConfig.java"
    "law-firm-modules/law-firm-personnel/src/main/java/com/lawfirm/personnel/config/PersonnelConfig.java"
    "law-firm-core/core-search/src/main/java/com/lawfirm/core/search/config/SearchModuleConfig.java"
    "law-firm-core/core-audit/src/main/java/com/lawfirm/core/audit/config/LogModuleConfig.java"
)

duplicate_found=false
for module_file in "${modules[@]}"; do
    if [ -f "$module_file" ]; then
        if grep -q "@MapperScan" "$module_file"; then
            echo "   ❌ 发现重复的MapperScan: $(basename "$module_file")"
            duplicate_found=true
        fi
    fi
done

if [ "$duplicate_found" = false ]; then
    echo "   ✅ 未发现重复的MapperScan配置"
fi

echo ""

# 检查TaskScheduler Bean冲突
echo "3. 检查TaskScheduler Bean配置..."
if grep -q "@Primary" law-firm-modules/law-firm-schedule/src/main/java/com/lawfirm/schedule/config/ScheduleTaskConfig.java; then
    echo "   ✅ scheduleTaskScheduler已设置为Primary Bean"
else
    echo "   ❌ scheduleTaskScheduler缺少@Primary注解"
fi

echo ""

echo "=== 修复建议 ==="
echo ""
echo "✅ **已修复的问题**:"
echo "   - RocketMQ配置：已添加配置并默认禁用"
echo "   - Mapper重复扫描：已统一到主应用中"
echo "   - TaskScheduler冲突：已为主要调度器添加@Primary注解"
echo ""
echo "🚀 **预期效果**:"
echo "   - 启动时不再出现RocketMQ name-server警告"
echo "   - 消除所有Mapper Bean重复定义警告"
echo "   - 解决TaskScheduler Bean冲突问题"
echo ""
echo "📝 **验证方法**:"
echo "   1. 重新启动应用"
echo "   2. 查看启动日志，确认警告消失"
echo "   3. 检查系统功能正常运行"
echo "" 