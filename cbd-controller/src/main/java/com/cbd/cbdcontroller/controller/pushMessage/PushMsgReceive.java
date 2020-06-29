package com.cbd.cbdcontroller.controller.pushMessage;

import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.cbd_interface.MQ.MQSender;
import com.cbd.cbdcommoninterface.cbd_interface.message.MessageService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IUserService;
import com.cbd.cbdcommoninterface.pojo.message.MailInfo;
import com.cbd.cbdcommoninterface.utils.BusinessMessage;
import com.cbd.cbdcommoninterface.utils.BusinessType;
import com.cbd.cbdcommoninterface.utils.ChatMessage;
import com.cbd.cbdcontroller.controller.websocket.ChatServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PushMsgReceive {
    @Autowired
    MessageService messageService;
    @Autowired
    MQSender mqSender;
    @Autowired
    IUserService userService;


    /**
     * 推送业务消息
     * @param message
     */
    @RabbitListener(queues = BusinessType.CBD_BUSINESS_QUEUE)
    public void pushMsg(String message){
        //json格式转化
        message = message.replaceAll("\\\\","");
        ChatMessage chatMessage = JSON.parseObject(message, ChatMessage.class);

        //查出业务消息未读条数
        Integer unReadCounts = messageService.pullUnConfirmMsgCounts(chatMessage.getReceiverId());
        chatMessage.setContent(chatMessage.getContent()+"您还有未读消息"+unReadCounts+"条，请尽快查看并处理！！！");
        log.info("业务消息，内容：{}",chatMessage.getContent());

        //推送消息
        ChatServer.sendMessage(JSON.toJSON(chatMessage).toString(), chatMessage.getReceiverId());

        //发送提醒邮件
        MailInfo mailInfo = new MailInfo();
        //调用人事系统查询用户邮箱
        String userMail = userService.findUserInfoByID(chatMessage.getReceiverId()).getEmail();
        mailInfo.setReceiverName(userMail);
        mailInfo.setTitle("车佰度业务消息提醒");
        mailInfo.setContent("您好，车佰度平台提醒您，"+englishTochinese(chatMessage.getMsgType()));
        BusinessMessage mailMessage = new BusinessMessage(BusinessType.SEND_MAIL_QUEUE, JSON.toJSON(mailInfo));
        mqSender.send(mailMessage);
    }

    private String englishTochinese(String type){
        String res = "";
        if (type.equals(ChatMessage.MsgType.ALLOCATION.toString()) || type.equals(ChatMessage.MsgType.ACCEPT.toString())){
            res = "您有设备调拨消息待查看，请您登录车佰度平台处理，若已处理请忽略此消息，谢谢！";
        }else if (type.equals(ChatMessage.MsgType.RETURN.toString())){
            res = "您有设备返厂消息待查看，请您登录车佰度平台处理，若已处理请忽略此消息，谢谢！";
        }else if (type.equals(ChatMessage.MsgType.DISTRIBUTE_CONTRACT.toString())){
            res = "您有合同派发消息待查看，请您登录车佰度平台处理，若已处理请忽略此消息，谢谢！";
        }else if (type.equals(ChatMessage.MsgType.PAY_CONTRACT.toString())){
            res = "您已成功支付，合同已生效，如需进行设备调拨，请您登录车佰度平台处理，若已处理请忽略此消息，谢谢！";
        }else if (type.equals(ChatMessage.MsgType.CONTRACT_ALLOCATION.toString())){
            res = "有新的业务公司已支付合同，现需您进行设备调拨，请您登录车佰度平台处理，若已处理请忽略此消息，谢谢！";
        }

        return res;
    }

}
