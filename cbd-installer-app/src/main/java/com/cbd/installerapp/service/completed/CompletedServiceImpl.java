package com.cbd.installerapp.service.completed;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.completed.CompletedListService;
import com.cbd.cbdcommoninterface.pojo.installerapp.waitingtask.OrderInfoDO;
import com.cbd.installerapp.dao.completed.CompletedListDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: Monster
 * @date: 2020/4/22 16:09
 * @Description
 */
public class CompletedServiceImpl implements CompletedListService {

    @Autowired
    private CompletedListDao completedListDao;

    @Override
    public List<OrderInfoDO> getList(Integer installerId, Integer orderTypeCode){
        return completedListDao.getList(installerId,orderTypeCode);
    }
}
