package com.lawfirm.model.cases.enums.admincause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 行政处理三级案由
 */
@Getter
public enum AdminTreatment implements BaseEnum<String> {
    RETURN_ILLEGAL_LAND("责令退还非法占用土地", "011501"),
    RETURN_LAND("责令交还土地", "011502"),
    ORDER_CORRECTION("责令改正", "011503"),
    ORDER_REMEDIAL("责令采取补救措施", "011504"),
    STOP_CONSTRUCTION("责令停止建设", "011505"),
    RESTORE_STATUS("责令恢复原状", "011506"),
    ORDER_DISCLOSURE("责令公开", "011507"),
    ORDER_RECALL("责令召回", "011508"),
    SUSPEND_PRODUCTION("责令暂停生产", "011509"),
    SUSPEND_SALES("责令暂停销售", "011510"),
    SUSPEND_USE("责令暂停使用", "011511"),
    PAID_RECOVERY("有偿收回国有土地使用权", "011512"),
    EXPULSION_DECISION("退学决定", "011513");

    private final String description;
    private final String code;
    private final AdminCause.Second parentCause = AdminCause.Second.ADMINISTRATIVE_TREATMENT;

    AdminTreatment(String description, String code) {
        this.description = description;
        this.code = code;
    }

    @Override
    public String getValue() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 