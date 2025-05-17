package com.lawfirm.core.workflow.service;

import com.lawfirm.model.workflow.service.ProcessPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;

/**
 * 流程权限服务实现类
 *
 * @author JunyuZhan
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "law-firm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class ProcessPermissionServiceImpl implements ProcessPermissionService {

    // 内存模拟权限存储
    private static final ConcurrentHashMap<Long, Map<String, Object>> PERMISSION_STORE = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(1);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPermission(Map<String, Object> permission) {
        Long id = ID_GENERATOR.getAndIncrement();
        permission.put("id", id);
        PERMISSION_STORE.put(id, new ConcurrentHashMap<>(permission));
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchCreatePermissions(List<Map<String, Object>> permissions) {
        int count = 0;
        for (Map<String, Object> perm : permissions) {
            createPermission(perm);
            count++;
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePermission(Map<String, Object> permission) {
        Object idObj = permission.get("id");
        if (idObj == null) return false;
        Long id = Long.valueOf(idObj.toString());
        if (!PERMISSION_STORE.containsKey(id)) return false;
        PERMISSION_STORE.put(id, new ConcurrentHashMap<>(permission));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePermission(Long permissionId) {
        return PERMISSION_STORE.remove(permissionId) != null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDeletePermissions(List<Long> permissionIds) {
        int count = 0;
        for (Long id : permissionIds) {
            if (PERMISSION_STORE.remove(id) != null) count++;
        }
        return count;
    }

    @Override
    public Map<String, Object> getPermissionById(Long permissionId) {
        return PERMISSION_STORE.get(permissionId);
    }

    @Override
    public Map<String, Object> getPermissionPage(int pageNum, int pageSize, String processDefinitionKey,
                                               Integer permissionType, Integer operationType,
                                               Integer targetType, Long targetId) {
        // 简单分页和条件过滤
        List<Map<String, Object>> all = new ArrayList<>(PERMISSION_STORE.values());
        List<Map<String, Object>> filtered = new ArrayList<>();
        for (Map<String, Object> perm : all) {
            boolean match = true;
            if (processDefinitionKey != null && !processDefinitionKey.equals(perm.get("processDefinitionKey"))) match = false;
            if (permissionType != null && !permissionType.equals(perm.get("permissionType"))) match = false;
            if (operationType != null && !operationType.equals(perm.get("operationType"))) match = false;
            if (targetType != null && !targetType.equals(perm.get("targetType"))) match = false;
            if (targetId != null && !targetId.equals(perm.get("targetId"))) match = false;
            if (match) filtered.add(perm);
        }
        int from = Math.max(0, (pageNum-1)*pageSize);
        int to = Math.min(filtered.size(), from+pageSize);
        List<Map<String, Object>> page = from < to ? filtered.subList(from, to) : new ArrayList<>();
        return Map.of("total", filtered.size(), "records", page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long assignUserDefinitionPermission(Long userId, String processDefinitionKey, Integer operationType, Integer permissionPolicy) {
        Map<String, Object> perm = new ConcurrentHashMap<>();
        perm.put("targetType", 1); // 1-用户
        perm.put("targetId", userId);
        perm.put("processDefinitionKey", processDefinitionKey);
        perm.put("operationType", operationType);
        perm.put("permissionPolicy", permissionPolicy);
        return createPermission(perm);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long assignRoleDefinitionPermission(Long roleId, String processDefinitionKey, Integer operationType, Integer permissionPolicy) {
        Map<String, Object> perm = new ConcurrentHashMap<>();
        perm.put("targetType", 2); // 2-角色
        perm.put("targetId", roleId);
        perm.put("processDefinitionKey", processDefinitionKey);
        perm.put("operationType", operationType);
        perm.put("permissionPolicy", permissionPolicy);
        return createPermission(perm);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long assignDeptDefinitionPermission(Long deptId, String processDefinitionKey, Integer operationType, Integer permissionPolicy) {
        Map<String, Object> perm = new ConcurrentHashMap<>();
        perm.put("targetType", 3); // 3-部门
        perm.put("targetId", deptId);
        perm.put("processDefinitionKey", processDefinitionKey);
        perm.put("operationType", operationType);
        perm.put("permissionPolicy", permissionPolicy);
        return createPermission(perm);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int assignStartPermission(String processDefinitionKey, Integer targetType, List<Long> targetIds, Integer permissionPolicy) {
        int count = 0;
        for (Long targetId : targetIds) {
            Map<String, Object> perm = new ConcurrentHashMap<>();
            perm.put("targetType", targetType);
            perm.put("targetId", targetId);
            perm.put("processDefinitionKey", processDefinitionKey);
            perm.put("operationType", 1); // 1-启动
            perm.put("permissionPolicy", permissionPolicy);
            createPermission(perm);
            count++;
        }
        return count;
    }

    @Override
    public boolean checkStartProcessPermission(Long userId, String processDefinitionKey) {
        // 简单校验：只要有targetType=1且targetId=userId的启动权限即可
        for (Map<String, Object> perm : PERMISSION_STORE.values()) {
            if (Integer.valueOf(1).equals(perm.get("targetType")) &&
                userId.equals(perm.get("targetId")) &&
                processDefinitionKey.equals(perm.get("processDefinitionKey")) &&
                Integer.valueOf(1).equals(perm.get("operationType"))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkViewProcessPermission(Long userId, String processDefinitionKey) {
        // 简单校验：只要有targetType=1且targetId=userId的查看权限即可
        for (Map<String, Object> perm : PERMISSION_STORE.values()) {
            if (Integer.valueOf(1).equals(perm.get("targetType")) &&
                userId.equals(perm.get("targetId")) &&
                processDefinitionKey.equals(perm.get("processDefinitionKey")) &&
                Integer.valueOf(2).equals(perm.get("operationType"))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkCancelProcessPermission(Long userId, String processDefinitionKey) {
        // 简单校验：只要有targetType=1且targetId=userId的取消权限即可
        for (Map<String, Object> perm : PERMISSION_STORE.values()) {
            if (Integer.valueOf(1).equals(perm.get("targetType")) &&
                userId.equals(perm.get("targetId")) &&
                processDefinitionKey.equals(perm.get("processDefinitionKey")) &&
                Integer.valueOf(3).equals(perm.get("operationType"))) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePermissionsByProcessDefinitionKey(String processDefinitionKey) {
        PERMISSION_STORE.entrySet().removeIf(e -> processDefinitionKey.equals(e.getValue().get("processDefinitionKey")));
    }
} 
