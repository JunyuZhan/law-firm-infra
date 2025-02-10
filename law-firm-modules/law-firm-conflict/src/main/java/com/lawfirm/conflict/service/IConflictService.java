package com.lawfirm.conflict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.conflict.entity.Conflict;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 利益冲突服务接口
 */
public interface IConflictService extends IService<Conflict> {
    
    /**
     * 检查当事人冲突
     *
     * @param partyId 当事人ID
     * @return 冲突列表
     */
    List<Conflict> checkPartyConflict(String partyId);
    
    /**
     * 检查案件冲突
     *
     * @param caseId 案件ID
     * @return 冲突列表
     */
    List<Conflict> checkCaseConflict(String caseId);
    
    /**
     * 检查律师冲突
     *
     * @param lawyerId 律师ID
     * @return 冲突列表
     */
    List<Conflict> checkLawyerConflict(Long lawyerId);
    
    /**
     * 处理冲突
     *
     * @param id 冲突ID
     * @param handlerId 处理人ID
     * @param status 处理状态
     * @param opinion 处理意见
     */
    void handleConflict(Long id, Long handlerId, Integer status, String opinion);
    
    /**
     * 分页查询冲突
     *
     * @param type 冲突类型
     * @param status 状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    IPage<Conflict> pageConflicts(Integer type, Integer status, LocalDateTime startTime, LocalDateTime endTime, long page, long size);
}
