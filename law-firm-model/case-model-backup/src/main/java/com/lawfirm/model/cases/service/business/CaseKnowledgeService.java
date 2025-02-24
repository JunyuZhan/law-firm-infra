package com.lawfirm.model.cases.service.business;

import com.lawfirm.model.base.dto.PageDTO;
import com.lawfirm.model.cases.vo.business.KnowledgeArticleVO;
import com.lawfirm.model.cases.vo.business.KnowledgeCommentVO;
import com.lawfirm.model.cases.vo.business.KnowledgeTagVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 案件知识库服务接口
 */
public interface CaseKnowledgeService {

    /**
     * 创建知识文章
     *
     * @param title 标题
     * @param content 内容
     * @param tags 标签列表
     * @param caseIds 关联案件ID列表
     * @return 文章ID
     */
    Long createArticle(String title, String content, List<String> tags, List<Long> caseIds);

    /**
     * 更新知识文章
     *
     * @param articleId 文章ID
     * @param title 标题
     * @param content 内容
     * @param tags 标签列表
     * @return 是否成功
     */
    Boolean updateArticle(Long articleId, String title, String content, List<String> tags);

    /**
     * 删除知识文章
     *
     * @param articleId 文章ID
     * @return 是否成功
     */
    Boolean deleteArticle(Long articleId);

    /**
     * 获取文章详情
     *
     * @param articleId 文章ID
     * @return 文章详情
     */
    KnowledgeArticleVO getArticleDetail(Long articleId);

    /**
     * 分页查询文章
     *
     * @param keyword 关键词
     * @param tags 标签列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageDTO<KnowledgeArticleVO> pageArticles(String keyword, List<String> tags,
            LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize);

    /**
     * 添加评论
     *
     * @param articleId 文章ID
     * @param content 评论内容
     * @param parentId 父评论ID
     * @return 评论ID
     */
    Long addComment(Long articleId, String content, Long parentId);

    /**
     * 删除评论
     *
     * @param commentId 评论ID
     * @return 是否成功
     */
    Boolean deleteComment(Long commentId);

    /**
     * 获取文章评论
     *
     * @param articleId 文章ID
     * @return 评论列表
     */
    List<KnowledgeCommentVO> listComments(Long articleId);

    /**
     * 创建标签
     *
     * @param name 标签名称
     * @param description 标签描述
     * @return 标签ID
     */
    Long createTag(String name, String description);

    /**
     * 更新标签
     *
     * @param tagId 标签ID
     * @param name 标签名称
     * @param description 标签描述
     * @return 是否成功
     */
    Boolean updateTag(Long tagId, String name, String description);

    /**
     * 删除标签
     *
     * @param tagId 标签ID
     * @return 是否成功
     */
    Boolean deleteTag(Long tagId);

    /**
     * 获取标签列表
     *
     * @return 标签列表
     */
    List<KnowledgeTagVO> listTags();

    /**
     * 搜索知识库
     *
     * @param keyword 关键词
     * @param filters 过滤条件
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageDTO<KnowledgeArticleVO> searchKnowledge(String keyword, Map<String, Object> filters,
            Integer pageNum, Integer pageSize);

    /**
     * 导出知识文章
     *
     * @param articleId 文章ID
     * @param format 导出格式
     * @return 文件路径
     */
    String exportArticle(Long articleId, String format);

    /**
     * 获取相关文章推荐
     *
     * @param articleId 文章ID
     * @param limit 推荐数量
     * @return 推荐文章列表
     */
    List<KnowledgeArticleVO> getRelatedArticles(Long articleId, Integer limit);

    /**
     * 获取知识库统计
     *
     * @return 统计数据
     */
    Map<String, Integer> getKnowledgeStats();
}