package com.lawfirm.common.data.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis Plus 字段自动填充处理器
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "delFlag", () -> 0, Integer.class);
        // TODO: 从上下文中获取当前用户
        this.strictInsertFill(metaObject, "createBy", () -> "system", String.class);
        this.strictInsertFill(metaObject, "updateBy", () -> "system", String.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
        // TODO: 从上下文中获取当前用户
        this.strictUpdateFill(metaObject, "updateBy", () -> "system", String.class);
    }
} 