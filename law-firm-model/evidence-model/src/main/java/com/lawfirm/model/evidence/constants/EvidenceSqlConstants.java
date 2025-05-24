package com.lawfirm.model.evidence.constants;

/**
 * 证据模块SQL常量类
 * 集中管理证据相关SQL查询语句、表名、字段名，便于维护和复用
 */
public final class EvidenceSqlConstants {
    private EvidenceSqlConstants() {}

    /**
     * 证据表相关SQL
     */
    public static class Evidence {
        public static final String TABLE_NAME = "evidence";
        public static final String FIELD_ID = "id";
        public static final String FIELD_CASE_ID = "case_id";
        public static final String FIELD_NAME = "name";
        public static final String FIELD_TYPE = "type";
        public static final String FIELD_SOURCE = "source";
        public static final String FIELD_PROOF_MATTER = "proof_matter";
        public static final String FIELD_SUBMITTER_ID = "submitter_id";
        public static final String FIELD_SUBMIT_TIME = "submit_time";
        public static final String FIELD_EVIDENCE_CHAIN = "evidence_chain";
        public static final String FIELD_CHALLENGE_STATUS = "challenge_status";
        /** 根据案件ID查询证据 */
        public static final String SELECT_BY_CASE_ID =
                "SELECT * FROM evidence WHERE case_id = #{caseId} AND deleted = 0";
        /** 根据证据ID查询 */
        public static final String SELECT_BY_ID =
                "SELECT * FROM evidence WHERE id = #{id} AND deleted = 0";
    }

    /**
     * 证据标签相关SQL
     */
    public static class Tag {
        public static final String TABLE_NAME = "evidence_tag";
        public static final String FIELD_ID = "id";
        public static final String FIELD_NAME = "name";
        public static final String FIELD_DESCRIPTION = "description";
        /** 查询所有标签 */
        public static final String SELECT_ALL =
                "SELECT * FROM evidence_tag WHERE deleted = 0";
    }

    /**
     * 证据-标签关联表相关SQL
     */
    public static class TagRelation {
        public static final String TABLE_NAME = "evidence_tag_relation";
        public static final String FIELD_ID = "id";
        public static final String FIELD_EVIDENCE_ID = "evidence_id";
        public static final String FIELD_TAG_ID = "tag_id";
        /** 查询证据的所有标签 */
        public static final String SELECT_TAGS_BY_EVIDENCE_ID =
                "SELECT t.* FROM evidence_tag t INNER JOIN evidence_tag_relation r ON t.id = r.tag_id WHERE r.evidence_id = #{evidenceId} AND t.deleted = 0 AND r.deleted = 0";
    }

    /**
     * 证据质证过程相关SQL
     */
    public static class Challenge {
        public static final String TABLE_NAME = "evidence_challenge";
        public static final String FIELD_ID = "id";
        public static final String FIELD_EVIDENCE_ID = "evidence_id";
        public static final String FIELD_CHALLENGER_ID = "challenger_id";
        public static final String FIELD_CHALLENGER_NAME = "challenger_name";
        public static final String FIELD_CHALLENGE_TIME = "challenge_time";
        public static final String FIELD_OPINION = "opinion";
        public static final String FIELD_CONCLUSION = "conclusion";
        public static final String FIELD_ATTACHMENT_ID = "attachment_id";
        /** 查询证据的所有质证记录 */
        public static final String SELECT_BY_EVIDENCE_ID =
                "SELECT * FROM evidence_challenge WHERE evidence_id = #{evidenceId} AND deleted = 0 ORDER BY challenge_time DESC";
    }

    /**
     * 证据流转/溯源相关SQL
     */
    public static class Trace {
        public static final String TABLE_NAME = "evidence_trace";
        public static final String FIELD_ID = "id";
        public static final String FIELD_EVIDENCE_ID = "evidence_id";
        public static final String FIELD_OPERATION_TYPE = "operation_type";
        public static final String FIELD_OPERATOR_ID = "operator_id";
        public static final String FIELD_OPERATOR_NAME = "operator_name";
        public static final String FIELD_OPERATION_TIME = "operation_time";
        public static final String FIELD_NODE = "node";
        public static final String FIELD_PRESERVATION_NO = "preservation_no";
    }

    /**
     * 证据与事实关联表相关SQL
     */
    public static class FactRelation {
        public static final String TABLE_NAME = "evidence_fact_relation";
        public static final String FIELD_ID = "id";
        public static final String FIELD_EVIDENCE_ID = "evidence_id";
        public static final String FIELD_FACT_ID = "fact_id";
        public static final String FIELD_PROOF_MATTER = "proof_matter";
        public static final String FIELD_PROOF_LEVEL = "proof_level";
    }

    /**
     * 证据附件相关SQL
     */
    public static class Attachment {
        public static final String TABLE_NAME = "evidence_attachment";
        public static final String FIELD_ID = "id";
        public static final String FIELD_EVIDENCE_ID = "evidence_id";
        public static final String FIELD_ATTACHMENT_TYPE = "attachment_type";
        public static final String FIELD_FILE_ID = "file_id";
        public static final String FIELD_DESCRIPTION = "description";
    }

    /**
     * 证据审核相关SQL
     */
    public static class Review {
        public static final String TABLE_NAME = "evidence_review";
        public static final String FIELD_ID = "id";
        public static final String FIELD_EVIDENCE_ID = "evidence_id";
        public static final String FIELD_REVIEWER_ID = "reviewer_id";
        public static final String FIELD_REVIEWER_NAME = "reviewer_name";
        public static final String FIELD_REVIEW_TIME = "review_time";
        public static final String FIELD_REVIEW_OPINION = "review_opinion";
        public static final String FIELD_REVIEW_STATUS = "review_status";
    }

    /**
     * 证据与人员关联表相关SQL
     */
    public static class Personnel {
        public static final String TABLE_NAME = "evidence_personnel";
        public static final String FIELD_ID = "id";
        public static final String FIELD_EVIDENCE_ID = "evidence_id";
        public static final String FIELD_PERSONNEL_ID = "personnel_id";
        public static final String FIELD_ROLE_TYPE = "role_type";
        public static final String FIELD_REMARK = "remark";
    }
} 