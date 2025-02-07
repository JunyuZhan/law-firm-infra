package com.lawfirm.system.service;

import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.model.system.entity.SysLawFirm;
import com.lawfirm.model.system.vo.SysLawFirmVO;

/**
 * 律师事务所服务接口
 */
public interface SysLawFirmService extends BaseService<SysLawFirm, SysLawFirmVO> {

    /**
     * 审核律师事务所
     *
     * @param id     事务所ID
     * @param status 状态
     * @param remark 备注
     */
    void audit(Long id, Integer status, String remark);
} 