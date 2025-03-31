package com.lawfirm.model.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.client.entity.common.ClientTag;
import com.lawfirm.model.client.constant.ClientSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 客户标签Mapper接口
 */
@Mapper
public interface TagMapper extends BaseMapper<ClientTag> {
    
    /**
     * 获取指定类型的标签
     *
     * @param tagType 标签类型
     * @return 标签列表
     */
    @Select("SELECT * FROM client_tag WHERE tag_type = #{tagType} AND deleted = 0")
    List<ClientTag> selectByType(@Param("tagType") String tagType);
    
    /**
     * 获取客户拥有的标签
     *
     * @param clientId 客户ID
     * @return 标签列表
     */
    @Select(ClientSqlConstants.Tag.SELECT_BY_CLIENT_ID)
    List<ClientTag> selectByClientId(@Param("clientId") Long clientId);
    
    /**
     * 批量获取标签
     *
     * @param ids 标签ID列表
     * @return 标签列表
     */
    @Select("<script>SELECT * FROM client_tag WHERE id IN <foreach collection='ids' item='id' open='(' separator=',' close=')'>#{id}</foreach> AND deleted = 0</script>")
    List<ClientTag> selectByIds(@Param("ids") List<Long> ids);
    
    /**
     * 更新标签状态
     *
     * @param id 标签ID
     * @param status 状态
     * @return 影响行数
     */
    @Update("UPDATE client_tag SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
} 