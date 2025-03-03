package com.lawfirm.model.cases.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseKnowledgeDTO;
import com.lawfirm.model.cases.vo.business.CaseKnowledgeVO;

import java.util.List;

/**
 * 案件知识库服务接口
 */
public interface CaseKnowledgeService {

    /**
     * 创建知识条目
     *
     * @param knowledgeDTO 知识信息
     * @return 知识条目ID
     */
    Long createKnowledge(CaseKnowledgeDTO knowledgeDTO);

    /**
     * 更新知识条目
     *
     * @param knowledgeDTO 知识信息
     * @return 是否成功
     */
    boolean updateKnowledge(CaseKnowledgeDTO knowledgeDTO);

    /**
     * 删除知识条目
     *
     * @param knowledgeId 知识条目ID
     * @return 是否成功
     */
    boolean deleteKnowledge(Long knowledgeId);

    /**
     * 获取知识条目详情
     *
     * @param knowledgeId 知识条目ID
     * @return 知识条目详情
     */
    CaseKnowledgeVO getKnowledgeDetail(Long knowledgeId);

    /**
     * 获取案件的所有知识条目
     *
     * @param caseId 案件ID
     * @return 知识条目列表
     */
    List<CaseKnowledgeVO> listCaseKnowledge(Long caseId);

    /**
     * 分页查询知识条目
     *
     * @param caseId 案件ID
     * @param keyword 关键词
     * @param categoryId 分类ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    IPage<CaseKnowledgeVO> pageKnowledge(Long caseId, String keyword, Long categoryId, 
                                       Integer pageNum, Integer pageSize);

    /**
     * 添加知识标签
     *
     * @param knowledgeId 知识条目ID
     * @param tag 标签
     * @return 是否成功
     */
    boolean addKnowledgeTag(Long knowledgeId, String tag);

    /**
     * 移除知识标签
     *
     * @param knowledgeId 知识条目ID
     * @param tag 标签
     * @return 是否成功
     */
    boolean removeKnowledgeTag(Long knowledgeId, String tag);

    /**
     * 设置知识分类
     *
     * @param knowledgeId 知识条目ID
     * @param categoryId 分类ID
     * @return 是否成功
     */
    boolean setKnowledgeCategory(Long knowledgeId, Long categoryId);

    /**
     * 添加相关案件
     *
     * @param knowledgeId 知识条目ID
     * @param caseId 案件ID
     * @return 是否成功
     */
    boolean addRelatedCase(Long knowledgeId, Long caseId);

    /**
     * 移除相关案件
     *
     * @param knowledgeId 知识条目ID
     * @param caseId 案件ID
     * @return 是否成功
     */
    boolean removeRelatedCase(Long knowledgeId, Long caseId);

    /**
     * 添加知识引用
     *
     * @param knowledgeId 知识条目ID
     * @param referenceType 引用类型
     * @param referenceId 引用ID
     * @return 是否成功
     */
    boolean addKnowledgeReference(Long knowledgeId, Integer referenceType, Long referenceId);

    /**
     * 移除知识引用
     *
     * @param knowledgeId 知识条目ID
     * @param referenceType 引用类型
     * @param referenceId 引用ID
     * @return 是否成功
     */
    boolean removeKnowledgeReference(Long knowledgeId, Integer referenceType, Long referenceId);

    /**
     * 搜索知识条目
     *
     * @param keyword 关键词
     * @param tags 标签列表
     * @param categoryIds 分类ID列表
     * @return 知识条目列表
     */
    List<CaseKnowledgeVO> searchKnowledge(String keyword, List<String> tags, List<Long> categoryIds);

    /**
     * 获取相似知识条目
     *
     * @param knowledgeId 知识条目ID
     * @return 知识条目列表
     */
    List<CaseKnowledgeVO> getSimilarKnowledge(Long knowledgeId);

    /**
     * 获取热门知识条目
     *
     * @param limit 数量限制
     * @return 知识条目列表
     */
    List<CaseKnowledgeVO> getHotKnowledge(Integer limit);

    /**
     * 增加知识条目浏览次数
     *
     * @param knowledgeId 知识条目ID
     * @return 是否成功
     */
    boolean incrementViewCount(Long knowledgeId);

    /**
     * 检查知识条目是否存在
     *
     * @param knowledgeId 知识条目ID
     * @return 是否存在
     */
    boolean checkKnowledgeExists(Long knowledgeId);

    /**
     * 统计案件知识条目数量
     *
     * @param caseId 案件ID
     * @param categoryId 分类ID
     * @return 数量
     */
    int countKnowledge(Long caseId, Long categoryId);
} 