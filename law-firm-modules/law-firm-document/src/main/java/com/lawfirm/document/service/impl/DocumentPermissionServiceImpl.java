package com.lawfirm.modules.document.service.impl;

import com.lawfirm.modules.document.entity.DocumentPermission;
import com.lawfirm.modules.document.enums.DocumentPermissionEnum;
import com.lawfirm.modules.document.enums.DocumentPermissionTargetEnum;
import com.lawfirm.modules.document.repository.DocumentPermissionRepository;
import com.lawfirm.modules.document.service.DocumentPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档权限服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentPermissionServiceImpl implements DocumentPermissionService {

    private final DocumentPermissionRepository documentPermissionRepository;

    @Override
    @Transactional
    public DocumentPermission grantPermission(Long documentId, DocumentPermissionTargetEnum targetType, 
            Long targetId, DocumentPermissionEnum permission) {
        // 检查是否已存在相同权限
        if (hasPermission(documentId, targetType, targetId, permission)) {
            throw new IllegalStateException("权限已存在");
        }

        // 创建权限记录
        DocumentPermission documentPermission = new DocumentPermission()
                .setDocumentId(documentId)
                .setTargetType(targetType)
                .setTargetId(targetId)
                .setPermission(permission)
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setIsDeleted(false);

        return documentPermissionRepository.save(documentPermission);
    }

    @Override
    @Transactional
    public void revokePermission(Long permissionId) {
        DocumentPermission permission = getPermission(permissionId);
        if (permission == null) {
            return;
        }

        // 逻辑删除
        permission.setIsDeleted(true)
                .setDeleteTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now());
        documentPermissionRepository.save(permission);
    }

    @Override
    @Transactional
    public List<DocumentPermission> grantPermissions(Long documentId, DocumentPermissionTargetEnum targetType, 
            List<Long> targetIds, DocumentPermissionEnum permission) {
        return targetIds.stream()
                .map(targetId -> {
                    try {
                        return grantPermission(documentId, targetType, targetId, permission);
                    } catch (IllegalStateException e) {
                        log.warn("权限已存在：documentId={}, targetType={}, targetId={}, permission={}", 
                                documentId, targetType, targetId, permission);
                        return null;
                    }
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void revokePermissions(List<Long> permissionIds) {
        permissionIds.forEach(this::revokePermission);
    }

    @Override
    public List<DocumentPermission> getDocumentPermissions(Long documentId) {
        return documentPermissionRepository.findByDocumentId(documentId).stream()
                .filter(p -> !Boolean.TRUE.equals(p.getIsDeleted()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentPermission> getTargetPermissions(DocumentPermissionTargetEnum targetType, Long targetId) {
        return documentPermissionRepository.findByTargetTypeAndTargetId(targetType, targetId).stream()
                .filter(p -> !Boolean.TRUE.equals(p.getIsDeleted()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasPermission(Long documentId, DocumentPermissionTargetEnum targetType, 
            Long targetId, DocumentPermissionEnum permission) {
        return documentPermissionRepository.existsByDocumentIdAndTargetTypeAndTargetIdAndPermission(
                documentId, targetType, targetId, permission);
    }

    @Override
    public DocumentPermission getPermission(Long permissionId) {
        return documentPermissionRepository.findById(permissionId)
                .filter(p -> !Boolean.TRUE.equals(p.getIsDeleted()))
                .orElse(null);
    }

    @Override
    public Page<DocumentPermission> listPermissions(Pageable pageable) {
        return documentPermissionRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public DocumentPermission updatePermission(DocumentPermission permission) {
        DocumentPermission existingPermission = getPermission(permission.getId());
        if (existingPermission == null) {
            throw new IllegalArgumentException("权限不存在");
        }

        // 更新基本信息
        permission.setUpdateTime(LocalDateTime.now());
        return documentPermissionRepository.save(permission);
    }

    @Override
    @Transactional
    public void clearDocumentPermissions(Long documentId) {
        documentPermissionRepository.deleteByDocumentId(documentId);
    }

    @Override
    @Transactional
    public void clearTargetPermissions(DocumentPermissionTargetEnum targetType, Long targetId) {
        documentPermissionRepository.deleteByTargetTypeAndTargetId(targetType, targetId);
    }
} 