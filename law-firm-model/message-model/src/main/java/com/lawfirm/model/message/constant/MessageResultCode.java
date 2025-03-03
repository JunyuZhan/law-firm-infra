package com.lawfirm.model.message.constant;

import lombok.Getter;

/**
 * 消息模块错误码
 * 错误码区间：700-799
 */
@Getter
public enum MessageResultCode {
    
    // 消息发送相关错误码: 700-709
    MESSAGE_SEND_FAILED(700, "消息发送失败"),
    MESSAGE_SEND_TIMEOUT(701, "消息发送超时"),
    MESSAGE_SEND_REJECTED(702, "消息被拒绝发送"),
    
    // 消息接收相关错误码: 710-719
    MESSAGE_RECEIVE_FAILED(710, "消息接收失败"),
    MESSAGE_RECEIVE_TIMEOUT(711, "消息接收超时"),
    MESSAGE_RECEIVE_DUPLICATE(712, "收到重复消息"),
    
    // 消息处理相关错误码: 720-729
    MESSAGE_PROCESS_FAILED(720, "消息处理失败"),
    MESSAGE_PROCESS_TIMEOUT(721, "消息处理超时"),
    MESSAGE_PROCESS_REJECTED(722, "消息处理被拒绝"),
    
    // 消息验证相关错误码: 730-739
    MESSAGE_VALIDATION_FAILED(730, "消息验证失败"),
    MESSAGE_FORMAT_INVALID(731, "消息格式无效"),
    MESSAGE_CONTENT_INVALID(732, "消息内容无效"),
    
    // 消息存储相关错误码: 740-749
    MESSAGE_SAVE_FAILED(740, "消息保存失败"),
    MESSAGE_UPDATE_FAILED(741, "消息更新失败"),
    MESSAGE_DELETE_FAILED(742, "消息删除失败"),
    MESSAGE_NOT_FOUND(743, "消息不存在"),
    
    // 消息路由相关错误码: 750-759
    MESSAGE_ROUTE_FAILED(750, "消息路由失败"),
    MESSAGE_ROUTE_NOT_FOUND(751, "消息路由不存在"),
    MESSAGE_ROUTE_INVALID(752, "消息路由配置无效"),
    
    // 消息重试相关错误码: 760-769
    MESSAGE_RETRY_FAILED(760, "消息重试失败"),
    MESSAGE_RETRY_EXCEEDED(761, "消息重试次数超限"),
    MESSAGE_RETRY_REJECTED(762, "消息重试被拒绝");

    private final int code;
    private final String message;

    MessageResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
} 