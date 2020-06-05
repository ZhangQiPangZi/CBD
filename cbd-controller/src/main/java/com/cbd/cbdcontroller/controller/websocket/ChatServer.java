package com.cbd.cbdcontroller.controller.websocket;


import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.cbd_interface.MQ.MQSender;
import com.cbd.cbdcommoninterface.cbd_interface.message.MessageService;
import com.cbd.cbdcommoninterface.cbd_interface.salesapp.messagelist.MessageListService;
import com.cbd.cbdcommoninterface.enums.ReadFlagEnum;
import com.cbd.cbdcommoninterface.utils.BusinessMessage;
import com.cbd.cbdcommoninterface.utils.BusinessType;
import com.cbd.cbdcommoninterface.utils.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 聊天服务端
 *
 * @see ServerEndpoint WebSocket服务端 需指定端点的访问路径
 * @see Session   WebSocket会话对象 通过它给客户端发送消息
 */
@Component
@Slf4j
@ServerEndpoint("/chat/{userId}")
public class ChatServer {

    @Autowired
    private static MQSender mqSender;
    @Autowired
    private static MessageService messageService;

    /**
     * 全部在线会话 基于场景考虑 使用线程安全的Map存储会话对象。
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();


    /**
     * 客户端打开连接
     */
    @OnOpen
    public void onOpen(@PathParam("userId")String userId, Session session) {
        log.info("connent userId:{}",userId);
        onlineSessions.put(userId, session);

    }

    /**
     * 客户端发送消息
     * PS: 这里约定传递的消息为JSON字符串 方便传递更多参数！
     */
    @OnMessage
    public void onMessage(String jsonStr) {
        log.info("get new message: {}",jsonStr);
        ChatMessage message = JSON.parseObject(jsonStr, ChatMessage.class);

        //发送信息到客户端
        sendMessage(jsonStr,message.getReceiverId());
    }

    /**
     * 当关闭连接：1.移除会话对象 2.更新在线人数
     */
    @OnClose
    public void onClose(@PathParam("userId")String userId) {
        onlineSessions.remove(userId);
    }

    /**
     * 当通信发生异常：打印错误日志
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("通信异常!{}",error);
    }

    /**
     * 发送信息
     */
    public static void sendMessage(String msg,String receiverId) {
        ChatMessage message = JSON.parseObject(msg, ChatMessage.class);

        Integer readFlag = ReadFlagEnum.UNREAD.ordinal();
        try {
            //如果用户在线,直接推送消息
            if(onlineSessions.containsKey(receiverId)){
                //保存消息
                //为空代表不是业务消息，是客服聊天
                if (StringUtils.isEmpty(message.getMesID())){
                    //这边已读未读只针对于客服聊天，业务消息在手动确认前一直是未确认状态，即未读
                    readFlag = ReadFlagEnum.READ.ordinal();
                }

                messageService.saveMessage(message.getSenderId(), message.getReceiverId(), message.getMsgType(), message.getContent(), readFlag);

                //同步推送
                onlineSessions.get(receiverId).getBasicRemote().sendText(message.getContent());
            }else {
                //异步保存离线消息
                BusinessMessage businessMessage = new BusinessMessage(BusinessType.SAVE_MESSAGE_QUEUE, msg);
                mqSender.send(businessMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
