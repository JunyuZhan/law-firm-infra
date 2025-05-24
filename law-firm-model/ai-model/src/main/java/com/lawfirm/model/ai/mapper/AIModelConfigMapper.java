package com.lawfirm.model.ai.mapper;

import com.lawfirm.model.ai.entity.AIModelConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AIModelConfigMapper {
    List<AIModelConfig> selectAll();
    AIModelConfig selectById(@Param("id") Long id);
    void insert(AIModelConfig config);
    void update(AIModelConfig config);
    void delete(@Param("id") Long id);
    AIModelConfig selectDefault();
    void setDefault(@Param("id") Long id);
} 