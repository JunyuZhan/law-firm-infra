package com.lawfirm.staff.controller.common;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.staff.model.request.common.message.MessagePageRequest;
import com.lawfirm.staff.model.request.common.message.MessageSendRequest;
import com.lawfirm.staff.model.response.common.message.MessageResponse;
import com.lawfirm.staff.model.response.common.message.UnreadCountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "消息管理")
@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    @Operation(summary = "分页查询消息")
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('message:query')")
    public Result<PageResult<MessageResponse>> page(MessagePageRequest request) {
        // TODO: 调用消息服务
        return Result.ok();
    }

    @Operation(summary = "发送消息")
    @PostMapping("/send")
    @PreAuthorize("hasAuthority('message:send')")
    @OperationLog(value = "发送消息")
    public Result<Void> send(@RequestBody @Validated MessageSendRequest request) {
        // TODO: 调用消息服务
        return Result.ok();
    }

    @Operation(summary = "获取消息详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('message:query')")
    public Result<MessageResponse> get(@PathVariable Long id) {
        // TODO: 调用消息服务
        return Result.ok();
    }

    @Operation(summary = "标记消息已读")
    @PutMapping("/read/{id}")
    @PreAuthorize("hasAuthority('message:read')")
    @OperationLog(value = "标记消息已读")
    public Result<Void> read(@PathVariable Long id) {
        // TODO: 调用消息服务
        return Result.ok();
    }

    @Operation(summary = "标记全部已读")
    @PutMapping("/read/all")
    @PreAuthorize("hasAuthority('message:read')")
    @OperationLog(value = "标记全部已读")
    public Result<Void> readAll() {
        // TODO: 调用消息服务
        return Result.ok();
    }

    @Operation(summary = "删除消息")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('message:delete')")
    @OperationLog(value = "删除消息")
    public Result<Void> delete(@PathVariable Long id) {
        // TODO: 调用消息服务
        return Result.ok();
    }

    @Operation(summary = "获取未读消息数")
    @GetMapping("/unread/count")
    @PreAuthorize("hasAuthority('message:query')")
    public Result<UnreadCountResponse> getUnreadCount() {
        // TODO: 调用消息服务
        return Result.ok();
    }

    @Operation(summary = "获取我的消息")
    @GetMapping("/my")
    @PreAuthorize("hasAuthority('message:query')")
    public Result<PageResult<MessageResponse>> getMyMessages(MessagePageRequest request) {
        // TODO: 调用消息服务，只查询当前登录用户的消息
        return Result.ok();
    }
} 