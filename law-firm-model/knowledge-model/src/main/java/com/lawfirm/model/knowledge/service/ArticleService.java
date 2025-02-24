package com.lawfirm.model.knowledge.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.knowledge.dto.article.ArticleCreateDTO;
import com.lawfirm.model.knowledge.dto.article.ArticleQueryDTO;
import com.lawfirm.model.knowledge.dto.article.ArticleUpdateDTO;
import com.lawfirm.model.knowledge.entity.Article;
import com.lawfirm.model.knowledge.vo.ArticleVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 文章服务接口
 */
public interface ArticleService extends BaseService<Article> {

    /**
     * 创建文章
     */
    ArticleVO create(ArticleCreateDTO createDTO);

    /**
     * 更新文章
     */
    ArticleVO update(ArticleUpdateDTO updateDTO);

    /**
     * 分页查询文章
     */
    Page<ArticleVO> query(ArticleQueryDTO queryDTO);

    /**
     * 获取文章详情
     */
    ArticleVO getDetail(Long id);

    /**
     * 获取相关文章
     */
    List<ArticleVO.ArticleBriefVO> getRelated(Long id, int limit);

    /**
     * 增加浏览次数
     */
    void increaseViewCount(Long id);

    /**
     * 点赞文章
     */
    void like(Long id);

    /**
     * 收藏文章
     */
    void favorite(Long id);

    /**
     * 分享文章
     */
    void share(Long id);

    /**
     * 置顶文章
     */
    void setTop(Long id, Boolean top);

    /**
     * 推荐文章
     */
    void setRecommended(Long id, Boolean recommended);

    /**
     * 移动文章到指定分类
     */
    void moveToCategory(Long id, Long categoryId);

    /**
     * 批量移动文章到指定分类
     */
    void batchMoveToCategory(List<Long> ids, Long categoryId);

    /**
     * 获取分类下的文章数量
     */
    Long countByCategory(Long categoryId);

    /**
     * 获取标签下的文章数量
     */
    Long countByTag(String tag);

    /**
     * 获取作者的文章数量
     */
    Long countByAuthor(Long authorId);

    /**
     * 获取热门文章
     */
    List<ArticleVO.ArticleBriefVO> getHotArticles(int limit);

    /**
     * 获取推荐文章
     */
    List<ArticleVO.ArticleBriefVO> getRecommendedArticles(int limit);

    /**
     * 获取最新文章
     */
    List<ArticleVO.ArticleBriefVO> getLatestArticles(int limit);
} 