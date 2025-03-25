package com.lawfirm.model.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.personnel.entity.Contact;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 人事联系人数据访问接口
 * 重命名为PersonnelContactMapper避免与客户模块中的同名接口冲突
 */
@Mapper
public interface PersonnelContactMapper extends BaseMapper<Contact> {

    /**
     * 根据人员ID查询联系人列表
     *
     * @param personId 人员ID
     * @return 联系人列表
     */
    List<Contact> selectByPersonId(Long personId);

    /**
     * 根据联系人类型查询
     *
     * @param personId 人员ID
     * @param type 联系人类型
     * @return 联系人列表
     */
    List<Contact> selectByType(Long personId, String type);
} 