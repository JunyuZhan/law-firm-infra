package com.lawfirm.model.organization.service;

import com.lawfirm.model.organization.entity.base.Position;
import com.lawfirm.model.organization.enums.PositionTypeEnum;

import java.util.List;

/**
 * 职位服务接口
 */
public interface PositionService {
    
    /**
     * 创建职位
     *
     * @param position 职位信息
     * @return 创建的职位ID
     */
    Long createPosition(Position position);
    
    /**
     * 更新职位信息
     *
     * @param position 职位信息
     * @return 是否更新成功
     */
    boolean updatePosition(Position position);
    
    /**
     * 根据ID查询职位
     *
     * @param id 职位ID
     * @return 职位信息
     */
    Position getPositionById(Long id);
    
    /**
     * 根据ID删除职位
     *
     * @param id 职位ID
     * @return 是否删除成功
     */
    boolean deletePosition(Long id);
    
    /**
     * 根据部门ID查询职位列表
     *
     * @param departmentId 部门ID
     * @return 职位列表
     */
    List<Position> getPositionsByDepartmentId(Long departmentId);
    
    /**
     * 根据职位类型查询职位列表
     *
     * @param type 职位类型
     * @return 职位列表
     */
    List<Position> getPositionsByType(PositionTypeEnum type);
    
    /**
     * 查询所有职位
     *
     * @return 职位列表
     */
    List<Position> getAllPositions();
} 