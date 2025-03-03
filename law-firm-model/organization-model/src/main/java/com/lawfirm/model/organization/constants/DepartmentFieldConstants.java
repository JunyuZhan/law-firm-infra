package com.lawfirm.model.organization.constants;

import com.lawfirm.model.base.constants.BaseConstants;

/**
 * 部门字段常量
 */
public interface DepartmentFieldConstants extends BaseConstants {

    /**
     * 部门编码限制
     */
    interface Code extends BaseConstants.Field {
        /**
         * 业务部门编码前缀
         */
        String BUSINESS_PREFIX = "BD";

        /**
         * 职能部门编码前缀
         */
        String FUNCTIONAL_PREFIX = "FD";

        /**
         * 管理部门编码前缀
         */
        String MANAGEMENT_PREFIX = "MD";

        /**
         * 编码字段
         */
        String FIELD = "code";
    }

    /**
     * 部门名称长度限制
     */
    interface Name extends BaseConstants.Field {
        /**
         * 最小长度
         */
        int MIN_LENGTH = 2;

        /**
         * 最大长度
         */
        int MAX_LENGTH = 64;

        /**
         * 名称字段
         */
        String FIELD = "name";
    }

    /**
     * 部门层级限制
     */
    interface Level extends BaseConstants.Field {
        /**
         * 最大层级深度
         */
        int MAX_DEPTH = 5;

        /**
         * 层级路径分隔符
         */
        String PATH_SEPARATOR = BaseConstants.Symbol.SLASH;

        /**
         * 层级字段
         */
        String FIELD = "level";

        /**
         * 路径字段
         */
        String PATH_FIELD = "path";
    }

    /**
     * 业务部门特有限制
     */
    interface Business extends BaseConstants.Field {
        /**
         * 业务领域最大长度
         */
        int DOMAIN_MAX_LENGTH = 128;

        /**
         * 案件类型最大数量
         */
        int MAX_CASE_TYPES = 10;

        /**
         * 业务领域字段
         */
        String DOMAIN_FIELD = "business_domain";

        /**
         * 案件类型字段
         */
        String CASE_TYPES_FIELD = "case_types";
    }

    /**
     * 职能部门特有限制
     */
    interface Functional extends BaseConstants.Field {
        /**
         * 职能类型最大长度
         */
        int TYPE_MAX_LENGTH = 64;

        /**
         * 服务范围最大长度
         */
        int SCOPE_MAX_LENGTH = 256;

        /**
         * 职能类型字段
         */
        String TYPE_FIELD = "functional_type";

        /**
         * 服务范围字段
         */
        String SCOPE_FIELD = "service_scope";
    }

    /**
     * 团队限制
     */
    interface Team extends BaseConstants.Field {
        /**
         * 团队编码前缀
         */
        String CODE_PREFIX = "TM";

        /**
         * 团队名称最大长度
         */
        int NAME_MAX_LENGTH = 64;

        /**
         * 团队成员最大数量
         */
        int MAX_MEMBERS = 100;

        /**
         * 团队编码字段
         */
        String CODE_FIELD = "team_code";

        /**
         * 团队名称字段
         */
        String NAME_FIELD = "team_name";

        /**
         * 团队成员字段
         */
        String MEMBERS_FIELD = "team_members";
    }
} 