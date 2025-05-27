package com.lawfirm.auth.config;

import com.lawfirm.model.auth.constant.PermissionConstants;
import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 启动时自动同步权限点常量到数据库
 */
@Slf4j
@Component("permissionSyncConfig")
public class PermissionSyncConfig implements ApplicationRunner {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void run(ApplicationArguments args) {
        try {
            // 1. 反射获取 PermissionConstants 所有静态字段
            Class<?> clazz = PermissionConstants.class;
            Field[] fields = clazz.getFields();
            Set<String> codeSet = new HashSet<>();
            for (Field field : fields) {
                if (field.getType() == String.class) {
                    codeSet.add((String) field.get(null));
                }
            }

            // 2. 查询数据库所有权限点
            List<Permission> dbPermissions = permissionMapper.selectAll();
            Set<String> dbCodeSet = new HashSet<>();
            for (Permission p : dbPermissions) {
                dbCodeSet.add(p.getCode());
            }

            // 3. 自动插入缺失的权限点
            int insertCount = 0;
            for (String code : codeSet) {
                if (!dbCodeSet.contains(code)) {
                    Permission permission = new Permission();
                    permission.setCode(code);
                    permission.setName(code); // 可根据需要设置更友好的名称
                    permission.setType(2); // 2=API权限
                    permission.setStatus(0); // 正常
                    permission.setSort(0); // 设置排序
                    permission.setDeleted(0); // 设置未删除
                    permission.preInsert(); // 调用预插入方法设置时间字段
                    permissionMapper.insert(permission);
                    insertCount++;
                    log.info("自动注册权限点：{}", code);
                }
            }

            // 4. 可选：提示数据库中多余的权限点
            for (String dbCode : dbCodeSet) {
                if (!codeSet.contains(dbCode)) {
                    log.warn("数据库中存在未定义的权限点：{}", dbCode);
                }
            }

            log.info("权限点自动同步完成，新增 {} 个权限点。", insertCount);
        } catch (Exception e) {
            log.error("权限点自动同步失败", e);
        }
    }
} 