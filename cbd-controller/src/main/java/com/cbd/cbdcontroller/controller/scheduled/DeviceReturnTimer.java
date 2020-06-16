package com.cbd.cbdcontroller.controller.scheduled;

import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.cbd_interface.MQ.MQSender;
import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.cbd_interface.message.MessageService;
import com.cbd.cbdcommoninterface.utils.BusinessMessage;
import com.cbd.cbdcommoninterface.utils.BusinessType;
import com.cbd.cbdcommoninterface.utils.ChatMessage;
import com.cbd.cbdcommoninterface.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于查找是否有需要返厂的设备，有的话通知库管
 */
@Configuration
@EnableScheduling
public class DeviceReturnTimer {
    @Autowired
    DeviceService deviceService;
    @Autowired
    MQSender mqSender;
    @Autowired
    MessageService messageService;

    @Scheduled(cron = "0 0 8-18/1 * * ?")
    //@Scheduled(cron = "0/5 * * * * ?")
    private void findReturnDevice(){
        //查找所有库管ID
        List<String> managerIDList = deviceService.getAllDevManagerIDList();

        //查找是否有需要返厂的设备
        for (String id : managerIDList){
            Integer counts = deviceService.getNeedReturnDeviceCounts(id);
            //如果有待返厂设备
            if (counts > 0){
                String msg = "您管理的设备有"+counts+"台需要返厂，点击“立即处理”操作设备返厂，请尽快完成！";
                //如果消息记录里有未读的待返厂设备消息，那么更新那条消息即可
                String returnRecordID = messageService.judgeHasReturnRecord(id);
                ChatMessage chatMessage = new ChatMessage();
                if(!StringUtils.isEmpty(returnRecordID)){
                    messageService.updateMessage(returnRecordID, msg);
                    chatMessage = messageService.getMsgInfo(returnRecordID);
                }else {
                    chatMessage.setSenderId("系统消息");
                    chatMessage.setReceiverId(id);
                    chatMessage.setMesID(UUIDUtils.getUUID());
                    chatMessage.setContent(msg);
                    chatMessage.setMsgType(ChatMessage.MsgType.RETURN.toString());
                }

                BusinessMessage message = new BusinessMessage(BusinessType.CBD_BUSINESS_QUEUE, JSON.toJSON(chatMessage));
                mqSender.send(message);
            }
        }

    }


}
