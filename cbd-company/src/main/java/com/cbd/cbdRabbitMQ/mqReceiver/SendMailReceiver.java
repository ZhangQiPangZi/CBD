package com.cbd.cbdRabbitMQ.mqReceiver;

import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.cbd_interface.message.EmailService;
import com.cbd.cbdcommoninterface.pojo.message.MailInfo;
import com.cbd.cbdcommoninterface.result.CodeMsg;
import com.cbd.cbdcommoninterface.result.GlobalException;
import com.cbd.cbdcommoninterface.utils.BusinessType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SendMailReceiver {
    @Autowired
    EmailService emailService;

    @RabbitListener(queues = BusinessType.SEND_MAIL_QUEUE)
    @Transactional(rollbackFor = Exception.class)
    public void sendMail(String message){
        //json格式转化
        message = message.replaceAll("\\\\","");
        MailInfo mailInfo = JSON.parseObject(message, MailInfo.class);

        log.info("开始发送邮件:收信人：{}, 内容：{}",mailInfo.getReceiverName(), mailInfo.getContent());

        try{
            emailService.sendStringEmail(mailInfo.getReceiverName(), mailInfo.getTitle(), mailInfo.getContent());
        }catch (Exception e){
            log.info("发送邮件错误:{}", e.toString());
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
    }
}
