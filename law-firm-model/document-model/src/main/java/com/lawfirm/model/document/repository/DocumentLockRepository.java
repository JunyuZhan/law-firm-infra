package com.lawfirm.model.document.repository;

import com.lawfirm.model.document.entity.DocumentLock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 文档锁定Repository接口
 */
@Repository
public interface DocumentLockRepository extends JpaRepository<DocumentLock, Long> {
    
    /**
     * 查询文档的所有锁定记录
     */
    List<DocumentLock> findByDocumentIdOrderByLockTimeDesc(Long documentId);
    
    /**
     * 分页查询文档锁定记录
     */
    Page<DocumentLock> findByDocumentIdOrderByLockTimeDesc(Long documentId, Pageable pageable);
    
    /**
     * 查询文档的当前有效锁定
     */
    Optional<DocumentLock> findFirstByDocumentIdAndExpireTimeAfterOrderByLockTimeDesc(
            Long documentId, LocalDateTime currentTime);
    
    /**
     * 查询用户的所有锁定记录
     */
    List<DocumentLock> findByUserIdOrderByLockTimeDesc(Long userId);
    
    /**
     * 查询用户当前的所有锁定
     */
    List<DocumentLock> findByUserIdAndExpireTimeAfterOrderByLockTimeDesc(
            Long userId, LocalDateTime currentTime);
    
    /**
     * 查询会话的所有锁定记录
     */
    List<DocumentLock> findBySessionIdOrderByLockTimeDesc(String sessionId);
    
    /**
     * 查询已过期的锁定记录
     */
    @Query("SELECT l FROM DocumentLock l WHERE l.expireTime < :currentTime")
    List<DocumentLock> findExpiredLocks(LocalDateTime currentTime);
    
    /**
     * 删除文档的所有锁定记录
     */
    void deleteByDocumentId(Long documentId);
    
    /**
     * 删除用户的所有锁定记录
     */
    void deleteByUserId(Long userId);
    
    /**
     * 删除会话的所有锁定记录
     */
    void deleteBySessionId(String sessionId);
    
    /**
     * 检查文档是否被锁定
     */
    boolean existsByDocumentIdAndExpireTimeAfter(Long documentId, LocalDateTime currentTime);
    
    /**
     * 检查文档是否被指定用户锁定
     */
    boolean existsByDocumentIdAndUserIdAndExpireTimeAfter(
            Long documentId, Long userId, LocalDateTime currentTime);
} 