package com.lawfirm.model.knowledge.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.knowledge.entity.Tag;
import com.lawfirm.model.knowledge.vo.TagVO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 标签服务接口
 */
public interface TagService extends BaseService<Tag> {

    /**
     * 创建标签
     */
    TagVO create(String name, String description);

    /**
     * 更新标签
     */
    TagVO update(Long id, String name, String description);

    /**
     * 分页查询标签
     */
    Page<TagVO> query(String keyword, int page, int size);

    /**
     * 获取标签详情
     */
    TagVO getDetail(Long id);

    /**
     * 获取标签详情（通过名称）
     */
    TagVO getByName(String name);

    /**
     * 检查标签名称是否可用
     */
    boolean checkNameAvailable(String name);

    /**
     * 更新标签使用次数
     */
    void updateUseCount(Long id);

    /**
     * 批量更新标签使用次数
     */
    void batchUpdateUseCount(List<Long> ids);

    /**
     * 更新标签排序
     */
    void updateWeight(Long id, Integer weight);

    /**
     * 设置标签推荐状态
     */
    void setRecommended(Long id, Boolean recommended);

    /**
     * 获取热门标签
     */
    List<TagVO> getHotTags(int limit);

    /**
     * 获取推荐标签
     */
    List<TagVO> getRecommendedTags(int limit);

    /**
     * 获取相关标签
     */
    List<TagVO> getRelatedTags(Long tagId, int limit);

    /**
     * 获取文章的标签
     */
    List<TagVO> getArticleTags(Long articleId);

    /**
     * 设置文章标签
     */
    void setArticleTags(Long articleId, List<String> tagNames);
} 