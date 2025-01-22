package com.lawfirm.common.util.id;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class IdUtils {
    private static final Snowflake SNOWFLAKE = IdUtil.getSnowflake(1, 1);

    public static long nextId() {
        return SNOWFLAKE.nextId();
    }

    public static String nextIdStr() {
        return SNOWFLAKE.nextIdStr();
    }

    public static String simpleUUID() {
        return IdUtil.simpleUUID();
    }

    public static String fastUUID() {
        return IdUtil.fastUUID();
    }

    public static String objectId() {
        return IdUtil.objectId();
    }
} 