package com.lawfirm.cases.service;

import com.lawfirm.common.data.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 基础服务接口
 * @param <T> 实体类型，必须继承自BaseEntity
 * @param <V> 视图类型
 */
public interface BaseService<T extends BaseEntity, V> {
    
    V create(T entity);
    
    V update(Long id, T entity);
    
    void delete(Long id);
    
    Optional<V> findById(Long id);
    
    List<V> findAll();
    
    Page<V> findPage(Pageable pageable);
} 