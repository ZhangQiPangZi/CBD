package com.cbd.installerapp.service.waitingtask;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.RemoveOrderService;
import com.cbd.cbdcommoninterface.request.installerapp.waitingtask.RemoveQuery;
import com.cbd.installerapp.dao.waitingtask.InstallOrderDao;
import com.cbd.installerapp.dao.waitingtask.RemoveOrderDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Monster
 * @date: 2020/4/22 11:15
 * @Description
 */
public class RemoveOrderServiceImpl implements RemoveOrderService {
    @Autowired
    private RemoveOrderDao removeOrderDao;
    @Autowired
    private InstallOrderDao installOrderDao;

    @Override
    public int removeDev(RemoveQuery query){
        return removeOrderDao.removeDev(query);
    }

    @Override
    public int removeOrderComplete(Integer orderId){
        return installOrderDao.complete(orderId);
    }
}
