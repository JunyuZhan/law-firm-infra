package com.lawfirm.model.cases.service.team;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.team.CaseParticipantDTO;
import com.lawfirm.model.cases.vo.team.CaseParticipantVO;

import java.util.List;

/**
 * 案件参与方服务接口
 */
public interface CaseParticipantService {

    /**
     * 添加参与方
     *
     * @param participantDTO 参与方信息
     * @return 参与方ID
     */
    Long addParticipant(CaseParticipantDTO participantDTO);

    /**
     * 批量添加参与方
     *
     * @param participantDTOs 参与方信息列表
     * @return 是否成功
     */
    boolean batchAddParticipants(List<CaseParticipantDTO> participantDTOs);

    /**
     * 更新参与方信息
     *
     * @param participantDTO 参与方信息
     * @return 是否成功
     */
    boolean updateParticipant(CaseParticipantDTO participantDTO);

    /**
     * 删除参与方
     *
     * @param participantId 参与方ID
     * @return 是否成功
     */
    boolean deleteParticipant(Long participantId);

    /**
     * 批量删除参与方
     *
     * @param participantIds 参与方ID列表
     * @return 是否成功
     */
    boolean batchDeleteParticipants(List<Long> participantIds);

    /**
     * 获取参与方详情
     *
     * @param participantId 参与方ID
     * @return 参与方详情
     */
    CaseParticipantVO getParticipantDetail(Long participantId);

    /**
     * 获取案件的所有参与方
     *
     * @param caseId 案件ID
     * @return 参与方列表
     */
    List<CaseParticipantVO> listCaseParticipants(Long caseId);

    /**
     * 分页查询参与方
     *
     * @param caseId 案件ID
     * @param participantType 参与方类型
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    IPage<CaseParticipantVO> pageParticipants(Long caseId, Integer participantType, Integer pageNum, Integer pageSize);

    /**
     * 检查参与方是否存在
     *
     * @param caseId 案件ID
     * @param participantType 参与方类型
     * @param participantName 参与方名称
     * @return 是否存在
     */
    boolean checkParticipantExists(Long caseId, Integer participantType, String participantName);

    /**
     * 统计案件参与方数量
     *
     * @param caseId 案件ID
     * @param participantType 参与方类型
     * @return 数量
     */
    int countParticipants(Long caseId, Integer participantType);
}