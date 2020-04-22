package com.cbd.installerapp.service.waitingtask;

import com.cbd.cbdcommoninterface.cbd_interface.installerapp.waitingtask.InstallOrderService;
import com.cbd.installerapp.dao.waitingtask.InstallOrderDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Monster
 * @date: 2020/4/22 16:54
 * @Description
 */
public class InstallOrderServiceImpl implements InstallOrderService {
    @Autowired
    private InstallOrderDao installOrderDao;

    @Override
    public int installOrderComplete(Integer devId,Integer simId,Integer orderId){
        installOrderDao.installOrderComplete(devId,simId,orderId);
        return installOrderDao.complete(orderId);
    }
}
