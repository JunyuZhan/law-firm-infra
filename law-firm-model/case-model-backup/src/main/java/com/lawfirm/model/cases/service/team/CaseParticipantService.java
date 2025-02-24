package com.lawfirm.model.cases.service.team;

import com.lawfirm.model.base.dto.PageDTO;
import com.lawfirm.model.cases.dto.team.CaseParticipantDTO;
import com.lawfirm.model.cases.vo.team.CaseParticipantVO;

import java.util.List;

/**
 * 案件参与方服务接口
 */
public interface CaseParticipantService {

    /**
     * 添加案件参与方
     *
     * @param participantDTO 参与方信息
     * @return 参与方ID
     */
    Long addParticipant(CaseParticipantDTO participantDTO);

    /**
     * 更新案件参与方信息
     *
     * @param participantDTO 参与方信息
     * @return 是否成功
     */
    Boolean updateParticipant(CaseParticipantDTO participantDTO);

    /**
     * 删除案件参与方
     *
     * @param id 参与方ID
     * @return 是否成功
     */
    Boolean deleteParticipant(Long id);

    /**
     * 批量删除案件参与方
     *
     * @param ids 参与方ID列表
     * @return 是否成功
     */
    Boolean batchDeleteParticipants(List<Long> ids);

    /**
     * 获取案件参与方详情
     *
     * @param id 参与方ID
     * @return 参与方详情
     */
    CaseParticipantVO getParticipantDetail(Long id);

    /**
     * 获取案件的所有参与方
     *
     * @param caseId 案件ID
     * @return 参与方列表
     */
    List<CaseParticipantVO> listCaseParticipants(Long caseId);

    /**
     * 分页查询案件参与方
     *
     * @param caseId 案件ID
     * @param type 参与方类型
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageDTO<CaseParticipantVO> pageParticipants(Long caseId, String type, Integer pageNum, Integer pageSize);

    /**
     * 检查参与方是否存在利益冲突
     *
     * @param participantDTO 参与方信息
     * @return 是否存在冲突
     */
    Boolean checkConflict(CaseParticipantDTO participantDTO);

    /**
     * 更新参与方联系信息
     *
     * @param id 参与方ID
     * @param mobile 手机号
     * @param email 邮箱
     * @return 是否成功
     */
    Boolean updateContactInfo(Long id, String mobile, String email);
} 