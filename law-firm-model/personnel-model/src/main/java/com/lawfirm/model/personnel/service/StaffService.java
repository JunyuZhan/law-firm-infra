package com.lawfirm.model.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.personnel.dto.staff.CreateStaffDTO;
import com.lawfirm.model.personnel.dto.staff.UpdateStaffDTO;
import com.lawfirm.model.personnel.entity.Staff;
import com.lawfirm.model.personnel.vo.StaffVO;

import java.util.List;

/**
 * 行政人员服务接口
 */
public interface StaffService extends EmployeeService {

    /**
     * 创建行政人员
     *
     * @param createDTO 创建参数
     * @return 行政人员ID
     */
    Long createStaff(CreateStaffDTO createDTO);

    /**
     * 更新行政人员
     *
     * @param updateDTO 更新参数
     * @return 是否成功
     */
    boolean updateStaff(UpdateStaffDTO updateDTO);

    /**
     * 获取行政人员详情
     *
     * @param id 行政人员ID
     * @return 行政人员详情
     */
    StaffVO getStaffDetail(Long id);

    /**
     * 获取行政人员列表
     *
     * @param centerType 所属中心
     * @param functionType 职能类型
     * @return 行政人员列表
     */
    List<StaffVO> listStaffs(Integer centerType, Integer functionType);

    /**
     * 分页查询行政人员
     *
     * @param page 分页参数
     * @param centerType 所属中心
     * @param functionType 职能类型
     * @param keyword 关键字
     * @return 分页结果
     */
    Page<StaffVO> pageStaffs(Page<Staff> page, Integer centerType, Integer functionType, String keyword);

    /**
     * 调整所属中心
     *
     * @param id 行政人员ID
     * @param centerType 新中心类型
     * @param reason 调整原因
     * @return 是否成功
     */
    boolean changeCenter(Long id, Integer centerType, String reason);

    /**
     * 调整职能类型
     *
     * @param id 行政人员ID
     * @param functionType 新职能类型
     * @param reason 调整原因
     * @return 是否成功
     */
    boolean changeFunction(Long id, Integer functionType, String reason);

    /**
     * 设置兼任职能
     *
     * @param id 行政人员ID
     * @param hasOtherFunctions 是否兼任其他职能
     * @param otherFunctions 兼任职能描述
     * @return 是否成功
     */
    boolean setOtherFunctions(Long id, Boolean hasOtherFunctions, String otherFunctions);
} 