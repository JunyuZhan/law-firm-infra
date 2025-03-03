package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 案件阶段枚举
 * 
 * 注意：案件阶段(Stage)表示案件处理的主要法律程序阶段，如立案前、一审、二审等。
 * 而案件进展(Progress)表示在各阶段内的具体进展状态，更加细化和具体。
 * 一个阶段通常包含多个进展状态。
 */
@Getter
public enum CaseStageEnum implements BaseEnum<Integer> {

    /**
     * 立案前
     */
    PRE_FILING(1, "立案前"),

    /**
     * 一审
     */
    FIRST_INSTANCE(2, "一审"),

    /**
     * 二审
     */
    SECOND_INSTANCE(3, "二审"),

    /**
     * 再审
     */
    RETRIAL(4, "再审"),

    /**
     * 执行
     */
    ENFORCEMENT(5, "执行"),

    /**
     * 仲裁
     */
    ARBITRATION(6, "仲裁"),

    /**
     * 调解
     */
    MEDIATION(7, "调解"),

    /**
     * 和解
     */
    SETTLEMENT(8, "和解");

    private final Integer value;
    private final String description;

    CaseStageEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 枚举值
     * @return 对应的枚举实例，如果未找到则返回null
     */
    public static CaseStageEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseStageEnum stage : values()) {
            if (stage.value.equals(value)) {
                return stage;
            }
        }
        return null;
    }

    /**
     * 根据进展状态获取对应的案件阶段
     *
     * @param progress 案件进展状态
     * @return 对应的案件阶段
     */
    public static CaseStageEnum getStageFromProgress(CaseProgressEnum progress) {
        if (progress == null) {
            return null;
        }

        // 根据进展枚举的值范围判断所属阶段
        int progressValue = progress.getValue();
        
        if (progressValue >= 10 && progressValue < 40) {
            // 10-39: 初始阶段、证据准备阶段、文书准备阶段都属于立案前
            return PRE_FILING;
        } else if (progressValue >= 40 && progressValue < 50) {
            // 40-49: 立案阶段
            if (progress == CaseProgressEnum.ARBITRATION_FILING) {
                return ARBITRATION;
            }
            return FIRST_INSTANCE;
        } else if (progressValue >= 50 && progressValue < 60) {
            // 50-59: 庭前准备阶段
            return FIRST_INSTANCE;
        } else if (progressValue >= 60 && progressValue < 70) {
            // 60-69: 审判阶段
            // 根据具体进展判断是一审、二审还是再审
            if (progress == CaseProgressEnum.APPEAL_PREPARATION || 
                progress == CaseProgressEnum.APPEAL_FILED) {
                return SECOND_INSTANCE;
            }
            return FIRST_INSTANCE;
        } else if (progressValue >= 70 && progressValue < 80) {
            // 70-79: 执行阶段
            if (progress == CaseProgressEnum.SETTLEMENT_NEGOTIATION) {
                return SETTLEMENT;
            } else if (progress == CaseProgressEnum.ARBITRATION_REVOCATION) {
                return ARBITRATION;
            }
            return ENFORCEMENT;
        } else if (progressValue >= 80) {
            // 80-99: 结案阶段和终结阶段
            return FIRST_INSTANCE; // 默认归为一审结案
        }
        
        // 默认返回立案前阶段
        return PRE_FILING;
    }

    /**
     * 获取指定阶段下的所有可能进展状态
     *
     * @param stage 案件阶段
     * @return 该阶段下的所有进展状态列表
     */
    public static List<CaseProgressEnum> getProgressListByStage(CaseStageEnum stage) {
        if (stage == null) {
            return Collections.emptyList();
        }

        // 获取所有进展枚举
        CaseProgressEnum[] allProgress = CaseProgressEnum.values();
        
        // 根据阶段筛选对应的进展状态
        switch (stage) {
            case PRE_FILING:
                // 返回值在10-39范围内的进展
                return Arrays.stream(allProgress)
                        .filter(p -> p.getValue() >= 10 && p.getValue() < 40)
                        .collect(Collectors.toList());
            case FIRST_INSTANCE:
                // 返回一审相关的进展
                return Arrays.stream(allProgress)
                        .filter(p -> (p.getValue() >= 40 && p.getValue() < 70 && 
                                p != CaseProgressEnum.ARBITRATION_FILING &&
                                p != CaseProgressEnum.APPEAL_PREPARATION &&
                                p != CaseProgressEnum.APPEAL_FILED) ||
                                (p.getValue() >= 80))
                        .collect(Collectors.toList());
            case SECOND_INSTANCE:
                // 返回二审相关的进展
                return Arrays.stream(allProgress)
                        .filter(p -> p == CaseProgressEnum.APPEAL_PREPARATION ||
                                p == CaseProgressEnum.APPEAL_FILED)
                        .collect(Collectors.toList());
            case RETRIAL:
                // 返回再审相关的进展（当前枚举中可能没有明确的再审进展）
                return Collections.emptyList();
            case ENFORCEMENT:
                // 返回执行相关的进展
                return Arrays.stream(allProgress)
                        .filter(p -> p.getValue() >= 70 && p.getValue() < 80 &&
                                p != CaseProgressEnum.SETTLEMENT_NEGOTIATION &&
                                p != CaseProgressEnum.ARBITRATION_REVOCATION)
                        .collect(Collectors.toList());
            case ARBITRATION:
                // 返回仲裁相关的进展
                return Arrays.stream(allProgress)
                        .filter(p -> p == CaseProgressEnum.ARBITRATION_FILING ||
                                p == CaseProgressEnum.ARBITRATION_APPLICATION_DRAFTING ||
                                p == CaseProgressEnum.ARBITRATION_REVOCATION)
                        .collect(Collectors.toList());
            case MEDIATION:
                // 返回调解相关的进展（当前枚举中可能没有明确的调解进展）
                return Collections.emptyList();
            case SETTLEMENT:
                // 返回和解相关的进展
                return Arrays.stream(allProgress)
                        .filter(p -> p == CaseProgressEnum.SETTLEMENT_NEGOTIATION)
                        .collect(Collectors.toList());
            default:
                return Collections.emptyList();
        }
    }

    /**
     * 判断给定的进展状态是否属于当前阶段
     *
     * @param stage 案件阶段
     * @param progress 案件进展状态
     * @return 如果进展状态属于该阶段，则返回true；否则返回false
     */
    public static boolean isProgressInStage(CaseStageEnum stage, CaseProgressEnum progress) {
        if (stage == null || progress == null) {
            return false;
        }
        
        CaseStageEnum progressStage = getStageFromProgress(progress);
        return stage.equals(progressStage);
    }

    /**
     * 获取下一个阶段
     *
     * @param currentStage 当前阶段
     * @param caseType 案件类型
     * @return 下一个阶段，如果没有下一个阶段则返回null
     */
    public static CaseStageEnum getNextStage(CaseStageEnum currentStage, CaseTypeEnum caseType) {
        if (currentStage == null || caseType == null) {
            return null;
        }
        
        switch (currentStage) {
            case PRE_FILING:
                // 立案前阶段后通常是一审
                return FIRST_INSTANCE;
            case FIRST_INSTANCE:
                // 一审后通常是二审
                return SECOND_INSTANCE;
            case SECOND_INSTANCE:
                // 二审后可能是执行或再审
                return ENFORCEMENT;
            case RETRIAL:
                // 再审后通常是执行
                return ENFORCEMENT;
            case ENFORCEMENT:
                // 执行是最后阶段
                return null;
            case ARBITRATION:
                // 仲裁后可能是执行
                return ENFORCEMENT;
            case MEDIATION:
            case SETTLEMENT:
                // 调解或和解后，如果成功则可能进入执行阶段
                return ENFORCEMENT;
            default:
                return null;
        }
    }
} 