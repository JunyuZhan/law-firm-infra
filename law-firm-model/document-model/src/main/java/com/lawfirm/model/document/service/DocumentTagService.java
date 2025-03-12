package com.lawfirm.model.document.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.document.dto.tag.TagCreateDTO;
import com.lawfirm.model.document.dto.tag.TagQueryDTO;
import com.lawfirm.model.document.dto.tag.TagUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentTag;
import com.lawfirm.model.document.vo.TagVO;

import java.util.List;

/**
 * 文档标签服务接口
 */
public interface DocumentTagService extends BaseService<DocumentTag> {

    /**
     * 创建标签
     *
     * @param createDTO 创建参数
     * @return 标签ID
     */
    Long createTag(TagCreateDTO createDTO);

    /**
     * 更新标签
     *
     * @param updateDTO 更新参数
     */
    void updateTag(TagUpdateDTO updateDTO);

    /**
     * 删除标签
     *
     * @param id 标签ID
     */
    void deleteTag(Long id);

    /**
     * 获取标签
     *
     * @param id 标签ID
     * @return 标签视图对象
     */
    TagVO getTag(Long id);

    /**
     * 查询标签列表
     *
     * @param queryDTO 查询参数
     * @return 标签视图对象列表
     */
    List<TagVO> listTags(TagQueryDTO queryDTO);

    /**
     * 分页查询标签
     *
     * @param queryDTO 查询参数
     * @return 标签视图对象分页
     */
    Page<TagVO> pageTags(TagQueryDTO queryDTO);
} 