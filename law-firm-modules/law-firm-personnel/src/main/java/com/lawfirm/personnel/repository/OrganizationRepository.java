package com.lawfirm.personnel.repository;

import com.lawfirm.model.organization.entity.Branch;
import com.lawfirm.model.organization.entity.Department;
import com.lawfirm.model.organization.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 组织架构Repository接口
 */
@Repository
public interface OrganizationRepository {
    
    /**
     * 获取分所信息
     */
    Branch findBranchById(Long id);
    
    /**
     * 获取部门信息
     */
    Department findDepartmentById(Long id);
    
    /**
     * 获取职位信息
     */
    Position findPositionById(Long id);
} 