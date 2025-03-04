package com.lawfirm.core.message.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.core.message.facade.MessageFacade;
import com.lawfirm.model.message.dto.request.CaseMessageRequest;
import com.lawfirm.model.message.dto.request.NotifyRequest;
import com.lawfirm.model.message.dto.request.SystemAlertRequest;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.entity.base.BaseNotify;
import com.lawfirm.model.message.entity.business.CaseMessage;
import com.lawfirm.model.message.entity.system.SystemMessage;
import com.lawfirm.model.message.enums.NotifyChannelEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 消息控制器
 */
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageFacade messageFacade;

    /**
     * 发送通知
     */
    @PostMapping("/notify")
    @RequiresPermissions("message:notify")
    @Log(title = "消息管理", businessType = "发送通知消息")
    public CommonResult<Void> sendNotify(@Valid @RequestBody NotifyRequest request) {
        BaseNotify notify = new BaseNotify();
        notify.setTitle(request.getTitle());
        notify.setContent(request.getContent());
        messageFacade.sendNotify(notify, request.getReceivers(), NotifyChannelEnum.valueOf(request.getChannel()));
        return CommonResult.success();
    }

    /**
     * 发送案件消息
     */
    @PostMapping("/case")
    @RequiresPermissions("message:case")
    @Log(title = "消息管理", businessType = "发送案件消息")
    public CommonResult<Void> sendCaseMessage(@Valid @RequestBody CaseMessageRequest request) {
        CaseMessage message = new CaseMessage();
        message.setTitle(request.getTitle());
        message.setContent(request.getContent());
        messageFacade.sendCaseMessage(message, request.getCaseId(), request.getReceivers());
        return CommonResult.success();
    }

    /**
     * 发送系统预警
     */
    @PostMapping("/alert")
    @RequiresPermissions("message:alert")
    @Log(title = "消息管理", businessType = "发送系统预警")
    public CommonResult<Void> sendSystemAlert(@Valid @RequestBody SystemAlertRequest request) {
        SystemMessage message = new SystemMessage();
        message.setTitle(request.getTitle());
        message.setContent(request.getContent());
        message.setLevel(Integer.valueOf(request.getLevel()));
        messageFacade.sendSystemMessage(message, Integer.valueOf(request.getType()), request.getReceivers());
        return CommonResult.success();
    }

    /**
     * 批量发送通知
     */
    @PostMapping("/notify/batch")
    @RequiresPermissions("message:notify")
    @Log(title = "消息管理", businessType = "批量发送通知消息")
    public CommonResult<Void> sendBatchNotify(@Valid @RequestBody NotifyRequest request) {
        BaseNotify notify = new BaseNotify();
        notify.setTitle(request.getTitle());
        notify.setContent(request.getContent());
        messageFacade.sendNotify(notify, request.getReceivers(), NotifyChannelEnum.valueOf(request.getChannel()));
        return CommonResult.success();
    }

    /**
     * 获取消息详情
     */
    @GetMapping("/{messageId}")
    @RequiresPermissions("message:view")
    @Log(title = "消息管理", businessType = "查看消息详情")
    public CommonResult<BaseMessage> getMessage(@PathVariable String messageId) {
        return CommonResult.success(messageFacade.getMessage(messageId));
    }

    /**
     * 删除消息
     */
    @DeleteMapping("/{messageId}")
    @RequiresPermissions("message:delete")
    @Log(title = "消息管理", businessType = "删除消息")
    public CommonResult<Void> deleteMessage(@PathVariable String messageId) {
        messageFacade.deleteMessage(messageId);
        return CommonResult.success();
    }
} 