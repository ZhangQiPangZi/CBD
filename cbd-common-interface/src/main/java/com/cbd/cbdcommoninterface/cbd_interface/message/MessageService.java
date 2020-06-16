package com.cbd.cbdcommoninterface.cbd_interface.message;

import com.cbd.cbdcommoninterface.request.PageMsgRequest;
import com.cbd.cbdcommoninterface.response.ChatAlloteResponse;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.utils.ChatMessage;

import java.util.List;

public interface MessageService {
    /**
     * 保存消息
     * @param chatMessage
     */
    void saveMessage(ChatMessage chatMessage);

    /**
     * 拉取未确认消息条数,业务消息
     * @return
     */
    Integer pullUnConfirmMsgCounts(String receiverID);

    /**
     * 获取所有发送消息且客服未读的客户列表，
     * 一。客服上线拉取
     * 二。websockt监听调用
     * @return
     */
    PageResponse getUnreadCustomerList(PageMsgRequest pageMsgRequest);

    /**
     * 拉取未读消息列表，客服聊天消息
     * @param senderID
     * @param receiverID
     * @return
     */
    List<String> pullUnreadMsgList(String senderID, String receiverID);

    /**
     * 拉取未读消息列表，业务消息
     * @param pageMsgRequest
     * @return
     */
    PageResponse pullUnreadBussinessMsgList(PageMsgRequest pageMsgRequest);

    /**
     * 拉取历史消息列表，业务消息
     * @param pageMsgRequest
     * @return
     */
    PageResponse pullReadBussinessMsgList(PageMsgRequest pageMsgRequest);

    /**
     * 更改消息状态
     * @param mesID
     * @param readFlag
     */
    void updateMsgStatus(String mesID, Integer readFlag);

    /**
     * 确认消息
     * @param mesID
     * @return
     */
    Boolean confirmMessage(String mesID);

    /**
     * 获取专属客服
     * @param senderID
     * @return
     */
    ChatAlloteResponse alloteService(String senderID);

    /**
     * 拉取聊天历史消息
     * @param senderID
     * @param receiverID
     * @return
     */
    List<ChatMessage> pullHistoryMsg(String senderID, String receiverID);

    String judgeHasRecord(String id);

    /**
     * 更新消息内容
     * @param returnRecordID
     * @param msg
     */
    void updateMessage(String returnRecordID, String msg);

    /**
     * 获取消息详细信息
     * @param returnRecordID
     * @return
     */
    ChatMessage getMsgInfo(String returnRecordID);

    /**
     * 获取当前消息表中未读的待返厂设备消息ID
     * @param id
     * @return
     */
    String judgeHasReturnRecord(String id);
}
