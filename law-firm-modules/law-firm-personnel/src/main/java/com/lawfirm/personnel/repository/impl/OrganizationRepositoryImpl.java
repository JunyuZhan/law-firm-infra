package com.lawfirm.personnel.repository.impl;

import com.lawfirm.model.organization.entity.Branch;
import com.lawfirm.model.organization.entity.Department;
import com.lawfirm.model.organization.entity.Position;
import com.lawfirm.personnel.repository.OrganizationRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 组织架构Repository实现类
 */
@Repository
@RequiredArgsConstructor
public class OrganizationRepositoryImpl implements OrganizationRepository {

    private final EntityManager entityManager;

    @Override
    public Branch findBranchById(Long id) {
        return entityManager.find(Branch.class, id);
    }

    @Override
    public Department findDepartmentById(Long id) {
        return entityManager.find(Department.class, id);
    }

    @Override
    public Position findPositionById(Long id) {
        return entityManager.find(Position.class, id);
    }
} 