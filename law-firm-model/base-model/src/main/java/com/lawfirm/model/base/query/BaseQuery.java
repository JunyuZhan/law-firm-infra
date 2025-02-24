package com.lawfirm.model.base.query;

import com.lawfirm.common.web.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 基础查询对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BaseQuery extends PageRequest {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    @Override
    public String toString() {
        return "BaseQuery{" +
                "keyword='" + keyword + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseQuery baseQuery = (BaseQuery) o;
        return Objects.equals(keyword, baseQuery.keyword) &&
                Objects.equals(startTime, baseQuery.startTime) &&
                Objects.equals(endTime, baseQuery.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), keyword, startTime, endTime);
    }
} 