package com.lawfirm.model.message.service;

import com.lawfirm.model.message.dto.notify.NotifyCreateDTO;
import com.lawfirm.model.message.dto.notify.NotifyQueryDTO;
import com.lawfirm.model.message.vo.NotifyVO;

import java.util.List;

/**
 * 通知服务接口
 */
public interface NotifyService {

    /**
     * 创建通知
     *
     * @param createDTO 创建参数
     * @return 通知ID
     */
    Long createNotify(NotifyCreateDTO createDTO);

    /**
     * 发送通知
     *
     * @param id 通知ID
     */
    void sendNotify(Long id);

    /**
     * 重试发送通知
     *
     * @param id 通知ID
     */
    void retrySendNotify(Long id);

    /**
     * 取消发送通知
     *
     * @param id 通知ID
     */
    void cancelNotify(Long id);

    /**
     * 获取通知详情
     *
     * @param id 通知ID
     * @return 通知详情
     */
    NotifyVO getNotify(Long id);

    /**
     * 查询通知列表
     *
     * @param queryDTO 查询参数
     * @return 通知列表
     */
    List<NotifyVO> listNotifies(NotifyQueryDTO queryDTO);

    /**
     * 获取待发送的通知
     *
     * @return 通知列表
     */
    List<NotifyVO> listPendingNotifies();

    /**
     * 获取发送失败的通知
     *
     * @return 通知列表
     */
    List<NotifyVO> listFailedNotifies();

    /**
     * 获取我发送的通知
     *
     * @param senderId 发送者ID
     * @return 通知列表
     */
    List<NotifyVO> listMySentNotifies(Long senderId);

    /**
     * 获取我接收的通知
     *
     * @param receiverId 接收者ID
     * @return 通知列表
     */
    List<NotifyVO> listMyReceivedNotifies(Long receiverId);

    /**
     * 批量删除通知
     *
     * @param ids 通知ID列表
     */
    void deleteNotifies(List<Long> ids);
} 