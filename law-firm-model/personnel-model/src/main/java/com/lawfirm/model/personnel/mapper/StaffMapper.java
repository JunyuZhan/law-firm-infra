package com.lawfirm.model.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.personnel.entity.Staff;
import java.util.List;

/**
 * 行政人员数据访问接口
 */
public interface StaffMapper extends BaseMapper<Staff> {

    /**
     * 根据工号查询行政人员
     *
     * @param workNumber 工号
     * @return 行政人员信息
     */
    Staff selectByWorkNumber(String workNumber);

    /**
     * 根据部门ID查询行政人员列表
     *
     * @param departmentId 部门ID
     * @return 行政人员列表
     */
    List<Staff> selectByDepartmentId(Long departmentId);

    /**
     * 根据职位ID查询行政人员列表
     *
     * @param positionId 职位ID
     * @return 行政人员列表
     */
    List<Staff> selectByPositionId(Long positionId);
} 