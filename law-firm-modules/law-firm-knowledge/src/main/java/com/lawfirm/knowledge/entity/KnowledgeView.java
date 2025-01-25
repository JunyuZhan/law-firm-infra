package com.lawfirm.knowledge.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 知识浏览记录实体类
 */
@Data
@TableName("knowledge_view")
public class KnowledgeView {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 知识ID
     */
    private Long knowledgeId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 浏览时间
     */
    private LocalDateTime viewTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;
} 