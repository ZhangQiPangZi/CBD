package com.cbd.salesapp.service.messagelist;

import com.cbd.cbdcommoninterface.cbd_interface.salesapp.messagelist.MessageListService;
import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.DevIdDO;
import com.cbd.cbdcommoninterface.pojo.salesapp.messagelist.OrderInfoDO;
import com.cbd.cbdcommoninterface.request.PageRequest;
import com.cbd.cbdcommoninterface.response.PageResponse;
import com.cbd.cbdcommoninterface.response.salesapp.messagelist.OrderInfoVO;
import com.cbd.cbdcommoninterface.utils.PageUtils;
import com.cbd.salesapp.dao.messagelist.MessageListDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public PageResponse getList(Integer orderTypeCode, PageRequest pageRequest){
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        List<OrderInfoDO> result = messageListDao.getList(orderTypeCode);
        PageInfo<OrderInfoDO> resultList = new PageInfo<>(result);
        return PageUtils.getPageResponse(resultList);
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
    public DevIdDO reAssign(Integer id, Integer orderTypeCode){
        DevIdDO devIdDO = new DevIdDO();
        devIdDO.setFlag(-1);

        if(orderTypeCode!=3){
            devIdDO = messageListDao.getDevId(id);
            devIdDO.setFlag(1);
            messageListDao.deleteDevId(id);
        }
        messageListDao.reAssign(id);
        messageListDao.removeFromTaskList(id);

        return devIdDO;
    }

    @Override
    public String getUUID(Integer id) {
        return messageListDao.getUUID(id);
    }
}
