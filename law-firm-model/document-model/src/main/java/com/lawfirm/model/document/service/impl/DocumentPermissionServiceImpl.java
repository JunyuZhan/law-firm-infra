package com.lawfirm.model.document.service.impl;

import com.lawfirm.model.document.dto.permission.PermissionCreateDTO;
import com.lawfirm.model.document.dto.permission.PermissionUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentPermission;
import com.lawfirm.model.document.enums.DocumentOperationEnum;
import com.lawfirm.model.document.service.DocumentPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文档权限服务实现类
 */
@Service
public class DocumentPermissionServiceImpl implements DocumentPermissionService {

    // 使用内存存储，实际项目中应该使用数据库
    private final Map<Long, DocumentPermission> permissionMap = new ConcurrentHashMap<>();
    private final Map<Long, List<DocumentPermission>> documentPermissionsMap = new ConcurrentHashMap<>();
    private final Map<Long, List<DocumentPermission>> userPermissionsMap = new ConcurrentHashMap<>();
    private final Map<String, Map<Long, Map<Long, String>>> businessPermissionsMap = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public DocumentPermission createPermission(PermissionCreateDTO dto) {
        DocumentPermission permission = new DocumentPermission();
        permission.setId(generateId());
        permission.setDocumentId(dto.getDocumentId());
        permission.setSubjectId(dto.getSubjectId());
        permission.setSubjectType(dto.getSubjectType());
        permission.setPermissionType(dto.getPermissionType().name());
        permission.setIsAllowed(dto.getIsAllowed());
        permission.setPermissionSource(dto.getPermissionSource());
        permission.setExpireTime(dto.getExpireTime());
        permission.setIsEnabled(dto.getIsEnabled());
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        
        permissionMap.put(permission.getId(), permission);
        
        // 更新文档权限映射
        documentPermissionsMap.computeIfAbsent(dto.getDocumentId(), k -> new ArrayList<>())
                .add(permission);
        
        // 更新用户权限映射
        userPermissionsMap.computeIfAbsent(dto.getSubjectId(), k -> new ArrayList<>())
                .add(permission);
        
        return permission;
    }

    @Override
    @Transactional
    public DocumentPermission updatePermission(Long id, PermissionUpdateDTO dto) {
        DocumentPermission permission = permissionMap.get(id);
        if (permission == null) {
            throw new IllegalArgumentException("Permission not found: " + id);
        }
        
        if (dto.getDocumentId() != null) {
            permission.setDocumentId(dto.getDocumentId());
        }
        if (dto.getSubjectId() != null) {
            permission.setSubjectId(dto.getSubjectId());
        }
        if (dto.getSubjectType() != null) {
            permission.setSubjectType(dto.getSubjectType());
        }
        if (dto.getPermissionType() != null) {
            permission.setPermissionType(dto.getPermissionType().name());
        }
        if (dto.getIsAllowed() != null) {
            permission.setIsAllowed(dto.getIsAllowed());
        }
        if (dto.getPermissionSource() != null) {
            permission.setPermissionSource(dto.getPermissionSource());
        }
        if (dto.getExpireTime() != null) {
            permission.setExpireTime(dto.getExpireTime());
        }
        if (dto.getIsEnabled() != null) {
            permission.setIsEnabled(dto.getIsEnabled());
        }
        permission.setUpdateTime(LocalDateTime.now());
        
        return permission;
    }

    @Override
    @Transactional
    public void deletePermission(Long id) {
        DocumentPermission permission = permissionMap.remove(id);
        if (permission != null) {
            // 从文档权限映射中移除
            List<DocumentPermission> documentPermissions = documentPermissionsMap.get(permission.getDocumentId());
            if (documentPermissions != null) {
                documentPermissions.removeIf(p -> p.getId().equals(id));
            }
            
            // 从用户权限映射中移除
            List<DocumentPermission> userPermissions = userPermissionsMap.get(permission.getSubjectId());
            if (userPermissions != null) {
                userPermissions.removeIf(p -> p.getId().equals(id));
            }
        }
    }

    @Override
    public DocumentPermission getPermission(Long id) {
        return permissionMap.get(id);
    }

    @Override
    public List<DocumentPermission> listPermissions() {
        return new ArrayList<>(permissionMap.values());
    }

    @Override
    public List<DocumentPermission> getPermissionsByDocumentId(Long documentId) {
        return documentPermissionsMap.getOrDefault(documentId, new ArrayList<>());
    }

    @Override
    public List<DocumentPermission> getPermissionsByUserId(Long userId) {
        return userPermissionsMap.getOrDefault(userId, new ArrayList<>());
    }

    @Override
    public boolean checkPermission(Long documentId, Long userId, DocumentOperationEnum operation) {
        List<DocumentPermission> permissions = documentPermissionsMap.get(documentId);
        if (permissions == null) {
            return false;
        }
        return permissions.stream()
                .anyMatch(p -> p.getSubjectId().equals(userId) && 
                               p.getPermissionType().equals(operation.name()) && 
                               Boolean.TRUE.equals(p.getIsAllowed()));
    }

    @Override
    @Transactional
    public void grantPermission(Long documentId, Long userId, DocumentOperationEnum operation) {
        PermissionCreateDTO dto = new PermissionCreateDTO();
        dto.setDocumentId(documentId);
        dto.setSubjectId(userId);
        dto.setSubjectType("USER");
        dto.setPermissionType(operation);
        dto.setIsAllowed(true);
        createPermission(dto);
    }

    @Override
    @Transactional
    public void revokePermission(Long documentId, Long userId, DocumentOperationEnum operation) {
        List<DocumentPermission> permissions = documentPermissionsMap.get(documentId);
        if (permissions != null) {
            permissions.stream()
                    .filter(p -> p.getSubjectId().equals(userId) && 
                                p.getPermissionType().equals(operation.name()))
                    .findFirst()
                    .ifPresent(p -> deletePermission(p.getId()));
        }
    }

    @Override
    public boolean existsPermission(Long id) {
        return permissionMap.containsKey(id);
    }

    @Override
    public long countPermissions() {
        return permissionMap.size();
    }

    @Override
    @Transactional
    public void syncBusinessDocumentsPermission(String businessType, Long businessId, 
                                              List<UserPermission> userPermissions) {
        Map<Long, Map<Long, String>> businessMap = businessPermissionsMap
                .computeIfAbsent(businessType, k -> new ConcurrentHashMap<>());
        
        Map<Long, String> userPermissionMap = businessMap
                .computeIfAbsent(businessId, k -> new ConcurrentHashMap<>());
        
        userPermissionMap.clear();
        for (UserPermission up : userPermissions) {
            userPermissionMap.put(up.getUserId(), up.getPermission());
        }
    }

    @Override
    @Transactional
    public void addUserPermission(String businessType, Long businessId, Long userId, String permission) {
        Map<Long, Map<Long, String>> businessMap = businessPermissionsMap
                .computeIfAbsent(businessType, k -> new ConcurrentHashMap<>());
        
        Map<Long, String> userPermissionMap = businessMap
                .computeIfAbsent(businessId, k -> new ConcurrentHashMap<>());
        
        userPermissionMap.put(userId, permission);
    }

    @Override
    @Transactional
    public void removeUserPermission(String businessType, Long businessId, Long userId) {
        Map<Long, Map<Long, String>> businessMap = businessPermissionsMap.get(businessType);
        if (businessMap != null) {
            Map<Long, String> userPermissionMap = businessMap.get(businessId);
            if (userPermissionMap != null) {
                userPermissionMap.remove(userId);
            }
        }
    }

    private Long generateId() {
        return System.currentTimeMillis();
    }
} 