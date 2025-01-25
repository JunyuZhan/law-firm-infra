package com.lawfirm.finance.repository;

import com.lawfirm.finance.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {
    
    List<Expense> findByLawFirmId(Long lawFirmId);
    
    List<Expense> findByDepartmentId(Long departmentId);
    
    List<Expense> findByApplicantId(Long applicantId);
    
    List<Expense> findByExpenseStatus(String expenseStatus);
    
    List<Expense> findByExpenseType(String expenseType);
} 