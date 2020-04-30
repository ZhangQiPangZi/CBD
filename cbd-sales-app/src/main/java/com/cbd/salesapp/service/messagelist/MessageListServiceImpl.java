package com.cbd.salesapp.service.messagelist;

import com.cbd.cbdcommoninterface.cbd_interface.salesapp.messagelist.MessageListService;
import com.cbd.cbdcommoninterface.pojo.salesapp.messagelist.OrderInfoDO;
import com.cbd.cbdcommoninterface.response.salesapp.messagelist.OrderInfoVO;
import com.cbd.salesapp.dao.messagelist.MessageListDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/21 15:53
 * @Description
 */
public class MessageListServiceImpl implements MessageListService {
    @Autowired
    private MessageListDao messageListDao;

    @Override
    public List<OrderInfoDO> getList(Integer orderTypeCode){
        return messageListDao.getList(orderTypeCode);
    }

    @Override
    public int processedCount(){
        return messageListDao.processedCount();
    }

    @Override
    public int pendingCount(){
        return messageListDao.pendingCount();
    }

    @Override
    public OrderInfoVO getOrderInfo(Integer id){
        return messageListDao.getOrderInfo(id);
    }

    @Override
    public int reAssign(Integer id){
        messageListDao.reAssign(id);
        return messageListDao.removeFromTaskList(id);
    }
}
