package com.lawfirm.core.workflow.repository;

import com.lawfirm.core.workflow.entity.ProcessPermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 流程权限Repository
 */
@Repository
public interface ProcessPermissionRepository extends JpaRepository<ProcessPermissionEntity, Long>, JpaSpecificationExecutor<ProcessPermissionEntity> {
    
    /**
     * 根据流程定义Key查询
     */
    Optional<ProcessPermissionEntity> findByProcessKey(String processKey);
    
    /**
     * 根据流程分类和启用状态查询
     */
    List<ProcessPermissionEntity> findByCategoryAndEnabled(String category, Boolean enabled);
    
    /**
     * 根据流程定义Key删除
     */
    void deleteByProcessKey(String processKey);
} 