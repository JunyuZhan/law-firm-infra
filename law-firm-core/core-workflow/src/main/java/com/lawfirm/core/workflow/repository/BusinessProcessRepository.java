package com.lawfirm.core.workflow.repository;

import com.lawfirm.core.workflow.entity.BusinessProcessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 业务流程关联Repository
 */
@Repository
public interface BusinessProcessRepository extends JpaRepository<BusinessProcessEntity, Long>, JpaSpecificationExecutor<BusinessProcessEntity> {
    
    /**
     * 根据业务类型和业务ID查询
     */
    Optional<BusinessProcessEntity> findByBusinessTypeAndBusinessId(String businessType, String businessId);
    
    /**
     * 根据流程实例ID查询
     */
    Optional<BusinessProcessEntity> findByProcessInstanceId(String processInstanceId);
    
    /**
     * 根据业务类型、流程状态和发起人查询
     */
    List<BusinessProcessEntity> findByBusinessTypeAndProcessStatusAndStartUserId(String businessType, String processStatus, String startUserId);
    
    /**
     * 根据业务类型和业务ID删除
     */
    void deleteByBusinessTypeAndBusinessId(String businessType, String businessId);
} 