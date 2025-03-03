package com.lawfirm.model.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.personnel.dto.person.CreatePersonDTO;
import com.lawfirm.model.personnel.dto.person.UpdatePersonDTO;
import com.lawfirm.model.personnel.entity.Person;
import com.lawfirm.model.personnel.vo.PersonVO;

import java.util.List;

/**
 * 人员服务接口
 */
public interface PersonService extends BaseService<Person> {

    /**
     * 创建人员
     *
     * @param createDTO 创建参数
     * @return 人员ID
     */
    Long createPerson(CreatePersonDTO createDTO);

    /**
     * 更新人员
     *
     * @param updateDTO 更新参数
     * @return 是否成功
     */
    boolean updatePerson(UpdatePersonDTO updateDTO);

    /**
     * 获取人员详情
     *
     * @param id 人员ID
     * @return 人员详情
     */
    PersonVO getPersonDetail(Long id);

    /**
     * 获取人员列表
     *
     * @param firmId 律所ID
     * @param type 人员类型
     * @return 人员列表
     */
    List<PersonVO> listPersons(Long firmId, Integer type);

    /**
     * 分页查询人员
     *
     * @param page 分页参数
     * @param firmId 律所ID
     * @param type 人员类型
     * @param keyword 关键字
     * @return 分页结果
     */
    Page<PersonVO> pagePersons(Page<Person> page, Long firmId, Integer type, String keyword);

    /**
     * 删除人员
     *
     * @param id 人员ID
     * @return 是否成功
     */
    boolean deletePerson(Long id);

    /**
     * 批量删除人员
     *
     * @param ids 人员ID列表
     * @return 是否成功
     */
    boolean deletePersonBatch(List<Long> ids);

    /**
     * 启用人员
     *
     * @param id 人员ID
     * @return 是否成功
     */
    boolean enablePerson(Long id);

    /**
     * 禁用人员
     *
     * @param id 人员ID
     * @return 是否成功
     */
    boolean disablePerson(Long id);

    /**
     * 检查人员编号是否存在
     *
     * @param personCode 人员编号
     * @param excludeId 排除的人员ID
     * @return 是否存在
     */
    boolean checkPersonCodeExists(String personCode, Long excludeId);

    /**
     * 根据人员编号获取人员
     *
     * @param personCode 人员编号
     * @return 人员信息
     */
    PersonVO getPersonByCode(String personCode);
} 