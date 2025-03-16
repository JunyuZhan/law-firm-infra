package com.lawfirm.personnel.service;

import com.lawfirm.model.organization.dto.position.PositionDTO;
import com.lawfirm.model.organization.entity.base.Position;
import com.lawfirm.model.organization.enums.PositionTypeEnum;
import com.lawfirm.model.organization.service.PositionService;

import java.util.List;

/**
 * 扩展职位服务接口
 * 在原有PositionService的基础上增加DTO相关操作
 */
public interface ExtendedPositionService extends PositionService {
    
    /**
     * 创建职位（接收DTO对象）
     *
     * @param positionDTO 职位DTO
     * @return 创建的职位ID
     */
    Long createPosition(PositionDTO positionDTO);
    
    /**
     * 更新职位信息（通过ID和DTO）
     *
     * @param id 职位ID
     * @param positionDTO 职位DTO
     * @return 是否更新成功
     */
    boolean updatePosition(Long id, PositionDTO positionDTO);
    
    /**
     * 根据ID查询职位DTO
     *
     * @param id 职位ID
     * @return 职位DTO
     */
    PositionDTO getPositionDTOById(Long id);
    
    /**
     * 根据组织ID查询职位DTO列表
     *
     * @param organizationId 组织ID
     * @return 职位DTO列表
     */
    List<PositionDTO> listPositionsByOrganizationId(Long organizationId);
    
    /**
     * 启用职位
     *
     * @param id 职位ID
     * @return 是否成功
     */
    boolean enablePosition(Long id);
    
    /**
     * 禁用职位
     *
     * @param id 职位ID
     * @return 是否成功
     */
    boolean disablePosition(Long id);
} 