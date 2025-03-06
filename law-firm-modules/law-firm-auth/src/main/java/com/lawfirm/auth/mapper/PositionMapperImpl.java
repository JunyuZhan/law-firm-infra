package com.lawfirm.auth.mapper;

import com.lawfirm.model.auth.entity.Position;
import com.lawfirm.model.auth.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 职位Mapper接口实现类
 * 用于扩展基础PositionMapper接口的功能
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class PositionMapperImpl implements PositionMapper {

    // 注入实际的Mapper代理对象，用于调用基础功能
    private final PositionMapper positionMapper;
    
    @Override
    public Position selectById(Long id) {
        log.debug("自定义PositionMapperImpl.selectById，参数：{}", id);
        return positionMapper.selectById(id);
    }
    
    @Override
    public Position selectByCode(String code) {
        log.debug("自定义PositionMapperImpl.selectByCode，参数：{}", code);
        return positionMapper.selectByCode(code);
    }
    
    @Override
    public List<Position> selectAll() {
        log.debug("自定义PositionMapperImpl.selectAll");
        return positionMapper.selectAll();
    }
    
    @Override
    public List<Position> selectByIds(List<Long> ids) {
        log.debug("自定义PositionMapperImpl.selectByIds，参数个数：{}", ids.size());
        return positionMapper.selectByIds(ids);
    }
    
    // 实现其他接口方法...
}
