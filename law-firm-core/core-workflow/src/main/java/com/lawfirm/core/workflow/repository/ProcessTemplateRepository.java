package com.lawfirm.core.workflow.repository;

import com.lawfirm.core.workflow.entity.ProcessTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 流程模板Repository
 */
@Repository
public interface ProcessTemplateRepository extends JpaRepository<ProcessTemplateEntity, Long>, JpaSpecificationExecutor<ProcessTemplateEntity> {
    
    /**
     * 根据流程定义Key查询最新版本
     */
    Optional<ProcessTemplateEntity> findByProcessKeyAndLatestTrue(String processKey);
    
    /**
     * 根据流程定义Key和版本号查询
     */
    Optional<ProcessTemplateEntity> findByProcessKeyAndVersion(String processKey, Integer version);
    
    /**
     * 根据流程分类查询最新版本
     */
    List<ProcessTemplateEntity> findByCategoryAndLatestTrue(String category);
    
    /**
     * 根据部署ID查询
     */
    Optional<ProcessTemplateEntity> findByDeploymentId(String deploymentId);
    
    /**
     * 根据流程定义ID查询
     */
    Optional<ProcessTemplateEntity> findByProcessDefinitionId(String processDefinitionId);
} 