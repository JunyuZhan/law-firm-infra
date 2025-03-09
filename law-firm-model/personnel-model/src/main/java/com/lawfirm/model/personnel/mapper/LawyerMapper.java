package com.lawfirm.model.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.personnel.entity.Lawyer;
import java.util.List;

/**
 * 律师数据访问接口
 */
public interface LawyerMapper extends BaseMapper<Lawyer> {

    /**
     * 根据执业证号查询律师
     *
     * @param licenseNumber 执业证号
     * @return 律师信息
     */
    Lawyer selectByLicenseNumber(String licenseNumber);

    /**
     * 根据工号查询律师
     *
     * @param workNumber 工号
     * @return 律师信息
     */
    Lawyer selectByWorkNumber(String workNumber);

    /**
     * 根据专业领域查询律师列表
     *
     * @param practiceArea 专业领域
     * @return 律师列表
     */
    List<Lawyer> selectByPracticeArea(String practiceArea);
} 