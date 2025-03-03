package com.lawfirm.model.cases.constants;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 案件状态相关常量
 */
public interface CaseStatusConstants extends BaseConstants {

    /**
     * 状态码定义
     */
    interface StatusCode {
        /**
         * 待立案
         */
        int PENDING = 10;

        /**
         * 立案审批中
         */
        int APPROVING = 20;

        /**
         * 已立案
         */
        int FILED = 30;

        /**
         * 进行中
         */
        int IN_PROGRESS = 40;

        /**
         * 审判中
         */
        int IN_TRIAL = 50;

        /**
         * 待结案
         */
        int PENDING_CLOSE = 60;

        /**
         * 已结案
         */
        int CLOSED = 70;

        /**
         * 已归档
         */
        int ARCHIVED = 80;

        /**
         * 已取消
         */
        int CANCELLED = 90;
    }

    /**
     * 状态流转规则
     */
    interface StatusFlow {
        /**
         * 允许的状态流转
         * key: 当前状态
         * value: 允许流转到的状态数组
         */
        int[][] ALLOWED_TRANSITIONS = {
            {StatusCode.PENDING, StatusCode.APPROVING, StatusCode.CANCELLED},
            {StatusCode.APPROVING, StatusCode.FILED, StatusCode.PENDING, StatusCode.CANCELLED},
            {StatusCode.FILED, StatusCode.IN_PROGRESS, StatusCode.CANCELLED},
            {StatusCode.IN_PROGRESS, StatusCode.IN_TRIAL, StatusCode.PENDING_CLOSE, StatusCode.CANCELLED},
            {StatusCode.IN_TRIAL, StatusCode.IN_PROGRESS, StatusCode.PENDING_CLOSE, StatusCode.CANCELLED},
            {StatusCode.PENDING_CLOSE, StatusCode.CLOSED, StatusCode.IN_PROGRESS, StatusCode.CANCELLED},
            {StatusCode.CLOSED, StatusCode.ARCHIVED},
            {StatusCode.ARCHIVED}
        };
    }

    /**
     * 状态权限控制
     */
    interface StatusPermission {
        /**
         * 需要审批的状态变更
         */
        int[] NEED_APPROVAL = {
            StatusCode.PENDING,
            StatusCode.APPROVING,
            StatusCode.PENDING_CLOSE,
            StatusCode.CLOSED
        };

        /**
         * 允许编辑的状态
         */
        int[] EDITABLE = {
            StatusCode.PENDING,
            StatusCode.APPROVING,
            StatusCode.FILED,
            StatusCode.IN_PROGRESS,
            StatusCode.IN_TRIAL,
            StatusCode.PENDING_CLOSE
        };

        /**
         * 允许删除的状态
         */
        int[] DELETABLE = {
            StatusCode.PENDING,
            StatusCode.CANCELLED
        };
    }

    /**
     * 状态操作限制
     */
    interface StatusOperation {
        /**
         * 允许添加文档的状态
         */
        int[] ALLOW_ADD_DOCUMENT = {
            StatusCode.PENDING,
            StatusCode.FILED,
            StatusCode.IN_PROGRESS,
            StatusCode.IN_TRIAL,
            StatusCode.PENDING_CLOSE
        };

        /**
         * 允许修改团队的状态
         */
        int[] ALLOW_MODIFY_TEAM = {
            StatusCode.PENDING,
            StatusCode.FILED,
            StatusCode.IN_PROGRESS,
            StatusCode.IN_TRIAL
        };

        /**
         * 允许修改参与方的状态
         */
        int[] ALLOW_MODIFY_PARTICIPANT = {
            StatusCode.PENDING,
            StatusCode.FILED,
            StatusCode.IN_PROGRESS
        };
    }

    // 定义特定于案件的状态常量
    String STATUS_PENDING = "待处理";
} 