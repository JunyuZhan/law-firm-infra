package com.lawfirm.document.manager.editor.impl;

import com.lawfirm.common.cache.service.CacheService;
import com.lawfirm.common.core.utils.SecurityUtils;
import com.lawfirm.core.audit.service.AuditService;
import com.lawfirm.document.manager.editor.DocumentEditorManager;
import com.lawfirm.model.document.dto.document.DocumentEditDTO;
import com.lawfirm.model.document.vo.DocumentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 文档编辑器抽象基类
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractDocumentEditor implements DocumentEditorManager {

    private final CacheService cacheService;
    private final AuditService auditService;
    private final SimpMessagingTemplate messagingTemplate;

    // 锁定超时时间（分钟）
    protected static final long LOCK_TIMEOUT = 30;
    // 自动保存间隔（秒）
    protected static final long AUTO_SAVE_INTERVAL = 60;
    // WebSocket主题前缀
    protected static final String WS_TOPIC_PREFIX = "/topic/document/";

    @Override
    public boolean lockDocument(Long documentId, Long userId) {
        String lockKey = getLockKey(documentId);
        // 检查是否已被锁定
        Long lockedBy = cacheService.get(lockKey);
        if (lockedBy != null && !lockedBy.equals(userId)) {
            return false;
        }
        // 设置锁定
        return cacheService.setIfAbsent(lockKey, userId, LOCK_TIMEOUT, TimeUnit.MINUTES);
    }

    @Override
    public boolean unlockDocument(Long documentId, Long userId) {
        String lockKey = getLockKey(documentId);
        Long lockedBy = cacheService.get(lockKey);
        if (lockedBy != null && lockedBy.equals(userId)) {
            cacheService.delete(lockKey);
            return true;
        }
        return false;
    }

    @Override
    public Long checkLockStatus(Long documentId) {
        return cacheService.get(getLockKey(documentId));
    }

    @Override
    public void autoSave(Long documentId, InputStream content, Long userId) {
        // 记录审计日志
        auditService.recordOperation(
            "DOCUMENT",
            documentId.toString(),
            "AUTO_SAVE",
            userId,
            "自动保存文档"
        );
        // 保存内容
        saveContent(documentId, content, userId);
    }

    @Override
    public void joinCollaboration(Long documentId, Long userId) {
        String collaborationKey = getCollaborationKey(documentId);
        cacheService.sAdd(collaborationKey, userId);
        // 广播加入消息
        broadcastCollaborationEvent(documentId, userId, "JOIN");
    }

    @Override
    public void leaveCollaboration(Long documentId, Long userId) {
        String collaborationKey = getCollaborationKey(documentId);
        cacheService.sRemove(collaborationKey, userId);
        // 广播离开消息
        broadcastCollaborationEvent(documentId, userId, "LEAVE");
    }

    @Override
    public List<Long> getCurrentEditors(Long documentId) {
        String collaborationKey = getCollaborationKey(documentId);
        return cacheService.sMembers(collaborationKey);
    }

    @Override
    public void broadcastEdit(Long documentId, DocumentEditDTO editDTO) {
        String topic = WS_TOPIC_PREFIX + documentId;
        messagingTemplate.convertAndSend(topic, editDTO);
    }

    /**
     * 获取文档锁定的缓存键
     */
    protected String getLockKey(Long documentId) {
        return "document:lock:" + documentId;
    }

    /**
     * 获取协同编辑的缓存键
     */
    protected String getCollaborationKey(Long documentId) {
        return "document:collaboration:" + documentId;
    }

    /**
     * 广播协同编辑事件
     */
    protected void broadcastCollaborationEvent(Long documentId, Long userId, String event) {
        DocumentEditDTO editDTO = new DocumentEditDTO()
            .setOperationType(event)
            .setUserId(userId)
            .setTimestamp(System.currentTimeMillis());
        broadcastEdit(documentId, editDTO);
    }

    /**
     * 检查编辑权限
     */
    protected boolean checkEditPermission(Long documentId, Long userId) {
        // TODO: 实现权限检查逻辑
        return true;
    }
} 