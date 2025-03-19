package com.lawfirm.api.adaptor.personnel;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.organization.dto.position.PositionDTO;
import com.lawfirm.model.organization.entity.base.Position;
import com.lawfirm.model.organization.enums.PositionTypeEnum;
import com.lawfirm.model.organization.vo.position.PositionVO;
import com.lawfirm.personnel.service.ExtendedPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 职位管理适配器
 */
@Component
public class PositionAdaptor extends BaseAdaptor {

    @Autowired
    private ExtendedPositionService positionService;

    /**
     * 创建职位
     */
    public PositionVO createPosition(PositionDTO dto) {
        Long id = positionService.createPosition(dto);
        Position position = positionService.getPositionById(id);
        return convert(position, PositionVO.class);
    }

    /**
     * 更新职位信息
     */
    public PositionVO updatePosition(Long id, PositionDTO dto) {
        positionService.updatePosition(id, dto);
        Position position = positionService.getPositionById(id);
        return convert(position, PositionVO.class);
    }

    /**
     * 删除职位
     */
    public boolean deletePosition(Long id) {
        return positionService.deletePosition(id);
    }

    /**
     * 获取职位详情
     */
    public PositionVO getPosition(Long id) {
        Position position = positionService.getPositionById(id);
        return convert(position, PositionVO.class);
    }

    /**
     * 获取所有职位
     */
    public List<PositionVO> listPositions() {
        List<Position> positions = positionService.getAllPositions();
        return positions.stream()
                .map(position -> convert(position, PositionVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询职位
     */
    public List<PositionVO> getPositionsByDepartmentId(Long departmentId) {
        List<Position> positions = positionService.getPositionsByDepartmentId(departmentId);
        return positions.stream()
                .map(position -> convert(position, PositionVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据职位类型查询职位
     */
    public List<PositionVO> getPositionsByType(PositionTypeEnum type) {
        List<Position> positions = positionService.getPositionsByType(type);
        return positions.stream()
                .map(position -> convert(position, PositionVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 启用职位
     */
    public boolean enablePosition(Long id) {
        return positionService.enablePosition(id);
    }

    /**
     * 禁用职位
     */
    public boolean disablePosition(Long id) {
        return positionService.disablePosition(id);
    }

    /**
     * 检查职位是否存在
     */
    public boolean existsPosition(Long id) {
        Position position = positionService.getPositionById(id);
        return position != null;
    }

    /**
     * 获取职位数量
     */
    public long countPositions() {
        return positionService.getAllPositions().size();
    }

    /**
     * 获取部门职位数量
     */
    public long countPositionsByDepartmentId(Long departmentId) {
        return positionService.getPositionsByDepartmentId(departmentId).size();
    }
} 