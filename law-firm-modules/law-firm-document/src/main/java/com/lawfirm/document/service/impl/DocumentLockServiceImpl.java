package com.lawfirm.model.document.service.impl;

import com.lawfirm.model.document.dto.DocumentEditLockDTO;
import com.lawfirm.model.document.entity.DocumentLock;
import com.lawfirm.model.document.repository.DocumentLockRepository;
import com.lawfirm.model.document.service.DocumentLockService;
import com.lawfirm.model.document.vo.DocumentEditVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文档锁定服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentLockServiceImpl implements DocumentLockService {

    private final DocumentLockRepository documentLockRepository;
    
    @Override
    @Transactional
    public DocumentLock lockDocument(DocumentEditLockDTO lockDTO) {
        // 检查文档是否已被锁定
        if (isLocked(lockDTO.getDocumentId())) {
            throw new IllegalStateException("文档已被锁定");
        }
        
        // 创建锁定记录
        DocumentLock lock = new DocumentLock()
                .setDocumentId(lockDTO.getDocumentId())
                .setSessionId(lockDTO.getSessionId())
                .setClientInfo(lockDTO.getClientInfo())
                .setLockScope(lockDTO.getLockScope())
                .setLockTime(LocalDateTime.now())
                .setExpireTime(LocalDateTime.now().plusMinutes(lockDTO.getLockDuration()));
        
        return documentLockRepository.save(lock);
    }
    
    @Override
    @Transactional
    public void unlockDocument(Long documentId, String sessionId) {
        DocumentLock lock = getCurrentLock(documentId);
        if (lock != null && lock.getSessionId().equals(sessionId)) {
            documentLockRepository.delete(lock);
        }
    }
    
    @Override
    @Transactional
    public void forceUnlockDocument(Long documentId) {
        documentLockRepository.deleteByDocumentId(documentId);
    }
    
    @Override
    @Transactional
    public DocumentLock renewLock(Long documentId, String sessionId, Integer duration) {
        DocumentLock lock = getCurrentLock(documentId);
        if (lock != null && lock.getSessionId().equals(sessionId)) {
            lock.setExpireTime(LocalDateTime.now().plusMinutes(duration));
            return documentLockRepository.save(lock);
        }
        throw new IllegalStateException("文档未被当前会话锁定");
    }
    
    @Override
    public DocumentEditVO getLockStatus(Long documentId) {
        DocumentLock lock = getCurrentLock(documentId);
        if (lock == null) {
            return new DocumentEditVO()
                    .setDocumentId(documentId)
                    .setLocked(false)
                    .setLockedByMe(false);
        }
        
        return new DocumentEditVO()
                .setDocumentId(documentId)
                .setUserId(lock.getUserId())
                .setUserName(lock.getUserName())
                .setSessionId(lock.getSessionId())
                .setClientInfo(lock.getClientInfo())
                .setLockScope(lock.getLockScope())
                .setLockTime(lock.getLockTime())
                .setExpireTime(lock.getExpireTime())
                .setLocked(true)
                .setLockedByMe(false); // 需要在Controller层设置
    }
    
    @Override
    public DocumentLock getLock(Long lockId) {
        return documentLockRepository.findById(lockId).orElse(null);
    }
    
    @Override
    public DocumentLock getCurrentLock(Long documentId) {
        return documentLockRepository
                .findFirstByDocumentIdAndExpireTimeAfterOrderByLockTimeDesc(
                        documentId, LocalDateTime.now())
                .orElse(null);
    }
    
    @Override
    public List<DocumentLock> getUserLocks(Long userId) {
        return documentLockRepository.findByUserIdAndExpireTimeAfterOrderByLockTimeDesc(
                userId, LocalDateTime.now());
    }
    
    @Override
    public List<DocumentLock> getSessionLocks(String sessionId) {
        return documentLockRepository.findBySessionIdOrderByLockTimeDesc(sessionId);
    }
    
    @Override
    public Page<DocumentLock> listLocks(Long documentId, Pageable pageable) {
        return documentLockRepository.findByDocumentIdOrderByLockTimeDesc(documentId, pageable);
    }
    
    @Override
    @Transactional
    public void cleanExpiredLocks() {
        List<DocumentLock> expiredLocks = documentLockRepository.findExpiredLocks(LocalDateTime.now());
        documentLockRepository.deleteAll(expiredLocks);
    }
    
    @Override
    public boolean isLocked(Long documentId) {
        return documentLockRepository.existsByDocumentIdAndExpireTimeAfter(
                documentId, LocalDateTime.now());
    }
    
    @Override
    public boolean isLockedByUser(Long documentId, Long userId) {
        return documentLockRepository.existsByDocumentIdAndUserIdAndExpireTimeAfter(
                documentId, userId, LocalDateTime.now());
    }
    
    @Override
    public boolean canEdit(Long documentId, Long userId) {
        DocumentLock lock = getCurrentLock(documentId);
        return lock == null || lock.getUserId().equals(userId);
    }
} 