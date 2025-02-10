package com.lawfirm.document.service.impl;

import com.lawfirm.model.document.repository.DocumentLockRepository;
import com.lawfirm.model.document.entity.DocumentLock;
import com.lawfirm.model.document.service.DocumentEditSessionService;
import com.lawfirm.model.document.vo.DocumentEditSessionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文档编辑会话服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentEditSessionServiceImpl implements DocumentEditSessionService {

    private final DocumentLockRepository documentLockRepository;
    private final Map<String, DocumentEditSessionVO> activeSessions = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public DocumentEditSessionVO createSession(Long documentId, Long userId, String sessionId) {
        // 检查是否已有活动会话
        if (hasActiveSession(documentId)) {
            throw new IllegalStateException("文档已有活动编辑会话");
        }

        // 创建锁定记录
        DocumentLock lock = new DocumentLock()
                .setDocumentId(documentId)
                .setUserId(userId)
                .setSessionId(sessionId)
                .setLockTime(LocalDateTime.now())
                .setExpireTime(LocalDateTime.now().plusHours(1))
                .setCreateTime(LocalDateTime.now())
                .setUpdateTime(LocalDateTime.now())
                .setIsDeleted(false);
        
        documentLockRepository.save(lock);

        // 创建会话记录
        DocumentEditSessionVO session = new DocumentEditSessionVO()
                .setDocumentId(documentId)
                .setUserId(userId)
                .setSessionId(sessionId)
                .setStartTime(LocalDateTime.now())
                .setLastActiveTime(LocalDateTime.now());

        activeSessions.put(sessionId, session);
        return session;
    }

    @Override
    @Transactional
    public void closeSession(String sessionId) {
        DocumentEditSessionVO session = activeSessions.remove(sessionId);
        if (session != null) {
            // 释放文档锁
            documentLockRepository.deleteBySessionId(sessionId);
            log.info("关闭编辑会话：sessionId={}, documentId={}", sessionId, session.getDocumentId());
        }
    }

    @Override
    public void updateSessionActivity(String sessionId) {
        DocumentEditSessionVO session = activeSessions.get(sessionId);
        if (session != null) {
            session.setLastActiveTime(LocalDateTime.now());
            // 更新锁定记录的过期时间
            documentLockRepository.updateExpireTime(sessionId, LocalDateTime.now().plusHours(1));
        }
    }

    @Override
    public boolean isSessionActive(String sessionId) {
        DocumentEditSessionVO session = activeSessions.get(sessionId);
        return session != null && !isSessionExpired(session);
    }

    @Override
    public List<DocumentEditSessionVO> getActiveSessions(Long documentId) {
        return activeSessions.values().stream()
                .filter(session -> session.getDocumentId().equals(documentId))
                .filter(session -> !isSessionExpired(session))
                .toList();
    }

    @Override
    public void cleanExpiredSessions() {
        LocalDateTime now = LocalDateTime.now();
        activeSessions.entrySet().removeIf(entry -> {
            DocumentEditSessionVO session = entry.getValue();
            if (isSessionExpired(session)) {
                // 清理过期的锁定记录
                documentLockRepository.deleteBySessionId(session.getSessionId());
                log.info("清理过期会话：sessionId={}, documentId={}", 
                        session.getSessionId(), session.getDocumentId());
                return true;
            }
            return false;
        });
    }

    private boolean hasActiveSession(Long documentId) {
        return activeSessions.values().stream()
                .anyMatch(session -> session.getDocumentId().equals(documentId) 
                        && !isSessionExpired(session));
    }

    private boolean isSessionExpired(DocumentEditSessionVO session) {
        return session.getLastActiveTime().plusHours(1).isBefore(LocalDateTime.now());
    }
} 