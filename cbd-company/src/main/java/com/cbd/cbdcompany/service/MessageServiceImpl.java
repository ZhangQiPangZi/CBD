package com.cbd.cbdcompany.service;

import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.cbd_interface.MQ.MQSender;
import com.cbd.cbdcommoninterface.cbd_interface.company.CompanyService;
import com.cbd.cbdcommoninterface.cbd_interface.device.DeviceService;
import com.cbd.cbdcommoninterface.cbd_interface.message.MessageService;
import com.cbd.cbdcommoninterface.cbd_interface.redis.RedisService;
import com.cbd.cbdcommoninterface.cbd_interface.user.IUserService;
import com.cbd.cbdcommoninterface.dto.ChatInfoDto;
import com.cbd.cbdcommoninterface.enums.ReadFlagEnum;
import com.cbd.cbdcommoninterface.keys.ContractAcceptKey;
import com.cbd.cbdcommoninterface.pojo.message.DeviceMessageRecord;
import com.cbd.cbdcommoninterface.request.PageMsgRequest;
import com.cbd.cbdcommoninterface.request.PageRequest;
import com.cbd.cbdcommoninterface.response.ChatAlloteResponse;
import com.cbd.cbdcommoninterface.response.ChatInfoResponse;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.utils.*;
import com.cbd.cbdcompany.dao.MessageDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.*;

@Slf4j
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDao messageDao;
    @Autowired
    DeviceService deviceService;
    @Autowired
    RedisService redisService;
    @Autowired
    IUserService userService;
    @Autowired
    CompanyService companyService;
    @Autowired
    @Qualifier("MQSenderImpl")
    MQSender mqSender;

    @Override
    public void saveMessage(ChatMessage chatMessage) {
        if (getMsgInfo(chatMessage.getMesID()) == null){
            messageDao.saveMessage(chatMessage);
        }
    }

    @Override
    public Integer pullUnConfirmMsgCounts(String receiverID) {
        Integer msgCounts = messageDao.pullUnConfirmMsgCounts(receiverID);
        return msgCounts;
    }


    @Override
    public PageResponse getUnreadCustomerList(PageMsgRequest pageMsgRequest) {
        Page page = PageHelper.startPage(pageMsgRequest.getPageRequest().getPageNum(), pageMsgRequest.getPageRequest().getPageSize());
        //先获取所有向客服发送消息且有未读消息的senderID列表
        List<String> chatSenderIDList = messageDao.getUnreadCustomerList(pageMsgRequest.getReceiverID());

        //封装response
        List<ChatInfoResponse> chatInfoResponseList = new ArrayList<>();
        for (String senderID : chatSenderIDList){
            ChatInfoResponse temp = new ChatInfoResponse();
            temp.setSenderID(senderID);
            temp.setUnReadCounts(messageDao.getUnreadChatCounts(senderID, pageMsgRequest.getReceiverID()));
            //调人事接口，获取名字
            temp.setCustomerName(userService.findUserInfoByID(senderID).getUserName());

            chatInfoResponseList.add(temp);
        }

        PageInfo<ChatInfoResponse> chatInfoResponsePageInfo = new PageInfo<>(chatInfoResponseList);
        chatInfoResponsePageInfo.setPages(page.getPages());
        chatInfoResponsePageInfo.setTotal(page.getTotal());

        return PageUtils.getPageResponse(chatInfoResponsePageInfo);
    }

    @Override
    public List<String> pullUnreadMsgList(String senderID, String receiverID) {
        //查出指定客户发送给客服的未读消息
        List<ChatMessage> chatMessageList = messageDao.pullUnreadMsgList(senderID, receiverID);
        List<String> unReadMsgList = new ArrayList<>();

        for (ChatMessage chatMessage : chatMessageList){
            unReadMsgList.add(chatMessage.getContent());
        }

        return unReadMsgList;
    }

    @Override
    public PageResponse pullUnreadBussinessMsgList(PageMsgRequest pageMsgRequest) {
        Page page = PageHelper.startPage(pageMsgRequest.getPageRequest().getPageNum(), pageMsgRequest.getPageRequest().getPageSize());
        //获取业务未读消息
        List<ChatMessage> chatMessageList = messageDao.pullUnreadBussinessMsgList(pageMsgRequest.getReceiverID());

        PageInfo<ChatMessage> chatMessagePageInfo = new PageInfo<>(chatMessageList);
        chatMessagePageInfo.setPages(page.getPages());
        chatMessagePageInfo.setTotal(page.getTotal());

        return PageUtils.getPageResponse(chatMessagePageInfo);
    }

    @Override
    public PageResponse pullReadBussinessMsgList(PageMsgRequest pageMsgRequest) {
        Page page = PageHelper.startPage(pageMsgRequest.getPageRequest().getPageNum(), pageMsgRequest.getPageRequest().getPageSize());
        //获取业务已读消息
        List<ChatMessage> chatMessageList = messageDao.pullReadBussinessMsgList(pageMsgRequest.getReceiverID());

        PageInfo<ChatMessage> chatMessagePageInfo = new PageInfo<>(chatMessageList);
        chatMessagePageInfo.setPages(page.getPages());
        chatMessagePageInfo.setTotal(page.getTotal());

        return PageUtils.getPageResponse(chatMessagePageInfo);
    }

    @Override
    public void updateMsgStatus(String mesID, Integer readFlag) {
        messageDao.updateMsgStatus(mesID, readFlag);
    }

    @Override
    public Boolean confirmMessage(String mesID) {
        String content = "";
        //先判断是否是业务消息中的设备调拨
        ChatMessage msg = messageDao.findMsgInfo(mesID);
        String mesType = msg.getMsgType();
        log.info("消息类型：{}",mesType);
        //只有设备调拨消息才需要再次推送消息
        if (mesType.equals("ALLOCATION")){
            deviceService.confirmDeviceMessageByMesID(mesID);
            //调人事接口，获取公司名
            String cpyName = companyService.findCompanyInfoByCompanyID(userService.findUserInfoByID(msg.getReceiverId()).getCompanyID()).getCompanyName();
            content = "您向"+cpyName+"调拨的设备已接收，谢谢！！！";
            //推送消息
            ChatMessage chatMessage = new ChatMessage();
            //因为是确认信息，所以发送方和接收方反过来
            chatMessage.setSenderId(msg.getReceiverId());
            chatMessage.setReceiverId(msg.getSenderId());
            chatMessage.setMesID(UUIDUtils.getUUID());
            chatMessage.setContent(content);
            chatMessage.setMsgType(ChatMessage.MsgType.ACCEPT.toString());
            BusinessMessage message = new BusinessMessage(BusinessType.CBD_BUSINESS_QUEUE, JSON.toJSON(chatMessage));
            mqSender.send(message);
        }else if (mesType.equals("CONTRACT_ALLOCATION")){
            // 第一次确认不能直接调拨设备，要等车佰度那边确认之后才可批量调拨设备
            if (!redisService.exists(ContractAcceptKey.CONTRACT_ACCEPT,mesID)){
                //向车佰度管理员推送消息
                DeviceMessageRecord deviceMessageRecord = deviceService.getDevMessageRecord(mesID);
                String cpyName = companyService.findCompanyInfoByCompanyID(userService.findUserInfoByID(msg.getReceiverId()).getCompanyID()).getCompanyName();
                content = mesID+":"+cpyName+"已经签订合同,现需要您调拨"+deviceMessageRecord.getDevName()+"设备"+deviceMessageRecord.getDevNums()+"台，点击“调拨”按钮一键调拨，谢谢！！！";
                //推送消息
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setSenderId("系统消息");
                chatMessage.setReceiverId(msg.getSenderId());
                chatMessage.setMesID(UUIDUtils.getUUID());
                chatMessage.setContent(content);
                chatMessage.setMsgType(ChatMessage.MsgType.ACCEPT.toString());
                BusinessMessage message = new BusinessMessage(BusinessType.CBD_BUSINESS_QUEUE, JSON.toJSON(chatMessage));
                mqSender.send(message);

                redisService.set(ContractAcceptKey.CONTRACT_ACCEPT,mesID,true);
            }else{
                deviceService.confirmDeviceMessageByMesID(mesID);
                redisService.delete(ContractAcceptKey.CONTRACT_ACCEPT,mesID);
            }
        }

        //修改消息记录表中消息状态为已读
        updateMsgStatus(mesID, ReadFlagEnum.READ.ordinal());

        return true;
    }

    @Override
    public ChatAlloteResponse alloteService(String senderID) {
        //先检测是否已分配客服
        String serviceID = messageDao.getChatServiceID(senderID).toString();
        if (StringUtils.isEmpty(serviceID)){
            //分配客服并绑定
            //先获取所有客服ID
            List<Integer>serverIDList = messageDao.getAllServerID();
            //再获取每个客服负责的客户数，利用treeMap排序，负责客户数量最少的客服为本次选中的客服
            Map<Integer, Integer> map = new TreeMap<>();
            for (Integer serverID : serverIDList){
                Integer customerCounts = messageDao.getCustomerCounts(serverID);
                map.put(serverID, customerCounts);
            }
            //由于对value排序，所以
            List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());

            //升序排序
            Collections.sort(list, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

            //记录到客服客户映射表
            messageDao.insertChatMap(serviceID, senderID);

            serviceID = list.get(0).getKey().toString();
        }

        ChatAlloteResponse chatAlloteResponse = new ChatAlloteResponse();
        chatAlloteResponse.setServerID(serviceID);
        //调人事接口，获取姓名
        chatAlloteResponse.setServerName(userService.findUserInfoByID(serviceID).getUserName());
        return chatAlloteResponse;
    }

    @Override
    public List<ChatMessage> pullHistoryMsg(String senderID, String receiverID) {
        //获取所有历史消息
        List<ChatMessage> chatMessageList = messageDao.pullHistoryMsg(senderID, receiverID);
        //根据时间戳排序
        //升序排序
        Collections.sort(chatMessageList, (o1, o2) -> {
            Long o1Stamp = Long.parseLong(o1.getTimeStamp());
            Long o2Stamp = Long.parseLong(o1.getTimeStamp());
            return o1Stamp.compareTo(o2Stamp);
        });

        return chatMessageList;
    }

    @Override
    public String judgeHasRecord(String id) {
        String mesID = messageDao.getMsgIDByReceiverID(id);
        return mesID;
    }

    @Override
    public void updateMessage(String returnRecordID, String msg) {
        log.info("消息ID：{},更新的消息内容：{}",returnRecordID, msg);
        messageDao.updateMessageContent(returnRecordID, msg);
    }

    @Override
    public ChatMessage getMsgInfo(String returnRecordID) {
        return messageDao.findMsgInfo(returnRecordID);
    }

    @Override
    public String judgeHasReturnRecord(String id) {
        String mesID = messageDao.getMsgIDByReceiverID(id);
        return mesID;
    }


}
