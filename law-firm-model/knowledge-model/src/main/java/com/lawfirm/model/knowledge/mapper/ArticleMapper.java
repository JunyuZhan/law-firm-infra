package com.lawfirm.model.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.knowledge.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章数据访问层
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
} 