package com.lawfirm.auth.mapper;

import com.lawfirm.model.auth.entity.Department;
import com.lawfirm.model.auth.mapper.DepartmentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门Mapper接口实现类
 * 用于扩展基础DepartmentMapper接口的功能
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class DepartmentMapperImpl implements DepartmentMapper {

    // 注入实际的Mapper代理对象，用于调用基础功能
    private final DepartmentMapper departmentMapper;
    
    @Override
    public Department selectById(Long id) {
        log.debug("自定义DepartmentMapperImpl.selectById，参数：{}", id);
        return departmentMapper.selectById(id);
    }
    
    @Override
    public Department selectByCode(String code) {
        log.debug("自定义DepartmentMapperImpl.selectByCode，参数：{}", code);
        return departmentMapper.selectByCode(code);
    }
    
    @Override
    public List<Department> selectAll() {
        log.debug("自定义DepartmentMapperImpl.selectAll");
        return departmentMapper.selectAll();
    }
    
    @Override
    public List<Department> selectByUserId(Long userId) {
        log.debug("自定义DepartmentMapperImpl.selectByUserId，参数：{}", userId);
        return departmentMapper.selectByUserId(userId);
    }
    
    @Override
    public List<Department> selectByParentId(Long parentId) {
        log.debug("自定义DepartmentMapperImpl.selectByParentId，参数：{}", parentId);
        return departmentMapper.selectByParentId(parentId);
    }
    
    // 实现其他接口方法...
}
