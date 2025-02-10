package com.lawfirm.model.document.service;

import com.lawfirm.model.document.dto.DocumentEditLockDTO;
import com.lawfirm.model.document.entity.DocumentLock;
import com.lawfirm.model.document.vo.DocumentEditVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 文档锁定服务接口
 */
public interface DocumentLockService {
    
    /**
     * 锁定文档
     */
    DocumentLock lockDocument(DocumentEditLockDTO lockDTO);
    
    /**
     * 解锁文档
     */
    void unlockDocument(Long documentId, String sessionId);
    
    /**
     * 强制解锁文档
     */
    void forceUnlockDocument(Long documentId);
    
    /**
     * 续期锁定
     */
    DocumentLock renewLock(Long documentId, String sessionId, Integer duration);
    
    /**
     * 获取文档锁定状态
     */
    DocumentEditVO getLockStatus(Long documentId);
    
    /**
     * 获取锁定详情
     */
    DocumentLock getLock(Long lockId);
    
    /**
     * 获取文档的当前锁定
     */
    DocumentLock getCurrentLock(Long documentId);
    
    /**
     * 获取用户的所有锁定
     */
    List<DocumentLock> getUserLocks(Long userId);
    
    /**
     * 获取会话的所有锁定
     */
    List<DocumentLock> getSessionLocks(String sessionId);
    
    /**
     * 分页查询锁定记录
     */
    Page<DocumentLock> listLocks(Long documentId, Pageable pageable);
    
    /**
     * 清理过期的锁定记录
     */
    void cleanExpiredLocks();
    
    /**
     * 检查文档是否被锁定
     */
    boolean isLocked(Long documentId);
    
    /**
     * 检查文档是否被指定用户锁定
     */
    boolean isLockedByUser(Long documentId, Long userId);
    
    /**
     * 检查是否可以编辑文档
     */
    boolean canEdit(Long documentId, Long userId);
} 