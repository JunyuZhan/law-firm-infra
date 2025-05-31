package com.lawfirm.auth.util;

import com.lawfirm.model.auth.constant.PermissionConstants;
import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限验证工具类
 * <p>
 * 用于验证权限常量与数据库权限表的一致性，确保所有定义的权限码在数据库中都有对应记录。
 * 可以通过配置 law-firm.permission.validate=true 来启用权限验证。
 * </p>
 * 
 * @author law-firm-system
 * @since 1.0.0
 */
@Slf4j
@Component("permissionValidator")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "law-firm.permission.validate", havingValue = "true")
public class PermissionValidator implements CommandLineRunner {

    private final PermissionService permissionService;

    @Override
    public void run(String... args) throws Exception {
        log.info("开始验证权限常量与数据库权限表的一致性...");
        
        try {
            // 获取所有权限常量
            List<String> constantPermissions = getAllPermissionConstants();
            log.info("从PermissionConstants中获取到{}个权限常量", constantPermissions.size());
            
            // 获取数据库中的所有权限
            List<Permission> dbPermissions = permissionService.listAllPermissions()
                .stream()
                .map(vo -> {
                    Permission permission = new Permission();
                    permission.setCode(vo.getCode());
                    return permission;
                })
                .collect(Collectors.toList());
            
            Set<String> dbPermissionCodes = dbPermissions.stream()
                .map(Permission::getCode)
                .collect(Collectors.toSet());
            
            log.info("从数据库中获取到{}个权限记录", dbPermissionCodes.size());
            
            // 检查缺失的权限
            List<String> missingInDb = constantPermissions.stream()
                .filter(code -> !dbPermissionCodes.contains(code))
                .collect(Collectors.toList());
            
            // 检查多余的权限
            List<String> extraInDb = dbPermissionCodes.stream()
                .filter(code -> !constantPermissions.contains(code))
                .collect(Collectors.toList());
            
            // 输出验证结果
            if (missingInDb.isEmpty() && extraInDb.isEmpty()) {
                log.info("✅ 权限验证通过！所有权限常量与数据库记录完全一致");
            } else {
                log.warn("⚠️ 权限验证发现不一致问题：");
                
                if (!missingInDb.isEmpty()) {
                    log.warn("❌ 以下权限常量在数据库中缺失({})个):", missingInDb.size());
                    missingInDb.forEach(code -> log.warn("  - {}", code));
                }
                
                if (!extraInDb.isEmpty()) {
                    log.warn("❌ 以下权限在数据库中存在但不在常量中({}个):", extraInDb.size());
                    extraInDb.forEach(code -> log.warn("  - {}", code));
                }
                
                // 生成修复建议
                generateFixSuggestions(missingInDb, extraInDb);
            }
            
        } catch (Exception e) {
            log.error("权限验证过程中发生异常", e);
        }
    }

    /**
     * 通过反射获取所有权限常量
     */
    private List<String> getAllPermissionConstants() throws IllegalAccessException {
        List<String> permissions = new ArrayList<>();
        
        Field[] fields = PermissionConstants.class.getDeclaredFields();
        for (Field field : fields) {
            // 只处理String类型的静态final字段
            if (field.getType() == String.class && 
                java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
                
                field.setAccessible(true);
                String value = (String) field.get(null);
                if (value != null && !value.trim().isEmpty()) {
                    permissions.add(value);
                }
            }
        }
        
        return permissions;
    }

    /**
     * 生成修复建议
     */
    private void generateFixSuggestions(List<String> missingInDb, List<String> extraInDb) {
        log.info("\n🔧 修复建议:");
        
        if (!missingInDb.isEmpty()) {
            log.info("\n1. 添加缺失的权限到数据库:");
            log.info("   可以执行以下SQL语句:");
            missingInDb.forEach(code -> {
                String[] parts = code.split(":");
                String module = parts.length > 0 ? parts[0] : "unknown";
                String operation = parts.length > 1 ? parts[1] : "unknown";
                String name = generatePermissionName(module, operation);
                
                log.info("   INSERT INTO sys_permission (permission_code, permission_name, permission_type, status) VALUES ('{}', '{}', 'BUTTON', 1);", 
                        code, name);
            });
        }
        
        if (!extraInDb.isEmpty()) {
            log.info("\n2. 处理多余的权限:");
            log.info("   以下权限可能需要添加到PermissionConstants中，或者从数据库中删除:");
            extraInDb.forEach(code -> log.info("   - {}", code));
        }
        
        log.info("\n3. 建议在修复后重新运行权限验证确保一致性。");
    }

    /**
     * 根据模块和操作生成权限名称
     */
    private String generatePermissionName(String module, String operation) {
        String moduleName = getModuleName(module);
        String operationName = getOperationName(operation);
        return moduleName + operationName;
    }

    /**
     * 获取模块中文名称
     */
    private String getModuleName(String module) {
        switch (module.toLowerCase()) {
            case "sys": return "系统管理";
            case "case": return "案件管理";
            case "client": return "客户管理";
            case "contract": return "合同管理";
            case "finance": return "财务管理";
            case "personnel": return "人事管理";
            case "document": return "文档管理";
            case "evidence": return "证据管理";
            case "schedule": return "日程管理";
            case "task": return "任务管理";
            case "knowledge": return "知识库";
            case "system": return "系统配置";
            default: return module;
        }
    }

    /**
     * 获取操作中文名称
     */
    private String getOperationName(String operation) {
        switch (operation.toLowerCase()) {
            case "create": return "新增";
            case "edit": case "update": return "编辑";
            case "delete": return "删除";
            case "view": case "list": return "查看";
            case "approve": return "审批";
            case "export": return "导出";
            case "import": return "导入";
            case "archive": return "归档";
            case "assign": return "分配";
            case "transfer": return "转移";
            case "comment": return "评论";
            case "pay": return "支付";
            case "confirm": return "确认";
            case "cancel": return "取消";
            default: return operation;
        }
    }
}