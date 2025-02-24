package com.lawfirm.model.cases.constants;

/**
 * 案件消息提示常量
 */
public interface CaseMessageConstants {

    /**
     * 操作成功提示
     */
    interface Success {
        String CREATE = "案件创建成功";
        String UPDATE = "案件更新成功";
        String DELETE = "案件删除成功";
        String ARCHIVE = "案件归档成功";
        String ASSIGN = "案件分配成功";
        String TRANSFER = "案件转办成功";
        String CLOSE = "案件结案成功";
        String REOPEN = "案件重启成功";
        String APPROVE = "案件审批通过";
        String REJECT = "案件审批拒绝";
        String ADD_DOCUMENT = "文档添加成功";
        String ADD_PARTICIPANT = "参与方添加成功";
        String ADD_TEAM_MEMBER = "团队成员添加成功";
        String ADD_REMINDER = "提醒事项添加成功";
    }

    /**
     * 操作失败提示
     */
    interface Failure {
        String CREATE = "案件创建失败";
        String UPDATE = "案件更新失败";
        String DELETE = "案件删除失败";
        String ARCHIVE = "案件归档失败";
        String ASSIGN = "案件分配失败";
        String TRANSFER = "案件转办失败";
        String CLOSE = "案件结案失败";
        String REOPEN = "案件重启失败";
        String APPROVE = "案件审批失败";
        String REJECT = "案件审批拒绝失败";
        String ADD_DOCUMENT = "文档添加失败";
        String ADD_PARTICIPANT = "参与方添加失败";
        String ADD_TEAM_MEMBER = "团队成员添加失败";
        String ADD_REMINDER = "提醒事项添加失败";
    }

    /**
     * 状态变更提示
     */
    interface StatusChange {
        String TO_PENDING = "案件已设置为待立案状态";
        String TO_APPROVING = "案件已提交立案审批";
        String TO_FILED = "案件已立案";
        String TO_IN_PROGRESS = "案件已开始处理";
        String TO_IN_TRIAL = "案件已进入审判阶段";
        String TO_PENDING_CLOSE = "案件已提交结案申请";
        String TO_CLOSED = "案件已结案";
        String TO_ARCHIVED = "案件已归档";
        String TO_CANCELLED = "案件已取消";
    }

    /**
     * 权限相关提示
     */
    interface Permission {
        String NO_PERMISSION = "您没有权限执行此操作";
        String STATUS_NOT_ALLOWED = "当前状态不允许此操作";
        String ROLE_NOT_ALLOWED = "当前角色不允许此操作";
        String NEED_APPROVAL = "此操作需要审批";
    }

    /**
     * 业务规则提示
     */
    interface BusinessRule {
        String CASE_NOT_FOUND = "案件不存在";
        String CASE_NUMBER_EXISTS = "案件编号已存在";
        String INVALID_CASE_NUMBER = "案件编号格式不正确";
        String INVALID_STATUS_CHANGE = "不允许的状态变更";
        String REQUIRED_FIELD_MISSING = "必填字段缺失";
        String INVALID_AMOUNT = "金额格式不正确";
        String INVALID_DATE = "日期格式不正确";
        String FILE_TOO_LARGE = "文件大小超过限制";
        String INVALID_FILE_TYPE = "不支持的文件类型";
        String TEAM_MEMBER_EXISTS = "团队成员已存在";
        String PARTICIPANT_EXISTS = "参与方已存在";
        String CONFLICT_CHECK_FAILED = "利益冲突检查未通过";
    }

    /**
     * 通知提示
     */
    interface Notification {
        String CASE_ASSIGNED = "您有新的案件待处理";
        String CASE_APPROVED = "您的案件已审批通过";
        String CASE_REJECTED = "您的案件审批未通过";
        String DOCUMENT_ADDED = "案件有新的文档添加";
        String REMINDER_DUE = "案件提醒事项即将到期";
        String STATUS_CHANGED = "案件状态已更新";
        String TEAM_CHANGED = "案件团队成员已变更";
        String PARTICIPANT_CHANGED = "案件参与方信息已变更";
    }
} 