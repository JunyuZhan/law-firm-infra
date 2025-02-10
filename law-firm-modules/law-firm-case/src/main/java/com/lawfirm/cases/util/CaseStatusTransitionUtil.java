package com.lawfirm.cases.util;

import com.lawfirm.cases.constant.CaseStatusEnum;
import java.util.*;

/**
 * 案件状态流转工具类
 */
public class CaseStatusTransitionUtil {
    
    // 状态流转图(key:当前状态, value:允许流转到的目标状态集合)
    private static final Map<CaseStatusEnum, Set<CaseStatusEnum>> STATUS_TRANSITIONS = new HashMap<>();
    
    static {
        // 已登记 -> 立案中
        STATUS_TRANSITIONS.put(CaseStatusEnum.REGISTERED, 
            new HashSet<>(Collections.singletonList(CaseStatusEnum.FILING)));
            
        // 立案中 -> 已立案/已驳回
        STATUS_TRANSITIONS.put(CaseStatusEnum.FILING, 
            new HashSet<>(Arrays.asList(CaseStatusEnum.FILED, CaseStatusEnum.REJECTED)));
            
        // 已立案 -> 办理中
        STATUS_TRANSITIONS.put(CaseStatusEnum.FILED,
            new HashSet<>(Collections.singletonList(CaseStatusEnum.PROCESSING)));
            
        // 办理中 -> 准备中/调解中/已暂停/已终止/已撤销
        STATUS_TRANSITIONS.put(CaseStatusEnum.PROCESSING,
            new HashSet<>(Arrays.asList(
                CaseStatusEnum.PREPARING,
                CaseStatusEnum.MEDIATION,
                CaseStatusEnum.SUSPENDED,
                CaseStatusEnum.TERMINATED,
                CaseStatusEnum.CANCELLED
            )));
            
        // 准备中 -> 开庭中
        STATUS_TRANSITIONS.put(CaseStatusEnum.PREPARING,
            new HashSet<>(Collections.singletonList(CaseStatusEnum.IN_COURT)));
            
        // 开庭中 -> 已判决/调解中
        STATUS_TRANSITIONS.put(CaseStatusEnum.IN_COURT,
            new HashSet<>(Arrays.asList(CaseStatusEnum.JUDGMENT, CaseStatusEnum.MEDIATION)));
            
        // 调解中 -> 已判决/办理中
        STATUS_TRANSITIONS.put(CaseStatusEnum.MEDIATION,
            new HashSet<>(Arrays.asList(CaseStatusEnum.JUDGMENT, CaseStatusEnum.PROCESSING)));
            
        // 已判决 -> 执行中/结案中
        STATUS_TRANSITIONS.put(CaseStatusEnum.JUDGMENT,
            new HashSet<>(Arrays.asList(CaseStatusEnum.EXECUTING, CaseStatusEnum.CLOSING)));
            
        // 执行中 -> 已执行
        STATUS_TRANSITIONS.put(CaseStatusEnum.EXECUTING,
            new HashSet<>(Collections.singletonList(CaseStatusEnum.EXECUTED)));
            
        // 已执行 -> 结案中
        STATUS_TRANSITIONS.put(CaseStatusEnum.EXECUTED,
            new HashSet<>(Collections.singletonList(CaseStatusEnum.CLOSING)));
            
        // 结案中 -> 已结案
        STATUS_TRANSITIONS.put(CaseStatusEnum.CLOSING,
            new HashSet<>(Collections.singletonList(CaseStatusEnum.CLOSED)));
            
        // 已暂停 -> 办理中
        STATUS_TRANSITIONS.put(CaseStatusEnum.SUSPENDED,
            new HashSet<>(Collections.singletonList(CaseStatusEnum.PROCESSING)));
    }
    
    /**
     * 检查状态流转是否合法
     *
     * @param fromStatus 当前状态
     * @param toStatus 目标状态
     * @return 是否合法
     */
    public static boolean isValidTransition(CaseStatusEnum fromStatus, CaseStatusEnum toStatus) {
        if (fromStatus == null || toStatus == null) {
            return false;
        }
        Set<CaseStatusEnum> allowedStatus = STATUS_TRANSITIONS.get(fromStatus);
        return allowedStatus != null && allowedStatus.contains(toStatus);
    }
    
    /**
     * 获取状态的下一个可选状态集合
     *
     * @param currentStatus 当前状态
     * @return 可选状态集合
     */
    public static Set<CaseStatusEnum> getNextStatus(CaseStatusEnum currentStatus) {
        return STATUS_TRANSITIONS.getOrDefault(currentStatus, Collections.emptySet());
    }
} 