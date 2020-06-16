package com.cbd.cbdcompany.dao;

import com.cbd.cbdcommoninterface.utils.ChatMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageDao {
    @Insert("insert into messageRecord(mesID, msgType, senderId, receiverId, content, readFlag, timeStamp) " +
            "values(#{mesID}, #{msgType}, #{senderId}, #{receiverId}, #{content}, #{readFlag}, #{timeStamp})")
    void saveMessage(ChatMessage chatMessage);

    ChatMessage findMsgInfo(@Param("mesID") String mesID);

    /**
     * 拉取未确认消息条数,业务消息
     * @return
     */
    @Select("select count(*) from messageRecord where msgType != 'CHAT' and readFlag = 0 and receiverID = #{receiverID}")
    Integer pullUnConfirmMsgCounts(@Param("receiverID")String receiverID);

    @Update("update messageRecord " +
            "set readFlag = #{readFlag} " +
            "where mesID = #{mesID}")
    void updateMsgStatus(@Param("mesID") String mesID, @Param("readFlag") Integer readFlag);

    /**
     * 获取所有向客服发送消息且有未读消息的senderID列表
     * @param receiverID
     * @return
     */
    @Select("select senderID " +
            "from messageRecord " +
            "where receiverID = #{receiverID} and readFlag = 0")
    List<String> getUnreadCustomerList(@Param("receiverID") String receiverID);

    @Select("select count (*) " +
            "from messageRecord " +
            "where senderID = #{senderID} and receiverID = #{receiverID} and readFlag = 0")
    Integer getUnreadChatCounts(@Param("senderID")String senderID, @Param("receiverID")String receiverID);

    List<ChatMessage> pullUnreadMsgList(@Param("senderID")String senderID, @Param("receiverID")String receiverID);

    List<ChatMessage> pullUnreadBussinessMsgList(@Param("receiverID") String receiverID);

    @Select("select serviceID from customerServiceMap where customerID = #{senderID}")
    Integer getChatServiceID(@Param("senderID") String senderID);

    @Insert("insert into customerServiceMap(serviceID, customerID) values(#{serviceID}, #{senderID})")
    void insertChatMap(@Param("serviceID") String serviceID, @Param("senderID") String senderID);

    @Select("select userID from role_user u join role r on u.roleID = r.roleID where r.roleName = '客服'")
    List<Integer> getAllServerID();

    @Select("select count(*) from customerServiceMap where serviceID = #{serviceID}")
    Integer getCustomerCounts(@Param("serviceID") Integer serverID);

    List<ChatMessage> pullHistoryMsg(@Param("senderID") String senderID, @Param("receiverID") String receiverID);

    @Select("select mesID from messageRecord where receiverID = #{receiverID} and msgType = 'RETURN'")
    String getMsgIDByReceiverID(@Param("receiverID") String id);

    @Update("update messageRecord " +
            "set content = #{content}" +
            " where mesID = #{mesID}")
    void updateMessageContent(@Param("mesID")String returnRecordID, @Param("content") String msg);

    List<ChatMessage> pullReadBussinessMsgList(String receiverID);
}
